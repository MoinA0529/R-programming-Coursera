import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EnumeratesServiceImplTest {

    @Mock
    private EnumeratesRepository repository;

    @InjectMocks
    private EnumeratesServiceImpl service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByRowId_ExistingId_ReturnsEnumeratesEntity() {
        // Arrange
        Integer existingId = 1;
        EnumeratesEntity entity = new EnumeratesEntity();
        when(repository.findByRowId(existingId)).thenReturn(Optional.of(entity));

        // Act
        Optional<EnumeratesEntity> result = service.findByRowId(existingId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(entity, result.get());
        verify(repository, times(1)).findByRowId(existingId);
    }

    @Test
    public void testFindByRowId_NonExistingId_ReturnsEmptyOptional() {
        // Arrange
        Integer nonExistingId = 100;
        when(repository.findByRowId(nonExistingId)).thenReturn(Optional.empty());

        // Act
        Optional<EnumeratesEntity> result = service.findByRowId(nonExistingId);

        // Assert
        assertFalse(result.isPresent());
        verify(repository, times(1)).findByRowId(nonExistingId);
    }

    @Test
    public void testInsertEnumeratesDetails_SavesEntity() {
        // Arrange
        EnumeratesEntity entity = new EnumeratesEntity();
        boolean isSaved = true;

        // Act
        boolean result = service.insertEnumeratesDetails(entity);

        // Assert
        assertTrue(result);
        verify(repository, times(1)).save(entity);
    }
}


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class EnumeratesRepoTest {

    private EnumeratesRepo enumeratesRepo;
    private EntityManagerFactory entityManagerFactory;

    @BeforeEach
    public void setup() {
        entityManagerFactory = Persistence.createEntityManagerFactory("testPersistenceUnit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        enumeratesRepo = new EnumeratesRepoImpl(entityManager);
    }

    @Test
    public void testFindByRowId_ExistingId_ReturnsEnumerateEntity() {
        // Arrange
        Integer existingRowId = 1;
        EnumerateEntity entity = new EnumerateEntity();
        entity.setRowId(existingRowId);
        enumeratesRepo.save(entity);

        // Act
        Optional<EnumerateEntity> result = enumeratesRepo.findByRowId(existingRowId);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getRowId()).isEqualTo(existingRowId);
    }

    @Test
    public void testFindByRowId_NonExistingId_ReturnsEmptyOptional() {
        // Arrange
        Integer nonExistingRowId = 100;

        // Act
        Optional<EnumerateEntity> result = enumeratesRepo.findByRowId(nonExistingRowId);

        // Assert
        assertThat(result).isEmpty();
    }
}

import org.junit.jupiter.api.Test;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class DocSpaceDetailTest {

    @Test
    public void testGetRelationtype() {
        // Arrange
        DocSpaceDetail docSpaceDetail = new DocSpaceDetail();
        String expectedRelationType = "someRelationType";
        Field relationTypeField = ReflectionUtils.findField(DocSpaceDetail.class, "relationtype");
        ReflectionUtils.makeAccessible(relationTypeField);
        ReflectionUtils.setField(relationTypeField, docSpaceDetail, expectedRelationType);

        // Act
        String actualRelationType = docSpaceDetail.getRelationtype();

        // Assert
        assertEquals(expectedRelationType, actualRelationType);
    }

    @Test
    public void testSetRelationtype() {
        // Arrange
        DocSpaceDetail docSpaceDetail = new DocSpaceDetail();
        String relationType = "newRelationType";

        // Act
        docSpaceDetail.setRelationtype(relationType);

        // Assert
        Field relationTypeField = ReflectionUtils.findField(DocSpaceDetail.class, "relationtype");
        ReflectionUtils.makeAccessible(relationTypeField);
        String actualRelationType = (String) ReflectionUtils.getField(relationTypeField, docSpaceDetail);
        assertEquals(relationType, actualRelationType);
    }
    }
