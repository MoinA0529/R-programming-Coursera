import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class EbReportDBConfigurationTest {

    @Mock
    private Environment env;

    private EbReportDBConfiguration config;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        config = new EbReportDBConfiguration();
        config.setEnv(env);
    }

    @Test
    void testUserDataSource() {
        // Act
        DataSource dataSource = config.userDataSource();
        
        // Assert
        assertNotNull(dataSource, "DataSource should not be null");
    }
}
