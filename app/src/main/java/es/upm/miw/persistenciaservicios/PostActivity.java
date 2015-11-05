package es.upm.miw.persistenciaservicios;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import es.upm.miw.persistenciaservicios.models.Post;

public class PostActivity extends AppCompatActivity {

    private TextView tvId, tvUserId, tvTitle, tvBody;
    private Context contexto;
    private Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_post_activity);

        contexto = getApplicationContext();

        // Recuperar post sobre el que se ha hecho click
        post = getIntent().getParcelableExtra("SHOW_POST");
        Log.i("SHOW_POST", post.toString());

        // Identificar vistas
        tvId = (TextView) findViewById(R.id.tvId);
        tvUserId = (TextView) findViewById(R.id.tvUserId);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvBody = (TextView) findViewById(R.id.tvBody);

        // Asignar valores a las vistas
        tvId.setText(String.format("%d", post.getId()));
        tvUserId.setText(String.format("%d", post.getId()));
        tvTitle.setText(post.getTitle());
        tvBody.setText(post.getBody());
    }

}
