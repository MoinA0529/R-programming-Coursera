@Mock
private WebClient webClientMock;

@Mock
private WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock;

@Mock
private WebClient.RequestHeadersSpec requestHeadersSpecMock;

@Mock
private WebClient.ResponseSpec responseSpecMock;

@InjectMocks
private ExternalServiceConfig externalServiceConfig;

@Test
public void testInvokeExternalService_Success() throws DocCenterServiceException {
    // Arrange
    String serviceUrl = "/test";
    String key = "key";
    String value = "value";
    String expectedResponse = "Success Response";

    when(webClientMock.get()).thenReturn(requestHeadersUriSpecMock);
    when(requestHeadersUriSpecMock.uri(any(Function.class))).thenReturn(requestHeadersSpecMock);
    when(requestHeadersSpecMock.headers(any(Consumer.class))).thenReturn(requestHeadersSpecMock);
    when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
    when(responseSpecMock.onStatus(any(Predicate.class), any(Function.class))).thenReturn(responseSpecMock);
    when(responseSpecMock.bodyToMono(String.class)).thenReturn(Mono.just(expectedResponse));

    // Act
    Mono<String> result = externalServiceConfig.invokeExternalService(serviceUrl, key, value, headers -> {});

    // Assert
    StepVerifier.create(result)
            .expectNext(expectedResponse)
            .verifyComplete();
}
