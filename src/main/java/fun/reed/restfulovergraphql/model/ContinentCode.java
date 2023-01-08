package fun.reed.restfulovergraphql.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ContinentCode
 */
public enum ContinentCode {
    AF("AF"),
    AN("AN"),
    AS("AS"),
    EU("EU"),
    NA("NA"),
    OC("OC"),
    SA("SA");

    private String code;

    ContinentCode(String code) {
        this.code = code;
    }

    //Get all String codes
    public static List<String> allStrCodes() {
        return Arrays.stream(ContinentCode.values()).map(ContinentCode::getCode).collect(Collectors.toList());
    }

    public String getCode() {
        return code;
    }
}
