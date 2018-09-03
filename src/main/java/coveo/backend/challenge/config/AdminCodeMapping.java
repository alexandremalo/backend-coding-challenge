package coveo.backend.challenge.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class AdminCodeMapping {

    //Country code to country name (ex: CA -> Canada)
    public static final Map<String, String> COUNTRY_MAPPING;
    static {
        Map<String, String> countryMap = new HashMap<String, String>();

        countryMap.put("CA", "Canada");
        countryMap.put("US", "USA");

        COUNTRY_MAPPING = Collections.unmodifiableMap(countryMap);
    }

    //Canadian province num-code to province short-name (ex: 01 -> AB)
    public static final Map<String, String> PROVINCE_MAPPING;
    static {
        Map<String, String> provinceMap = new HashMap<String, String>();

        provinceMap.put("01", "AB");
        provinceMap.put("02", "BC");
        provinceMap.put("03", "MB");
        provinceMap.put("04", "NB");
        provinceMap.put("05", "NL");
        //Skip 06 ??
        provinceMap.put("07", "NS");
        provinceMap.put("08", "ON");
        provinceMap.put("09", "PE");
        provinceMap.put("10", "QC");
        provinceMap.put("11", "SK");
        provinceMap.put("12", "YT");
        provinceMap.put("13", "NT");
        provinceMap.put("14", "NU");

        PROVINCE_MAPPING = Collections.unmodifiableMap(provinceMap);
    }

    //Country code to custom admin2 mapping (optional)
    public static final Map<String, Map<String, String>> ADMIN_2_CODE_MAPPING;
    static {
        Map<String, Map<String, String>> admin2CodeMapping = new HashMap<String, Map<String, String>>();

        admin2CodeMapping.put("CA", PROVINCE_MAPPING);

        ADMIN_2_CODE_MAPPING = Collections.unmodifiableMap(admin2CodeMapping);
    }

}
