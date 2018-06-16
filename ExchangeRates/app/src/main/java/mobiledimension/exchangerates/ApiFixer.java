package mobiledimension.exchangerates;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiFixer {
    @GET("{date}")
    Call<PostModel> getData(@Path("date") String date, @Query("access_key") String accessKey, @Query("base") String  base);
}
