package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        notesList.addAndSaveNote(dog);
        notesList.addAndSaveNote(groceries);
        notesList.addAndSaveNote(homework);
        searchList = new NotesList();
        searchList.addAndSaveNote(dog);
        searchList.addAndSaveNote(homework);
    }

    @Test
    public void testAddAndSaveNote() {
        emptyList.addAndSaveNote(dog);
        assertFalse(emptyList.isEmpty());
        //TODO: test save stuff
    }

    @Test
    public void testDeleteNote() {
        notesList.deleteNote(groceries);
        System.out.println(notesList);
        // TODO: can't test using size or anything bc it doesn't work on notesList
    }

    @Test
    public void testSuccessSearch() {
        //TODO: how do I do this
        assertEquals(notesList.search("bone"), searchList);
    }

    @Test
    public void testNoResultsSearch() {
        assertEquals(notesList.search("mud"), emptyList);
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
