package es.upm.miw.persistenciaservicios;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import es.upm.miw.persistenciaservicios.models.JSONPlaceholderAPIService;
import es.upm.miw.persistenciaservicios.models.Post;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class NewPostActivity extends AppCompatActivity {

    static String API_BASE_URL = "http://jsonplaceholder.typicode.com";

    static String LOG_TAG = "JSONPlaceholder";

    EditText id;
    EditText userId;
    EditText title;
    EditText body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_new_post_activity);

         id=(EditText)findViewById(R.id.textViewId);
         userId=(EditText)findViewById(R.id.textViewUserId);
         title=(EditText)findViewById(R.id.textViewTitle);
         body=(EditText)findViewById(R.id.textViewBody);

    }

    public void addPost(View view) {
        //Recoger datos del nuevo post
        Post newPost=new Post(Integer.parseInt(id.getText().toString()),Integer.parseInt(userId.getText().toString()),title.getText().toString(),body.getText().toString());

        //Call REST API
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JSONPlaceholderAPIService apiService = retrofit.create(JSONPlaceholderAPIService.class);

        //Call<Post> call_async = apiService.getGroupById(19);
        Call<Post> call_async = apiService.addPost(newPost);

        // Asíncrona
        call_async.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Response<Post> response, Retrofit retrofit) {
                if (response.isSuccess()) {// tasks available
                    //Get post
                    Post post = response.body();
                    Log.d(LOG_TAG,post.toString());
                    Intent intent =new Intent(NewPostActivity.this,PostsActivity.class);
                    startActivity(intent);
                } else {// error response, no access to resource?

                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(LOG_TAG, t.getMessage());
            }
        });
    }
}
