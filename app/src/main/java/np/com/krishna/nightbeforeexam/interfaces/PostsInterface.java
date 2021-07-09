package np.com.krishna.nightbeforeexam.interfaces;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

import java.util.List;

import np.com.krishna.nightbeforeexam.models.Comments;
import np.com.krishna.nightbeforeexam.models.MessageResponse;
import np.com.krishna.nightbeforeexam.models.Posts;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface PostsInterface {


    @GET("posts/listpostsbyuserid/{userId}")
    Call<List<Posts>> getPostsByUserId(@Header("Authorization") String token, @Path("userId") Long userId);

    @GET("follow/getPosts")
    Call<List<Posts>> getPostsOfMyFollowings(@Header("Authorization") String token);

    @Multipart
    @POST("posts/newpost")
    Call<ResponseBody> createNewPost(@Header("Authorization") String token, @Part("postsJson") RequestBody postsJson);

    @Multipart
    @POST("posts/newpost")
    Call<ResponseBody> createPost(@Header("Authorization") String token,
                                  @Part("postsJson") RequestBody postsJson,
                                  @Part MultipartBody.Part file);

    @GET("comments/listcommentsbypostid/{postId}")
    Call<List<Comments>> getCommentsByPostId(@Header("Authorization") String token, @Path("postId") Long postId);

    @POST("comments/createcomment/{postId}")
    Call<ResponseBody> createComment(@Header("Authorization") String token, @Path("postId") Long postId, @Body Comments comments);

}
