package np.com.krishna.nightbeforeexam.activities;


import androidx.appcompat.app.AppCompatActivity;
import np.com.krishna.nightbeforeexam.ApiClients.ApiClient;
import np.com.krishna.nightbeforeexam.HelperClasses.SharedPrefManager;
import np.com.krishna.nightbeforeexam.R;
import np.com.krishna.nightbeforeexam.fragments.EmailVerificationFragment;
import np.com.krishna.nightbeforeexam.fragments.ForgotPasswordFragment;
import np.com.krishna.nightbeforeexam.interfaces.AuthenticationApiInterface;
import np.com.krishna.nightbeforeexam.models.LoginRequest;
import np.com.krishna.nightbeforeexam.models.Programs;
import np.com.krishna.nightbeforeexam.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private TextView forgotPassword;
    private static String token;

    private static String username;
    private static String userEmail;
    private static Long id;
    private static String fullName;
    private static Long programId;
    private static String programName;

   // private ProgressBar progressbar = findViewById(R.id.progressBar);
   AuthenticationApiInterface authenticationApiInterface;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);

        setContentView(R.layout.activity_login);


        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);

        String savedToken = sharedPreferences.getString("token","");
        if(!savedToken.equals("")){
            Intent intent = new Intent(LoginActivity.this, StartActivity.class);
            startActivity(intent);
            finish();
        }

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        forgotPassword = findViewById(R.id.forgotPassword);

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              ForgotPasswordFragment forgotPasswordFragment = new ForgotPasswordFragment();
              getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,forgotPasswordFragment).addToBackStack(null).commit();
            }
        });


        //registration
        Button btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        //event handler when login button is clicked.
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginRequest loginRequest = new LoginRequest();
                loginRequest.setUsername(etUsername.getText().toString());
                loginRequest.setPassword(etPassword.getText().toString());
                String email = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();



                if (TextUtils.isEmpty(email)) {
                    etUsername.setError("Email is Required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    etPassword.setError("Password is Required");
                    return;
                }

                if (password.length() < 6) {
                    etPassword.setError("Password must be 8 characters long or more");
                    return;
                }

                //Initialize progressbar
              //  progressbar.setVisibility(View.VISIBLE);
                //Authenticate the user.

             authenticationApiInterface = ApiClient.getUserService();
                authenticationApiInterface.userLogin(loginRequest).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.isSuccessful()){
                                    if (response.code() == 200) {

                                        User loginResponse = response.body();
                                        if(loginResponse.getMessage() == null) {
                                            token = "Bearer" + " " + loginResponse.getToken();
                                            username = loginResponse.getUsername();
                                            userEmail = loginResponse.getEmail();
                                            id = loginResponse.getId();
                                            fullName = loginResponse.getFullname();
                                            Programs programs = loginResponse.getCourseDetail();
                                            programId = programs.getId();
                                            programName = programs.getProgramName();

                                            //Log.d(TAG, "onResponse: "+loginResponse.getCourseDetail().toString());

                                            SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("token", token);
                                            editor.putString("username", username);
                                            editor.putString("email", userEmail);
                                            editor.putLong("id", id);
                                            editor.putString("fullname", fullName);
                                            // editor.putLong("programId",programId);
                                            editor.putLong("programId", programId);
                                            editor.putString("programName", programName);

                                            editor.commit();

                                            Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();
                                            startHomeActivity();
                                        }else{
                                            Toast.makeText(LoginActivity.this, "You need to verify your email", Toast.LENGTH_SHORT).show();

                                            EmailVerificationFragment emailVerificationFragment = new EmailVerificationFragment();
                                            Bundle bundle = new Bundle();
                                            bundle.putString("username",email);
                                            emailVerificationFragment.setArguments(bundle);
                                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,emailVerificationFragment).addToBackStack(null).commit();

                                        }


                                    }
                                   /* else if(response.body().getMessage().equals("Verify your email first")){
                                        Toast.makeText(LoginActivity.this, "You need to verify your email", Toast.LENGTH_SHORT).show();

                                        EmailVerificationFragment emailVerificationFragment = new EmailVerificationFragment();
                                        Bundle bundle = new Bundle();
                                        bundle.putString("username",email);
                                        emailVerificationFragment.setArguments(bundle);
                                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,emailVerificationFragment).addToBackStack(null).commit();

                                    }*/

                        }else{
                            Toast.makeText(LoginActivity.this,"Login Credentials do not match ", Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(LoginActivity.this,"Failure "+t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

            }
        });



    }

    private void saveUserCredentials(String userCredentials) {
        new SharedPrefManager(this).saveUserCredentials(userCredentials);
    }

    private void startHomeActivity() {
        Intent i = new Intent(LoginActivity.this,StartActivity.class);
        startActivity(i);
        finish();

    }

    }
