package com.dijaelramos.agenda;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import android.widget.*;

public class MainActivity extends AppCompatActivity {

    EditText et_nome, et_matricula;
    Button btn_gravar, btn_consultar, btn_fechar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_nome = (EditText) findViewById(R.id.et_nome_consulta);
        et_matricula = (EditText) findViewById(R.id.et_matricula_consulta);

        btn_gravar = (Button) findViewById(R.id.btn_anterior);
        btn_consultar = (Button) findViewById(R.id.btn_proximo);
        btn_fechar = (Button) findViewById(R.id.btn_voltar_consulta);

        Banco.abrirBanco(this);
        Banco.abrirOuCriarTabela(this);
        Banco.fecharDB();
    }

    public void inserirRegistro(View v){
        String st_nome, st_matricula;
        st_nome=et_nome.getText().toString();
        st_matricula=et_matricula.getText().toString();
        if (st_nome=="" || st_matricula=="") {
            TelaMsg.mostrar("Campos estão vazios", this);
            return;
        }

        Banco.inserirRegistro(st_nome, st_matricula, this);

        et_nome.setText(null);
        et_matricula.setText(null);
    }
    public void abrir_tela_consulta(View v) {
        Intent it_tela_consulta=new Intent(this, TelaConsulta.class);
        startActivity(it_tela_consulta);
    }

    public void fechar_tela(View v) {
        this.finish();
    }
}