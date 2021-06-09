package np.com.krishna.nightbeforeexam.interfaces;

import java.util.List;

import np.com.krishna.nightbeforeexam.models.LoginRequest;
import np.com.krishna.nightbeforeexam.models.LoginResponse;
import np.com.krishna.nightbeforeexam.models.SignupRequest;
import np.com.krishna.nightbeforeexam.models.SignupResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AuthenticationApiInterface {


    @GET("test/all")
   public Call <String> message();


    @POST("auth/signin")
    Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);

    @POST("auth/signup")
    Call<SignupResponse> userRegister(@Body SignupRequest signupRequest);

    @GET("admin/demoRequest")
    Call<ResponseBody> greetings(@Header("Authorization") String token);
}
