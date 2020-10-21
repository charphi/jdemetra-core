/*
 * Copyright 2019 National Bank of Belgium.
 *
 * Licensed under the EUPL, Version 1.2 or – as soon they will be approved
 * by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 *      https://joinup.ec.europa.eu/software/page/eupl
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jdplus.dstats;

import jdplus.data.DataBlock;
import nbbrd.design.Development;
import nbbrd.design.Immutable;
import demetra.math.Constants;
import jdplus.math.matrices.LowerTriangularMatrix;
import jdplus.math.matrices.SymmetricMatrix;
import lombok.NonNull;
import jdplus.random.RandomNumberGenerator;
import demetra.data.DoubleSeq;
import jdplus.math.matrices.Matrix;

/**
 *
 * @author Jean Palate
 */
@Development(status = Development.Status.Release)
@Immutable(lazy = true)
public final class MultivariateT {

    private static final Normal N=new Normal(0,1);

    private final DataBlock location;
    private final Matrix scale;
    private final Chi2 chi2;
    private volatile Matrix lchol;
    
    /**
     * T is generated by X = u + sqrt(W) A Z
     * <br> where:
     * <br> W = n/ Chi2(n)
     * <br> Z~N(0, I)
     * <br> A*A' = V = L*L'
     * @param location u
     * @param scale V
     * @param df degrees of freedom of the Chi2
     */
    public MultivariateT(DoubleSeq location, Matrix scale, double df){
        this.location=DataBlock.of(location);
        this.scale=scale.deepClone();
        this.chi2=new Chi2(df);
    }

    /**
     * @return the location
     */
    public DataBlock getLocation() {
        return location;
    }

    /**
     * @return the scale
     */
    public Matrix getScale() {
        return scale;
    }

    /**
     * @return the df
     */
    public double getDegreesOfFreedom() {
        return chi2.getDegreesofFreedom();
    }
    
    /**
     * Fills the given datablock by randoms generated by the random generator
     * @param rng
     * @param rnd The buffer that will contain the generated random numbers
     */
    public void random(@NonNull RandomNumberGenerator rng, DataBlock rnd) {
        rnd.set(()->N.random(rng));
        Matrix lm=l();
        LowerTriangularMatrix.xL(lm, rnd);
        double z=chi2.random(rng);
        rnd.mul(Math.sqrt(chi2.getDegreesofFreedom()/z));
        rnd.add(location);
    }
    
    private Matrix l(){
        Matrix tmp = this.lchol;
        if (tmp == null) {
            synchronized (this) {
                tmp = this.lchol;
                if (tmp == null) {
                    tmp=scale.deepClone();
                    SymmetricMatrix.lcholesky(tmp, Constants.getEpsilon());
                    this.lchol = tmp;
                }
            }
        }
        return lchol;
    }
}
