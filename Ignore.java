public List<DataItem> searchRepCodeOwnersByECN(String ecn) throws DocCenterWebAppException {
    OperationalLogManager operationalLogManager = new OperationalLogManager();
    operationalLogManager.startDetailLogEntry("searchRepCodeOwnersByECN", "get",
            "Apigee/FATool", "Fetching the list of RepCode Owners", "");

    ParameterizedTypeReference<RepCodeOwnersSearchResponseDetails> typeReference = new ParameterizedTypeReference<>() {};

    RepCodeOwnersSearchResponseDetails response;

    try {
        Map<String, Object> eserPayload = new HashMap<>();
        eserPayload.put("data", new RepCodeOwnersSearchRequest());

        Map<String, Object> eserHeaders = new HashMap<>();
        eserHeaders.put("Authorization", "Bearer OAuth Token passed.");
        eserHeaders.put("ecn", ecn);

        eserLogManager.logRequestTransmit(eserHeaders, eserPayload);

        response = externalServiceConfig.invokeAPIGEE(HttpMethod.POST, ExternalService.FA_TOOL, "/api/repCodeOwners/search/v1",
                null, null, eserHeaders, eserPayload, typeReference).block();

        operationalLogManager.logDetailEntry(LOGGER, LogLevel.INFO, "Search RepCode Owners by ECN from FA Tool Service - Success", "", "", null);

        eserPayload.put("data", response);
        eserLogManager.logResponseReceived(eserHeaders, eserPayload);
    } catch (DocCenterWebAppException e) {
        eserLogManager.logError(e);
        operationalLogManager.logDetailEntry(LOGGER, LogLevel.ERROR, "Search RepCode Owners by ECN from FA Tool Service Failed", "12334", e.getMessage(), e);
        throw e;
    }

    List<DataItem> dataItems = new ArrayList<>();
    if (response != null && response.getData() != null) {
        for (RepCodeOwnersSearchResponse repCodeOwner : response.getData()) {
            DataItem dataItem = new DataItem();
            dataItem.setDocSpaceId(repCodeOwner.getDocSpaceId());
            dataItem.setMembers(repCodeOwner.getMembers());
            dataItems.add(dataItem);
        }
    }

    return dataItems;
}
