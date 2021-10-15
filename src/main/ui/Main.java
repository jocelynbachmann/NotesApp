package ui;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            NotesApp notesApp = new NotesApp();
            notesApp.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
