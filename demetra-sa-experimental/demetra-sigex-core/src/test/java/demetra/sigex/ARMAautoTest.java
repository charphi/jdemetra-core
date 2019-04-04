/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demetra.sigex;

import demetra.data.DoubleSequence;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jean Palate <jean.palate@nbb.be>
 */
public class ARMAautoTest {
    
    public ARMAautoTest() {
    }

    @Test
    public void test1() {
        DoubleSequence acf = ARMAauto.compute(DoubleSequence.of(.25), DoubleSequence.empty(), 10);
        //System.out.println(acf);
        assertEquals(acf.get(0), 1.066666666, 1e-5);
        assertTrue(acf.allMatch(x->x>0));
    }
    
    @Test
    public void test2() {
        DoubleSequence acf = ARMAauto.compute(DoubleSequence.of(-.25), DoubleSequence.empty(), 10);
//        System.out.println(acf);
        assertEquals(acf.get(0), 1.066666666, 1e-5);
        assertEquals(acf.get(1)/acf.get(0), -.25, 1e-9);
    }
    
    @Test
    public void test3() {
        DoubleSequence acf = ARMAauto.compute(DoubleSequence.of(-.25, .2), DoubleSequence.of(.3, .4), 10);
        System.out.println(acf);
        assertEquals(acf.get(10), 0.006516581, 1e-5);
    }
    
}
