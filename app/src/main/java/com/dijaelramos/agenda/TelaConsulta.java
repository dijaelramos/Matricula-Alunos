package com.dijaelramos.agenda;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import kotlin.text.UStringsKt;

public class TelaConsulta extends AppCompatActivity {

    EditText et_nome, et_matricula;
    Button btn_anterior, btn_proximo, btn_voltar, btn_editar, btn_apagar;
    SQLiteDatabase db=null;
    Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_consulta);

        et_nome=(EditText)findViewById(R.id.et_nome_consulta);
        et_matricula=(EditText)findViewById(R.id.et_matricula_consulta);

        btn_anterior=(Button)findViewById(R.id.btn_anterior);
        btn_proximo=(Button)findViewById(R.id.btn_proximo);
        btn_voltar=(Button)findViewById(R.id.btn_voltar_consulta);

        btn_editar=(Button)findViewById(R.id.btn_anterior);
        btn_apagar=(Button)findViewById(R.id.btn_anterior);

        cursor=Banco.buscarTodosDados(this);
        if(cursor.getCount()!=0){
            mostrarDados();
        }else {
            TelaMsg.mostrar("Nenhum registro encontrado", this);
        }

    }
    public void fechar_tela(View v) {
        this.finish();
    }
        public void proximoRegistro(View v){
        try{
            cursor.moveToNext();
            mostrarDados();
        }catch (Exception ex){
            if (cursor.isAfterLast()){
                TelaMsg.mostrar("Não há mais registros", this);
            }else {
                TelaMsg.mostrar("Erro ao navegar pelos registros", this);
            }
        }
    }

    public void registroAnterior(View v){
        try{
            cursor.moveToPrevious();
            mostrarDados();
        }catch (Exception ex){
            if (cursor.isBeforeFirst()){
                TelaMsg.mostrar("Não há mais registros", this);
            }else {
                TelaMsg.mostrar("Erro ao navegar pelos registros", this);
            }
        }
    }
    @SuppressLint("Range")
    public void mostrarDados(){
        et_nome.setText(cursor.getString(cursor.getColumnIndex("nome")));
        et_matricula.setText(cursor.getString(cursor.getColumnIndex("matricula")));
    }
    public void apagarRegistro(View v){

        int id=cursor.getInt(0);

        if (id == 0) {
            TelaMsg.mostrar("Campos estão vazios", this);
            return;
        }

        Banco.apagarRegistro(id, this);

        mostrarDados();
        et_nome.setText(null);
        et_matricula.setText(null);
        cursor.requery();
    }

    public void editarRegistro(View v){
        String st_nome, st_matricula;
        int id=cursor.getInt(0);

        st_nome=et_nome.getText().toString();
        st_matricula=et_matricula.getText().toString();

        if (st_nome.isEmpty() || st_nome.isEmpty()) {
            TelaMsg.mostrar("Campos estão vazios", this);
            return;
        }
        cursor.moveToPosition(st_nome.indexOf(st_nome));
        Banco.editarRegistro(id, st_nome, st_matricula, this);

        mostrarDados();
        et_nome.setText(null);
        et_matricula.setText(null);
        cursor.requery();
    }
}