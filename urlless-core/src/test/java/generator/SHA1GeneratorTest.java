package generator;

import com.test.gateway.UrlGatewayFake;
import com.test.generator.SHA1Generator;
import com.test.interactor.ShortenerInteractor;
import com.test.shortener.ShortenedURL;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SHA1GeneratorTest {
    @Test
    public void shouldResolveCollisions(){
        UrlGatewayFake urlGateway = new UrlGatewayFake();
        SHA1Generator generator = new SHA1Generator();

        ShortenerInteractor interactor = new ShortenerInteractor(urlGateway, generator);

        ShortenedURL result1 = interactor.create("http://test");
        ShortenedURL result2 = interactor.create("http://test");

        assertNotEquals(result1.getId(), result2.getId());
        assertTrue(result1.getId().matches("^[a-zA-Z0-9-=]{6}$"), result1.getId() + " should match regex ");
        assertTrue(result2.getId().matches("^[a-zA-Z0-9-=]{6}$"), result2.getId() + " should match regex");
    }
}
