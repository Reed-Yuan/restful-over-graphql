package fun.reed.restfulovergraphql.ratelimit;

/**
 * OverLimitException - over limit exception
 */
public class OverLimitException extends RuntimeException {
    public OverLimitException(String message) {
        super(message);
    }
}
