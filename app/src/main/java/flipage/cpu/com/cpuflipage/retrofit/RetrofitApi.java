package flipage.cpu.com.cpuflipage.retrofit;

import java.util.List;

import flipage.cpu.com.cpuflipage.data.CommentRequest;
import flipage.cpu.com.cpuflipage.data.Department;
import flipage.cpu.com.cpuflipage.data.News;
import flipage.cpu.com.cpuflipage.data.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface RetrofitApi {

    @GET("api/news/fetchAllNews")
    Call<List<News>> getNews();

    @GET("api/user/getUser")
    Call<User> getUser(@Query("userName") String username, @Query("password") String password);

    @POST("api/user/createUser")
    Call<ResponseBody> createUser(@Body User user);

    @POST("api/user/updateUser")
    Call<User> updateUser(@Body User user);

    @POST("api/news/addComment")
    Call<ResponseBody> addComment(@Body CommentRequest request);

    @POST("/api/news/save")
    Call<ResponseBody> createNews(@Body News news);

    @GET("/api/department/findAll")
    Call<List<Department>> getDepartments();
}
