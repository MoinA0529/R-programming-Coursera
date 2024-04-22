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
