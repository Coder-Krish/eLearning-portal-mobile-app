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

import com.google.android.gms.common.api.Api;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import np.com.krishna.nightbeforeexam.ApiClients.ApiClient;
import np.com.krishna.nightbeforeexam.R;
import np.com.krishna.nightbeforeexam.adapters.DiscussionDetailsRecyclerAdapter;
import np.com.krishna.nightbeforeexam.models.DiscussionForum;
import np.com.krishna.nightbeforeexam.models.RepliesOnDiscussion;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class DiscussionDetailsFragment extends Fragment {

    private RecyclerView recyclerView;
    private EditText replyContent;
    private Button btnReply;
    private TextView postedBy;
    Long discussionId;

    private ArrayList<RepliesOnDiscussion> repliesOnDiscussionArrayList;
    private DiscussionDetailsRecyclerAdapter discussionDetailsRecyclerAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_discussion_details, container, false);

        DiscussionForum discussionForum = new DiscussionForum();
        discussionForum = getArguments().getParcelable("data");
        discussionId = discussionForum.getId();
        Log.d(TAG, "onCreateView: From details of discussion "+discussionId);
        recyclerView = v.findViewById(R.id.repliesOnDiscussion);
        repliesOnDiscussionArrayList = new ArrayList<>();
        replyContent = v.findViewById(R.id.replyOnDiscussion);
        btnReply = v.findViewById(R.id.btnReply);
        postedBy = v.findViewById(R.id.discussionCreatedBy);

        postedBy.setText("Discussion Created By: "+discussionForum.getUser().getUsername());

        btnReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                String token = sharedPreferences.getString("token","");

                String reply = replyContent.getText().toString().trim();
                RepliesOnDiscussion repliesOnDiscussion = new RepliesOnDiscussion();
                repliesOnDiscussion.setReply(reply);

                Call<ResponseBody>call = ApiClient.getDiscusionsService().createReplyonDiscussion(token,discussionId,repliesOnDiscussion);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            Log.d(TAG, "onResponse: Replied");
                            initializeData(discussionId);
                        }else{
                            Log.d(TAG, "onResponse: Sorry something is wrong "+response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.d(TAG, "onFailure: Sorry cannot connect to the server "+t.getMessage());
                    }
                });


            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        initializeData(discussionId);
        return v;
        }

        private void initializeData(Long discussionId){

            Log.d(TAG, "initializeData: "+ discussionId);

            SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
            String token = sharedPreferences.getString("token","");

            Call<List<RepliesOnDiscussion>> call = ApiClient.getDiscusionsService().getRepliesOnDiscussion(token, discussionId);
            call.enqueue(new Callback<List<RepliesOnDiscussion>>() {
                @Override
                public void onResponse(Call<List<RepliesOnDiscussion>> call, Response<List<RepliesOnDiscussion>> response) {
                    if(response.isSuccessful()){
                        Log.d(TAG, "onResponse: getting replies");
                        repliesOnDiscussionArrayList = new ArrayList<>(response.body());
                       // Log.d(TAG, "onResponse: from inside an initailizeData "+ repliesOnDiscussionArrayList.get(0).getReply());
                        discussionDetailsRecyclerAdapter = new DiscussionDetailsRecyclerAdapter(getContext() ,repliesOnDiscussionArrayList);
                        recyclerView.setAdapter(discussionDetailsRecyclerAdapter);

                    }else{
                        Log.d(TAG, "onResponse: Sorry something is wrong "+ response.code());
                    }
                }

                @Override
                public void onFailure(Call<List<RepliesOnDiscussion>> call, Throwable t) {
                    Log.d(TAG, "onFailure: Sorry cannot connect to the server "+ t.getMessage());
                }
            });

        }
    }
