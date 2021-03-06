/*
* Copyright 2013 National Bank of Belgium
*
* Licensed under the EUPL, Version 1.1 or – as soon they will be approved 
* by the European Commission - subsequent versions of the EUPL (the "Licence");
* You may not use this work except in compliance with the Licence.
* You may obtain a copy of the Licence at:
*
* http://ec.europa.eu/idabc/eupl
*
* Unless required by applicable law or agreed to in writing, software 
* distributed under the Licence is distributed on an "AS IS" basis,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the Licence for the specific language governing permissions and 
* limitations under the Licence.
*/
package ec.tstoolkit.arima;

import ec.tstoolkit.design.Development;
import ec.tstoolkit.design.Immutable;
import ec.tstoolkit.maths.linearfilters.BackFilter;
import ec.tstoolkit.maths.linearfilters.ForeFilter;
import ec.tstoolkit.maths.linearfilters.FiniteFilter;
import ec.tstoolkit.maths.linearfilters.RationalFilter;

/**
 * 
  * @author Jean Palate
 */
@Development(status = Development.Status.Alpha)
@Immutable
public class CrossCovarianceFunction {

    /**
     * 
     * @param m1
     * @param m2
     * @return
     */
    public static CrossCovarianceFunction create(final ArimaModel m1, final ArimaModel m2) {
	try {
	    return new CrossCovarianceFunction(new RationalFilter(FiniteFilter.multiply(new FiniteFilter(
		    m1.getMA()), m2.getMA().mirror()), m1.getAR(), m2.getAR()
		    .mirror()), Math.sqrt(m1.getInnovationVariance()
		    * m2.getInnovationVariance()));
	} catch (ArimaException ex) {
	    return null;
	}
    }

    private final RationalFilter m_r;

    private double m_c = 1;

    /**
     * 
     * @param m1
     * @param m2
     */
    public CrossCovarianceFunction(final LinearModel m1, final LinearModel m2) {
	RationalFilter f1 = m1.getFilter();
	RationalFilter f2 = m2.getFilter();
	// m_r = f1*f2.Mirror...
	FiniteFilter n1 = new FiniteFilter(f1.getNumerator()), n2 = new FiniteFilter(
		f2.getNumerator()).mirror();
	BackFilter db1 = f1.getRationalBackFilter().getDenominator(), db2 = f2.getRationalBackFilter()
		.getDenominator();
	ForeFilter df1 = f1.getRationalForeFilter().getDenominator(), df2 = f2.getRationalForeFilter()
		.getDenominator();

	m_r = new RationalFilter(FiniteFilter.multiply(n1, n2), db1
		.times(df2.mirror()), df1.times(db2.mirror()));
	m_c = Math
		.sqrt(m1.getInnovationVariance() * m2.getInnovationVariance());

    }

    private CrossCovarianceFunction(RationalFilter r, double c) {
	m_r = r;
	m_c = c;
    }

    /**
     * 
     * @param k
     * @return
     */
    public double get(final int k) {
	return m_r.getWeight(k) * m_c;
    }

    /**
     * 
     * @return
     */
    public int getLBound() {
	return m_r.getLBound();
    }

    /**
     * 
     * @return
     */
    public int getUBound() {
	return m_r.getUBound();
    }

    /**
     * 
     * @return
     */
    public boolean hasLBound() {
	return m_r.hasLowerBound();
    }

    /**
     * 
     * @return
     */
    public boolean hasUBound() {
	return m_r.hasUpperBound();
    }

    /**
     * 
     * @param n
     * @param m
     */
    public void prepare(final int n, final int m) {
	m_r.prepare(n, m);
    }
}
