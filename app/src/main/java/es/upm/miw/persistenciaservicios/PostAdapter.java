package es.upm.miw.persistenciaservicios;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import es.upm.miw.persistenciaservicios.models.JSONPlaceholderAPIService;
import es.upm.miw.persistenciaservicios.models.Post;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class PostAdapter extends ArrayAdapter<Post> {

    private Context context;
    private List<Post> posts;
    Post post;
    static String API_BASE_URL = "http://jsonplaceholder.typicode.com";

    static String LOG_TAG = "JSONPlaceholder";

    public PostAdapter(Context context, List<Post> posts) {
        super(context, R.layout.layout_posts_activity, posts);
        this.posts = posts;
        this.context = context;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_post_activity, null);
        }

        //Recuperamos el post sobre el que se ha pulsado
         post = this.posts.get(position);

        //Añadimos los datos del post a la vista
        if (post != null) {
            TextView tvUserId = (TextView) convertView.findViewById(R.id.tvUserId);
            tvUserId.setText(String.valueOf(post.getId()));
            TextView tvId = (TextView) convertView.findViewById(R.id.tvId);
            tvId.setText(String.valueOf(post.getId()));
            TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            tvTitle.setText(post.getTitle());
            TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
            tvBody.setText(post.getBody());
        }
        //Añadimos un listener para tener en cuenta el boton sobre el que se ha pulsado

        Button btnDelete = (Button) convertView.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get id
                int id=post.getId();
                Log.i(LOG_TAG, "ID delete => " + id);
                //Call REST
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(API_BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                JSONPlaceholderAPIService apiService = retrofit.create(JSONPlaceholderAPIService.class);

                Call<Post> call_async = apiService.deletePost(id);

                // Asíncrona
                call_async.enqueue(new Callback<Post>() {
                    @Override
                    public void onResponse(Response<Post> response, Retrofit retrofit) {
                        // recupero el post obtenido
                        Post post = response.body();

                        Log.i(LOG_TAG, "ASYNC => " + post.toString());

                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.e(LOG_TAG, t.getMessage());
                    }
                });

            }
        });

        Button btnFavorite = (Button) convertView.findViewById(R.id.btnFavorite);

        return convertView;
        //return null;
    }
}
