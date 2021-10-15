package ui;

import model.Note;
import model.NotesList;

import java.io.IOException;
import java.util.Scanner;

// Notes Application
// This class references code from the TellerApp project
// Link: https://github.students.cs.ubc.ca/CPSC210/TellerApp/blob/master/src/main/ca/ubc/cpsc210/bank/ui/TellerApp.java
public class NotesApp {
    private Scanner input;
    private NotesList notesList;

    // EFFECTS: constructs a notes app
    public NotesApp() throws IOException {
        notesList = new NotesList();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // MODIFIES: this
    // EFFECTS: runs notes app by displaying menu to user and processing user inputs
    public void run() {
        boolean keepGoing = true;
        while (keepGoing) {
            displayMenu();
            String command = input.next();
            keepGoing = handleCommand(command);
        }
        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes inputs from user
    private boolean handleCommand(String command) {
        switch (command) {
            case "n":
                handleN();
                break;
            case "vn":
                handleVn();
                break;
            case "v":
                handleV();
                break;
            case "s":
                handleS();
                break;
            case "d":
                handleD();
                break;
            case "q":
                return false;
        }
        return true;
    }

    // MODIFIES: this
    // EFFECTS: creates a new note with title and text collected from user inputs
    private void handleN() {
        System.out.println("Input your title");
        String title = input.next();
        System.out.println("Write your note");
        String text = input.next();
        Note newNote = new Note(title, text);
        notesList.addNote(newNote);
        System.out.println("New note was added");
    }

    // EFFECTS: prints a numbered list of the titles of notes in the list
    private void handleVn() {
        int index = 0;
        for (Note note: notesList.getNotesList()) {
            index++;
            System.out.println(index + ") " + note.getTitle());
        }
    }

    // EFFECTS: if notesList is empty, tells user there are no notes to view,
    //          else opens (displays the title and text) the note that the user specifies
    private void handleV() {
        if (notesList.isEmpty()) {
            System.out.println("There are no notes to view");
        } else {
            System.out.println("Enter the number of the note you would like to view");
            String noteNumToView = input.next();
            int indexNumToView = 0;
            boolean failed = false;
            try {
                indexNumToView = Integer.parseInt(noteNumToView) - 1;
            } catch (NumberFormatException e) {
                System.out.println("Please enter the note according to its number");
                failed = true;
            }
            if (! failed) {
                Note noteToView = notesList.getNotesList().get(indexNumToView);
                System.out.println(noteToView.getTitle() + "\n" + noteToView.getText());
            }
        }
    }

    // EFFECTS: prints a list of the titles of notes containing the given search input
    private void handleS() {
        System.out.println("What are you searching for?");
        String searchInput = input.next();
        if (notesList.search(searchInput).isEmpty()) {
            System.out.println("There are no notes containing the term or phrase: " + searchInput);
        } else {
            for (Note note : notesList.search(searchInput)) {
                System.out.println(note.getTitle());
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: if notesList is empty, tells user there are no notes to delete,
    //          else deletes the specified note and displays updated list of notes
    private void handleD() {
        if (notesList.isEmpty()) {
            System.out.println("There are no notes to delete");
        } else {
            System.out.println("Enter the number of the note you would like to delete");
            String noteNumToDelete = input.next();
            int indexNumToDelete = 0;
            boolean failed = false;
            try {
                indexNumToDelete = Integer.parseInt(noteNumToDelete) - 1;
            } catch (NumberFormatException e) {
                System.out.println("Please enter the note according to its number");
                failed = true;
            }
            if (! failed) {
                Note noteToDelete = notesList.getNotesList().get(indexNumToDelete);
                notesList.deleteNote(noteToDelete);
                handleVn();
            }
        }
    }

    // EFFECTS: displays options menu to user
    private void displayMenu() {
        System.out.println("New Note (n)");
        System.out.println("View All Notes (vn)");
        System.out.println("View Note (v)");
        System.out.println("Search Notes (s)");
        System.out.println("Delete Note (d)");
        System.out.println("Quit the App (q)");
    }
}
