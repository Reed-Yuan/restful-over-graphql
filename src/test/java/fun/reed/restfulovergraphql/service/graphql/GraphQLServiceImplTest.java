package fun.reed.restfulovergraphql.service.graphql;

import fun.reed.restfulovergraphql.model.CountryCode;
import graphql.query;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GraphQLServiceImplTest {

    @Test
    void GetContinentByCode() {
        GraphQLServiceImpl queryService = new GraphQLServiceImpl();
        query.GetContinentByCode.Result.continent continent = queryService.GetContinentByCode("NA");
        assertNotNull(continent);
    }

    @Test
    void getContinentByCountryCodes() {
        GraphQLServiceImpl queryService = new GraphQLServiceImpl();
        List<query.GetContinentByCountryCodes.Result.countries> countries = queryService.GetContinentByCountryCodes(List.of(CountryCode.US, CountryCode.CA));
        assertNotNull(countries);
        assertEquals(2, countries.size());
    }

    @Test
    void getContinents() {
        GraphQLServiceImpl queryService = new GraphQLServiceImpl();
        List<query.GetContinents.Result.continents> continents = queryService.GetContinents(List.of("NA", "EU"));
        assertNotNull(continents);
        assertEquals(2, continents.size());
    }
}