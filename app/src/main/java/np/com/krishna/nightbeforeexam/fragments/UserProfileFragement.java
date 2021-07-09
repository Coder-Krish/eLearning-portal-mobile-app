package np.com.krishna.nightbeforeexam.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import np.com.krishna.nightbeforeexam.ApiClients.ApiClient;
import np.com.krishna.nightbeforeexam.HelperClasses.MyAdapter;
import np.com.krishna.nightbeforeexam.HelperClasses.ProfileAdapter;
import np.com.krishna.nightbeforeexam.R;
import np.com.krishna.nightbeforeexam.models.MessageResponse;
import np.com.krishna.nightbeforeexam.models.ProfilePicture;
import np.com.krishna.nightbeforeexam.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class UserProfileFragement extends Fragment {

    private TextView loggedInUserNameTV;
    private TextView fullname,followerTV,followingTV;
    // private TextView loadUploadDirTV;
    private ImageView profilePicDisplay;
    //private String imgUrl;
    private String token;
    private Long userId;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_profile, container, false);

        loggedInUserNameTV = v.findViewById(R.id.username);
        fullname = v.findViewById(R.id.fullname);
   /*     followerTV = v.findViewById(R.id.followerCount);
        followingTV = v.findViewById(R.id.followingCount);*/
        profilePicDisplay = v.findViewById(R.id.profilePicture);

        tabLayout = v.findViewById(R.id.tabLayout);
        viewPager = v.findViewById(R.id.viewPager);

        tabLayout.addTab(tabLayout.newTab().setText("Posts"));
        tabLayout.addTab(tabLayout.newTab().setText("Discussions"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        User user = getArguments().getParcelable("data");
        fullname.setText(user.getFullname());
        loggedInUserNameTV.setText(user.getUsername());
        Long userId = user.getId();

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token","");
        Call<ProfilePicture> call = ApiClient.getProfileService().loadProfilePicture(token,userId);
        call.enqueue(new Callback<ProfilePicture>() {
            @Override
            public void onResponse(Call<ProfilePicture> call, Response<ProfilePicture> response) {
                if(response.isSuccessful()){
                    try {
                        //System.out.println(response.body().getUploadDir());
                        String fileName = response.body().getFileName();

                        String url = "http://192.168.1.102:8080/api/test/downloadFile/"+fileName;
                        Picasso.get().load(url).into(profilePicDisplay);



                    }catch(Exception e){
                        //Toast.makeText(ProfileFragment.this, "Exception: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onResponse: Exception "+e.getMessage());
                    }
                }else{
                    Picasso.get().load(R.drawable.avatar).into(profilePicDisplay);
                    Toast.makeText(getActivity(), "Error: "+response.code(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ProfilePicture> call, Throwable t) {
                Picasso.get().load(R.drawable.avatar).into(profilePicDisplay);
            }
        });

        /*tab layout starts*/

        final ProfileAdapter adapter = new ProfileAdapter(getChildFragmentManager(),this.getActivity(), tabLayout.getTabCount(),userId);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        /*tab layout ends*/

    /*    countFollower(userId);
        countFollowing(userId);*/
        return v;
    }

 /*   void countFollower(Long userId){
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token","");
        Call<MessageResponse> followerCountCall = ApiClient.getProfileService().countFollowers(token,userId);
        followerCountCall.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if(response.isSuccessful()){

                    followerTV.setText(response.body().getMessage());
                }else{
                    followerTV.setText("0 Follower");
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Failure: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    void countFollowing(Long userId){
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token","");
        Call<MessageResponse> followingCountCall = ApiClient.getProfileService().countFollowing(token,userId);
        followingCountCall.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if(response.isSuccessful()){
                    followingTV.setText(response.body().getMessage());
                }else{
                    followingTV.setText("0 Follower");
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Failure: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }*/
}
