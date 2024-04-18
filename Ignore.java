
@Bean
public CloudkeystoreService cloudkeystoreService() {
    return new CloudkeystoreService().addCacheResolver(createCloudVenaficertificateCacheResolver());
}

private CloudVenaficertificateCacheResolver createCloudVenaficertificateCacheResolver() {
    String venariCertificateName = "venafi-certificates";
    return new CloudVenaficertificateCacheResolver(venariCertificateName).loadCache();
}


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Import(DocCenterWebSecurityConfig.class)
public class DocCenterWebSecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAllowAllRequests() throws Exception {
        mockMvc.perform(get("/any-url"))
                .andExpect(status().isOk());
    }

    @Test
    public void testCsrfDisabled() throws Exception {
        mockMvc.perform(post("/any-url")
                .contentType("application/json")
                .content("{}"))
                .andExpect(status().isOk());
    }
}


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DocCenterServiceExceptionTest {

    @Test
    void testDefaultConstructor() {
        // Arrange
        String expectedMessage = "com.example.DocCenterServiceException";

        // Act
        DocCenterServiceException exception = new DocCenterServiceException();

        // Assert
        Assertions.assertEquals(expectedMessage, exception.toString());
        Assertions.assertNull(exception.getCause());
    }

    @Test
    void testMessageAndCauseConstructor() {
        // Arrange
        String expectedMessage = "An error occurred";
        Throwable expectedCause = new RuntimeException("Cause of the error");

        // Act
        DocCenterServiceException exception = new DocCenterServiceException(expectedMessage, expectedCause);

        // Assert
        Assertions.assertEquals(expectedMessage, exception.getMessage());
        Assertions.assertEquals(expectedCause, exception.getCause());
    }

    @Test
    void testExceptionThrown() {
        // Arrange
        String expectedMessage = "Exception message";
        Throwable expectedCause = new IllegalArgumentException("Cause of the exception");

        // Act & Assert
        Assertions.assertThrows(DocCenterServiceException.class, () -> {
            throw new DocCenterServiceException(expectedMessage, expectedCause);
        }, expectedMessage);
    }
}
