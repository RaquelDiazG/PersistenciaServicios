package es.upm.miw.persistenciaservicios;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import es.upm.miw.persistenciaservicios.models.APIutils;
import es.upm.miw.persistenciaservicios.models.JSONPlaceholderAPIService;
import es.upm.miw.persistenciaservicios.models.Post;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class NewPostActivity extends AppCompatActivity {

    private EditText id, userId, title, body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_new_post_activity);

        // Identificar vistas
        id = (EditText) findViewById(R.id.textViewId);
        userId = (EditText) findViewById(R.id.textViewUserId);
        title = (EditText) findViewById(R.id.textViewTitle);
        body = (EditText) findViewById(R.id.textViewBody);
    }

    public void addPost(View view) {
        //Recoger datos del nuevo post
        Post newPost = new Post(Integer.parseInt(id.getText().toString()),
                Integer.parseInt(userId.getText().toString()),
                title.getText().toString(),
                body.getText().toString());

        //Llamada asíncrona REST API
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIutils.getInstance().getAPI_BASE_URL())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JSONPlaceholderAPIService apiService = retrofit.create(JSONPlaceholderAPIService.class);

        Call<Post> call_async = apiService.addPost(newPost);

        call_async.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Response<Post> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    //Recuperar el post insertado
                    Post post = response.body();
                    Log.d("OK Add post =>", post.toString());
                    //Redirección
                    Intent intent = new Intent(NewPostActivity.this, PostsActivity.class);
                    startActivity(intent);
                    //Notificacion
                    Toast.makeText(getApplicationContext(), "Added post", Toast.LENGTH_LONG).show();
                } else {
                    Log.d("ERROR Add post =>", response.errorBody().toString());
                    //Notificacion
                    Toast.makeText(getApplicationContext(), "Error adding post", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("FAIL add post =>", t.getMessage());
                //Notificacion
                Toast.makeText(getApplicationContext(), "Error adding post", Toast.LENGTH_LONG).show();
            }
        });
    }
}
