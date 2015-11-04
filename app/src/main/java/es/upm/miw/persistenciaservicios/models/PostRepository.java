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
        //Borramos la tabla
        String sql = "DROP TABLE IF EXIST " + PostTable.TABLE_NAME;
        db.execSQL(sql);
        //Generamos la nueva tabla
        onCreate(db);
    }

    public long add(Post post) {
        //Acceder a la BBDD en modo esccritura
        SQLiteDatabase db = this.getWritableDatabase();
        //Creamos un contenedor de valores
        ContentValues valores = new ContentValues();
        //Añadimos los valores del post
        valores.put(PostTable.COL_NAME_USER_ID, post.getUserId());
        valores.put(PostTable.COL_NAME_TITLE, post.getTitle());
        valores.put(PostTable.COL_NAME_BODY, post.getBody());
        return db.insert(PostTable.TABLE_NAME, null, valores);
    }

    public List<Post> getAll() {
        return getAll(PostTable.COL_NAME_ID, true);
    }

    public List<Post> getAll(String columna, boolean ordenAscendente) {
        List<Post> resultado = new ArrayList<>();
        String consultaSQL = "SELECT * FROM " + PostTable.TABLE_NAME
                + " ORDER BY " + columna + (ordenAscendente ? " ASC" : " DESC");

        // Accedo a la DB en modo lectura
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(consultaSQL, null);

        // Recorrer cursor asignando resultados
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Post futbolista = new Post(
                        cursor.getInt(cursor.getColumnIndex(PostTable.COL_NAME_ID)),
                        cursor.getInt(cursor.getColumnIndex(PostTable.COL_NAME_USER_ID)),
                        cursor.getString(cursor.getColumnIndex(PostTable.COL_NAME_TITLE)),
                        cursor.getString(cursor.getColumnIndex(PostTable.COL_NAME_BODY))
                );
                resultado.add(futbolista);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return resultado;
    }


    public long count() {
        String sql = "SELECT COUNT(*) FROM " + PostTable.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        long numero = cursor.getLong(0);
        cursor.close();

        return numero;
    }

    public long deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(PostTable.TABLE_NAME, "1", null);
    }

    public Post getPostByID(int id) {
        String sql = "SELECT * FROM " + PostTable.TABLE_NAME
                + " WHERE " + PostTable.COL_NAME_ID + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Post post = null;
        Cursor cursor = db.rawQuery(
                sql,
                new String[]{ String.valueOf(id) }
        );

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
