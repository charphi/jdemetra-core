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
package demetra.benchmarking.r;

import demetra.data.Data;
import demetra.tempdisagg.univariate.TemporalDisaggregationResults;
import demetra.timeseries.TsData;
import demetra.timeseries.TsPeriod;
import org.junit.Test;
import static org.junit.Assert.*;
import demetra.data.DoubleSeq;

/**
 *
 * @author Jean Palate
 */
public class TemporalDisaggregationTest {
    
    public TemporalDisaggregationTest() {
    }

    @Test
    public void testChowLin() {
        TsData y = TsData.of(TsPeriod.yearly(1977), DoubleSeq.of(Data.PCRA));
        TsData q = TsData.of(TsPeriod.quarterly(1977, 1), DoubleSeq.of(Data.IND_PCR));
        TemporalDisaggregationResults rslt = TemporalDisaggregation.process(y, true, false, new TsData[]{q}, "Ar1", 0, "Sum", 0, 0, false, 0, false, "Diffuse", false);
    }
    
    @Test
    public void testLitterman() {
        TsData y = TsData.of(TsPeriod.yearly(1977), DoubleSeq.of(Data.PCRA));
        TsData q = TsData.of(TsPeriod.quarterly(1977, 1), DoubleSeq.of(Data.IND_PCR));
        TemporalDisaggregationResults rslt = TemporalDisaggregation.process(y, false, false, new TsData[]{q}, "RwAr1", 0, "Sum", 0, 0, false, 0, false, "Augmented", false);
    }

    @Test
    public void testFernandez() {
        TsData y = TsData.of(TsPeriod.yearly(1977), DoubleSeq.of(Data.PCRA));
        TsData q = TsData.of(TsPeriod.quarterly(1977, 1), DoubleSeq.of(Data.IND_PCR));
        TemporalDisaggregationResults rslt = TemporalDisaggregation.process(y, false, false, new TsData[]{q}, "Rw", 0, "Sum", 0, 0, false, 0, false, "Augmented", false);
    }
}
