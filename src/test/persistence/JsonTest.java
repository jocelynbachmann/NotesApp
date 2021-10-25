package persistence;

import model.Note;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkNote(String title, String text, Note note) {
        assertEquals(title, note.getTitle());
        assertEquals(text, note.getText());
    }
}
