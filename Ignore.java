public List<DataItem> searchRepCodeOwnersByECN(String ecn) throws DocCenterWebAppException {
    ParameterizedTypeReference<RepCodeOwnersSearchResponseDetails> typeReference = new ParameterizedTypeReference<>() {};

    RepCodeOwnersSearchResponseDetails response;

    try {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("ecn", ecn);

        response = externalServiceConfig.invokeAPIGEE(HttpMethod.POST, ExternalService.FA_TOOL, "/api/repCodeOwners/search/v1",
                null, null, queryParams, null, typeReference).block();
    } catch (DocCenterWebAppException e) {
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
