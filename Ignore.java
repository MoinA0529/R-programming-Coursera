import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class FAToolServiceTest {

    @Mock
    private ExternalServiceConfig externalServiceConfig;

    private FAToolService faToolService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        faToolService = new FAToolService(externalServiceConfig);
    }

    @Test
    void searchRepCodeOwnersByECN_ValidECN_ReturnsDataItems() throws DocCenterWebAppException {
        // Arrange
        String ecn = "1234";
        RepCodeOwnersSearchResponseDetails responseDetails = createResponseDetails();
        when(externalServiceConfig.invokeAPIGEE(eq(HttpMethod.POST), eq(ExternalService.FA_TOOL),
                eq("/api/repCodeOwners/search/v1"), eq("{}"), isNull(), any(MultiValueMap.class),
                isNull(), any(ParameterizedTypeReference.class))).thenReturn(Mono.just(responseDetails));

        // Act
        List<DataItem> result = faToolService.searchRepCodeOwnersByECN(ecn);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        DataItem dataItem = result.get(0);
        assertEquals("docSpaceId1", dataItem.getDocSpaceId());
        assertEquals(2, dataItem.getMembers().size());
    }

    @Test
    void searchRepCodeOwnersByECN_NullResponse_ReturnsEmptyList() throws DocCenterWebAppException {
        // Arrange
        String ecn = "1234";
        when(externalServiceConfig.invokeAPIGEE(eq(HttpMethod.POST), eq(ExternalService.FA_TOOL),
                eq("/api/repCodeOwners/search/v1"), eq("{}"), isNull(), any(MultiValueMap.class),
                isNull(), any(ParameterizedTypeReference.class))).thenReturn(Mono.empty());

        // Act
        List<DataItem> result = faToolService.searchRepCodeOwnersByECN(ecn);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void searchRepCodeOwnersByECN_ExceptionThrown_PropagatesException() {
        // Arrange
        String ecn = "1234";
        DocCenterWebAppException exception = new DocCenterWebAppException("Error");
        when(externalServiceConfig.invokeAPIGEE(eq(HttpMethod.POST), eq(ExternalService.FA_TOOL),
                eq("/api/repCodeOwners/search/v1"), eq("{}"), isNull(), any(MultiValueMap.class),
                isNull(), any(ParameterizedTypeReference.class))).thenThrow(exception);

        // Act & Assert
        assertThrows(DocCenterWebAppException.class, () -> faToolService.searchRepCodeOwnersByECN(ecn));
    }

    private RepCodeOwnersSearchResponseDetails createResponseDetails() {
        RepCodeOwnersSearchResponseDetails responseDetails = new RepCodeOwnersSearchResponseDetails();
        List<RepCodeOwnersSearchResponse> data = new ArrayList<>();
        RepCodeOwnersSearchResponse response = new RepCodeOwnersSearchResponse();
        response.setDocSpaceId("docSpaceId1");
        List<MemberData> members = new ArrayList<>();
        members.add(new MemberData());
        members.add(new MemberData());
        response.setMembers(members);
        data.add(response);
        responseDetails.setData(data);
        return responseDetails;
    }
}
