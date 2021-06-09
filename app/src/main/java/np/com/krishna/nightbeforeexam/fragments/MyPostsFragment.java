package np.com.krishna.nightbeforeexam.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.api.Api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import np.com.krishna.nightbeforeexam.ApiClients.ApiClient;
import np.com.krishna.nightbeforeexam.R;
import np.com.krishna.nightbeforeexam.adapters.MyPostsRecyclerAdapter;
import np.com.krishna.nightbeforeexam.interfaces.PostsInterface;
import np.com.krishna.nightbeforeexam.interfaces.ProfileInterface;
import np.com.krishna.nightbeforeexam.models.Posts;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPostsFragment extends Fragment {
   private static RecyclerView recyclerView;
   private MyPostsRecyclerAdapter myPostsRecyclerAdapter;
   private ArrayList<Posts> posts;




    public MyPostsFragment(){

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
            initializeData();


            return view;
    }

    private void initializeData() {

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token","");
        Long userId = sharedPreferences.getLong("id",0);
        Call<List<Posts>> call = ApiClient.getPostsService().getPostsByUserId(token,userId);
        call.enqueue(new Callback<List<Posts>>() {
            @Override
            public void onResponse(Call<List<Posts>> call, Response<List<Posts>> response) {
                if (response.isSuccessful()) {
                    if (response != null) {

                        posts = new ArrayList<>(response.body());

                        myPostsRecyclerAdapter = new MyPostsRecyclerAdapter(getContext(), posts);
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


}
