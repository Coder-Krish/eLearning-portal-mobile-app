package np.com.krishna.nightbeforeexam.interfaces;

import java.util.List;

import np.com.krishna.nightbeforeexam.models.Posts;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface PostsInterface {


    @GET("posts/listpostsbyuserid/{userId}")
    Call<List<Posts>> getPostsByUserId(@Header("Authorization") String token, @Path("userId") Long userId);

    @GET("follow/getPosts")
    Call<List<Posts>> getPostsOfMyFollowings(@Header("Authorization") String token);


}
