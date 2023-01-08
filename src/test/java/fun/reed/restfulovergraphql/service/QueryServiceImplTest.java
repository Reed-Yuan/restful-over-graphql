package fun.reed.restfulovergraphql.service;

import fun.reed.restfulovergraphql.model.CountryCode;
import fun.reed.restfulovergraphql.service.graphql.GraphQLServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QueryServiceImplTest {

    @Test
    void getContinentsByCountryCodes2() {
        GraphQLServiceImpl graphQLService = new GraphQLServiceImpl();
        QueryServiceImpl queryService = new QueryServiceImpl(graphQLService);
        var continents = queryService.getContinentsByCountryCodes(List.of(CountryCode.US, CountryCode.CA));
        assertNotNull(continents);
        assertEquals(1, continents.size());
        Assertions.assertEquals("North America", continents.get(0).getName());
        Assertions.assertEquals(2, continents.get(0).getCountries().size());
        Assertions.assertEquals(39, continents.get(0).getOtherCountries().size());
    }
    @Test
    void getContinentsByCountryCodes1() {
        GraphQLServiceImpl graphQLService = new GraphQLServiceImpl();
        QueryServiceImpl queryService = new QueryServiceImpl(graphQLService);
        var continents = queryService.getContinentsByCountryCodes(List.of(CountryCode.US));
        assertNotNull(continents);
        assertEquals(1, continents.size());
        Assertions.assertEquals("North America", continents.get(0).getName());
        Assertions.assertEquals(1, continents.get(0).getCountries().size());
        Assertions.assertEquals(40, continents.get(0).getOtherCountries().size());
    }

    @Test
    void getContinentByCode() {
        GraphQLServiceImpl graphQLService = new GraphQLServiceImpl();
        QueryServiceImpl queryService = new QueryServiceImpl(graphQLService);
        var continent = queryService.getContinentByCode("NA");
        assertNotNull(continent);
        Assertions.assertEquals("North America", continent.getName());
        Assertions.assertEquals(41, continent.getCountries().size());
        Assertions.assertEquals(0, continent.getOtherCountries().size());

    }

    @Test
    void getAllContinents() {
        GraphQLServiceImpl graphQLService = new GraphQLServiceImpl();
        QueryServiceImpl queryService = new QueryServiceImpl(graphQLService);
        var continents = queryService.getAllContinents();
        assertNotNull(continents);
        assertEquals(7, continents.size());
    }
}
