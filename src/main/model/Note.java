package model;

// Represents a note that has a title and a body of text
public class Note {
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
}
