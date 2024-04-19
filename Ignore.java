import com.google.gson.GsonBuilder;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestPropertySource(properties = "fa.baseUrl=http://localhost:8092")
class FAServiceIntegrationTest {

    private static MockWebServer mockWebServer;
    private static ExternalServiceConfig externalServiceConfig;

    @Qualifier("faWebClient")
    private WebClient webClient;

    @Autowired
    OauthService oauthService;

    @BeforeAll
    static void setup() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start(8092);
        externalServiceConfig = new ExternalServiceConfig(WebClient.create());
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void getDocSpaceListByECN_shouldReturnDocumentSpaceDetails() {
        // Arrange
        String ecn = "123";
        String responseJson = "{\"data\": [{\"id\": \"space1\", \"customerEcn\": \"123\", \"status\": \"active\"}, {\"id\": \"space2\", \"customerEcn\": \"123\", \"status\": \"inactive\"}]}";
        DocumentSpaceDetail space1 = new DocumentSpaceDetail();
        space1.setId("space1");
        space1.setCustomerEcn("123");
        space1.setStatus("active");

        DocumentSpaceDetail space2 = new DocumentSpaceDetail();
        space2.setId("space2");
        space2.setCustomerEcn("123");
        space2.setStatus("inactive");

        List<DocumentSpaceDetail> expectedDetails = Arrays.asList(space1, space2);

        mockWebServer.enqueue(new MockResponse()
                .setBody(responseJson)
                .addHeader("Content-Type", "application/json"));

        FAServiceIntegration faServiceIntegration = new FAServiceIntegration(externalServiceConfig, oauthService);

        // Act
        List<DocumentSpaceDetail> actualDetails = faServiceIntegration.getDocSpaceListByECN(ecn);

        // Assert
        assertEquals(expectedDetails.size(), actualDetails.size());
        assertEquals(expectedDetails.get(0).getId(), actualDetails.get(0).getId());
        assertEquals(expectedDetails.get(0).getCustomerEcn(), actualDetails.get(0).getCustomerEcn());
        assertEquals(expectedDetails.get(0).getStatus(), actualDetails.get(0).getStatus());
        assertEquals(expectedDetails.get(1).getId(), actualDetails.get(1).getId());
        assertEquals(expectedDetails.get(1).getCustomerEcn(), actualDetails.get(1).getCustomerEcn());
        assertEquals(expectedDetails.get(1).getStatus(), actualDetails.get(1).getStatus());
    }

    // Add more test cases for different scenarios
}
