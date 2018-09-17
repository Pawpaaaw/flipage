package flipage.cpu.com.cpuflipage.retrofit;

import java.io.IOException;
import java.util.List;

import flipage.cpu.com.cpuflipage.data.Comment;
import flipage.cpu.com.cpuflipage.data.Department;
import flipage.cpu.com.cpuflipage.data.News;
import flipage.cpu.com.cpuflipage.data.Topic;
import flipage.cpu.com.cpuflipage.data.User;
import flipage.cpu.com.cpuflipage.data.Post;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RetrofitImplementation {

    private RetrofitApi api;

    public RetrofitImplementation() {
        api = RetrofitHelper.getInstance().createRetrofitInstance();
    }

    public void createUser(User user, flipage.cpu.com.cpuflipage.utils.Callback callback) {
        api.createUser(user).enqueue(getResponseBodyCallback(callback));
    }

    public void updateUser(User user, flipage.cpu.com.cpuflipage.utils.Callback callback) {
        api.updateUser(user).enqueue(getUserCallback(callback));
    }

    public void getUser(String userName, String password, flipage.cpu.com.cpuflipage.utils.Callback callback) {
        api.getUser(userName, password).enqueue(getUserCallback(callback));
    }

    public void createNews(News news, flipage.cpu.com.cpuflipage.utils.Callback callback) {
        api.createNews(news).enqueue(getResponseBodyCallback(callback));
    }

    public void getNewsByDeptId(String deptId,flipage.cpu.com.cpuflipage.utils.Callback callback) {
        api.getNewsByDepartment(deptId).enqueue(getNewsCallback(callback));
    }

    public void getNews(flipage.cpu.com.cpuflipage.utils.Callback callback) {
        api.getNews().enqueue(getNewsCallback(callback));
    }

    public void getDepartments(flipage.cpu.com.cpuflipage.utils.Callback callback) {
        api.getDepartments().enqueue(getDepartmentCallback(callback));
    }

    public void createPost(Post post, flipage.cpu.com.cpuflipage.utils.Callback callback) {
        api.createPost(post).enqueue(getResponseBodyCallback(callback));
    }

    public void addCommentToTopic(Comment comment, flipage.cpu.com.cpuflipage.utils.Callback callback) {
        api.addComment(comment).enqueue(getTopicCallback(callback));
    }


    public void addCommentToPost(Comment comment, flipage.cpu.com.cpuflipage.utils.Callback callback) {
        api.addPostComment(comment).enqueue(getPostCallback(callback));
    }

    public void getAllPosts(flipage.cpu.com.cpuflipage.utils.Callback callback) {
        api.getPosts().enqueue(getPostsCallback(callback));
    }


    private Callback<List<Department>> getDepartmentCallback(flipage.cpu.com.cpuflipage.utils.Callback callback) {
        return new Callback<List<Department>>() {
            @Override
            public void onResponse(Call<List<Department>> call, Response<List<Department>> response) {
                if (callback != null)
                    if (response.isSuccessful()) {
                        callback.onSuccess(response.body());
                    } else {
                        readError(response, callback);
                    }
            }

            @Override
            public void onFailure(Call<List<Department>> call, Throwable t) {
                String errorDesc;
                if (t instanceof IOException) {
                    errorDesc = String.valueOf(t.getCause());
                } else if (t instanceof IllegalStateException) {
                    errorDesc = String.valueOf(t.getCause());
                } else {
                    errorDesc = String.valueOf(t.getLocalizedMessage());
                }
                callback.onError(errorDesc);
            }
        };
    }

    private Callback<List<News>> getNewsCallback(flipage.cpu.com.cpuflipage.utils.Callback callback) {
        return new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                if (callback != null)
                    if (response.isSuccessful()) {
                        callback.onSuccess(response.body());
                    } else {
                        readError(response, callback);
                    }
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {
                String errorDesc;
                if (t instanceof IOException) {
                    errorDesc = String.valueOf(t.getCause());
                } else if (t instanceof IllegalStateException) {
                    errorDesc = String.valueOf(t.getCause());
                } else {
                    errorDesc = String.valueOf(t.getLocalizedMessage());
                }
                callback.onError(errorDesc);
            }
        };
    }

    private Callback<Post> getPostCallback(flipage.cpu.com.cpuflipage.utils.Callback callback) {
        return new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (callback != null)
                    if (response.isSuccessful()) {
                        callback.onSuccess(response.body());
                    } else {
                        readError(response, callback);
                    }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                String errorDesc;
                if (t instanceof IOException) {
                    errorDesc = String.valueOf(t.getCause());
                } else if (t instanceof IllegalStateException) {
                    errorDesc = String.valueOf(t.getCause());
                } else {
                    errorDesc = String.valueOf(t.getLocalizedMessage());
                }
                callback.onError(errorDesc);
            }
        };
    }

    private Callback<List<Post>> getPostsCallback(flipage.cpu.com.cpuflipage.utils.Callback callback) {
        return new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (callback != null)
                    if (response.isSuccessful()) {
                        callback.onSuccess(response.body());
                    } else {
                        readError(response, callback);
                    }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                String errorDesc;
                if (t instanceof IOException) {
                    errorDesc = String.valueOf(t.getCause());
                } else if (t instanceof IllegalStateException) {
                    errorDesc = String.valueOf(t.getCause());
                } else {
                    errorDesc = String.valueOf(t.getLocalizedMessage());
                }
                callback.onError(errorDesc);
            }
        };
    }

    private Callback<List<Topic>> getTopicsCallback(flipage.cpu.com.cpuflipage.utils.Callback callback) {
        return new Callback<List<Topic>>() {
            @Override
            public void onResponse(Call<List<Topic>> call, Response<List<Topic>> response) {
                if (callback != null)
                    if (response.isSuccessful()) {
                        callback.onSuccess(response.body());
                    } else {
                        readError(response, callback);
                    }
            }

            @Override
            public void onFailure(Call<List<Topic>> call, Throwable t) {
                String errorDesc;
                if (t instanceof IOException) {
                    errorDesc = String.valueOf(t.getCause());
                } else if (t instanceof IllegalStateException) {
                    errorDesc = String.valueOf(t.getCause());
                } else {
                    errorDesc = String.valueOf(t.getLocalizedMessage());
                }
                callback.onError(errorDesc);
            }
        };
    }


    private Callback<User> getUserCallback(flipage.cpu.com.cpuflipage.utils.Callback callback) {
        return new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (callback != null)
                    if (response.isSuccessful()) {
                        callback.onSuccess(response.body());
                    } else {
                        readError(response, callback);
                    }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                String errorDesc;
                if (t instanceof IOException) {
                    errorDesc = String.valueOf(t.getCause());
                } else if (t instanceof IllegalStateException) {
                    errorDesc = String.valueOf(t.getCause());
                } else {
                    errorDesc = String.valueOf(t.getLocalizedMessage());
                }
                callback.onError(errorDesc);
            }
        };
    }

    private Callback<Topic> getTopicCallback(flipage.cpu.com.cpuflipage.utils.Callback callback) {
        return new Callback<Topic>() {
            @Override
            public void onResponse(Call<Topic> call, Response<Topic> response) {
                if (callback != null)
                    if (response.isSuccessful()) {
                        callback.onSuccess(response.body());
                    } else {
                        readError(response, callback);
                    }
            }

            @Override
            public void onFailure(Call<Topic> call, Throwable t) {
                String errorDesc;
                if (t instanceof IOException) {
                    errorDesc = String.valueOf(t.getCause());
                } else if (t instanceof IllegalStateException) {
                    errorDesc = String.valueOf(t.getCause());
                } else {
                    errorDesc = String.valueOf(t.getLocalizedMessage());
                }
                callback.onError(errorDesc);
            }
        };
    }

    private Callback<ResponseBody> getResponseBodyCallback(flipage.cpu.com.cpuflipage.utils.Callback callback) {
        return new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (callback != null)
                    if (response.isSuccessful()) {
                        callback.onSuccess(response.body());
                    } else {
                        readError(response, callback);
                    }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                String errorDesc;
                if (t instanceof IOException) {
                    errorDesc = String.valueOf(t.getMessage());
                } else if (t instanceof IllegalStateException) {
                    errorDesc = String.valueOf(t.getMessage());
                } else {
                    errorDesc = String.valueOf(t.getLocalizedMessage());
                }
                callback.onError(errorDesc);
            }
        };
    }


    private void readError(Response<?> response, flipage.cpu.com.cpuflipage.utils.Callback callback) {
        ApiError error = ErrorUtil.parseError(response);
        if (error.status() != 0 && error.status() != 500) {
            callback.onError(error.message());
        } else
            callback.onError("Something went wrong. Please try again");
    }
}
