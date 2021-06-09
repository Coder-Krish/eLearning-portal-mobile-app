package np.com.krishna.nightbeforeexam.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import np.com.krishna.nightbeforeexam.ApiClients.ApiClient;
import np.com.krishna.nightbeforeexam.activities.LoginActivity;
import np.com.krishna.nightbeforeexam.R;
import np.com.krishna.nightbeforeexam.adapters.AllPostsRecyclerAdapter;
import np.com.krishna.nightbeforeexam.adapters.MyPostsRecyclerAdapter;
import np.com.krishna.nightbeforeexam.models.Posts;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    ArrayList<Posts> posts;
    RecyclerView recyclerView;

    private String token;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);


        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");


        FloatingActionButton btnFloating = v.findViewById(R.id.btnFloating);
        btnFloating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(getActivity())
                        .setTitle("Information")
                        .setMessage("Post functionality coming soon!")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create();
                dialog.show();
            }
        });
        recyclerView = v.findViewById(R.id.allPostsRecyclerView);
        posts = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        initializeData();

        return v;

    }

    private void initializeData() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token","");
                Call<List<Posts>> call = ApiClient.getPostsService().getPostsOfMyFollowings(token);
                call.enqueue(new Callback<List<Posts>>() {
                    @Override
                    public void onResponse(Call<List<Posts>> call, Response<List<Posts>> response) {
                        if(response.isSuccessful()){
                            posts = new ArrayList<>(response.body());
                            recyclerView.setAdapter(new AllPostsRecyclerAdapter(getContext(),posts));

                        }else{
                            Toast.makeText(getContext(), "Error: Sorry we are looking into this", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Posts>> call, Throwable t) {
                        Toast.makeText(getContext(), "Failure: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }
}
