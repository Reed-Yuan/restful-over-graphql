package fun.reed.restfulovergraphql.controller;

import fun.reed.restfulovergraphql.model.Continent;
import fun.reed.restfulovergraphql.model.ContinentsDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ContinentController {

    /**
     * GetContinentsByCountryCodes - get continents by country codes
     * @param apiKey       API key
     * @param countryCodes comma separated list of country codes
     * @return ResponseEntity
     * @throws JsonProcessingException
     */
    @GetMapping(value = "/v1/continent", produces = "application/json")
    ResponseEntity<ContinentsDTO> getContinentsByCountryCodes(@RequestHeader(value = "X-api-key", required = false) String apiKey,
                                                              @RequestParam(name = "country-code", required = false) List<String> countryCodes) throws JsonProcessingException;

    /**
     * GetContinentByID - get continent by continent code
     * @param cntCode continent code
     * @return ResponseEntity<Continent>
     * @throws JsonProcessingException
     */
    @GetMapping(value = "/v1/continent/{cntCode}", produces = "application/json")
    ResponseEntity<Continent> getContinentByID(@PathVariable String cntCode) throws JsonProcessingException;

}
