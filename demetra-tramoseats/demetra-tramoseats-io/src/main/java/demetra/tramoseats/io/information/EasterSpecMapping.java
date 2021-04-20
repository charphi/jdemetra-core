/*
 * Copyright 2020 National Bank of Belgium
 *
 * Licensed under the EUPL, Version 1.2 or – as soon they will be approved 
 * by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * https://joinup.ec.europa.eu/software/page/eupl
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and 
 * limitations under the Licence.
 */
package demetra.tramoseats.io.information;

import demetra.data.Parameter;
import demetra.information.InformationSet;
import demetra.tramo.EasterSpec;
import java.util.Map;

/**
 *
 * @author PALATEJ
 */
@lombok.experimental.UtilityClass
class EasterSpecMapping {

    final String DURATION = "duration", TYPE = "type", TEST = "test", JULIAN = "julian";

    String varName() {
        return "easter";
    }

    void fillDictionary(String prefix, Map<String, Class> dic) {
        dic.put(InformationSet.item(prefix, TYPE), String.class);
        dic.put(InformationSet.item(prefix, DURATION), Integer.class);
        dic.put(InformationSet.item(prefix, TEST), String.class);
        dic.put(InformationSet.item(prefix, JULIAN), Boolean.class);
    }

    void write(InformationSet regInfo, EasterSpec spec, boolean verbose) {
        if (!verbose && spec.isDefault()) {
            return;
        }
        InformationSet cinfo = regInfo.subSet(RegressionSpecMapping.CALENDAR);
        InformationSet easterInfo = cinfo.subSet(CalendarSpecMapping.EASTER);
        if (verbose || spec.getDuration() != EasterSpec.DEF_IDUR) {
            easterInfo.add(DURATION, spec.getDuration());
        }
        if (verbose || spec.getType() != EasterSpec.Type.Unused) {
            easterInfo.add(TYPE, spec.getType().name());
        }
        if (verbose || spec.isTest()) {
            easterInfo.add(TEST, spec.isTest());
        }
        if (verbose || spec.isJulian() != EasterSpec.DEF_JULIAN) {
            easterInfo.add(JULIAN, spec.isJulian());
        }
        Parameter coef = spec.getCoefficient();
        RegressionSpecMapping.add(regInfo, varName(), coef);
    }

    EasterSpec read(InformationSet regInfo) {
        InformationSet cinfo = regInfo.getSubSet(RegressionSpecMapping.CALENDAR);
        if (cinfo == null) {
            return EasterSpec.DEFAULT_UNUSED;
        }
        InformationSet easterInfo = cinfo.getSubSet(CalendarSpecMapping.EASTER);
        if (easterInfo == null) {
            return EasterSpec.DEFAULT_UNUSED;
        }
        EasterSpec.Builder builder = EasterSpec.builder();
        Integer d = easterInfo.get(DURATION, Integer.class);
        if (d != null) {
            builder = builder.duration(d);
        }
        String type = easterInfo.get(TYPE, String.class);
        if (type != null) {
            builder = builder.type(EasterSpec.Type.valueOf(type));
        }
        Boolean test = easterInfo.get(TEST, Boolean.class);
        if (test != null) {
            builder = builder.test(test);
        }
        Boolean jul = easterInfo.get(JULIAN, Boolean.class);
        if (jul != null) {
            builder = builder.julian(jul);
        }
        return builder.coefficient(RegressionSpecMapping.coefficientOf(regInfo, varName()))
                .build();
    }
}
