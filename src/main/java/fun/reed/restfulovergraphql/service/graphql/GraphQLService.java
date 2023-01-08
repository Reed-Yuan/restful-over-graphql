package fun.reed.restfulovergraphql.service.graphql;

import fun.reed.restfulovergraphql.model.CountryCode;
import graphql.query;

import java.util.List;

/**
 * GraphQLService
 */
public interface GraphQLService {
    /**
     * GetContinentByCode
     * @param id for example: "NA"
     * @return fun.reed.restfulovergraphql.service.graphql.query.GetContinentByCode.Result.continent
     */
    query.GetContinentByCode.Result.continent GetContinentByCode(String id);

    /**
     * GetContinentByCountryCodes
     * @param ids
     * @return java.util.List<fun.reed.restfulovergraphql.service.graphql.query.GetContinentByCountryCodes.Result.countries>
     */
    List<query.GetContinentByCountryCodes.Result.countries> GetContinentByCountryCodes(List<CountryCode> ids);

    /**
     * GetContinents
     * @param ids for example: ["NA", "EU"]
     * @return java.util.List<fun.reed.restfulovergraphql.service.graphql.query.GetContinents.Result.continents>
     */
    List<query.GetContinents.Result.continents> GetContinents(List<String> ids);
}
