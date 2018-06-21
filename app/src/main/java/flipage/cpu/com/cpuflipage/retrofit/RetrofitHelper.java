package flipage.cpu.com.cpuflipage.retrofit;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import flipage.cpu.com.cpuflipage.BuildConfig;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitHelper {

    private static RetrofitHelper INSTANCE;
    public static Retrofit.Builder builder = null;
    private Retrofit retrofit;


    public static RetrofitHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RetrofitHelper();
        }
        return INSTANCE;
    }

    public Retrofit getRetrofitInstance() {
        if (builder != null) {
            return builder.build();
        }
        return null;
    }

    public Retrofit buildRetro() {
        return retrofit = builder.build();
    }

    public RetrofitApi createRetrofitInstance() {
        builder = new Retrofit.Builder()
                .baseUrl(BuildConfig.API)
                .addConverterFactory(GsonConverterFactory.create());

        return new Retrofit.Builder()
                .baseUrl(BuildConfig.API)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient())
                .build().create(RetrofitApi.class);
    }

    private OkHttpClient okHttpClient() {
        OkHttpClient okClient = new OkHttpClient.Builder()
                .addInterceptor(
                        new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Request original = chain.request();

                                Request.Builder requestBuilder = original.newBuilder()
                                        .header("Accept", "application/json")
                                        .method(original.method(), original.body());

                                Request request = requestBuilder.build();
                                return chain.proceed(request);
                            }
                        })
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();
        return okClient;
    }
}
