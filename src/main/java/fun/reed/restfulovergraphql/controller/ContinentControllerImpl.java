package fun.reed.restfulovergraphql.controller;

import fun.reed.restfulovergraphql.model.Continent;
import fun.reed.restfulovergraphql.model.ContinentCode;
import fun.reed.restfulovergraphql.model.ContinentsDTO;
import fun.reed.restfulovergraphql.model.CountryCode;
import fun.reed.restfulovergraphql.ratelimit.OverLimitException;
import fun.reed.restfulovergraphql.ratelimit.RatePlanService;
import fun.reed.restfulovergraphql.service.QueryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ContinentController implementation
 */
@RestController
public class ContinentControllerImpl implements ContinentController {
    private static final Logger LOG = LoggerFactory.getLogger(ContinentControllerImpl.class);

    private final QueryService queryService;

    private final RatePlanService ratePlanService;

    @Autowired
    public ContinentControllerImpl(QueryService queryService, RatePlanService ratePlanService) {
        this.queryService = queryService;
        this.ratePlanService = ratePlanService;
    }

    /**
     * GetContinentsByCountryCodes - get continents by country codes
     * @param apiKey       API key
     * @param countryCodes comma separated list of country codes
     * @return ResponseEntity
     * @throws JsonProcessingException
     */
    @Override
    public ResponseEntity<ContinentsDTO> getContinentsByCountryCodes(String apiKey, List<String> countryCodes) throws JsonProcessingException {
        LOG.debug("queryRequest: {}", countryCodes);
        if (countryCodes == null || countryCodes.isEmpty()) {
            return getAllContinents();
        }
        Bucket bucket = ratePlanService.resolveBucket(apiKey == null ? "FREE" : apiKey);
        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
        if (probe.isConsumed()) {
            List<Continent> continents = queryService.getContinentsByCountryCodes(
                    countryCodes.stream()
                            .map(String::toUpperCase)
                            .map(CountryCode::valueOf)
                            .toList());
            LOG.debug("queryResponse: {}", continents);
            ContinentsDTO continentsDTO = new ContinentsDTO(continents);
            return new ResponseEntity<>(continentsDTO, HttpStatus.OK);
        }

        long waitForRefill = probe.getNanosToWaitForRefill() / 1_000_000_000;
        throw new OverLimitException("Rate limit exceeded. Please wait " + waitForRefill + " seconds before trying again.");
    }

    /**
     * GetContinentByID - get continent by continent code
     *
     * @param cntCode continent code
     * @return ResponseEntity<Continent>
     * @throws JsonProcessingException
     */
    @Override
    public ResponseEntity<Continent> getContinentByID(@PathVariable String cntCode) throws JsonProcessingException {
        LOG.debug("queryRequest: {}", cntCode);
        ContinentCode continentCode = ContinentCode.valueOf(cntCode.toUpperCase());
        Continent continent = queryService.getContinentByCode(continentCode.getCode());
        LOG.debug("queryResponse: {}", continent);
        return new ResponseEntity<>(continent, HttpStatus.OK);
    }

    /**
     * GetAllContinents - get all continents
     *
     * @return ResponseEntity<ContinentsDTO>
     * @throws JsonProcessingException
     */
    private ResponseEntity<ContinentsDTO> getAllContinents() throws JsonProcessingException {
        LOG.debug("queryRequest: {}", "all");
        List<Continent> continents = queryService.getAllContinents();
        LOG.debug("queryResponse: {}", continents);
        ContinentsDTO continentsDTO = new ContinentsDTO(continents);
        return new ResponseEntity<>(continentsDTO, HttpStatus.OK);
    }
}
