package flipage.cpu.com.cpuflipage.retrofit;

import java.util.List;

import flipage.cpu.com.cpuflipage.data.Comment;
import flipage.cpu.com.cpuflipage.data.Department;
import flipage.cpu.com.cpuflipage.data.News;
import flipage.cpu.com.cpuflipage.data.Topic;
import flipage.cpu.com.cpuflipage.data.User;
import flipage.cpu.com.cpuflipage.data.Post;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface RetrofitApi {

    @GET("api/news/findByDepartmentId")
    Call<List<News>> getNewsByDepartment(@Query("departmentId") long departmentId);

    @GET("api/news/fetchAllNews")
    Call<List<News>> getNews();

    @POST("/api/news/save")
    Call<ResponseBody> createNews(@Body News news);



    @GET("api/user/getUser")
    Call<User> getUser(@Query("idNumber") String username, @Query("password") String password);

    @POST("api/user/createUser")
    Call<ResponseBody> createUser(@Body User user);

    @POST("api/user/updateUser")
    Call<User> updateUser(@Body User user);



    @GET("/api/department/findAll")
    Call<List<Department>> getDepartments();




    @POST("/api/post/save")
    Call<ResponseBody> createPost(@Body Post post);

    @GET("/api/post/fetchAllPost")
    Call<List<Post>> getPosts();

    @POST("/api/post/addComment")
    Call<Post> addPostComment(@Body Comment request);



    @POST("/api/topic/addComment")
    Call<Topic> addComment(@Body Comment request);



}
