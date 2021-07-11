package np.com.krishna.nightbeforeexam.interfaces;

import java.util.List;

import np.com.krishna.nightbeforeexam.models.User;
import np.com.krishna.nightbeforeexam.models.UserView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserApiInterface {

    @GET("search/getUser/{searchValue}")
    Call<List<User>> getUser(@Header("Authorization") String token, @Path("searchValue") String searchValue);

    @GET("user/getUser/{id}")
    Call<UserView> getUserDetails(@Header("Authorization")String token, @Path("id") Long id);

    @PUT("user/updateuser/{userId}")
    Call<ResponseBody> updateUserDetails(@Header("Authorization") String token, @Path("userId") Long userId, @Body User user);

}
