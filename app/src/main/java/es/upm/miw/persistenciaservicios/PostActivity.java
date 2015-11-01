package es.upm.miw.persistenciaservicios;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import es.upm.miw.persistenciaservicios.models.Post;

public class PostActivity extends AppCompatActivity {

    TextView tvId, tvUserId, tvTitle, tvBody;
    Context contexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_post_activity);

        contexto = getApplicationContext();

        // Recuperar post
        Post post = getIntent().getParcelableExtra("SHOW_POST");
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

    public void favoritePost(View view) {

    }

    public void deletePost(View view) {

    }
}
