package persistence;

import model.Note;
import model.NotesList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            NotesList nl = new NotesList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyNotesList() {
        try {
            NotesList nl = new NotesList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyNotesList.json");
            writer.open();
            writer.write(nl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyNotesList.json");
            nl = reader.read();
            assertEquals(0, nl.getSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterRegularNotesList() {
        try {
            NotesList nl = new NotesList();
            nl.addNote(new Note("My Dog","Today I adopted a dog."));
            nl.addNote(new Note("Grocery List", "I need to buy apples, milk, and a dog bone."));
            nl.addNote(new Note("Anatomy Notes","The femur is the strongest bone in the human body."));
            JsonWriter writer = new JsonWriter("./data/testWriterRegularNotesList.json");
            writer.open();
            writer.write(nl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterRegularNotesList.json");
            nl = reader.read();
            List<Note> notes = nl.getNotesList();
            assertEquals(3, notes.size());
            checkNote("My Dog", "Today I adopted a dog.", notes.get(0));
            checkNote("Grocery List", "I need to buy apples, milk, and a dog bone.", notes.get(1));
            checkNote("Anatomy Notes", "The femur is the strongest bone in the human body.", notes.get(2));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}