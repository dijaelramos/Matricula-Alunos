package com.dijaelramos.agenda;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase; //Banco de Dados
import android.database.Cursor; //Navegar entre os registros
import android.content.ContextWrapper;

import static android.content.Context.MODE_PRIVATE;

public class Banco {
    static SQLiteDatabase db=null;
    static Cursor cursor;

    public static void abrirBanco(Activity act) {
        try {
            ContextWrapper cw=new ContextWrapper(act);
            db=cw.openOrCreateDatabase("bancoMatricula", MODE_PRIVATE, null);
        }catch (Exception ex) {
            TelaMsg.mostrar("Erro ao abrir ou criar banco de dados", act);
        }
    }

    public static void fecharDB() {
        db.close();
    }

    public static void abrirOuCriarTabela(Activity act) {
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS matricula (id INTEGER PRIMARY KEY, nome TEXT, matricula TEXT);");
        }catch (Exception ex) {
            TelaMsg.mostrar("Erro ao criar tablela", act);
        }
    }

    public static void inserirRegistro(String nome, String matricula, Activity act) {
        abrirBanco(act);
        try {
            db.execSQL("INSERT INTO matricula (nome, matricula) VALUES ('"+nome+"', '"+matricula+"')");
        }catch (Exception ex){
            TelaMsg.mostrar("Erro ao inserir a matricula", act);
        }finally {
            TelaMsg.mostrar("Registro inserido com sucesso", act);
        }
        fecharDB();
    }
    public static Cursor buscarTodosDados(Activity act){
        abrirBanco(act);
        cursor=db.query("matricula",
                new String[]{"id","nome", "matricula"},
                null,
                null,
                null,
                null,
                null,
                null
        );
        cursor.moveToFirst();
        return cursor;
    }
    /*======================================================================================*/
    /*======================================================================================*/
    /*======================================================================================*/
    /*======================================================================================*/
    /*======================================================================================*/
    /*======================================================================================*/
    public static void apagarRegistro(int id,  Activity act) {
        abrirBanco(act);
        try {
            db.delete("matricula", "id = ?", new String[]{String.valueOf(id)});
//            String sql = ("DELETE FROM matricula WHERE id = " + id);
//            db.execSQL(sql, new Object[]{id});
        }catch (Exception ex){
            TelaMsg.mostrar("Erro ao apagar registro", act);
        }finally {
            TelaMsg.mostrar("Registro apagado com sucesso", act);
        }
        fecharDB();
    }

    public static void editarRegistro(int id, String nome, String matricula, Activity act) {
        String novoNome = nome;
        String novaMatricula = matricula;

        abrirBanco(act);
        try {
            String sql = "UPDATE matricula SET nome = ?, matricula = ? WHERE id = ?";
            db.execSQL(sql, new Object[]{novoNome, novaMatricula, id});
        }catch (Exception ex){
            TelaMsg.mostrar("Erro ao editar registro", act);
        }finally {
            TelaMsg.mostrar("Registro editado com sucesso", act);
        }
        fecharDB();
    }
}
