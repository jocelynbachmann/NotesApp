package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoteTest {
    Note dog;
    Note diary;
    Note spot;
    Note groceries;

    @BeforeEach
    public void setup() {
        dog = new Note("My Dog", "Today I adopted a dog.");
    }

    @Test
    public void testSuccessContainsInTitle() {
        assertTrue(dog.contains("My"));
    }

    @Test
    public void testSuccessContainsInText() {
        assertTrue(dog.contains("adopted"));
    }

    @Test
    public void testSuccessContainsInTitleAndText() {
        assertTrue(dog.contains("dog"));
    }

    @Test
    public void testFailContains() {
        assertFalse(dog.contains("milk"));
    }

    @Test
    public void testGetTitle() {
        assertEquals("My Dog", dog.getTitle());
    }

    @Test
    public void testGetText() {
        assertEquals("Today I adopted a dog.", dog.getText());
    }

    @Test
    public void testSetTitle() {
        dog.setTitle("My Dog");
        assertEquals("My Dog", dog.getTitle());
    }

    @Test
    public void testSetText() {
        dog.setText("cats");
        assertEquals("cats", dog.getText());
    }

    @Test
    public void testSuccessEquals() {
        assertTrue(dog.equals(dog));
    }

    @Test
    public void testFailEqualsTitle() {
        diary = new Note("My Diary", "Today I adopted a dog.");
        assertFalse(diary.equals(dog));
    }

    @Test
    public void testFailEqualsText() {
        spot = new Note("My Dog", "My dog Spot likes to play fetch.");
        assertFalse(spot.equals(dog));
    }

    @Test
    public void testFailEqualsTitleAndText() {
        groceries = new Note("Grocery List", "I need to buy apples, milk, and a dog bone.");
        assertFalse(groceries.equals(dog));
    }

}