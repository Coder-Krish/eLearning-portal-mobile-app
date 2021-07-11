package np.com.krishna.nightbeforeexam.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import np.com.krishna.nightbeforeexam.ApiClients.ApiClient;
import np.com.krishna.nightbeforeexam.R;
import np.com.krishna.nightbeforeexam.models.User;
import np.com.krishna.nightbeforeexam.models.UserView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class ProfileDetailsChangeFragment extends Fragment {

    EditText displayUsername,displayFullName,displayEmail,displayPhone,displayProgram,displayInstitution,displayAddress;
    Button btnUpdate;
    private UserView user;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile_details_change, container, false);

        displayUsername = v.findViewById(R.id.profileUsername);
        displayFullName = v.findViewById(R.id.profileFullName);
        displayEmail = v.findViewById(R.id.profileEmail);
        displayPhone = v.findViewById(R.id.profilePhone);
        displayProgram = v.findViewById(R.id.profileProgram);
        displayInstitution = v.findViewById(R.id.profileInstitution);
        displayAddress = v.findViewById(R.id.profileAddress);
        btnUpdate = v.findViewById(R.id.btnUpdate);

        displayUsername.setEnabled(false);
        displayEmail.setEnabled(false);
        displayProgram.setEnabled(false);

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
                }else{
                    Log.d(TAG, "onResponse: Sorry something is wrong "+response.code());
                }
            }

            @Override
            public void onFailure(Call<UserView> call, Throwable t) {
                Log.d(TAG, "onFailure: Sorry cannot connect to the server "+t.getMessage());
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = displayAddress.getText().toString().trim();
                String fullName = displayFullName.getText().toString().trim();
                Long phone = Long.parseLong(displayPhone.getText().toString().trim());
                String institution = displayInstitution.getText().toString().trim();

                if(TextUtils.isEmpty(address)){
                    displayAddress.setError("Address cannot be empty");
                    return;
                }
                if(TextUtils.isEmpty(fullName)){
                    displayFullName.setError("Fullname cannot be empty");
                    return;
                }

                if(TextUtils.isEmpty(phone.toString())){
                    displayPhone.setError("Phone cannot be empty");
                    return;
                }
                if(TextUtils.isEmpty(institution)){
                    displayInstitution.setError("Institution cannot be empty");
                    return;
                }
                if(displayPhone.length()>10 || displayPhone.length()<10){
                    displayPhone.setError("Phone should be of 10 characters long");
                }

                User user = new User();
                user.setAddress(address);
                user.setPhone(phone);
                user.setFullname(fullName);
                user.setInstitution(institution);

                Call<ResponseBody> call1 = ApiClient.getUser().updateUserDetails(token,userId, user);
                call1.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            Log.d(TAG, "onResponse: Updated successfully");
                            getFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileDetailsViewFragment()).addToBackStack(null).commit();
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

        return v;
    }
}
