package ui;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.*;
import javax.swing.event.*;

import model.Event;
import model.EventLog;
import model.Note;
import model.NotesList;
import persistence.JsonReader;
import persistence.JsonWriter;

// Creates the GUI for a notes app
// This class references code from the ListDemo example found at source linked below
// https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html
public class NotesUI extends JPanel implements ListSelectionListener {
    private JList list;
    private DefaultListModel listModel;

    private static final String JSON_STORE = "./data/notesList.json";
    private static final String NEW_NOTE = "New";
    private static final String SAVE_NOTE = "Save";
    private static final String DELETE_NOTE = "Delete";
    private static final String LOAD_NOTE = "Open";
    private static final String SPLASH_IMAGE = "icon.png";
    private static SplashScreen splashScreen;
    private JButton newButton;
    private JButton deleteButton;
    private JButton saveButton;
    private JButton loadButton;
    private JTextField noteTitle;
    private JTextArea noteText;
    private NotesList notesList;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: constructs window for notes app
    public NotesUI() {
        super(new BorderLayout());
        notesList = new NotesList();
        listModel = new DefaultListModel<Note>();

        createListAndScrollPane();
        createNewButton();
        createSaveButton();
        createLoadButton();
        createDeleteButton();
        createTitleField();
        createTextArea();
        createPanel();

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                createListAndScrollPane(), createTextArea());
        splitPane.setOneTouchExpandable(true);
        splitPane.setPreferredSize(new Dimension(1000, 500));
        add(splitPane, BorderLayout.CENTER);
        add(createPanel(), BorderLayout.PAGE_END);
    }

    // EFFECTS: creates the list of notes and puts it in a scroll pane
    private JScrollPane createListAndScrollPane() {
        list = new JList(listModel);

        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(10);

        JScrollPane listScrollPane = new JScrollPane(list);
        return listScrollPane;
    }

    // EFFECTS: creates button to add a new note
    private AddNoteListener createNewButton() {
        newButton = new JButton(NEW_NOTE);
        AddNoteListener addNoteListener = new AddNoteListener(newButton);

        newButton.setActionCommand(NEW_NOTE);
        newButton.addActionListener(addNoteListener);
        newButton.setEnabled(true);

        return addNoteListener;
    }

    // EFFECTS: creates button to save a note to file
    private void createSaveButton() {
        saveButton = new JButton(SAVE_NOTE);

        saveButton.setActionCommand(SAVE_NOTE);
        saveButton.addActionListener(new SaveNoteListener(saveButton));
        saveButton.setEnabled(true);
    }

    // EFFECTS: creates button to load notes from file
    private void createLoadButton() {
        loadButton = new JButton(LOAD_NOTE);

        loadButton.setActionCommand(LOAD_NOTE);
        loadButton.addActionListener(new LoadNoteListener(loadButton));
        loadButton.setEnabled(true);
    }

    // EFFECTS: creates button to delete a note
    private void createDeleteButton() {
        deleteButton = new JButton(DELETE_NOTE);

        deleteButton.setActionCommand(DELETE_NOTE);
        deleteButton.addActionListener(new DeleteNoteListener());
    }

    // EFFECTS: creates field to input title
    private void createTitleField() {
        noteTitle = new JTextField(20);

        if (notesList.isEmpty()) {
            noteTitle.setEnabled(false);
        } else {
            noteTitle.setEnabled(true);
        }
        noteTitle.addActionListener(createNewButton());
        noteTitle.getDocument().addDocumentListener(createNewButton());
    }

    // EFFECTS: creates area to input text
    private JPanel createTextArea() {
        noteText = new JTextArea();

        noteText.setLineWrap(true);
        noteText.setWrapStyleWord(true);

        JScrollPane noteTextScrollPane = new JScrollPane(noteText);
        JPanel noteTextPane = new JPanel();
        noteTextPane.setLayout(new BoxLayout(noteTextPane, BoxLayout.Y_AXIS));
        noteTextPane.add(noteTextScrollPane);

        return noteTextPane;
    }

    // EFFECTS: creates a panel that uses BoxLayout with New, Save, Load, and Delete buttons
    private JPanel createPanel() {
        JPanel buttonPane = new JPanel();

        buttonPane.setLayout(new BoxLayout(buttonPane,
                BoxLayout.LINE_AXIS));

        buttonPane.add(deleteButton);
        buttonPane.add(Box.createHorizontalStrut(20));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(20));
        buttonPane.add(noteTitle);
        buttonPane.add(newButton);
        buttonPane.add(saveButton);
        buttonPane.add(loadButton);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        return buttonPane;
    }

    // Represents a listener for deleting a note
    class DeleteNoteListener implements ActionListener {

        // EFFECTS: deletes selected note
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();
            listModel.remove(index);
            notesList.deleteNoteAt(index);

            int size = listModel.getSize();

            if (size == 0) {
                deleteButton.setEnabled(false);
            } else {
                if (index == listModel.getSize()) {
                    index--;
                }

                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
            }
        }
    }

    // Represents a listener for creating a new note
    class AddNoteListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        // MODIFIES: this
        // EFFECTS: creates a listener
        public AddNoteListener(JButton button) {
            this.button = button;
        }

        // EFFECTS: creates a new note
        public void actionPerformed(ActionEvent e) {
            Note note = new Note("New Note", "");

            listModel.addElement(note);
            notesList.addNote(note);
            noteText.setText("");
            loadButton.setEnabled(false);
            noteTitle.setEnabled(true);

            noteTitle.requestFocusInWindow();
            noteTitle.setText("");

            list.setSelectedIndex(listModel.getSize() - 1);
            list.ensureIndexIsVisible(listModel.getSize() - 1);
        }

        // EFFECTS: returns true if listModel contains a note with the given title
        protected boolean alreadyInList(String title) {
            return listModel.contains(title);
        }

        // Required by DocumentListener.
        public void insertUpdate(DocumentEvent e) {
        }

        // Required by DocumentListener.
        public void removeUpdate(DocumentEvent e) {
        }

        // Required by DocumentListener.
        public void changedUpdate(DocumentEvent e) {
        }
    }

    // Represents a listener for saving a note
    class SaveNoteListener implements ActionListener {
        private JButton button;

        // MODIFIES: this
        // EFFECTS: creates a listener
        public SaveNoteListener(JButton button) {
            this.button = button;
        }

        // EFFECTS: saves selected note
        public void actionPerformed(ActionEvent e) {
            try {
                int index = list.getSelectedIndex();
                Note note = notesList.getNotesList().get(index);

                note.setTitle(noteTitle.getText());
                note.setText(noteText.getText());
                listModel.set(index, note);

                save();
            } catch (IndexOutOfBoundsException ex) {
                Object[] options = {"Ok"};
                JOptionPane.showOptionDialog(null,
                        "Click 'New' to create a new note before entering the title and text!",
                        "How to Create a New Note", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                        options, options[0]);
            }
        }
    }

    // Represents a listener for loading notes
    class LoadNoteListener implements ActionListener {
        private JButton button;

        // MODIFIES: this
        // EFFECTS: creates a listener
        public LoadNoteListener(JButton button) {
            this.button = button;
        }

        // EFFECTS: loads notes from file
        public void actionPerformed(ActionEvent e) {
            load();
            loadButton.setEnabled(false);

            list.removeAll();
            for (Note n : notesList.getNotesList()) {
                listModel.addElement(n);
            }
            list.setSelectedIndex(notesList.getSize() - 1);
        }
    }

    // EFFECTS: changes what appears selected
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {

            if (list.getSelectedIndex() == -1) {
                deleteButton.setEnabled(false);

            } else {
                deleteButton.setEnabled(true);
                Note note = notesList.getNotesList().get(list.getSelectedIndex());
                noteText.setText(note.getText());
                noteTitle.setText(note.getTitle());
            }
        }
    }

    // EFFECTS: saves noteslist to file
    private void save() {
        jsonWriter = new JsonWriter(JSON_STORE);

        try {
            jsonWriter.open();
            jsonWriter.write(notesList);
            jsonWriter.close();
            System.out.println("Saved notes to" + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads noteslist from file
    private void load() {
        jsonReader = new JsonReader(JSON_STORE);

        try {
            notesList = jsonReader.read();
            System.out.println("Loaded notes from " + JSON_STORE);
            noteTitle.setEnabled(true);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: displays splash screen
    private static void showSplashScreen() {
        splashScreen = new SplashScreen(SPLASH_IMAGE);
        splashScreen.splash();
    }

    // EFFECTS: removes the splash screen
    private static final class SplashScreenCloser implements Runnable {
        @Override public void run() {
            splashScreen.dispose();
        }
    }

    // EFFECTS: creates and shows GUI for notes app, and prints EventLog to console when app is closed
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("ListDemo");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        WindowListener windowListener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int result = JOptionPane.showConfirmDialog(
                        frame, "Are you sure you want to close the application?");

                if (result == JOptionPane.OK_OPTION) {
                    for (Event event : EventLog.getInstance()) {
                        System.out.println(event.toString());
                        System.out.println("\n");
                    }
                    frame.setVisible(false);
                    frame.dispose();
                }
            }
        };

        JComponent newContentPane = new NotesUI();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        frame.addWindowListener(windowListener);
        frame.pack();
        frame.setVisible(true);
    }

    // EFFECTS: runs notes app
    public static void main(String[] args) {
        showSplashScreen();

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });

        EventQueue.invokeLater(new SplashScreenCloser());
    }
}
