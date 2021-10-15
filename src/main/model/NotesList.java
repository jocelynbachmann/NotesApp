package model;

import java.util.LinkedList;
import java.util.List;

// Represents a list of notes, or an empty list if no notes have been created
public class NotesList {
    private LinkedList<Note> notesList;

    // EFFECTS: creates an empty notes list
    public NotesList() {
        notesList = new LinkedList<>();
    }

    // MODIFIES: this
    // EFFECTS: creates a new note (adds given note to notesList)
    public void addNote(Note note) {
        notesList.add(note);

    }

    // REQUIRES: notesList is not empty
    // MODIFIES: this
    // EFFECTS: removes the given note from the list of notes
    public void deleteNote(Note note) {
        notesList.remove(note);
    }

    // EFFECTS: filters a list of notes for only those containing the given string and returns the filtered list
    public LinkedList<Note> search(String searchInput) {
        LinkedList<Note> searchList = new LinkedList<>();
        for (Note note : notesList) {
            if (note.contains(searchInput)) {
                searchList.add(note);
            }
        }
        return searchList;
    }

    // EFFECTS: returns true if the list is empty, false otherwise
    public boolean isEmpty() {
        return notesList.size() == 0;
    }

    public List<Note> getNotesList() {
        return (List<Note>) notesList.clone();
    }

}
