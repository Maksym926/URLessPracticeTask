package interactor;

import com.test.ShortenedURL;
import com.test.ShortenerInteractor;
import com.test.UrlGateway;
import com.test.UrlGatewayFake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShortenerURLTests {

    private ShortenerInteractor sut;

    @BeforeEach
    void setUp() {
        UrlGateway urlGateway = new UrlGatewayFake();
        sut = new ShortenerInteractor(urlGateway);
    }

    @Test
    public void shouldReturnEmptyOnNonExistingUrl(){

        Optional<ShortenedURL> result = sut.getById("Non-Existing-Url");
        assertEquals(Optional.empty(), result);

    }
    @Test
    public void shouldReturnResultOnExistingURL(){

        sut.create("http:/test", "12345Vq");
        ShortenedURL result = sut.getById("12345Vq").get();
        assertEquals("12345Vq", result.getId());
        assertEquals("http:/test", result.getUrl());

    }

}
