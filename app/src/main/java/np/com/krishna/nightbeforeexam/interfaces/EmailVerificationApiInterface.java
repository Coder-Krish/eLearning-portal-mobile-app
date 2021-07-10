package np.com.krishna.nightbeforeexam.interfaces;

import np.com.krishna.nightbeforeexam.models.MessageResponse;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EmailVerificationApiInterface {

    @POST("email/emailVerification/{username}")
    Call<MessageResponse> verifyEmail(@Path("username") String username, @Query("otpnum") int otpnum);

}
