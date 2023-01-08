package fun.reed.restfulovergraphql;

import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.endpoint.ApiVersion;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class BootApplicationTests {

    @Test
    void contextLoads() {
        assertTrue(ApiVersion.LATEST.equals(ApiVersion.V3));
    }
}
