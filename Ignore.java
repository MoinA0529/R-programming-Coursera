@Test
void testUpdateFileDetails() {
    // Arrange
    String id = "60a1c1d1c2d3e4f5g6h7i8j9"; // Example ObjectId as a string
    ObjectId objectId = new ObjectId(id);
    Fileupload existingFileupload = new Fileupload();
    existingFileupload.set_id(objectId);
    existingFileupload.setFileName("existing.txt");
    existingFileupload.setFileCategory("documents");
    existingFileupload.setFileSize(1024L);
    existingFileupload.setFileState("uploaded");
    existingFileupload.setNoOfChunks(1);
    existingFileupload.setUserId("user123");
    when(repository.findById(objectId)).thenReturn(Optional.of(existingFileupload));

    Fileupload updatedFileupload = new Fileupload();
    updatedFileupload.setFileName("updated.txt");
    updatedFileupload.setFileCategory("images");
    updatedFileupload.setFileSize(2048L);
    updatedFileupload.setFileState("processed");
    updatedFileupload.setNoOfChunks(2);
    updatedFileupload.setUserId("user456");

    Fileupload expectedFileupload = new Fileupload();
    expectedFileupload.set_id(objectId);
    expectedFileupload.setFileName("updated.txt");
    expectedFileupload.setFileCategory("images");
    expectedFileupload.setFileSize(2048L);
    expectedFileupload.setFileState("processed");
    expectedFileupload.setNoOfChunks(2);
    expectedFileupload.setUserId("user456");
    when(repository.save(any(Fileupload.class))).thenReturn(expectedFileupload);

    // Act
    Fileupload result = mongoService.updateFileDetails(id, updatedFileupload);

    // Assert
    assertNotNull(result);
    assertEquals(expectedFileupload, result);
    verify(repository, times(1)).findById(objectId);
    verify(repository, times(1)).save(any(Fileupload.class));
}
