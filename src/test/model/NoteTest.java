package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoteTest {
    Note dog;

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

}