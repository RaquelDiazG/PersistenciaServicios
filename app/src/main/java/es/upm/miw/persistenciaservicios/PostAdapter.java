package es.upm.miw.persistenciaservicios;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import es.upm.miw.persistenciaservicios.models.Post;

public class PostAdapter extends ArrayAdapter<Post> {

    private Context context;
    private List<Post> posts;

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
        Post post = this.posts.get(position);

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

        return convertView;
        //return null;
    }
}
