package es.upm.miw.persistenciaservicios;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import es.upm.miw.persistenciaservicios.models.APIutils;
import es.upm.miw.persistenciaservicios.models.JSONPlaceholderAPIService;
import es.upm.miw.persistenciaservicios.models.Post;
import es.upm.miw.persistenciaservicios.models.PostRepository;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class PostAdapter extends ArrayAdapter<Post> {

    private Context context;
    private List<Post> posts;
    private Post post;

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
                int id = post.getId();
                Log.i("ID to delete => ", String.valueOf(id));

                //Llamada asíncrona REST API
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(APIutils.getInstance().getAPI_BASE_URL())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                JSONPlaceholderAPIService apiService = retrofit.create(JSONPlaceholderAPIService.class);

                Call<Post> call_async = apiService.deletePost(id);

                call_async.enqueue(new Callback<Post>() {
                    @Override
                    public void onResponse(Response<Post> response, Retrofit retrofit) {
                        if (response.isSuccess()) {
                            //Recuperar el post eliminado
                            Post post = response.body();
                            Log.d("OK Delete post =>", post.toString());
                            //Notificacion
                            Toast.makeText(context, R.string.toastOkDeletePost, Toast.LENGTH_LONG).show();
                        } else {
                            Log.d("ERROR Delete post =>", response.errorBody().toString());
                            //Notificacion
                            Toast.makeText(context, R.string.toastErrorDeletePost, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.e("FAIL Delete post =>", t.getMessage());
                        //Notificacion
                        Toast.makeText(context, R.string.toastErrorDeletePost, Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

        Button btnFavorite = (Button) convertView.findViewById(R.id.btnFavorite);
        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Post favorite => ", post.toString());
                //Insertar post en la BBDD de favoritos
                PostRepository repository = new PostRepository(context);
                long ok = repository.add(post);
                //Notificacion
                if (ok == -1) {
                    Toast.makeText(context, R.string.toastErrorAddFavorite, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, R.string.toastOkAddFavorite, Toast.LENGTH_LONG).show();
                }
            }
        });
        return convertView;
    }
}
