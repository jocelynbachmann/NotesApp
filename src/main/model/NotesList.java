package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.LinkedList;
import java.util.List;

// Represents a list of notes, or an empty list if no notes have been created
public class NotesList implements Writable {
    private LinkedList<Note> notesList;

    // EFFECTS: creates an empty notes list
    public NotesList() {
        notesList = new LinkedList<>();
    }

    // MODIFIES: this
    // EFFECTS: creates a new note (adds given note to notesList)
    public void addNote(Note note) {
        EventLog.getInstance().logEvent(new Event("Added note: " + note.getTitle()));
        notesList.add(note);
    }

    // REQUIRES: notesList is not empty
    // MODIFIES: this
    // EFFECTS: removes the given note from the list of notes
    public void deleteNote(Note note) {
        EventLog.getInstance().logEvent(new Event("Deleted note: " + note.getTitle()));
        notesList.remove(note);
    }

    // REQUIRES: notesList is not empty
    // MODIFIES: this
    // EFFECTS: removes the note at the specified index
    public void deleteNoteAt(int index) {
        EventLog.getInstance().logEvent(new Event("Deleted note: " + notesList.get(index).getTitle()));
        notesList.remove(index);
    }

    // EFFECTS: filters a list of notes for only those containing the given string and returns the filtered list
    public LinkedList<Note> search(String searchInput) {
        LinkedList<Note> searchList = new LinkedList<>();
        for (Note note : notesList) {
            if (note.contains(searchInput)) {
                searchList.add(note);
            }
        }
        EventLog.getInstance().logEvent(new Event("Found search results for: " + searchInput));
        return searchList;
    }

    // EFFECTS: returns true if the list is empty, false otherwise
    public boolean isEmpty() {
        return notesList.size() == 0;
    }

    public List<Note> getNotesList() {
        return (List<Note>) notesList.clone();
    }

    public int getSize() {
        return getNotesList().size();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("notes", notesToJson());
        return json;
    }

    // EFFECTS: returns notes in this noteslist as a JSON array
    private JSONArray notesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Note n : notesList) {
            jsonArray.put(n.toJson());
        }

        return jsonArray;
    }

}
