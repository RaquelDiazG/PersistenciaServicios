package es.upm.miw.persistenciaservicios;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import es.upm.miw.persistenciaservicios.models.Post;
import es.upm.miw.persistenciaservicios.models.PostRepository;

public class FavoritesActivity extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_favorites_activity);
        //Guardar el contexto
        context=getApplicationContext();
        //Obtener los posts favoritos de BBDD
        PostRepository repository = new PostRepository(this);
        List<Post> listPost = repository.getAll();
        //Crear adapter y añadir datos
        ArrayAdapter<Post> adapter = new FavoritePostAdapter(this, listPost);
        ListView listPosts = (ListView) findViewById(R.id.listFavoritesPosts);
        listPosts.setAdapter(adapter);
        //Notificacion
        Toast.makeText(context, R.string.toastGetAllFavorites, Toast.LENGTH_LONG).show();
    }

    public void removeAllFavorites(View view) { //click en boton
        //Eliminar todos los posts de la BBDD de favoritos
        PostRepository repository = new PostRepository(context);
        boolean exito= repository.deleteAll();
        //Notificacion
        if(exito) {
            Toast.makeText(context, R.string.toastOkDeleteAllFavorites, Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(context, R.string.toastErrorDeleteAllFavorites, Toast.LENGTH_LONG).show();
        }
    }
}
