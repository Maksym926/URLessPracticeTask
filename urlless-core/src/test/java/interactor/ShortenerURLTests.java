package interactor;

import com.test.generator.IdGenerator;
import com.test.shortener.ShortenedURL;
import com.test.interactor.ShortenerInteractor;
import com.test.gateway.UrlGateway;
import com.test.gateway.UrlGatewayFake;
import fake.IdGeneratorFake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShortenerURLTests {

    private ShortenerInteractor sut;
    private UrlGateway urlGateway;
    private IdGeneratorFake generator;


    @BeforeEach
    void setUp() {
        generator = new IdGeneratorFake();
        urlGateway = new UrlGatewayFake();

        sut = new ShortenerInteractor(urlGateway, generator);
    }

    @Test
    public void shouldReturnEmptyOnNonExistingUrl(){

        Optional<ShortenedURL> result = sut.getById("Non-Existing-Url");
        assertEquals(Optional.empty(), result);

    }
    @Test
    public void shouldReturnResultOnExistingURL(){

        urlGateway.create("http:/test", "12345Vq");
        ShortenedURL result = sut.getById("12345Vq").get();
        assertEquals("12345Vq", result.getId());
        assertEquals("http:/test", result.getUrl());

    }
    @Test
    public void shouldReturnCreateUrl(){
        generator.add("abcd");
        ShortenedURL result = sut.create("http://test");
        assertEquals("abcd", result.getId());

    }



}
