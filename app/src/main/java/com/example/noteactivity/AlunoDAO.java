package com.example.noteactivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {

    private NoteDB noteDB;
    private SQLiteDatabase dbData;

    public AlunoDAO(Context context){

        noteDB  = new NoteDB(context);
        dbData = noteDB.getWritableDatabase();

    }

    public long insert(Aluno aluno){
        ContentValues cv = new ContentValues();
        cv.put("nome",aluno.getNome());
        cv.put("cpf",aluno.getCpf());
        cv.put("telephone",aluno.getTelephone());
        return dbData.insert("aluno",   null,cv);
    }

    public List<Aluno> obterTodos(){
        List<Aluno> alunos =  new ArrayList<>();
        Cursor cursor = dbData.query("aluno",new String[]{"id","nome","cpf","telephone"},
                null,null,null, null,null);

        while ((cursor.moveToNext())){
            Aluno a = new Aluno();
            a.setId(cursor.getInt(0));
            a.setNome(cursor.getString(1));
            a.setCpf(cursor.getString(2));
            a.setTelephone(cursor.getString(3));
            alunos.add(a);
        }
        return alunos;
    }
    public void Delete(Aluno aluno){
        dbData.delete("aluno","id = ?",new String[]{aluno.getId().toString()});
    }
    public void UpData(Aluno aluno){
        ContentValues cv = new ContentValues();
        cv.put("nome",aluno.getNome());
        cv.put("cpf",aluno.getCpf());
        cv.put("telephone",aluno.getTelephone());
        dbData.update("aluno",cv,"id = ?",new String[]{aluno.getId().toString()});
    }
}
