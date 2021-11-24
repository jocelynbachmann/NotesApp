package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a note that has a title and a body of text
public class Note implements Writable {
    private String text;
    private String title;

    // REQUIRES: textInput and titleInput have non-zero lengths
    // EFFECTS: note title is set to titleInput, text in the note is set to textInput
    public Note(String titleInput, String textInput) {
        text = textInput;
        title = titleInput;
    }

    // EFFECTS: returns true if the title or text of a note contains the given string, false otherwise
    public boolean contains(String searchInput) {
        return title.contains(searchInput) || text.contains(searchInput);
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public void setTitle(String newTitle) {
        EventLog.getInstance().logEvent(new Event("Set note title to: " + newTitle));
        title = newTitle;
    }

    public void setText(String newText) {
        EventLog.getInstance().logEvent(new Event("Set note text to: " + newText));
        text = newText;
    }

    // EFFECTS: returns true if the given note is the same as this (i.e. has the same title and text),
    //          false otherwise
    public boolean equals(Note note) {
        return title.equals(note.title) && text.equals(note.text);
    }

    @Override
    // EFFECTS: returns the note title as a string
    public String toString() {
        return this.getTitle();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("text", text);
        return json;
    }
}
