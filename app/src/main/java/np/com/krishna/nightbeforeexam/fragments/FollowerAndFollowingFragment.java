package np.com.krishna.nightbeforeexam.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import np.com.krishna.nightbeforeexam.adapters.FollowAndFollowingRecyclerAdapter;
import np.com.krishna.nightbeforeexam.adapters.NotesRecyclerAdapter;
import np.com.krishna.nightbeforeexam.models.Follow;
import np.com.krishna.nightbeforeexam.models.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class FollowerAndFollowingFragment extends Fragment implements FollowAndFollowingRecyclerAdapter.RecyclerViewNameClickListener {
    RecyclerView recyclerView;
    private ArrayList<Follow> followArrayList;
    private ArrayList<User> userArrayList;
    FollowAndFollowingRecyclerAdapter followAndFollowingRecyclerAdapter;
    private FollowAndFollowingRecyclerAdapter.RecyclerViewNameClickListener recyclerViewNameClickListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_follower_and_following, container, false);
        recyclerView = v.findViewById(R.id.listOfFollow);
        followArrayList = new ArrayList<>();
        userArrayList = new ArrayList<>();
        String follow = getArguments().getString("follow");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        initializaData(follow);

        return v;
        }

    private void initializaData(String follow) {
        if(follow.equals("follower")){
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
            String token = sharedPreferences.getString("token","");
            Log.d(TAG, "onCreateView: "+follow);
            Call<List<User>> call = ApiClient.getFollowServices().getFollower(token);
            call.enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    if(response.isSuccessful()){

                        userArrayList = new ArrayList<>(response.body());
                        setOnClickListener();
                        followAndFollowingRecyclerAdapter = new FollowAndFollowingRecyclerAdapter(getContext(),userArrayList,recyclerViewNameClickListener);
                        recyclerView.setAdapter(followAndFollowingRecyclerAdapter);

                    }else{
                        Log.d(TAG, "onResponse: Something is wrong");
                    }
                }

                @Override
                public void onFailure(Call<List<User>> call, Throwable t) {
                    Log.d(TAG, "onFailure: Failure");
                }
            });
        }
        if(follow.equals("following")){
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
            String token = sharedPreferences.getString("token","");
            Log.d(TAG, "onCreateView: "+follow);
            Call<List<User>> call = ApiClient.getFollowServices().getFollowing(token);
            call.enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    if(response.isSuccessful()){

                        userArrayList = new ArrayList<>(response.body());
                        setOnClickListener();
                        followAndFollowingRecyclerAdapter = new FollowAndFollowingRecyclerAdapter(getContext(),userArrayList,recyclerViewNameClickListener);
                        recyclerView.setAdapter(followAndFollowingRecyclerAdapter);

                    }else{
                        Log.d(TAG, "onResponse: Something is wrong");
                    }
                }

                @Override
                public void onFailure(Call<List<User>> call, Throwable t) {
                    Log.d(TAG, "onFailure: Failure");
                }
            });

        }

    }

    private void setOnClickListener() {
        recyclerViewNameClickListener = new FollowAndFollowingRecyclerAdapter.RecyclerViewNameClickListener() {
            @Override
            public void onClick(View v, int position) {
                Log.d(TAG, "onClick: "+ userArrayList.get(position).getFullname());

                UserProfileFragement userProfileFragement = new UserProfileFragement();
                Bundle bundle = new Bundle();
                bundle.putParcelable("data", userArrayList.get(position));
                userProfileFragement.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, userProfileFragement).addToBackStack(null).commit();

            }
        };
    }

    @Override
    public void onClick(View v, int position) {
        Log.d(TAG, "onClick: implemented");
    }
}
