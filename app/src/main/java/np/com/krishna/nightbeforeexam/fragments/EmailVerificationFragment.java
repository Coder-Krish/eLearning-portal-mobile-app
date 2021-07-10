package np.com.krishna.nightbeforeexam.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import np.com.krishna.nightbeforeexam.ApiClients.ApiClient;
import np.com.krishna.nightbeforeexam.R;
import np.com.krishna.nightbeforeexam.activities.LoginActivity;
import np.com.krishna.nightbeforeexam.models.MessageResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class EmailVerificationFragment extends Fragment {

    private EditText otp;
    private Button btnVerify;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_email_verification, container, false);

        otp = v.findViewById(R.id.otpToValidate);
        btnVerify = v.findViewById(R.id.btnVerification);

        String username = getArguments().getString("username");

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String normalOtp = otp.getText().toString().trim();


                if(TextUtils.isEmpty(normalOtp)){
                    otp.setError("Otp cannot be empty");
                    return;
                }
                if(normalOtp.length()>6 || normalOtp.length()<6){
                    otp.setError("Otp should be of 6 characters long");
                    return;
                }
                int validOtp = Integer.parseInt(normalOtp);
                Call<MessageResponse> call = ApiClient.getEmailVerificationServices().verifyEmail(username,validOtp);
                call.enqueue(new Callback<MessageResponse>() {
                    @Override
                    public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                        if(response.isSuccessful()){
                            if(response.body().getMessage().equals("Email verified you can log into the system now.")){
                                Intent intent = new Intent(getContext(), LoginActivity.class);
                                startActivity(intent);
                            }else if(response.body().getMessage().equals("Otp did not match")){
                                Log.d(TAG, "onResponse: Otp no matched");
                                Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Log.d(TAG, "onResponse: Sorry something is wrong "+ response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<MessageResponse> call, Throwable t) {
                        Log.d(TAG, "onFailure: Sorry cannot connect to the server "+t.getMessage());
                    }
                });

            }
        });

        return v;
    }

}
