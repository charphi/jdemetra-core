/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demetra.r;

import demetra.arima.ssf.SsfArima;
import demetra.data.DataBlock;
import demetra.data.DoubleSequence;
import demetra.information.InformationMapping;
import demetra.maths.MatrixType;
import demetra.maths.functions.IParametricMapping;
import demetra.maths.functions.ParamValidation;
import demetra.maths.functions.levmar.LevenbergMarquardtMinimizer;
import demetra.maths.matrices.Matrix;
import demetra.maths.matrices.QuadraticForm;
import demetra.maths.matrices.SymmetricMatrix;
import demetra.r.mapping.DiffuseLikelihoodInfo;
import demetra.sarima.SarimaModel;
import demetra.sarima.SarimaSpecification;
import demetra.descriptors.arima.SarimaDescriptor;
import demetra.likelihood.DiffuseConcentratedLikelihood;
import demetra.ssf.dk.DkToolkit;
import demetra.ssf.dk.SsfFunction;
import demetra.ssf.dk.SsfFunctionPoint;
import demetra.ssf.implementations.RegSsf;
import demetra.ssf.univariate.DefaultSmoothingResults;
import demetra.ssf.univariate.ISsf;
import demetra.ssf.univariate.SsfData;
import demetra.timeseries.TsDomain;
import demetra.timeseries.TsUnit;
import demetra.timeseries.calendars.DayClustering;
import demetra.modelling.regression.GenericTradingDaysVariable;
import demetra.modelling.regression.Regression;
import demetra.timeseries.TsData;
import java.util.LinkedHashMap;
import java.util.Map;
import demetra.processing.ProcResults;
import demetra.sarima.estimation.SarimaMapping;
import demetra.timeseries.calendars.GenericTradingDays;

/**
 *
 * @author Jean Palate <jean.palate@nbb.be>
 */
@lombok.experimental.UtilityClass
public class TimeVaryingRegression {

    @lombok.Data
    static class Airline {

        Matrix td;
        double regVariance;
        double theta, btheta;
    }

    @lombok.Value
    @lombok.Builder
    public static class Results implements ProcResults {

        TsDomain domain;
        MatrixType variables;
        MatrixType coefficients;
        MatrixType coefficientsStde;
        SarimaModel arima0, arima;
        DiffuseConcentratedLikelihood ll0;
        DiffuseConcentratedLikelihood ll;
        double nvar;

        private static final String ARIMA0 = "arima0", LL0 = "likelihood0",
                ARIMA = "arima", LL = "likelihood",
                STDCOEFF = "coefficients.stde", COEFF = "coefficients.value", TD = "td", TDEFFECT = "tdeffect";
        private static final InformationMapping<Results> MAPPING = new InformationMapping<>(Results.class);

        static {
            MAPPING.delegate(ARIMA0, SarimaDescriptor.getMapping(), r -> r.getArima0().toType());
            MAPPING.delegate(LL0, DiffuseLikelihoodInfo.getMapping(), r -> r.getLl0());
            MAPPING.delegate(ARIMA, SarimaDescriptor.getMapping(), r -> r.getArima().toType());
            MAPPING.delegate(LL, DiffuseLikelihoodInfo.getMapping(), r -> r.getLl());
            MAPPING.set("aic0", Double.class, r -> r.getLl0().AIC(2));
            MAPPING.set("aic", Double.class, r -> r.getLl().AIC(3));
            MAPPING.set("tdvar", Double.class, r -> r.getNvar());
            MAPPING.set(COEFF, MatrixType.class, r -> r.getCoefficients());
            MAPPING.set(STDCOEFF, MatrixType.class, r -> r.getCoefficientsStde());
            MAPPING.set(TD, MatrixType.class, r -> r.getVariables());
            MAPPING.set(TDEFFECT, TsData.class, r
                    -> {
                DataBlock tmp = DataBlock.make(r.getDomain().length());
                DataBlock prod = DataBlock.make(r.getDomain().length());
                for (int i = 0; i < r.variables.getColumnsCount(); ++i) {
                    prod.set(r.getCoefficients().column(i), r.getVariables().column(i), (a, b) -> a * b);
                    tmp.add(prod);
                }
                return TsData.ofInternal(r.getDomain().getStartPeriod(), tmp);
            });
        }

        @Override
        public boolean contains(String id) {
            return MAPPING.contains(id);
        }

        @Override
        public Map<String, Class> getDictionary() {
            Map<String, Class> dic = new LinkedHashMap<>();
            MAPPING.fillDictionary(null, dic, true);
            return dic;
        }

        @Override
        public <T> T getData(String id, Class<T> tclass) {
            return MAPPING.getData(this, id, tclass);
        }

        public static final InformationMapping<Results> getMapping() {
            return MAPPING;
        }
    }

    public Results regarima(TsData s, String td, String svar, double aicdiff) {
        int freq = s.getTsUnit().ratioOf(TsUnit.YEAR);
        SarimaSpecification spec = new SarimaSpecification(freq);
        spec.airline(true);
        DayClustering dc = days(td);
        Matrix mtd = generate(s.getDomain(), dc);
        Matrix nvar = generateVar(dc, svar);
        SsfData data = new SsfData(s.getValues());

        LevenbergMarquardtMinimizer min = new LevenbergMarquardtMinimizer();

        // fixed model
        TDvarMapping mapping0 = new TDvarMapping(mtd, true);
        SsfFunction<Airline, ISsf> fn0 = buildFunction(data, spec, mapping0, mtd, nvar);
        min.minimize(fn0.evaluate(mapping0.getDefaultParameters()));
        SsfFunctionPoint<Airline, ISsf> rfn0 = (SsfFunctionPoint<Airline, ISsf>) min.getResult();

        // compute the unconstrained solution
        TDvarMapping mapping = new TDvarMapping(mtd, false);
        SsfFunction<Airline, ISsf> fn = buildFunction(data, spec, mapping, mtd, nvar);

        Airline air0 = rfn0.getCore();
        air0.setRegVariance(.001);
        min.minimize(fn.ssqEvaluate(mapping.parametersOf(air0)));
        SsfFunctionPoint<Airline, ISsf> rfn = (SsfFunctionPoint<Airline, ISsf>) min.getResult();
        
        // compute AIC
        double aic0 = rfn0.getLikelihood().AIC(2);
        double aic = rfn.getLikelihood().AIC(3);
        ISsf ssf;
         if (aic+aicdiff < aic0){
            ssf=rfn.getSsf();
        }
        else
            ssf=rfn0.getSsf();

        DefaultSmoothingResults fs = DkToolkit.sqrtSmooth(ssf, data, true, true);
        Matrix c = Matrix.make(mtd.getRowsCount(), mtd.getColumnsCount() + 1);
        Matrix ec = Matrix.make(mtd.getRowsCount(), mtd.getColumnsCount() + 1);

        int del = freq + 2;
        double nwe = dc.getGroupCount(0);
        double[] z = new double[c.getColumnsCount() - 1];
        for (int i = 0; i < z.length; ++i) {
            c.column(i).copy(fs.getComponent(del + i));
            ec.column(i).copy(fs.getComponentVariance(del + i));
            z[i] = dc.getGroupCount(i + 1) / nwe;
            c.column(z.length).addAY(-z[i], c.column(i));
        }
        DataBlock Z = DataBlock.ofInternal(z);
        for (int i = 0; i < c.getRowsCount(); ++i) {
            Matrix var = fs.P(i).dropTopLeft(del, del);
            ec.set(i, z.length, QuadraticForm.apply(var, Z));
        }
        ec.apply(x -> x <= 0 ? 0 : Math.sqrt(x));
        
        Airline air=rfn.getCore();

        SarimaModel arima0 = SarimaModel.builder(spec)
                .theta(air0.theta)
                .btheta(air0.btheta)
                .build();
        SarimaModel arima = SarimaModel.builder(spec)
                .theta(air.theta)
                .btheta(air.btheta)
                .build();
        return Results.builder()
                .domain(s.getDomain())
                .arima0(arima0)
                .arima(arima)
                .ll0(rfn0.getLikelihood())
                .ll(rfn.getLikelihood())
                .nvar(air.regVariance)
                .variables(mtd)
                .coefficients(c)
                .coefficientsStde(ec)
                .build();
    }

    private SsfFunction<Airline, ISsf> buildFunction(SsfData data, SarimaSpecification spec, TDvarMapping mapping, Matrix mtd, Matrix nvar) {
        return SsfFunction.builder(data, mapping,
                params
                -> {
            SarimaModel arima = SarimaModel.builder(spec)
                    .theta(params.getTheta())
                    .btheta(params.getBtheta())
                    .build();
            ISsf ssf = SsfArima.of(arima);
            double nv = params.getRegVariance();
            Matrix v = nvar.deepClone();
            v.mul(nv);
            return RegSsf.ofTimeVarying(ssf, mtd, v);
        }).build();
    }

    private DayClustering days(String td) {
        DayClustering dc;
        switch (td) {
            case "TD2":
                dc = DayClustering.TD2;
                break;
            case "TD3":
                dc = DayClustering.TD3;
                break;
            case "TD3c":
                dc = DayClustering.TD3c;
                break;
            case "TD4":
                dc = DayClustering.TD4;
                break;
            default:
                dc = DayClustering.TD7;
                break;
        }
        return dc;
    }

    public Matrix generate(TsDomain domain, DayClustering dc) {
        GenericTradingDays gtd = GenericTradingDays.contrasts(dc);
        return Regression.matrix(domain, new GenericTradingDaysVariable(gtd));
    }

    public Matrix generateVar(DayClustering dc, String var) {
        int groupsCount = dc.getGroupsCount();
        Matrix full = Matrix.square(7);
        if (!var.equalsIgnoreCase("Contrasts")) {
            full.set(-1.0 / 7.0);
        }
        full.diagonal().add(1);
        Matrix Q = Matrix.make(groupsCount - 1, 7);
        int[] gdef = dc.getGroupsDefinition();
        for (int i = 1; i < groupsCount; ++i) {
            for (int j = 0; j < 7; ++j) {
                if (gdef[j] == i) {
                    Q.set(i - 1, j, 1);
                }
            }
        }
        return SymmetricMatrix.XSXt(full, Q);
    }

    private static class TDvarMapping implements IParametricMapping<Airline> {

        private final Matrix td;
        private final boolean fixed;
        private static final SarimaMapping airlineMapping;

        static {
        SarimaSpecification spec = new SarimaSpecification(12);
        spec.airline(true);
            airlineMapping = SarimaMapping.of(spec);
        }

        TDvarMapping(Matrix td, boolean fixed) {
            this.td = td;
            this.fixed = fixed;
        }

        public Matrix getTd() {
            return td;
        }

        @Override
        public Airline map(DoubleSequence p) {
            Airline airline = new Airline();
            airline.setTd(td);
            airline.setTheta(p.get(0));
            airline.setBtheta(p.get(1));
            if (fixed) {
                airline.setRegVariance(0);
            } else {
                airline.setRegVariance(p.get(2));
            }
            return airline;
        }

        public DoubleSequence parametersOf(Airline t) {
            double[] p = new double[fixed ? 2 : 3];
            p[0] = t.getTheta();
            p[1] = t.getBtheta();
            if (!fixed) {
                p[2] = t.getRegVariance();
            }
            return DoubleSequence.ofInternal(p);
        }

        @Override
        public boolean checkBoundaries(DoubleSequence inparams) {
            if (fixed) {
                return airlineMapping.checkBoundaries(inparams.extract(0, 2));
            } else {
                return inparams.get(2) >= 0 && airlineMapping.checkBoundaries(inparams.extract(0, 2));
            }
        }

        @Override
        public double epsilon(DoubleSequence inparams, int idx) {
            if (idx < 2) {
                return airlineMapping.epsilon(inparams, idx);
            }
            return inparams.get(2) * .001;
        }

        @Override
        public int getDim() {
            return fixed ? 2 : 3;
        }

        @Override
        public double lbound(int idx) {
            if (idx < 2) {
                return airlineMapping.lbound(idx);
            } else {
                return 0;
            }
        }

        @Override
        public double ubound(int idx) {
            if (idx < 2) {
                return airlineMapping.ubound(idx);
            } else {
                return 10;
            }
        }

        @Override
        public ParamValidation validate(DataBlock ioparams) {
            ParamValidation pv = ParamValidation.Valid;
            if (!fixed && ioparams.get(2) < 0) {
                pv = ParamValidation.Changed;
                ioparams.set(2, Math.min(10, -ioparams.get(2)));
            }
            if (!fixed && ioparams.get(2) > 10) {
                pv = ParamValidation.Changed;
                ioparams.set(2, 10);
            }
            ParamValidation pv2 = airlineMapping.validate(ioparams.extract(0, 2));
            if (pv == ParamValidation.Valid && pv2 == ParamValidation.Valid) {
                return ParamValidation.Valid;
            }
            if (pv == ParamValidation.Invalid || pv2 == ParamValidation.Invalid) {
                return ParamValidation.Invalid;
            }
            return ParamValidation.Changed;
        }

        @Override
        public String getDescription(int idx) {
            if (idx < 2) {
                return airlineMapping.getDescription(idx);
            } else {
                return "noise stdev";
            }
        }

        @Override
        public DoubleSequence getDefaultParameters() {
            return fixed ? DoubleSequence.of(-.6, -.6) : DoubleSequence.of(-.6, -.6, .001);
        }
    }

}
