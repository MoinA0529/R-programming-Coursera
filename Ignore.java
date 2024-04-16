import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;

class MongoServiceImplTest {

    @Mock
    private FileUploadRepository repository;

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private MongoServiceImpl mongoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() {
        // Arrange
        String id = "123";
        Fileupload fileupload = new Fileupload();
        fileupload.setFilename("test.txt");
        fileupload.setFileCategory("documents");
        fileupload.setFileState("uploaded");
        when(repository.findById(id)).thenReturn(Optional.of(fileupload));

        // Act
        Optional<Fileupload> result = mongoService.findById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(fileupload, result.get());
        verify(repository, times(1)).findById(id);
    }

    @Test
    void testInsertFileDeatils() {
        // Arrange
        Fileupload fileupload = new Fileupload();
        fileupload.setFilename("test.txt");
        fileupload.setFileCategory("documents");
        fileupload.setFileState("uploaded");

        // Act
        boolean result = mongoService.insertFileDeatils(fileupload);

        // Assert
        assertTrue(result);
        verify(repository, times(1)).save(fileupload);
    }
}
