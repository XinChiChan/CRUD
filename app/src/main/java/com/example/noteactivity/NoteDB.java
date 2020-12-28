package com.example.noteactivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;



public class NoteDB extends SQLiteOpenHelper {

    private static final String name = "note.db";
    private static final int version = 1;

    public NoteDB(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE aluno(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome varchar(50), cpf varchar(50), telephone varchat(50))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
