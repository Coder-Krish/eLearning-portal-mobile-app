package np.com.krishna.nightbeforeexam.interfaces;

import np.com.krishna.nightbeforeexam.models.MessageResponse;
import np.com.krishna.nightbeforeexam.models.ProfilePicture;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface ProfileInterface {


    @GET("profilePicture/loadProfilePicture/{id}")
    Call<ProfilePicture> loadProfilePicture(@Header("Authorization") String token, @Path("id") Long id);

    @GET("profilePicture/downloadFile/{fileName}")
    Call<ResponseBody> displayProfilePicture(@Header("Authorization") String token, @Path("fileName") String fileName);

    @GET("follow/countfollower/{userId}")
    Call<MessageResponse> countFollowers(@Header("Authorization") String token, @Path("userId") Long userId);


    @GET("follow/countfollowing/{userId}")
    Call<MessageResponse> countFollowing(@Header("Authorization") String token, @Path("userId") Long userId);

}
