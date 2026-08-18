package interactor;


import com.test.collection.UrlCollection;
import com.test.gateway.CollectionGateway;
import com.test.gateway.CollectionGatewayFake;
import com.test.interactor.CollectionInteractor;
import com.test.shortener.ShortenedURL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CollectionInteractorTest {

    private CollectionInteractor sut;
    private CollectionGateway collectionGateway;

    @BeforeEach
    void setUp() {
        collectionGateway = new CollectionGatewayFake();
        sut = new CollectionInteractor(collectionGateway);
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
}
