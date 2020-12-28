package com.example.noteactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText nome;
    private EditText cpf;
    private EditText telephone;
    private AlunoDAO dao;
    private Aluno aluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nome = findViewById(R.id.editNome);
        cpf = findViewById(R.id.editCPF);
        telephone = findViewById(R.id.editTelephone);

        dao = new AlunoDAO(this);

        Intent intent = getIntent();
        if(intent.hasExtra("aluno")){
            aluno = (Aluno)intent.getSerializableExtra("aluno");
            nome.setText(aluno.getNome());
            cpf.setText(aluno.getCpf());
            telephone.setText(aluno.getTelephone());
        }
    }

    public void salvar(View v) {
        if (aluno == null) {
            aluno = new Aluno();
            aluno.setNome(nome.getText().toString());
            aluno.setCpf(cpf.getText().toString());
            aluno.setTelephone(telephone.getText().toString());
            long id = dao.insert(aluno);
            Toast.makeText(this, "成功添加" + id, Toast.LENGTH_SHORT).show();
            finish();
        }else{
            aluno.setNome(nome.getText().toString());
            aluno.setCpf(cpf.getText().toString());
            aluno.setTelephone(telephone.getText().toString());
            dao.UpData(aluno);
            Toast.makeText(this, "更新成功" ,Toast.LENGTH_SHORT).show();
            finish();
        }
    }

}
