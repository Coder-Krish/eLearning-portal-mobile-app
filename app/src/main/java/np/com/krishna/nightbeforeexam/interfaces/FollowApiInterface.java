package np.com.krishna.nightbeforeexam.interfaces;

import java.util.List;

import np.com.krishna.nightbeforeexam.models.Follow;
import np.com.krishna.nightbeforeexam.models.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FollowApiInterface {

    @GET("follow/getfollowingid")
    Call<List<User>> getFollowing(@Header("Authorization") String token);


    @GET("follow/getfollowerid")
    Call<List<User>> getFollower(@Header("Authorization") String token);

    @GET("list/users/{id}")
    Call<User> getUsers(@Header("Authorization") String token, @Path("id") Long id);

    @POST("follow/followuser/{userId}")
    Call<ResponseBody> followUser(@Header("Authorization") String token, @Path("userId") Long userId);
}
