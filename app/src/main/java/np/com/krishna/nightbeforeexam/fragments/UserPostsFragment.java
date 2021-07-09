package np.com.krishna.nightbeforeexam.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import np.com.krishna.nightbeforeexam.adapters.AllPostsRecyclerAdapter;
import np.com.krishna.nightbeforeexam.adapters.MyPostsRecyclerAdapter;
import np.com.krishna.nightbeforeexam.models.Posts;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserPostsFragment extends Fragment implements AllPostsRecyclerAdapter.RecyclerViewAllPostsClickListener {

    private static RecyclerView recyclerView;
    private AllPostsRecyclerAdapter myPostsRecyclerAdapter;
    private AllPostsRecyclerAdapter.RecyclerViewAllPostsClickListener recyclerViewAllPostsClickListener;
    private ArrayList<Posts> posts;
    private Long userId;



    public UserPostsFragment(Long userId){
        this.userId = userId;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_myposts, container, false);

        recyclerView = view.findViewById(R.id.postsRecyclerView);
        posts = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        initializeData(userId);


        return view;
    }

    private void initializeData(Long userId) {

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token","");
       // Long userId = sharedPreferences.getLong("id",0);
        Call<List<Posts>> call = ApiClient.getPostsService().getPostsByUserId(token,userId);
        call.enqueue(new Callback<List<Posts>>() {
            @Override
            public void onResponse(Call<List<Posts>> call, Response<List<Posts>> response) {
                if (response.isSuccessful()) {
                    if (response != null) {

                        posts = new ArrayList<>(response.body());
                        setOnClickListener();
                        myPostsRecyclerAdapter = new AllPostsRecyclerAdapter(getContext(), posts, recyclerViewAllPostsClickListener);
                        recyclerView.setAdapter(myPostsRecyclerAdapter);
                    }else{
                        Toast.makeText(getActivity(), "No Data found", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getActivity(), "Unauthorized", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Posts>> call, Throwable t) {
                Toast.makeText(getActivity(), "Failure:"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setOnClickListener() {
        recyclerViewAllPostsClickListener = new AllPostsRecyclerAdapter.RecyclerViewAllPostsClickListener() {
            @Override
            public void onClick(View v, int position) {
                PostDetailsFragment postDetailsFragment = new PostDetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("postData",posts.get(position));
                postDetailsFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,postDetailsFragment).addToBackStack(null).commit();
            }
        };
    }


    @Override
    public void onClick(View v, int position) {

    }
}
