package np.com.krishna.nightbeforeexam.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import np.com.krishna.nightbeforeexam.ApiClients.ApiClient;
import np.com.krishna.nightbeforeexam.R;
import np.com.krishna.nightbeforeexam.adapters.MyDiscussionsRecyclerAdapter;
import np.com.krishna.nightbeforeexam.models.DiscussionForum;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import static android.content.ContentValues.TAG;

public class MyDiscussionsFragment extends Fragment implements MyDiscussionsRecyclerAdapter.RecyclerViewDiscussionsClickListener {

    private static RecyclerView recyclerView;
    private MyDiscussionsRecyclerAdapter myDiscussionsRecyclerAdapter;
    private ArrayList<DiscussionForum> discussions;
    private MyDiscussionsRecyclerAdapter.RecyclerViewDiscussionsClickListener recyclerViewDiscussionsClickListener;

    public MyDiscussionsFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mydiscussions, container, false);

        recyclerView = view.findViewById(R.id.discussionsRecyclerView);
        discussions = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        initializeData();
        return view;
    }

    private void initializeData() {


        // recyclerView.setAdapter(new MyDiscussionsRecyclerAdapter(getContext(), discussions));
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token","");
        Long userId = sharedPreferences.getLong("id",0);

        Call<List<DiscussionForum>> call = ApiClient.getDiscusionsService().getDiscussionsByUserId(token,userId);
        call.enqueue(new Callback<List<DiscussionForum>>() {
            @Override
            public void onResponse(retrofit2.Call<List<DiscussionForum>> call, Response<List<DiscussionForum>> response) {
                if (response.isSuccessful()) {
                    if (response != null) {

                        discussions = new ArrayList<>(response.body());
                        setOnClickListener();
                        myDiscussionsRecyclerAdapter = new MyDiscussionsRecyclerAdapter(getContext(), discussions, recyclerViewDiscussionsClickListener);
                        recyclerView.setAdapter(myDiscussionsRecyclerAdapter);
                    }else{
                        Toast.makeText(getActivity(), "No Data found", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getActivity(), "Unauthorized", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<DiscussionForum>> call, Throwable t) {
                Toast.makeText(getActivity(), "Failure:"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setOnClickListener() {
        recyclerViewDiscussionsClickListener = new MyDiscussionsRecyclerAdapter.RecyclerViewDiscussionsClickListener() {
            @Override
            public void onclick(View v, int position) {
                DiscussionDetailsFragment discussionDetailsFragment = new DiscussionDetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("data",discussions.get(position));
                discussionDetailsFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, discussionDetailsFragment).addToBackStack(null).commit();

            }
        };
    }

    @Override
    public void onclick(View v, int position) {
        Log.d(TAG, "onclick: Discussion Clicked");
    }
}
