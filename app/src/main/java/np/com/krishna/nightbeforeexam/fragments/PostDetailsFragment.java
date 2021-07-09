package np.com.krishna.nightbeforeexam.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import np.com.krishna.nightbeforeexam.ApiClients.ApiClient;
import np.com.krishna.nightbeforeexam.R;
import np.com.krishna.nightbeforeexam.adapters.PostsDetailsRecyclerAdapter;
import np.com.krishna.nightbeforeexam.models.Comments;
import np.com.krishna.nightbeforeexam.models.Posts;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class PostDetailsFragment extends Fragment {

    RecyclerView recyclerView;
    private ArrayList<Comments> commentsArrayList;
    private PostsDetailsRecyclerAdapter postsDetailsRecyclerAdapter;
    private Long postId;
    private EditText commentContent;
    private Button btnComment;
    TextView postCreatedBy;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_posts_detail, container, false);

        recyclerView = v.findViewById(R.id.commentsOnPost);
        commentContent = v.findViewById(R.id.comment);
        btnComment = v.findViewById(R.id.btnComment);
        postCreatedBy = v.findViewById(R.id.postCreatedBy);
        commentsArrayList = new ArrayList<>();
        Posts posts = getArguments().getParcelable("postData");
        postId = posts.getId();

        postCreatedBy.setText("You are commenting on "+posts.getUser().getUsername() +"'s post");
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token","");
        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = commentContent.getText().toString().trim();
                Comments comments = new Comments();
                comments.setComment(comment);
                Call<ResponseBody> call = ApiClient.getPostsService().createComment(token, postId,comments);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            Log.d(TAG, "onResponse: Comment created");
                            initializeData(postId);
                        }else{
                            Log.d(TAG, "onResponse: Something went wrong "+response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.d(TAG, "onResponse: Sorry cannot connect to the server ");
                    }
                });
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        initializeData(postId);
        return v;
        }

    private void initializeData(Long postId) {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token","");

        Call<List<Comments>> call = ApiClient.getPostsService().getCommentsByPostId(token,postId);
        call.enqueue(new Callback<List<Comments>>() {
            @Override
            public void onResponse(Call<List<Comments>> call, Response<List<Comments>> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "onResponse: getting comments");
                    commentsArrayList = new ArrayList<>(response.body());
                    postsDetailsRecyclerAdapter = new PostsDetailsRecyclerAdapter(getContext(), commentsArrayList);
                    recyclerView.setAdapter(postsDetailsRecyclerAdapter);
                }else{
                    Log.d(TAG, "onResponse: sorry something went wrong "+response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Comments>> call, Throwable t) {
                Log.d(TAG, "onFailure: Sorry cannot connect to the server");
            }
        });
    }
}

