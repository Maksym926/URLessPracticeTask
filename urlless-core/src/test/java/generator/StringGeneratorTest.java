package generator;

import com.test.collection.UrlCollection;
import com.test.gateway.CollectionGateway;
import com.test.gateway.CollectionGatewayFake;
import com.test.gateway.UrlGatewayFake;
import com.test.generator.SHA1Generator;
import com.test.generator.StringIdGenerator;
import com.test.interactor.CollectionInteractor;
import com.test.interactor.ShortenerInteractor;
import com.test.shortener.ShortenedURL;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringGeneratorTest {

    @Test
    public void shouldResolveCollisions(){
        CollectionGateway collectionGateway = new CollectionGatewayFake();
        StringIdGenerator collectionGenerator = new StringIdGenerator();

        UrlGatewayFake urlGateway = new UrlGatewayFake();
        SHA1Generator urlGenerator = new SHA1Generator();
        ShortenerInteractor shortenerInteractor = new ShortenerInteractor(urlGateway, urlGenerator);

        CollectionInteractor interactor = new CollectionInteractor(collectionGateway, shortenerInteractor, collectionGenerator);



        UrlCollection collection1 = interactor.create(List.of(ShortenedURL.builder()
                .Id("abcd")
                .url("http//test1")
                .build()));

        UrlCollection collection2 = interactor.create(List.of(ShortenedURL.builder()
                .Id("1324fw")
                .url("http//test1")
                .build()));

        assertNotEquals(collection1.getId(), collection2.getId());
        assertTrue(collection1.getId().matches("^[a-zA-Z0-9-=]{5}$"), collection1.getId() + " should match regex ");
        assertTrue(collection2.getId().matches("^[a-zA-Z0-9-=]{5}$"), collection2.getId() + " should match regex");
    }

}
