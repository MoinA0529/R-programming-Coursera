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
