package np.com.krishna.nightbeforeexam.activities;


import androidx.appcompat.app.AppCompatActivity;
import np.com.krishna.nightbeforeexam.ApiClients.ApiClient;
import np.com.krishna.nightbeforeexam.HelperClasses.SharedPrefManager;
import np.com.krishna.nightbeforeexam.R;
import np.com.krishna.nightbeforeexam.interfaces.AuthenticationApiInterface;
import np.com.krishna.nightbeforeexam.models.LoginRequest;
import np.com.krishna.nightbeforeexam.models.LoginResponse;
import np.com.krishna.nightbeforeexam.models.Programs;
import np.com.krishna.nightbeforeexam.models.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Tag;


import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
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
                            User loginResponse = response.body();
                            token = "Bearer"+" "+loginResponse.getToken();
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
                            editor.putLong("programId",programId);
                            editor.putString("programName",programName);

                            editor.commit();

                                    if (response.code() == 200) {

                                        Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();
                                        startHomeActivity();

                                    }else{
                                        Toast.makeText(LoginActivity.this, "Some Error occured", Toast.LENGTH_SHORT).show();
                                    }




                        }else{
                            Toast.makeText(LoginActivity.this,"Login Failed", Toast.LENGTH_LONG).show();

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
