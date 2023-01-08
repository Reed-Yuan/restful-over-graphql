package fun.reed.restfulovergraphql.ratelimit;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Refill;

import java.time.Duration;

/**
 * RatePlan - rate plan
 *         - 5 requests per minute for unauthenticated users
 *         - 20 requests per minute for authenticated users
 */
public enum RatePlan {
    FREE {
        Bandwidth getLimit() {
            return Bandwidth.classic(5, Refill.intervally(5, Duration.ofMinutes(1)));
        }
    },
    PRO {
        Bandwidth getLimit() {
            return Bandwidth.classic(20, Refill.intervally(20, Duration.ofMinutes(1)));
        }
    };

    abstract Bandwidth getLimit();

    static RatePlan resolvePlanFromApiKey(String apiKey) {
        if (apiKey == null || apiKey.isEmpty() || apiKey.equals("FREE")) {
            return FREE;
        } else {
            return PRO;
        }
    }

}
