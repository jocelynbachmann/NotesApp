package model;

import java.util.LinkedList;
import java.util.List;

// Represents a list of notes, or an empty list if no notes have been created
public class NotesList {
    public LinkedList<Note> notesList;
    //TODO: it says it has to be private

    // EFFECTS: creates an empty notes list
    public NotesList() {
        notesList = new LinkedList<>();
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    public void addAndSaveNote(Note note) {
        notesList.add(note);
        //TODO: add save stuff
    }

    // REQUIRES: notesList is not empty
    // MODIFIES: this
    // EFFECTS: removes the given note from the list of notes
    public void deleteNote(Note note) {
        notesList.remove(note);
    }

    // EFFECTS: filters a list of notes for only those containing the given string and returns this filtered list
    public List<Note> search(String searchInput) {
        LinkedList<Note> searchList = new LinkedList<>();
        for (Note note : notesList) {
            if (note.contains(searchInput)) {
                searchList.add(note);
            }
        }
        return searchList;
    }

}
