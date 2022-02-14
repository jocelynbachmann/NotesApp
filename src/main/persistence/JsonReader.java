package persistence;

import model.Note;
import model.NotesList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a writer that writes JSON representation of noteslist to file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads noteslist from file and returns it;
    // throws IOException if an error occurs reading data from file
    public NotesList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseNotesList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses noteslist from JSON object and returns it
    private NotesList parseNotesList(JSONObject jsonObject) {
        NotesList nl = new NotesList();
        addNotes(nl, jsonObject);
        return nl;
    }

    // MODIFIES: nl
    // EFFECTS: parses notes from JSON object and adds them to noteslist
    private void addNotes(NotesList nl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("notes");
        for (Object json : jsonArray) {
            JSONObject nextNote = (JSONObject) json;
            addNote(nl, nextNote);
        }
    }

    // MODIFIES: nl
    // EFFECTS: parses note from JSON object and adds it to noteslist
    private void addNote(NotesList nl, JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        String text = jsonObject.getString("text");
        Note note = new Note(title, text);
        nl.addNote(note);
    }
}