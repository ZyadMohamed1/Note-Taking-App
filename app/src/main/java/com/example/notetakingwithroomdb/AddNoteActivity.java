package com.example.notetakingwithroomdb;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.notetakingwithroomdb.roomdb.NoteDatabase;
import com.example.notetakingwithroomdb.roomdb.NoteModel;
import com.google.android.material.textfield.TextInputEditText;

import java.io.Serializable;
import java.lang.ref.WeakReference;

public class AddNoteActivity extends AppCompatActivity {
    //variable
    private TextInputEditText et_title, et_content;
    private boolean update;
    Button button;

    //obj
    private NoteDatabase noteDatabase;
    private NoteModel note;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        //widgets
        et_title = findViewById(R.id.et_title);
        et_content =findViewById(R.id.et_content);
        button = findViewById(R.id.but_save);

        //objects
        noteDatabase = NoteDatabase.getInstance(AddNoteActivity.this);

        //check Data
        if((note = (NoteModel) getIntent().getSerializableExtra("note")) != null){
            getSupportActionBar().setTitle("Update Note");
            update = true;
            button.setText("Update");
            et_title.setText(note.getTitle());
            et_content.setText(note.getContent());
        }

        //Button Events
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //update note
                if(update){

                    note.setContent(et_content.getText().toString());
                    note.setTitle(et_title.getText().toString());
                    noteDatabase.getNoteDao().updateNote(note);
                    setResult(note, 2);

                    //create a new note
                }else{
                    note = new NoteModel(et_content.getText().toString(),
                            et_title.getText().toString());

                    new InsertTask(AddNoteActivity.this, note).execute();
                }
            }
        });

    }

    //set Result method
    private void setResult(NoteModel note, int flag){
        setResult(flag,new Intent().putExtra("note", (Serializable) note));
        finish();
    }




    //insert Task
    private static class InsertTask extends AsyncTask<Void, Void, Boolean> {

        private WeakReference<AddNoteActivity> activityWeakReference;
        private NoteModel note;


        InsertTask(AddNoteActivity context, NoteModel note){
            activityWeakReference = new WeakReference<>(context);

            this.note=note;
        }


        @Override
        protected Boolean doInBackground(Void... voids) {

            long j = activityWeakReference.get().noteDatabase.getNoteDao().insertNote(note);
            note.setNote_id(j);

            return true;
        }


        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(aBoolean){
                activityWeakReference.get().setResult(note, 1);
                activityWeakReference.get().finish();
            }
        }
    }



}