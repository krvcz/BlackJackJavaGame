import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;



public class CardTest {

    @Test
    public void shouldThrowExceptionNotValidColor() {
        try {
            new Card("A", "Z");
        } catch (NoValidColor e) {
            assertEquals("This Color doesn't exist in accepted list", e.getMessage());
        } catch (NoValidFigure e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void shouldThrowExceptionNotValidFigure() {
        try {
            new Card("King", "C");
        } catch (NoValidFigure e) {
            assertEquals("This type of Figure doesn't exist in accepted list", e.getMessage());
        } catch ( NoValidColor e) {
            throw new RuntimeException(e);
        }
    }






}
