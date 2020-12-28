package com.example.noteactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class LiseView extends AppCompatActivity {

    private ListView listView;
    private AlunoDAO dao;
    private List<Aluno> alunos;
    private List<Aluno> alunosFiltrados = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lise_view);

        listView = findViewById(R.id.List_View);
        dao = new AlunoDAO(this);
        alunos = dao.obterTodos();
        alunosFiltrados.addAll(alunos);
//        ArrayAdapter<Aluno> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,alunosFiltrados);
        AlunoAdapter alunoAdapter = new AlunoAdapter(this,alunosFiltrados);

        listView.setAdapter(alunoAdapter);

        registerForContextMenu(listView);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu, menu);

        SearchView sv = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                procuraAluno(s);
                return false;
            }
        });

        return true;
    }

        public void onCreateContextMenu(ContextMenu menu, View v,ContextMenu.ContextMenuInfo menuInfo) {
            super.onCreateContextMenu(menu,v,menuInfo);
            MenuInflater i = getMenuInflater();
            i.inflate(R.menu.menu_content,menu);
        }

        public void procuraAluno(String nome){
            alunosFiltrados.clear();
            for (Aluno a : alunos) {
                if (a.getNome().toLowerCase().contains(nome.toLowerCase())){
                    alunosFiltrados.add(a);
            }
        }
        listView.invalidateViews();
    }

    public void Delete(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Aluno alunoPosition = alunosFiltrados.get(menuInfo.position);
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("確定要刪除嗎?")
                .setNegativeButton("取消",null)
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        alunosFiltrados.remove(alunoPosition);
                        alunos.remove(alunoPosition);
                        dao.Delete(alunoPosition);
                        listView.invalidateViews();
                    }
                }).create();
                alertDialog.show();
    }
    public void UpData(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Aluno alunoAtualizar = alunosFiltrados.get(menuInfo.position);
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("aluno",alunoAtualizar);
        startActivity(intent);

    }
    public  void onClickMenu(MenuItem item) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    @Override
    public void onResume(){
        super.onResume();
        alunos = dao.obterTodos();
        alunosFiltrados.clear();
        alunosFiltrados.addAll(alunos);
        listView.invalidateViews();
    }

}