package interactor;

import com.test.ShortenedURL;
import com.test.ShortenerInteractor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShortenerURLTests {

    @BeforeEach
    void setUp() {
        ShortenerInteractor sut = new ShortenerInteractor();
    }

    @Test
    public void shouldReturnEmptyOnNonExistingUrl(){
        ShortenerInteractor sut = new ShortenerInteractor();
        Optional<ShortenedURL> result = sut.getById("Non-Existing-Url");
        assertEquals(Optional.empty(), result);

    }

}
