# Note-Taking App

## Project Overview

This is a simple note-taking application that can be used by anyone, but is particularly useful for students, who 
may want to take notes in class or keep track of homework and due dates. Features include:
- Ability to create notes with titles and bodies of text, and view all notes in one place
- Option to **save and load** notes from file
- Option to delete notes

This project is of particular interest to me as I am a student who uses the iOS notes app often, but feel it
lacks some functionality. Though this project will cover only basic features, I would like to eventually 
build it into an application that functions more smoothly and offers more features than what I currently use.

## User Stories

- As a user, I want to be able to create and add a title and text to a note
- As a user, I want to be able to edit the title and text of a note after creating it
- As a user, I want to be able to delete a note
- As a user, I want to be able to view a list of all the notes I've created, and
 select a note to open (i.e. view the text of the note)
- As a user, I want to be able to save my notes to file 
- As a user, I want to be able to load my notes from file 

## Phase 4: Task 2
Tue Nov 23 21:29:30 PST 2021
Added note: dog

Tue Nov 23 21:29:30 PST 2021
Added note: bird

Tue Nov 23 21:29:30 PST 2021
Added note: cat

Tue Nov 23 21:29:30 PST 2021
Added note: hat

Tue Nov 23 21:29:30 PST 2021
Added note: rat

Tue Nov 23 21:29:30 PST 2021
Added note: bag

Tue Nov 23 21:29:30 PST 2021
Added note: yam

Tue Nov 23 21:29:30 PST 2021
Added note: carrot

Tue Nov 23 21:29:30 PST 2021
Added note: soup

Tue Nov 23 21:29:30 PST 2021
Added note: pop

Tue Nov 23 21:29:30 PST 2021
Added note: goblin

Tue Nov 23 21:29:30 PST 2021
Added note: ramb

Tue Nov 23 21:29:32 PST 2021
Added note: New Note

Tue Nov 23 21:29:47 PST 2021
Set note title to: yellow

Tue Nov 23 21:29:47 PST 2021
Set note text to: yellow flower

Tue Nov 23 21:29:57 PST 2021
Deleted note: hat

## Phase 4: Task 3
Note: the SplashScreen class was not included as I was told not to include static classes.

Overall, I think that my project is relatively well-structured and has very readable code and simple design.
Given more time, the only refactoring I can think to do relates to the relationship between NotesUI and Note:
- Currently, updating a note (including setting its initial title and text upon
creation) involves getting the inputs to the UI for the title and text fields, converting these inputs
into strings, and then updating the note, meaning you essentially have to keep the UI and note in 
sync manually. A better approach would be to bind the data between a note and the text and title fields of
the UI so that the synchronization happens automatically. Though I'm not sure exactly how I would do this,
I think that binding data can be done using specific Java libraries. 