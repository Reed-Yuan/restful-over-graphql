package fun.reed.restfulovergraphql.service;

import fun.reed.restfulovergraphql.model.Continent;
import fun.reed.restfulovergraphql.model.CountryCode;

import java.util.List;

/**
 * QueryService interface
 */
public interface QueryService {
    /**
     * GetContinentByCode - get continents by country codes
     * @param countryCodes
     * @return java.util.List<fun.reed.restfulovergraphql.model.Continent>
     */
    List<Continent> getContinentsByCountryCodes(List<CountryCode> countryCodes);

    /**
     * GetContinentByCode - get continent by continent code
     * @param code - continent code
     * @return Continent
     */
    Continent getContinentByCode(String code);

    /**
     * Get all continents
     * @return java.util.List<fun.reed.restfulovergraphql.model.Continent>
     */
    List<Continent> getAllContinents();
}
