package np.com.krishna.nightbeforeexam.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import np.com.krishna.nightbeforeexam.ApiClients.ApiClient;
import np.com.krishna.nightbeforeexam.R;
import np.com.krishna.nightbeforeexam.models.User;
import np.com.krishna.nightbeforeexam.models.UserView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class ProfileDetailsViewFragment extends Fragment {

    private TextView displayUsername,displayFullName,displayEmail,displayPhone,displayProgram,displayInstitution,displayAddress,displayGender;
    private UserView user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile_details_view, container, false);

        displayUsername = v.findViewById(R.id.displayUsername);
        displayFullName = v.findViewById(R.id.displayFullName);
        displayEmail = v.findViewById(R.id.displayEmail);
        displayPhone = v.findViewById(R.id.displayPhone);
        displayProgram = v.findViewById(R.id.displayProgram);
        displayInstitution = v.findViewById(R.id.displayInstitution);
        displayAddress = v.findViewById(R.id.displayAddress);
        displayGender = v.findViewById(R.id.displayGender);

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token","");
        Long userId = sharedPreferences.getLong("id",0);

        Call<UserView> call = ApiClient.getUser().getUserDetails(token, userId);
        call.enqueue(new Callback<UserView>() {
            @Override
            public void onResponse(Call<UserView> call, Response<UserView> response) {
                if(response.isSuccessful()){
                    user = response.body();
                    displayUsername.setText(user.getUsername());
                    displayAddress.setText(user.getAddress());
                    displayEmail.setText(user.getEmail());
                    displayFullName.setText(user.getFullname());
                    displayProgram.setText(user.getCourseDetail().getProgramName());
                    displayPhone.setText(user.getPhone().toString());
                    displayInstitution.setText(user.getInstitution());
                    displayGender.setText(user.getGender());
                }else{
                    Log.d(TAG, "onResponse: Sorry something is wrong "+response.code());
                }
            }

            @Override
            public void onFailure(Call<UserView> call, Throwable t) {
                Log.d(TAG, "onFailure: Sorry cannot connect to the server "+t.getMessage());
            }
        });


        return v;
        }
    }
