package es.upm.miw.persistenciaservicios;

import java.util.List;

import es.upm.miw.persistenciaservicios.models.Post;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

public interface JSONPlaceholderAPIService {

    /*
GET	/posts
GET	/posts1
GET	/posts/1/comments
GET	/comments?postId=1
GET	/posts?userId=1
POST	/posts
PUT	/posts/1
PATCH	/posts/1
DELETE	/posts/1
     */
    @GET("/posts")
    Call<List<Post>> getPosts();

    @GET("/posts/{id}")
    Call<Post> getPostById(@Path("id") String id);

}
