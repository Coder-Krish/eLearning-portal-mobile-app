package np.com.krishna.nightbeforeexam.fragments;

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
import np.com.krishna.nightbeforeexam.models.MessageResponse;
import np.com.krishna.nightbeforeexam.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class ForgotPasswordFragment extends Fragment {

    private EditText etUsername;
    private EditText etEmail;
    private Button btnForgot;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_forgot_password, container, false);

        etUsername = v.findViewById(R.id.forgotPasswordUsername);
        etEmail = v.findViewById(R.id.validEmail);

        btnForgot = v.findViewById(R.id.btnForgotPassword);

        btnForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String email = etEmail.getText().toString().trim();

                if(TextUtils.isEmpty(username)){
                    etUsername.setError("Username cannot be empty");
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    etUsername.setError("Email cannot be empty");
                    return;
                }

                User user = new User();
                user.setUsername(username);
                user.setEmail(email);
                Log.d(TAG, "onClick: Forgot button clicked");
                Call<MessageResponse> call = ApiClient.getPasswordServices().generateOtp(user);
                call.enqueue(new Callback<MessageResponse>() {
                    @Override
                    public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                        if(response.isSuccessful()){
                            if(response.body().getMessage().equals("Otp is generated check your email")){
                                Log.d(TAG, "onResponse: Otp generated");
                                OtpFragment otpFragment = new OtpFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString("username",username);
                                otpFragment.setArguments(bundle);

                                getFragmentManager().beginTransaction().replace(R.id.fragment_container, otpFragment).addToBackStack(null).commit();
                            }else{
                                Log.d(TAG, "onResponse: Sorry something is wrong "+response.code());
                            }
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
