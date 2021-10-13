package ui;

import model.Note;
import model.NotesList;

import java.util.LinkedList;
import java.util.Scanner;

// Notes Application
// This class references code from the TellerApp project
// Link: https://github.students.cs.ubc.ca/CPSC210/TellerApp/blob/master/src/main/ca/ubc/cpsc210/bank/ui/TellerApp.java
public class NotesApp {
    private Scanner input;
    private NotesList notesList;

    // EFFECTS: runs the notes application
    //TODO: and initializes what?
    public NotesApp() {
        notesList = new NotesList();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        runNotesApp();
    }

    // MODIFIES: this
    // EFFECTS: processes inputs from user
    private void runNotesApp() {
        LOOP:
        while (true) {
            displayMenu();
            String command = input.next();
            System.out.println(command);
            switch (command) {
                case "n":
                    handleN();
                    break;
                case "vn":
                    handleVn();
                    break;
                case "v":
                    System.out.println("Enter the number of the note you would like to view");
                    String noteNumToView = input.next();
                    int indexNumToView;
                    try {
                        indexNumToView = Integer.parseInt(noteNumToView) - 1;
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter the note according to its number");
                        break; //TODO: don't know what to do about this part when it's in a new fn
                    }
                    Note noteToView = notesList.notesList.get(indexNumToView);
                    System.out.println(noteToView.getTitle() + "\n" + noteToView.getText());
                    break;
                case "s":
                    handleS();
                    break;
                case "d":
                    System.out.println("Enter the number of the note you would like to delete");
                    String noteNumToDelete = input.next();
                    int indexNumToDelete;
                    try {
                        indexNumToDelete = Integer.parseInt(noteNumToDelete) - 1;
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter the note according to its number");
                        break; //TODO: same issue as V case above
                    }
                    Note noteToDelete = notesList.notesList.get(indexNumToDelete);
                    notesList.deleteNote(noteToDelete);
                    //TODO: insert call to "vn" function
                case "q":
                    break LOOP;
            }
        }
        System.out.println("\nGoodbye!");
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

    // MODIFIES: this and notesList TODO: confused
    // EFFECTS: creates a new note with title and text collected from user inputs
    private void handleN() {
        System.out.println("Input your title");
        String title = input.next();
        System.out.println("Write your note");
        String text = input.next();
        Note newNote = new Note(title, text);
        notesList.addAndSaveNote(newNote);
        System.out.println("New note was added");
    }

    // EFFECTS: prints a numbered list of the titles of notes in the list
    private void handleVn() {
        int index = 0;
        for (Note note: notesList.notesList) {
            index++;
            System.out.println(String.valueOf(index) + ") " + note.getTitle());
        }
    }

    // EFFECTS: prints a list of the titles of notes containing the given search input
    private void handleS() {
        System.out.println("What are you searching for?");
        String searchInput = input.next();
        for (Note note: notesList.search(searchInput)) {
            System.out.println(note.getTitle());
        }
    }
}
