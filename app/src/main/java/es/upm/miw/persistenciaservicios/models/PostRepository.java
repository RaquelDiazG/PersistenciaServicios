package es.upm.miw.persistenciaservicios.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import static es.upm.miw.persistenciaservicios.models.PostContract.PostTable;

public class PostRepository extends SQLiteOpenHelper {

    private static final String DATABASE_FILENAME = "favoritePosts.db";

    private static final int DATABASE_VERSION = 1;

    public PostRepository(Context context) {
        super(context, DATABASE_FILENAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Crear la tabla en la BBDD
        String sql = "CREATE TABLE " + PostTable.TABLE_NAME + "("
                + PostTable.COL_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PostTable.COL_NAME_USER_ID + " INTEGER,"
                + PostTable.COL_NAME_TITLE + " TEXT,"
                + PostTable.COL_NAME_BODY + " TEXT"
                + ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Borrar la tabla en la BBDD
        String sql = "DROP TABLE IF EXIST " + PostTable.TABLE_NAME;
        db.execSQL(sql);
        //Generar la nueva tabla
        onCreate(db);
    }

    public long add(Post post) {
        //Acceder a la BBDD en modo esccritura
        SQLiteDatabase db = this.getWritableDatabase();
        //Crear un contenedor de valores
        ContentValues contentValues = new ContentValues();
        //Añadir los valores del post al contenedor
        contentValues.put(PostTable.COL_NAME_USER_ID, post.getUserId());
        contentValues.put(PostTable.COL_NAME_TITLE, post.getTitle());
        contentValues.put(PostTable.COL_NAME_BODY, post.getBody());
        return db.insert(PostTable.TABLE_NAME, null, contentValues);
    }

    public List<Post> getAll() {
        return getAll(PostTable.COL_NAME_ID, true);
    }

    public List<Post> getAll(String columna, boolean ordenAscendente) {
        //Crear la consulta
        String consultaSQL = "SELECT * FROM " + PostTable.TABLE_NAME
                + " ORDER BY " + columna + (ordenAscendente ? " ASC" : " DESC");

        //Acceder a la BBDD en modo lectura
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(consultaSQL, null);

        //Recorrer el cursor asignando resultados
        List<Post> resultado = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Post post = new Post(
                        cursor.getInt(cursor.getColumnIndex(PostTable.COL_NAME_ID)),
                        cursor.getInt(cursor.getColumnIndex(PostTable.COL_NAME_USER_ID)),
                        cursor.getString(cursor.getColumnIndex(PostTable.COL_NAME_TITLE)),
                        cursor.getString(cursor.getColumnIndex(PostTable.COL_NAME_BODY))
                );
                resultado.add(post);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return resultado;
    }


    public long count() {
        //Crear la consulta
        String sql = "SELECT COUNT(*) FROM " + PostTable.TABLE_NAME;
        //Acceder a la BBDD en modo lectura
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        long numero = cursor.getLong(0);
        cursor.close();
        return numero;
    }

    public boolean deleteAll() {
        //Acceder en modo escritura
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(PostTable.TABLE_NAME, "1", null)!=0;
    }

    public boolean deletePostByID(int id) {
        //Acceder en modo escritura
        SQLiteDatabase db = this.getWritableDatabase();
       return db.delete(PostTable.TABLE_NAME, PostTable.COL_NAME_ID+"=?", new String[]{Integer.toString(id)})>0;
    }

    public Post getPostByID(int id) {
        //Crear la consulta
        String sql = "SELECT * FROM " + PostTable.TABLE_NAME
                + " WHERE " + PostTable.COL_NAME_ID + " = ?";
        //Acceder en modo lectura
        SQLiteDatabase db = this.getReadableDatabase();
        Post post = null;
        Cursor cursor = db.rawQuery(sql,
                new String[]{String.valueOf(id)}
        );
        //Recorrer el cursor asignando resultados
        if (cursor.moveToFirst()) {
            post = new Post(
                    cursor.getInt(cursor.getColumnIndex(PostTable.COL_NAME_ID)),
                    cursor.getInt(cursor.getColumnIndex(PostTable.COL_NAME_USER_ID)),
                    cursor.getString(cursor.getColumnIndex(PostTable.COL_NAME_TITLE)),
                    cursor.getString(cursor.getColumnIndex(PostTable.COL_NAME_BODY))
            );
            cursor.close();
        }
        return post;
    }
}
