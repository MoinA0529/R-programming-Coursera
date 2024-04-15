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
