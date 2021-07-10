package np.com.krishna.nightbeforeexam.interfaces;

import np.com.krishna.nightbeforeexam.models.ChangePassword;
import np.com.krishna.nightbeforeexam.models.MessageResponse;
import np.com.krishna.nightbeforeexam.models.User;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PasswordApiInterface {

    @PUT("changepassword/updatepassword/{userId}")
    Call<MessageResponse> changePassword(@Header("Authorization") String token, @Path("userId") Long userId, @Body ChangePassword changePassword);

    @POST("forgotPassword/generateOtp")
    Call<MessageResponse> generateOtp(@Body User user);

    @POST("forgotPassword/validateOtp/{username}")
    Call<MessageResponse> validateOtp(@Path("username") String username, @Query("otpnum") int otpnum);
}
