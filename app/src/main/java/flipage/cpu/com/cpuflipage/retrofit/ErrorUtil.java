package flipage.cpu.com.cpuflipage.retrofit;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

/**
 * Created by paolo on 5/3/18.
 */

public class ErrorUtil {
    public static ApiError parseError(Response<?> response) {
        Converter<ResponseBody, ApiError> converter =
                RetrofitHelper.getInstance().buildRetro()
                        .responseBodyConverter(ApiError.class, new Annotation[0]);

        ApiError error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new ApiError();
        }

        return error;
    }
}
