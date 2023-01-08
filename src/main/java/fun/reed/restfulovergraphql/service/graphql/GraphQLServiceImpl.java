package fun.reed.restfulovergraphql.service.graphql;

import fun.reed.restfulovergraphql.model.CountryCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import graphql.query;

import java.util.List;

@Service("graphQLService")
public class GraphQLServiceImpl implements GraphQLService {
    private static final Logger LOG = LoggerFactory.getLogger(GraphQLServiceImpl.class);

    @Value("${graphql-server.endpoint}")
    private String GRAPHQL_ENDPOINT = "https://countries.trevorblades.com/";

    /**
     * GetContinentByCode
     * @param id for example: "NA"
     * @return fun.reed.restfulovergraphql.service.graphql.query.GetContinentByCode.Result.continent
     */
    @Override
    public query.GetContinentByCode.Result.continent GetContinentByCode(String id) {
        query.GetContinentByCode builder = query.GetContinentByCode.builder(id).build();
        query.GetContinentByCode.Result result = builder.request(GRAPHQL_ENDPOINT).post();
        LOG.debug("result.getBindings().toJson(): {}", result.getBindings().toJson());
        query.GetContinentByCode.Result.continent continent = result.getContinent();
        return continent;
    }

    /**
     * GetContinentByCountryCodes
     * @param ids
     * @return java.util.List<fun.reed.restfulovergraphql.service.graphql.query.GetContinentByCountryCodes.Result.countries>
     */
    @Override
    public List<query.GetContinentByCountryCodes.Result.countries> GetContinentByCountryCodes(List<CountryCode> ids) {
        List<String> codes = ids.stream().map(CountryCode::getCode).collect(java.util.stream.Collectors.toList());
        query.GetContinentByCountryCodes builder = query.GetContinentByCountryCodes.builder(codes).build();
        query.GetContinentByCountryCodes.Result result = builder.request(GRAPHQL_ENDPOINT).post();
        LOG.debug("result.getBindings().toJson(): {}", result.getBindings().toJson());
        List<query.GetContinentByCountryCodes.Result.countries> countries = result.getCountries();
        return countries;
    }

    /**
     * GetContinents
     * @param ids for example: ["NA", "EU"]
     * @return java.util.List<fun.reed.restfulovergraphql.service.graphql.query.GetContinents.Result.continents>
     */
    @Override
    public List<query.GetContinents.Result.continents> GetContinents(List<String> ids) {
        query.GetContinents builder = query.GetContinents.builder(ids).build();
        query.GetContinents.Result result = builder.request(GRAPHQL_ENDPOINT).post();
        LOG.debug("result.getBindings().toJson(): {}", result.getBindings().toJson());
        List<query.GetContinents.Result.continents> continents = result.getContinents();
        return continents;
    }
}
