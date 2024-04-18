import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppDataAdapterTest {

    @Mock
    private Environment env;

    @InjectMocks
    private AppDataAdapter appDataAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void isActiveProfile_profileIsNull_returnsFalse() {
        boolean result = appDataAdapter.isActiveProfile(null);
        assertFalse(result);
    }

    @Test
    void isActiveProfile_profileIsNotActive_returnsFalse() {
        String profile = "dev";
        when(env.getActiveProfiles()).thenReturn(new String[]{"prod", "test"});

        boolean result = appDataAdapter.isActiveProfile(profile);
        assertFalse(result);
    }

    @Test
    void isActiveProfile_profileIsActive_returnsTrue() {
        String profile = "prod";
        when(env.getActiveProfiles()).thenReturn(new String[]{"prod", "test"});

        boolean result = appDataAdapter.isActiveProfile(profile);
        assertTrue(result);
    }
}
