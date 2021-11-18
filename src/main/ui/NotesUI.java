package ui;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.*;
import javax.swing.event.*;

import model.Note;
import model.NotesList;
import persistence.JsonReader;
import persistence.JsonWriter;

public class NotesUI extends JPanel
        implements ListSelectionListener {
    private JList list;
    private DefaultListModel listModel;

    private static final String JSON_STORE = "./data/notesList.json";
    private static final String addNote = "New";
    private static final String saveNote = "Save";
    private static final String deleteNote = "Delete";
    private static SplashScreen splashScreen;
    private static final String SPLASH_IMAGE = "notes-8.png";
    private JButton deleteButton;
    private JTextField noteTitle;
    private JTextArea noteText;
    private NotesList notesList;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    public NotesUI() {
        super(new BorderLayout());

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        notesList = new NotesList();
        listModel = new DefaultListModel<Note>();
        listModel.addListDataListener(new NotesListDataListener());

        //Create the list and put it in a scroll pane.
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(10);
        JScrollPane listScrollPane = new JScrollPane(list);

        JButton addButton = new JButton(addNote);
        AddNoteListener addNoteListener = new AddNoteListener(addButton);
        addButton.setActionCommand(addNote);
        addButton.addActionListener(addNoteListener);
        addButton.setEnabled(true);

        JButton saveButton = new JButton(saveNote);
        SaveNoteListener saveNoteListener = new SaveNoteListener(saveButton);
        saveButton.setActionCommand(saveNote);
        saveButton.addActionListener(saveNoteListener);
        saveButton.setEnabled(true);

        deleteButton = new JButton(deleteNote);
        deleteButton.setActionCommand(deleteNote);
        deleteButton.addActionListener(new DeleteNoteListener());

        noteTitle = new JTextField(10);
        noteTitle.addActionListener(addNoteListener);
        noteTitle.getDocument().addDocumentListener(addNoteListener);

        noteText = new JTextArea();
        noteText.setLineWrap(true);
        noteText.setWrapStyleWord(true);
        noteText.getDocument().addDocumentListener(new EditNoteListener());
        JScrollPane noteTextScrollPane = new JScrollPane(noteText);

        //Create a panel that uses BoxLayout.
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane,
                BoxLayout.LINE_AXIS));
        buttonPane.add(deleteButton);
        buttonPane.add(Box.createHorizontalStrut(20));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(20));
        buttonPane.add(noteTitle);
        buttonPane.add(addButton);
        buttonPane.add(saveButton);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        JPanel noteTextPane = new JPanel();
        noteTextPane.setLayout(new BoxLayout(noteTextPane,
                BoxLayout.Y_AXIS));
        noteTextPane.add(noteTextScrollPane);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                listScrollPane, noteTextPane);
        splitPane.setOneTouchExpandable(true);

        add(splitPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);
    }


    class NotesListDataListener implements ListDataListener {
        public void contentsChanged(ListDataEvent e) {

        }
        // Sent when the contents of the list has changed in a way that's too complex to characterize with the previous
        // methods.

        public void intervalAdded(ListDataEvent e) {
        }
        // Sent after the indices in the index0,index1 interval have been inserted in the data model.

        public void intervalRemoved(ListDataEvent e) {
        }
        // Sent after the indices in the index0,index1 interval have been removed from the data model.
    }

    class DeleteNoteListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove whatever's selected.
            int index = list.getSelectedIndex();
            listModel.remove(index);
            notesList.deleteNoteAt(index);

            int size = listModel.getSize();

            if (size == 0) { //Nobody's left, disable firing.
                deleteButton.setEnabled(false);

            } else { //Select an index.
                if (index == listModel.getSize()) {
                    //removed item in last position
                    index--;
                }

                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
            }
        }
    }

    //This listener is shared by the text field and the hire button.
    class AddNoteListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public AddNoteListener(JButton button) {
            this.button = button;
        }

        //Required by ActionListener.
        public void actionPerformed(ActionEvent e) {
            //String title = noteTitle.getText();

            //User didn't type in a unique name...
//            if (title.equals("") || alreadyInList(title)) {
//                Toolkit.getDefaultToolkit().beep();
//                noteTitle.requestFocusInWindow();
//                noteTitle.selectAll();
//                return;
//            }

            int index = list.getSelectedIndex(); //get selected index
            if (index == -1) { //no selection, so insert at beginning
                index = 0;
            } else {           //add after the selected item
                index++;
            }

            //If we just wanted to add to the end, we'd do this:
            //listModel.addElement(employeeName.getText());
            Note note = new Note("New Note", "");
            listModel.insertElementAt(note, index);
            notesList.addNote(note);
            noteText.setText("");

            //Reset the text field.
            noteTitle.requestFocusInWindow();
            noteTitle.setText("");

            //Select the new item and make it visible.
            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
        }

        //This method tests for string equality. You could certainly
        //get more sophisticated about the algorithm.  For example,
        //you might want to ignore white space and capitalization.
        protected boolean alreadyInList(String name) {
            return listModel.contains(name);
        }

        //Required by DocumentListener.
        public void insertUpdate(DocumentEvent e) {
          // enableButton();
        }

        //Required by DocumentListener.
        public void removeUpdate(DocumentEvent e) {
           // handleEmptyTextField(e);
        }

        //Required by DocumentListener.
        public void changedUpdate(DocumentEvent e) {
//            if (!handleEmptyTextField(e)) {
//                enableButton();
//            }
        }

        private void enableButton() {
//            if (!alreadyEnabled) {
//                button.setEnabled(true);
//            }
        }

        private boolean handleEmptyTextField(DocumentEvent e) {
//            if (e.getDocument().getLength() <= 0) {
//                button.setEnabled(false);
//                alreadyEnabled = false;
//                return true;
//            }
            return false;
        }
    }

    class SaveNoteListener implements ActionListener {
        private JButton button;

        public SaveNoteListener(JButton button) {
            this.button = button;
        }

        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();
            Note note = notesList.getNotesList().get(index);
            note.setTitle(noteTitle.getText());
            note.setText(noteText.getText());
            listModel.set(index, note);
            save();
        }
    }

    class EditNoteListener implements ActionListener, DocumentListener {

        public EditNoteListener() {

        }

        //Required by ActionListener.
        public void actionPerformed(ActionEvent e) {
            System.out.println(noteText.getText());
        }

        //Required by DocumentListener.
        public void insertUpdate(DocumentEvent e) {

        }

        //Required by DocumentListener.
        public void removeUpdate(DocumentEvent e) {

        }

        //Required by DocumentListener.
        public void changedUpdate(DocumentEvent e) {
        }
    }

    //This method is required by ListSelectionListener.
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {

            if (list.getSelectedIndex() == -1) {
                //No selection, disable fire button.
                deleteButton.setEnabled(false);

            } else {
                //Selection, enable the fire button.
                deleteButton.setEnabled(true);
            }
        }
    }

    // EFFECTS: saves noteslist to file
    private void save() {
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
        try {
            notesList = jsonReader.read();
            System.out.println("Loaded notes from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // TODO: REQUIRES?
    // TODO: MODIFIES?
    // EFFECTS: displays splash screen
    private static void showSplashScreen() {
        splashScreen = new SplashScreen(SPLASH_IMAGE);
        splashScreen.splash();
    }

    // TODO: REQUIRES?
    // TODO: MODIFIES?
    // EFFECTS: removes the splash screen
    private static final class SplashScreenCloser implements Runnable {
        @Override public void run() {
            splashScreen.dispose();
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("ListDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new NotesUI();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        showSplashScreen();

        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });

        EventQueue.invokeLater(new SplashScreenCloser());
    }
}
