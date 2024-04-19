import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = EbReportDBConfiguration.class)
class EbReportDBConfigurationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void testEbreportdbDatasource() {
        DataSource dataSource = applicationContext.getBean("ebreportdbDatasource", DataSource.class);
        assertThat(dataSource).isNotNull();
    }

    @Test
    void testEbreportdbEntityManager() {
        assertThat(applicationContext.containsBean("ebreportdbEntityManager")).isTrue();
    }

    @Test
    void testEbreportdbTransactionManager() {
        PlatformTransactionManager transactionManager = applicationContext.getBean("ebreportdbTransactionManager", PlatformTransactionManager.class);
        assertThat(transactionManager).isNotNull();
    }
}
