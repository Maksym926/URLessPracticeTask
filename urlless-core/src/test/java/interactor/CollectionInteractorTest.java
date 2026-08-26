package interactor;


import com.test.collection.UrlCollection;
import com.test.gateway.CollectionGateway;
import com.test.gateway.CollectionGatewayFake;
import com.test.gateway.UrlGateway;
import com.test.gateway.UrlGatewayFake;
import com.test.interactor.CollectionInteractor;
import com.test.interactor.ShortenerInteractor;
import com.test.shortener.ShortenedURL;
import fake.IdGeneratorFake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class CollectionInteractorTest {

    private CollectionInteractor sut;
    private CollectionGateway collectionGateway;

    private IdGeneratorFake generatorForCollection;
    private ShortenerInteractor shortenerInteractor;
    private UrlGateway urlGateway;

    @BeforeEach
    void setUp() {
        collectionGateway = new CollectionGatewayFake();
        generatorForCollection = new IdGeneratorFake();
        shortenerInteractor = shortenerInteractorSetUp();
        sut = new CollectionInteractor(collectionGateway, shortenerInteractor, generatorForCollection);
    }

    private ShortenerInteractor shortenerInteractorSetUp() {
        IdGeneratorFake generator = new IdGeneratorFake();
        urlGateway = new UrlGatewayFake();
        generator.add("abcd", "efgV21", "mknl");

        return  new ShortenerInteractor(urlGateway, generator);

    }

    @Test
    public void shouldReturnEmptyOnNonExistingCollection(){

        Optional<UrlCollection> result = sut.getById("NON-EXISTING-COLLECTION");

        assertEquals(Optional.empty(), result);
    }

    @Test
    public void shouldReturnResultOnExistingCollection(){
        collectionGateway.create("absd", List.of(new ShortenedURL("http://test", "ab1234")));
        UrlCollection collection = sut.getById("absd").get();

        assertEquals("absd", collection.getId());
        assertEquals("http://test", collection.getShortenedURLS().getFirst().getUrl());
    }

    @Test
    public void shouldReturnCreatedCollection(){
        generatorForCollection.add("abcde");
        List<ShortenedURL> shortenedURLS = createShortenedUrlListHelper(List.of("http/test1", "http/test2"));
        UrlCollection collection = sut.create(shortenedURLS);

        assertEquals("abcde", collection.getId());
    }
    @Test
    public void shouldCreateDifferentCollections(){
        generatorForCollection.add("abcde", "abcde", "abcde", "efgmln");
        List<ShortenedURL> shortenedURLS = createShortenedUrlListHelper(List.of("http/test1", "http/test2"));
        UrlCollection collection1 = sut.create(shortenedURLS);
        UrlCollection collection2 = sut.create(shortenedURLS);

        assertEquals("abcde", collection1.getId());
        assertEquals("efgmln", collection2.getId());
    }
    @Test
    public void shouldCreateCollectionWithShortenedUrls(){
        generatorForCollection.add("abcde");
        List<ShortenedURL> shortenedURLS = createShortenedUrlListHelper(List.of("http/test1", "http/test4"));
        shortenedURLS.add(ShortenedURL.builder().url("http/test3").build());

        UrlCollection collection = sut.create(shortenedURLS);

        for(ShortenedURL shortenedUrl : collection.getShortenedURLS()){
            assertNotEquals(Optional.empty(), urlGateway.getById(shortenedUrl.getId()));
        }

    }


    private List<ShortenedURL> createShortenedUrlListHelper(List<String> urls) {
        return new ArrayList<>(urls.stream().map(shortenerInteractor::create).toList());
    }
}
