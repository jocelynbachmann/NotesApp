package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class NotesListTest {
    Note dog;
    Note groceries;
    Note homework;
    NotesList emptyList;
    NotesList notesList;
    NotesList searchList;

    @BeforeEach
    public void setup() {
        dog = new Note("My Dog", "Today I adopted a dog.");
        groceries = new Note("Grocery List", "I need to buy apples, milk, and a dog bone.");
        homework = new Note("Anatomy Notes", "The femur is the strongest bone in the human body.");
        emptyList = new NotesList();
        notesList = new NotesList();
        notesList.addNote(dog);
        notesList.addNote(groceries);
        notesList.addNote(homework);
    }

    @Test
    public void testAddNote() {
        emptyList.addNote(dog);
        assertFalse(emptyList.isEmpty());
    }

    @Test
    public void testDeleteNote() {
        NotesList updatedNotes = new NotesList();
        updatedNotes.addNote(dog);
        updatedNotes.addNote(homework);
        notesList.deleteNote(groceries);
        assertEquals(updatedNotes.getNotesList().size(), notesList.getNotesList().size());
        for (int i = 0; i < updatedNotes.getNotesList().size(); i++) {
            assertTrue(updatedNotes.getNotesList().get(i).equals(notesList.getNotesList().get(i)));
        }
    }

    @Test
    public void testSuccessSearch() {
        searchList = new NotesList();
        searchList.addNote(groceries);
        searchList.addNote(homework);
        List<Note> results = notesList.search("bone");
        assertEquals(results.size(), searchList.getNotesList().size());
        for (int i = 0; i < results.size(); i++) {
            assertTrue(results.get(i).equals(searchList.getNotesList().get(i)));
        }
    }

    @Test
    public void testNoResultsSearch() {
        assertTrue(notesList.search("mud").isEmpty());
    }

    @Test
    public void testSuccessIsEmpty() {
        assertTrue(emptyList.isEmpty());
    }

    @Test
    public void testFailIsEmpty() {
        assertFalse(notesList.isEmpty());
    }
}
