package persistence;

import model.NotesList;
import model.Note;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            NotesList nl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyNotesList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyNotesList.json");
        try {
            NotesList nl = reader.read();
            assertEquals(0, nl.getSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderRegularNotesList() {
        JsonReader reader = new JsonReader("./data/testReaderRegularNotesList.json");
        try {
            NotesList nl = reader.read();
            List<Note> notes = nl.getNotesList();
            assertEquals(3, notes.size());
            checkNote("My Dog", "Today I adopted a dog.", notes.get(0));
            checkNote("Grocery List", "I need to buy apples, milk, and a dog bone.", notes.get(1));
            checkNote("Anatomy Notes", "The femur is the strongest bone in the human body.", notes.get(2));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}