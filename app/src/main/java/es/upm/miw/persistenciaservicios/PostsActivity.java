package es.upm.miw.persistenciaservicios;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import es.upm.miw.persistenciaservicios.models.JSONPlaceholderAPIService;
import es.upm.miw.persistenciaservicios.models.Post;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class PostsActivity extends AppCompatActivity {

    static String API_BASE_URL = "http://jsonplaceholder.typicode.com";

    static String LOG_TAG = "JSONPlaceholder";
    private ArrayAdapter<Post> adapter;
    private ListView listPosts;
    private List<Post> listCall;
    private EditText editTextId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_posts_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Identificar las vistas
        editTextId = (EditText) findViewById(R.id.editTextFind);

        //Boton flotante para crear un post
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostsActivity.this, NewPostActivity.class);
                startActivity(intent);
            }
        });

        //Crear adapter y añadir datos
        listCall = new ArrayList();
        adapter = new PostAdapter(this, listCall);
        listPosts = (ListView) findViewById(R.id.listPosts);
        listPosts.setAdapter(adapter);

        //Llamada asíncrona REST API
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JSONPlaceholderAPIService apiService = retrofit.create(JSONPlaceholderAPIService.class);

        Call<List<Post>> call_async = apiService.getAllPosts();

        call_async.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Response<List<Post>> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    //Get all posts
                    listCall = response.body();
                    Log.d("OK Get all post =>", listCall.toString());
                    //Actualizar adapter
                    adapter.addAll(listCall);
                    adapter.setNotifyOnChange(true);
                } else {
                    Log.d("ERROR Get all posts =>", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("FAIL Get all posts =>", t.getMessage());
            }
        });
    }

    public void findPostById(View view) {
        //Ocultar teclado
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editTextId.getWindowToken(), 0);

        //Get id
        int id = Integer.parseInt(editTextId.getText().toString());
        Log.d("id", String.valueOf(id));

        //Call REST
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JSONPlaceholderAPIService apiService = retrofit.create(JSONPlaceholderAPIService.class);

        Call<Post> call_async = apiService.getPostById(id);

        // Asíncrona
        call_async.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Response<Post> response, Retrofit retrofit) {
                //Recuperar el post obtenido
                Post post = response.body();
                adapter.clear();
                ArrayList<Post> newList = new ArrayList<Post>();
                newList.add(post);
                adapter.addAll(newList);
                listPosts.setAdapter(adapter);
                Log.i(LOG_TAG, "ASYNC => " + post.toString());
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(LOG_TAG, t.getMessage());
            }
        });
    }
}
