package np.com.krishna.nightbeforeexam.interfaces;

import java.util.List;

import np.com.krishna.nightbeforeexam.models.DiscussionForum;
import np.com.krishna.nightbeforeexam.models.RepliesOnDiscussion;
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

public interface DiscussionsInterface {

    @GET("discussionforum/listdiscussionbyuserid/{userId}")
    Call<List<DiscussionForum>> getDiscussionsByUserId(@Header("Authorization") String token, @Path("userId") Long userId);

    @GET("discussionforum/listdiscussionbysubject/{subjectId}")
    Call<List<DiscussionForum>> getDiscussionsBySubjectId(@Header("Authorization") String token, @Path("subjectId") Long subjectId);

    @Multipart
    @POST("discussionforum/createnewdiscussion/{subjectId}")
    Call<ResponseBody> createDiscussionWithContentOnly(@Header("Authorization") String token,
                                                       @Path("subjectId") Long subjectId,
                                                       @Part("discussionJson")RequestBody discussionJson);
    @Multipart
    @POST("discussionforum/createnewdiscussion/{subjectId}")
    Call<ResponseBody> createDiscussion(@Header("Authorization") String token,
                                           @Path("subjectId") Long subjectId,
                                           @Part("discussionJson")RequestBody discussionJson,
                                           @Part MultipartBody.Part file);

    @POST("reply/createreply/{discussionId}")
    Call<ResponseBody> createReplyonDiscussion(@Header("Authorization") String token,
                                               @Path("discussionId") Long discussionId,
                                               @Body RepliesOnDiscussion repliesOnDiscussion);

    @GET("reply/listrepliesbydiscussionid/{discussionId}")
    Call<List<RepliesOnDiscussion>> getRepliesOnDiscussion(@Header("Authorization") String token, @Path("discussionId") Long discussionId);

}
