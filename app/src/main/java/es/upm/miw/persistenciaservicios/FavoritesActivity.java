package es.upm.miw.persistenciaservicios;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import es.upm.miw.persistenciaservicios.models.Post;
import es.upm.miw.persistenciaservicios.models.PostRepository;

public class FavoritesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_posts_activity);
        //Obtener los posts favoritos de BBDD
        PostRepository repository = new PostRepository(this);
        List<Post> listPost = repository.getAll();
        //Crear adapter y añadir datos
        ArrayAdapter<Post> adapter = new PostAdapter(this, listPost);
        ListView listPosts = (ListView) findViewById(R.id.listPosts);
        listPosts.setAdapter(adapter);
        //Notificacion
        Toast.makeText(getApplicationContext(), "Get all favorites", Toast.LENGTH_LONG).show();
    }
}
