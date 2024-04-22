import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class DocumentServiceTest {

    @Mock
    private FAServiceIntegration faServiceIntegration;

    @InjectMocks
    private DocumentService documentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getDocSpaceDetail_shouldReturnDocumentSpaceDetail() {
        // Given
        String docSpaceId = "123";
        DocumentSpaceDetail expectedDetail = new DocumentSpaceDetail();
        expectedDetail.setId(docSpaceId);
        expectedDetail.setName("Document Space 1");

        when(faServiceIntegration.getDocSpaceDetailByDocSpaceId(docSpaceId)).thenReturn(expectedDetail);

        // When
        DocumentSpaceDetail actualDetail = documentService.getDocSpaceDetail(docSpaceId);

        // Then
        assertEquals(expectedDetail, actualDetail);
        verify(faServiceIntegration, times(1)).getDocSpaceDetailByDocSpaceId(docSpaceId);
    }

    @Test
    void getDocSpaceDetail_shouldReturnNull_whenDocumentSpaceDetailNotFound() {
        // Given
        String docSpaceId = "456";

        when(faServiceIntegration.getDocSpaceDetailByDocSpaceId(docSpaceId)).thenReturn(null);

        // When
        DocumentSpaceDetail actualDetail = documentService.getDocSpaceDetail(docSpaceId);

        // Then
        assertNull(actualDetail);
        verify(faServiceIntegration, times(1)).getDocSpaceDetailByDocSpaceId(docSpaceId);
    }
}

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DocumentServiceTest {

    @Mock
    private FAServiceIntegration faServiceIntegration;

    @InjectMocks
    private DocumentService documentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getDocSpaceList_shouldReturnDocumentSpaceDetails() {
        // Given
        String ecn = "123";
        List<DocumentSpaceDetail> expectedDetails = new ArrayList<>();
        expectedDetails.add(new DocumentSpaceDetail());
        expectedDetails.add(new DocumentSpaceDetail());

        when(faServiceIntegration.getDocSpaceListByECN(ecn)).thenReturn(expectedDetails);

        // When
        List<DocumentSpaceDetail> actualDetails = documentService.getDocSpaceList(ecn);

        // Then
        assertEquals(expectedDetails, actualDetails);
        verify(faServiceIntegration, times(1)).getDocSpaceListByECN(ecn);
    }

    @Test
    void getDocSpaceList_shouldReturnEmptyList_whenNoDocumentSpaceDetailsFound() {
        // Given
        String ecn = "456";
        List<DocumentSpaceDetail> expectedDetails = new ArrayList<>();

        when(faServiceIntegration.getDocSpaceListByECN(ecn)).thenReturn(expectedDetails);

        // When
        List<DocumentSpaceDetail> actualDetails = documentService.getDocSpaceList(ecn);

        // Then
        assertEquals(expectedDetails, actualDetails);
        verify(faServiceIntegration, times(1)).getDocSpaceListByECN(ecn);
    }
}
@Test
void addSpecificRequestHeaderValues_shouldAddHeadersToHttpHeaders() {
    // Arrange
    String token = "your-token";
    when(oauthService.getToken()).thenReturn(token);

    // Act
    Consumer<HttpHeaders> headerConsumer = (Consumer<HttpHeaders>) ReflectionTestUtils.invokeMethod(
            faServiceIntegration, "addSpecificRequestHeaderValues", token);

    HttpHeaders headers = new HttpHeaders();
    headerConsumer.accept(headers);

    // Assert
    assertTrue(headers.containsKey("X-WF-CLIENT-ID"));
    assertEquals("1WOCA", headers.getFirst("X-WF-CLIENT-ID"));

    assertTrue(headers.containsKey("X-WF-REQUEST-DATE"));
    assertNotNull(headers.getFirst("X-WF-REQUEST-DATE"));

    assertTrue(headers.containsKey(CONTENT_TYPE));
    assertEquals(MediaType.APPLICATION_JSON_VALUE, headers.getFirst(CONTENT_TYPE));

    assertTrue(headers.containsKey(AUTHORIZATION));
    assertEquals("Bearer " + token, headers.getFirst(AUTHORIZATION));

    assertTrue(headers.containsKey("X-REQUEST-ID"));
    assertNotNull(headers.getFirst("X-REQUEST-ID"));
    assertTrue(UUID.fromString(headers.getFirst("X-REQUEST-ID")) instanceof UUID);

    assertTrue(headers.containsKey("X-CORRELATION-ID"));
    assertNotNull(headers.getFirst("X-CORRELATION-ID"));
    assertTrue(UUID.fromString(headers.getFirst("X-CORRELATION-ID")) instanceof UUID);

    assertTrue(headers.containsKey("x-api-key"));
    assertEquals(faServiceIntegration.clientId, headers.getFirst("x-api-key"));
}


d(), actualDetails.get(0).getId());        assertEquals(expectedDetails.get(0).getCustomerEcn(), actualDetails.get(0).getCustomerEcn());
        assertEquals(expectedDetails.get(0).getStatus(), actualDetails.get(0).getStatus());
        assertEquals(expectedDetails.get(1).getId(), actualDetails.get(1).getId());
        assertEquals(expectedDetails.get(1).getCustomerEcn(), actualDetails.get(1).getCustomerEcn());
        assertEquals(expectedDetails.get(1).getStatus(), actualDetails.get(1).getStatus());
    }

    // Add more test cases for different scenarios
}
