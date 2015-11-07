package es.upm.miw.persistenciaservicios;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import es.upm.miw.persistenciaservicios.models.APIutils;
import es.upm.miw.persistenciaservicios.models.JSONPlaceholderAPIService;
import es.upm.miw.persistenciaservicios.models.Post;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class PostsActivity extends AppCompatActivity {

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                .baseUrl(APIutils.getInstance().getAPI_BASE_URL())
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
                    //Notificacion
                    Toast.makeText(getApplicationContext(), "Get all posts", Toast.LENGTH_LONG).show();
                } else {
                    Log.d("ERROR Get all posts =>", response.errorBody().toString());
                    //Notificacion
                    Toast.makeText(getApplicationContext(), "Error get all posts", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("FAIL Get all posts =>", t.getMessage());
                //Notificacion
                Toast.makeText(getApplicationContext(), "Error get all posst", Toast.LENGTH_LONG).show();
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

        //Llamada asíncrona REST API
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIutils.getInstance().getAPI_BASE_URL())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JSONPlaceholderAPIService apiService = retrofit.create(JSONPlaceholderAPIService.class);

        Call<Post> call_async = apiService.getPostById(id);

        call_async.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Response<Post> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    //Recuperar el post obtenido
                    Post post = response.body();
                    //Actualizar el adapter
                    adapter.clear();
                    ArrayList<Post> newList = new ArrayList<Post>();
                    newList.add(post);
                    adapter.addAll(newList);
                    listPosts.setAdapter(adapter);
                    //Notificacion
                    Toast.makeText(getApplicationContext(), "Get selected post", Toast.LENGTH_LONG).show();
                } else {
                    Log.d("ERROR Get all posts =>", response.errorBody().toString());
                    //Notificacion
                    Toast.makeText(getApplicationContext(), "Error get selected post", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("FAIL Get all posts =>", t.getMessage());
                //Notificacion
                Toast.makeText(getApplicationContext(), "Error get selected post", Toast.LENGTH_LONG).show();
            }
        });
    }
}
