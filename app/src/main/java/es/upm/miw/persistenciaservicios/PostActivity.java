package es.upm.miw.persistenciaservicios;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import es.upm.miw.persistenciaservicios.models.JSONPlaceholderAPIService;
import es.upm.miw.persistenciaservicios.models.Post;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class PostActivity extends AppCompatActivity {

    TextView tvId, tvUserId, tvTitle, tvBody;
    Context contexto;
    Post post;
    static String API_BASE_URL = "http://jsonplaceholder.typicode.com";

    static String LOG_TAG = "JSONPlaceholder";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_post_activity);

        contexto = getApplicationContext();

        // Recuperar post
        post = getIntent().getParcelableExtra("SHOW_POST");
        Log.i("MOSTRAR", post.toString());

        // Identificar vistas
        tvId = (TextView) findViewById(R.id.tvId);
        tvUserId = (TextView) findViewById(R.id.tvUserId);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvBody = (TextView) findViewById(R.id.tvBody);

        // Asignar valores a las vistas
        tvId.setText(String.format("%d", post.getId()));
        tvUserId.setText(String.format("%d", post.getId()));
        tvTitle.setText( post.getTitle());
        tvBody.setText(post.getBody());
    }

}
