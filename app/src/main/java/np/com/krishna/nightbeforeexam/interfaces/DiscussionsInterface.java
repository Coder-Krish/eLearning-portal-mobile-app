package np.com.krishna.nightbeforeexam.interfaces;

import java.util.List;

import np.com.krishna.nightbeforeexam.models.DiscussionForum;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface DiscussionsInterface {

    @GET("discussionforum/listdiscussionbyuserid/{userId}")
    Call<List<DiscussionForum>> getDiscussionsByUserId(@Header("Authorization") String token, @Path("userId") Long userId);


}
