package es.upm.miw.persistenciaservicios;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    static String API_BASE_URL = "http://jsonplaceholder.typicode.com";

    static String LOG_TAG = "JSONPlaceholder";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main_activity);

        /*final TextView tvContenido = (TextView) findViewById(R.id.get);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JSONPlaceholderAPIService apiService = retrofit.create(JSONPlaceholderAPIService.class);

        //Call<Post> call_async = apiService.getGroupById(19);
        Call<Post> call_async = apiService.getPostById("1");

        // Asíncrona
        call_async.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Response<Post> response, Retrofit retrofit) {
                // recupero el post obtenido
                Post post = response.body();
                tvContenido.setText(post.toString());
                Log.i(LOG_TAG, "ASYNC => " + post.toString());
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(LOG_TAG, t.getMessage());
            }
        });*/

    }

    public void showPosts(View view) {
        Intent intent = new Intent(this, PostsActivity.class);
        startActivity(intent);
    }
}
