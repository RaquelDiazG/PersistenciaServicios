package es.upm.miw.persistenciaservicios.models;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
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
    Call<List<Post>> getAllPosts();

    @GET("/posts/{id}")
    Call<Post> getPostById(@Path("id") Integer id);

    @POST("/posts")
    Call<Post> addPost(@Body Post post);

    @DELETE("/posts/{id}")
    Call<Post> deletePost(@Path("id") Integer id);

}
