package es.upm.miw.persistenciaservicios;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import es.upm.miw.persistenciaservicios.models.Post;
import es.upm.miw.persistenciaservicios.models.PostRepository;

public class FavoritesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_posts_activity);
        ;
        //Get favorite post from BBDD
        PostRepository repository = new PostRepository(this);
        List<Post> listPost = repository.getAll();
        //Create adapter and set data
        ArrayAdapter<Post> adapter = new PostAdapter(this, listPost);
        ListView listPosts = (ListView) findViewById(R.id.listPosts);
        listPosts.setAdapter(adapter);
    }
}
