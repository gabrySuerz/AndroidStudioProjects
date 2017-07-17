package com.esame.note_15_06_2017.FirebaseStuff;

import com.esame.note_15_06_2017.Models.Note;
import com.esame.note_15_06_2017.Utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gabrysuerz on 22/06/17.
 */

public class SampleData {

    public static List<String> getSampleCategories() {
        List<String> categoryNames = new ArrayList<>();

        categoryNames.add("Family");
        categoryNames.add("Word");
        categoryNames.add("Productivity");
        categoryNames.add("Personal");
        categoryNames.add("Finance");
        categoryNames.add("Fitness");
        categoryNames.add("Blog Posts");
        categoryNames.add("Social Media");


        return categoryNames;

    }


    public static List<Note> getSampleNotes() {

        List<Note> notes = new ArrayList<>();
        //create the dummy note
        Note note1 = new Note();
        note1.setTitle("DisneyLand Trip");
        note1.setContent("We went to Disneyland today and the kids had lots of fun!");
        note1.setNoteType(Constants.NOTE_TYPE_AUDIO);
        notes.add(note1);


        //create the dummy note
        Note note2 = new Note();
        note2.setTitle("Gym Work Out");
        note2.setContent("I went to the Gym today and I got a lot of exercises");
        notes.add(note2);


        //create the dummy note
        Note note3 = new Note();
        note3.setTitle("Blog Post Idea");
        note3.setContent("I will like to write a blog post about how to make money online");
        note3.setNoteType(Constants.NOTE_TYPE_REMINDER);
        notes.add(note3);


        //create the dummy note
        Note note4 = new Note();
        note4.setTitle("Cupcake Recipe");
        note4.setContent("Today I found a recipe to make cup cake from www.google.");
        note4.setNoteType(Constants.NOTE_TYPE_TEXT);
        notes.add(note4);


        //create the dummy note
        Note note5 = new Note();
        note5.setTitle("Notes from Networking Event");
        note5.setContent("Today I attended a developer’s networking event and it was great");
        notes.add(note5);

        return notes;
    }
}
