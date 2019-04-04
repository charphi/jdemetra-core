/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demetra.stats;

import demetra.data.Doubles;
import demetra.data.Doubles;
import demetra.data.WindowFunction;
import demetra.maths.matrices.Matrix;
import demetra.maths.matrices.SymmetricMatrix;
import java.util.function.DoubleUnaryOperator;
import java.util.function.IntToDoubleFunction;
import demetra.data.DoubleSeq;

/**
 *
 * @author Jean Palate <jean.palate@nbb.be>
 */
@lombok.experimental.UtilityClass
public class RobustCovarianceComputer {

    /**
     * Computes a robust covariance estimate of X 
     * Cov = 1/n sum(w(k)x(0...n-k-1;)'x(k...n-1;)), k in ]-truncationLag, truncationLag[
     *
     * @param x Input matrix
     * @param winFunction Window function
     * @param truncationLag Truncation lag (excluded from the computation)
     * @return
     */
    public Matrix covariance(Matrix x, WindowFunction winFunction, int truncationLag) {
        DoubleUnaryOperator w = winFunction.window();
        int n = x.getRowsCount(), nx = x.getColumnsCount();
        Matrix s = SymmetricMatrix.XtX(x);
        s.mul(w.applyAsDouble(0));
        Matrix ol = Matrix.square(nx);
        double q = 1+truncationLag;
        for (int l = 1; l <= truncationLag; ++l) {
            double wl = w.applyAsDouble(l / q);
            Matrix m = x.extract(0, n - l, 0, nx);
            Matrix ml = x.extract(l, n - l, 0, nx);
            ol.product(m.transpose(), ml);
            s.addAY(wl, ol);
            s.addAY(wl, ol.transpose());
        }
        s.div(n);
        return s;
    }

    public double covariance(DoubleSeq x, WindowFunction winFunction, int truncationLag) {
        DoubleUnaryOperator w = winFunction.window();
        DoubleSeq y=Doubles.removeMean(x);
        IntToDoubleFunction acf = AutoCovariances.autoCovarianceFunction(y, 0);
        double s = acf.applyAsDouble(0);
        double q = 1+truncationLag;
        for (int l = 1; l <= truncationLag; ++l) {
            double wl = w.applyAsDouble(l / q);
            s += 2*wl * acf.applyAsDouble(l);
        }
        return s;
    }

}
