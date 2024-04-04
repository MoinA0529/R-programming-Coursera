@Mock
private CloudKeystoreService cloudKeystoreService;

@InjectMocks
private WebClientConfiguration webClientConfiguration;

@Test
public void testGetWebclient() throws Exception {
    // Arrange
    CachedKeyStore cachedKeyStore = new CachedKeyStore();
    cachedKeyStore.setKeystore(mock(KeyStore.class));
    cachedKeyStore.setPassword("password");
    when(cloudKeystoreService.cache().get("mykeystore")).thenReturn(cachedKeyStore);

    // Act
    WebClient webClient = webClientConfiguration.getWebclient();

    // Assert
    assertNotNull(webClient);
    assertEquals(webClientConfiguration.faBaseUrl, webClient.getWebClientConfiguration().getBaseUrl().toString());
}

@Test
public void testBuildCloseableHttpAsyncClient() throws Exception {
    // Arrange
    CachedKeyStore cachedKeyStore = new CachedKeyStore();
    cachedKeyStore.setKeystore(mock(KeyStore.class));
    cachedKeyStore.setPassword("password");
    when(cloudKeystoreService.cache().get("mykeystore")).thenReturn(cachedKeyStore);

    // Act
    CloseableHttpAsyncClient httpClient = webClientConfiguration.buildCloseableHttpAsyncClient();

    // Assert
    assertNotNull(httpClient);
    assertTrue(httpClient instanceof CloseableHttpAsyncClient);
}

@Test
public void testAddCommonRequestHeaderValues() {
    // Act
    Consumer<HttpHeaders> headerConsumer = webClientConfiguration.addCommonRequestHeaderValues();
    HttpHeaders headers = new HttpHeaders();
    headerConsumer.accept(headers);

    // Assert
    assertTrue(headers.containsKey(WF_SENDERMESSAGEID));
    assertTrue(headers.containsKey(WF_CREATIONTIMESTAMP));
    assertEquals(APPID, headers.getFirst(WF_SENDERAPPLICATIONID));
    assertEquals(LOCALHOST, headers.getFirst(WF_SENDERHOSTNAME));
}

@Test(expected = Exception.class)
public void testGetWebclientWithException() throws Exception {
    // Arrange
    when(cloudKeystoreService.cache().get("mykeystore")).thenThrow(new Exception());

    // Act
    webClientConfiguration.getWebclient();
}
