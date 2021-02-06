package com.example.notetakingwithroomdb.roomdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM " +Constants.TABLE_NAME_NOTE)
    List<NoteModel> getNotes();


    //insert the data
    @Insert
    long insertNote(NoteModel note);

    //update
    @Update
    void updateNote(NoteModel repos);

    //delete
    @Delete
    void deleteNote(NoteModel note);

    //Delete all from DB
    @Delete
    void DeleteNotes(NoteModel... note);
}
