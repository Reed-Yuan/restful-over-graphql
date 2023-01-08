package fun.reed.restfulovergraphql.service;

import fun.reed.restfulovergraphql.model.Continent;
import fun.reed.restfulovergraphql.model.ContinentCode;
import fun.reed.restfulovergraphql.model.CountryCode;
import fun.reed.restfulovergraphql.service.graphql.GraphQLService;
import graphql.query;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * QueryService implementation
 */
@Service("queryService")
public class QueryServiceImpl implements QueryService {
    private static final Logger LOG = LoggerFactory.getLogger(QueryServiceImpl.class);

    private final GraphQLService graphQLService;

    @Autowired
    QueryServiceImpl(GraphQLService graphQLService) {
        this.graphQLService = graphQLService;
    }

    /**
     * GetContinentByCode - get continents by country codes
     * @param countryCodes
     * @return java.util.List<fun.reed.restfulovergraphql.model.Continent>
     */
    @Override
    @NotNull
    public List<Continent> getContinentsByCountryCodes(List<CountryCode> countryCodes) {
        Map<String, HashSet<CountryCode>> continentSetMap = getContinents(countryCodes);
        List<String> continentCodes = continentSetMap.keySet().stream().toList();
        List<query.GetContinents.Result.continents> qrqContinents = graphQLService.GetContinents(continentCodes);
        List<Continent> continents = new ArrayList<>();
        for (query.GetContinents.Result.continents qrqContinent : qrqContinents) {
            continents.add(convertContinent(qrqContinent, continentSetMap.get(qrqContinent.getCode())));
        }
        return continents;
    }

    @NotNull
    private Continent convertContinent(query.GetContinents.Result.continents qrqContinent, HashSet<CountryCode> countriesInput) {
        // Convert graphql continent.countryCodes to CountryCode
        HashSet<CountryCode> countryCodesFull = new HashSet<>();
        for (query.GetContinents.Result.continents.countries country : qrqContinent.getCountries()) {
            CountryCode countryCode = CountryCode.valueOf(country.getCode());
            countryCodesFull.add(countryCode);
        }
        HashSet<CountryCode> otherCountries = new HashSet<>(countryCodesFull);
        otherCountries.removeAll(countriesInput);
        return new Continent(countriesInput, qrqContinent.getName(), otherCountries);
    }

    @NotNull
    private Map<String, HashSet<CountryCode>> getContinents(List<CountryCode> countryCodes) {
        List<query.GetContinentByCountryCodes.Result.countries> countries = graphQLService.GetContinentByCountryCodes(countryCodes);
        Map<String, HashSet<CountryCode>> continentMap = new HashMap<>();
        for (query.GetContinentByCountryCodes.Result.countries country : countries) {
            LOG.debug("country: {}", country);
            String continent = country.getContinent().getCode();
            CountryCode countryCode = CountryCode.valueOf(country.getCode());
            if (continentMap.containsKey(continent)) {
                continentMap.get(continent).add(countryCode);
            } else {
                HashSet<CountryCode> codes = new HashSet<>();
                codes.add(countryCode);
                continentMap.put(continent, codes);
            }
        }
        return continentMap;
    }

    /**
     * GetContinentByCode - get continent by continent code
     * @param code - continent code
     * @return Continent
     */
    @Override
    public Continent getContinentByCode(String code) {
        query.GetContinentByCode.Result.continent qrqContinent = graphQLService.GetContinentByCode(code);
        HashSet<CountryCode> countryCodesFull = new HashSet<>();
        for (query.GetContinentByCode.Result.continent.countries country : qrqContinent.getCountries()) {
            CountryCode countryCode = CountryCode.valueOf(country.getCode());
            countryCodesFull.add(countryCode);
        }

        return new Continent(countryCodesFull, qrqContinent.getName(), new HashSet<>());
    }

    /**
     * Get all continents
     * @return java.util.List<fun.reed.restfulovergraphql.model.Continent>
     */
    @Override
    public List<Continent> getAllContinents() {
        List<query.GetContinents.Result.continents> qrqContinents = graphQLService.GetContinents(ContinentCode.allStrCodes());
        List<Continent> continents = new ArrayList<>();
        for (query.GetContinents.Result.continents qrqContinent : qrqContinents) {
            Continent continent = convertContinent(qrqContinent, new HashSet<>());
            continent.getCountries().addAll(continent.getOtherCountries());
            continent.getOtherCountries().clear();
            continents.add(continent);
        }
        return continents;
    }
}

