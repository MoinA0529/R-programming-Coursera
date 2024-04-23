import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
class EbReportDBConfigurationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void testUserDataSource() {
        // Arrange
        EbReportDBConfiguration config = new EbReportDBConfiguration();
        
        // Act
        DataSource dataSource = config.userDataSource();
        
        // Assert
        assertNotNull(dataSource, "DataSource should not be null");
    }

    @Test
    void testEntityManagerFactoryBean() {
        // Act
        DataSource dataSource = applicationContext.getBean("ebreportdbDatasource", DataSource.class);
        
        // Assert
        assertNotNull(dataSource, "DataSource bean should not be null");
    }
}
