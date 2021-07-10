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
import np.com.krishna.nightbeforeexam.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class SearchFragment extends Fragment implements FollowAndFollowingRecyclerAdapter.RecyclerViewNameClickListener {

    private EditText searchContent;
    private Button btnSearch;
    private RecyclerView recyclerView;

    private ArrayList<User> userArrayList;

    private FollowAndFollowingRecyclerAdapter followAndFollowingRecyclerAdapter;

    FollowAndFollowingRecyclerAdapter.RecyclerViewNameClickListener recyclerViewNameClickListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search,container,false);

        searchContent = v.findViewById(R.id.searchContent);
        btnSearch = v.findViewById(R.id.btnSearch);
        recyclerView = v.findViewById(R.id.searchResult);
        userArrayList = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchValue = searchContent.getText().toString().trim();

                SharedPreferences sharedPreferences = getContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                String token = sharedPreferences.getString("token","");


                Call<List<User>> call = ApiClient.getUser().getUser(token, searchValue);
                call.enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        if(response.isSuccessful()){
                            userArrayList = new ArrayList<>(response.body());
                            setOnclickListener();
                            followAndFollowingRecyclerAdapter = new FollowAndFollowingRecyclerAdapter(getContext(),userArrayList,recyclerViewNameClickListener);
                            recyclerView.setAdapter(followAndFollowingRecyclerAdapter);

                        }
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {
                        Log.d(TAG, "onFailure:Search, Sorry Cannot connect to the server "+t.getMessage());
                    }
                });
            }
        });

        return v;
    }

    private void setOnclickListener() {

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

    }
}
