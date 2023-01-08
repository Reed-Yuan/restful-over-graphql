package fun.reed.restfulovergraphql.ratelimit;

import io.github.bucket4j.Bucket;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * RateLimitService - rate limit service
 */
@Service
public class RatePlanService {
    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    /**
     * GetBucket - get bucket for api key
     * @param apiKey - api key
     * @return
     */
    public Bucket resolveBucket(String apiKey) {
        return cache.computeIfAbsent(apiKey, this::newBucket);
    }

    private Bucket newBucket(String apiKey) {
        RatePlan ratePlan = RatePlan.resolvePlanFromApiKey(apiKey);
        return Bucket.builder()
                .addLimit(ratePlan.getLimit())
                .build();
    }

}
