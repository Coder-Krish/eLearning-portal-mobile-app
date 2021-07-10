package np.com.krishna.nightbeforeexam.interfaces;

import java.util.List;

import np.com.krishna.nightbeforeexam.models.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface UserApiInterface {

    @GET("search/getUser/{searchValue}")
    Call<List<User>> getUser(@Header("Authorization") String token, @Path("searchValue") String searchValue);

}
