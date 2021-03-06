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
package ec.tstoolkit.ucarima;

import ec.tstoolkit.maths.Complex;
import ec.tstoolkit.maths.polynomials.AbstractRootSelector;
import ec.tstoolkit.maths.polynomials.Polynomial;
import ec.tstoolkit.maths.polynomials.UnitRoots;

/**
 * 
 * @author Jean Palate
 */
public class SeasonalSelector extends AbstractRootSelector {

    private double m_k = 0.5;

    private double m_epsphi = Math.PI / 90; // (== 2 degrees)

    private int m_freq;

    /**
	 *
	 */
    public SeasonalSelector() {
	m_freq = 12;
    }

    /**
     * 
     * @param freq
     */
    public SeasonalSelector(final int freq) {
	m_freq = freq;
    }

    /**
     * 
     * @param freq
     * @param epsphi
     */
    public SeasonalSelector(final int freq, final double epsphi) {
	m_freq = freq;
	m_epsphi = epsphi;
    }

    @Override
    public boolean accept(final Complex root) {
	if (Math.abs(root.getIm()) < 1e-6) {
	    if (1/root.getRe() < -m_k)
		return true;
	    else
		return false;
	}

	double pi = 2 * Math.PI / m_freq;
	double arg = Math.abs(root.arg());
        double eps=m_epsphi/180*Math.PI;
	for (int i = 1; i <= m_freq / 2; ++i) {
	    if (Math.abs(pi * i - arg) <= eps)
		return true;
	}
	return false;
    }

    /**
     * 
     * @return
     */
    public int getFrequency()
    {
	return m_freq;
    }

    /**
     * 
     * @return
     */
    public double getK() {
	return m_k;
    }

    /**
     * @return Tolerance in radian
     */
    public double getTolerance() {
	return m_epsphi;
    }

    /**
     * 
     * @param value
     */
    public void setFrequency(final int value) {
	m_freq = value;
    }

    /**
     * 
     * @param value
     */
    public void setK(final double value) {
	m_k = value;
    }

    /**
     * 
     * @param value
     */
    public void setTolerance(final double value) {
	m_epsphi = value;
    }

    @Override
    public boolean selectUnitRoots(Polynomial p) {
        // remove (1_B)
        m_sel=Polynomial.ONE;
        m_nsel=p;
        if (m_freq == 1)
            return false;
        Polynomial S=UnitRoots.S(m_freq, 1);
        do{
            Polynomial.Division div = Polynomial.divide(m_nsel, S);
            if ( div.isExact() ){
                m_sel=m_sel.times(S);
                m_nsel=div.getQuotient();
            }else
                break;
        }while (p.getDegree()>=S.getDegree());
        return m_sel.getDegree()>0;
    }

}
