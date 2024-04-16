import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DocCenterServiceExceptionTest {

    @Test
    void testDefaultConstructor() {
        // Arrange
        String expectedMessage = "com.example.DocCenterServiceException";

        // Act
        DocCenterServiceException exception = new DocCenterServiceException();

        // Assert
        Assertions.assertEquals(expectedMessage, exception.toString());
        Assertions.assertNull(exception.getCause());
    }

    @Test
    void testMessageAndCauseConstructor() {
        // Arrange
        String expectedMessage = "An error occurred";
        Throwable expectedCause = new RuntimeException("Cause of the error");

        // Act
        DocCenterServiceException exception = new DocCenterServiceException(expectedMessage, expectedCause);

        // Assert
        Assertions.assertEquals(expectedMessage, exception.getMessage());
        Assertions.assertEquals(expectedCause, exception.getCause());
    }

    @Test
    void testExceptionThrown() {
        // Arrange
        String expectedMessage = "Exception message";
        Throwable expectedCause = new IllegalArgumentException("Cause of the exception");

        // Act & Assert
        Assertions.assertThrows(DocCenterServiceException.class, () -> {
            throw new DocCenterServiceException(expectedMessage, expectedCause);
        }, expectedMessage);
    }
}
