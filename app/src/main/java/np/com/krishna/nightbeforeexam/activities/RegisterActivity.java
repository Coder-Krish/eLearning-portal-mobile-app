package np.com.krishna.nightbeforeexam.activities;


import androidx.appcompat.app.AppCompatActivity;
import np.com.krishna.nightbeforeexam.ApiClients.ApiClient;
import np.com.krishna.nightbeforeexam.R;
import np.com.krishna.nightbeforeexam.fragments.EmailVerificationFragment;
import np.com.krishna.nightbeforeexam.models.LoginResponse;
import np.com.krishna.nightbeforeexam.models.Programs;
import np.com.krishna.nightbeforeexam.models.SignupRequest;
import np.com.krishna.nightbeforeexam.models.SignupResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "night before exam";
    private EditText fullname,username,password,email,phone,institution,course,gender,address,programs;
   // Spinner programsSpinner;
    ArrayList<Programs> programsArrayList;
    AutoCompleteTextView programsSpinner;
    ArrayList<String> p;
    ArrayAdapter<String> programAdapter;
    String programName;
    Long programId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final TextView tRegistered= findViewById(R.id.tRegistered);
       // EditText etFullName=findViewById(R.id.etFullName);
        Button btnRegister=findViewById(R.id.btnRegister);
        AutoCompleteTextView spinner = findViewById(R.id.etGender);

        programsArrayList = new ArrayList<>();
        p = new ArrayList<>();


        fullname = findViewById(R.id.etFullname);
        username = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPassword);
        email = findViewById(R.id.etEmail);
        phone = findViewById(R.id.etPhone);
        institution = findViewById(R.id.etInstitution);
       // course = findViewById(R.id.etProgram);
        gender = findViewById(R.id.etGender);
        address = findViewById(R.id.etAddress);
        programsSpinner = findViewById(R.id.etPrograms);
        programs = findViewById(R.id.etPrograms);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        getProgramsFromServer();




        final ProgressBar progressbar=findViewById(R.id.progressBar);

        tRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Register process
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    SignupRequest signupRequest = new SignupRequest();


                    programName = programs.getText().toString().trim();

                   // Long pId= getProgramByName(programName);


                    signupRequest.setFullname(fullname.getText().toString());
                    signupRequest.setUsername(username.getText().toString());
                    signupRequest.setPassword(password.getText().toString());
                    signupRequest.setEmail(email.getText().toString());
                    signupRequest.setInstitution(institution.getText().toString());
                    //signupRequest.setProgramId(pId);
                    signupRequest.setProgramName(programName);
                    Long phoneNumber =Long.parseLong(phone.getText().toString());
                    signupRequest.setPhone(phoneNumber);
                    signupRequest.setGender(gender.getText().toString());
                    signupRequest.setAddress(address.getText().toString());

                    if(TextUtils.isEmpty(fullname.getText().toString().trim())){
                        email.setError("Fullname is required");
                        return;
                    }
                    if(TextUtils.isEmpty(username.getText().toString().trim())){
                        username.setError("Username is required");
                        return;
                    }
                    if(TextUtils.isEmpty(password.getText().toString().trim())){
                        password.setError("Password is required");
                        return;
                    }
                    if(TextUtils.isEmpty(email.getText().toString().trim())){
                        email.setError("Email is required");
                        return;
                    }
                    if(TextUtils.isEmpty(institution.getText().toString().trim())){
                        institution.setError("Institution is required");
                        return;
                    }
                    if(TextUtils.isEmpty(programs.getText().toString().trim())){
                        programs.setError("Program is required");
                        return;
                    }
                    if(TextUtils.isEmpty(phone.getText().toString().trim())){
                        phone.setError("Phone is required");
                        return;
                    }
                    if(TextUtils.isEmpty(address.getText().toString().trim())){
                        address.setError("Address is required");
                        return;
                    }
                    if(TextUtils.isEmpty(gender.getText().toString().trim())){
                        gender.setError("Gender is required");
                        return;
                    }
                    if(password.length()<6){
                        password.setError("Password must have a length of 6 characters or more");
                        return;
                    }
                    if(phone.length()<10 || phone.length()>10){
                        phone.setError("Phone number should be of 10 digits");
                        return;
                    }

                    Call<SignupResponse> signupResponseCall = ApiClient.getUserService().userRegister(signupRequest);
                    signupResponseCall.enqueue(new Callback<SignupResponse>() {
                        @Override
                        public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                            SignupResponse signupResponse = response.body();
                            Toast.makeText(RegisterActivity.this, signupResponse.getMessage(), Toast.LENGTH_SHORT).show();

                            EmailVerificationFragment emailVerificationFragment = new EmailVerificationFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("username",username.getText().toString());
                            emailVerificationFragment.setArguments(bundle);
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, emailVerificationFragment).addToBackStack(null).commit();


                        }

                        @Override
                        public void onFailure(Call<SignupResponse> call, Throwable t) {
                            Toast.makeText(RegisterActivity.this, "Failure: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

            }
        });





    }

    private Long  getProgramByName(String programName) {
        Log.d(TAG, "getProgramByName: From getProgramName "+programName);
        Call<Programs> call = ApiClient.getProgramsService().getProgramByName(programName);
        call.enqueue(new Callback<Programs>() {
            @Override
            public void onResponse(Call<Programs> call, Response<Programs> response) {
                if(response.isSuccessful()){
                    programId = response.body().getId();

                }
            }

            @Override
            public void onFailure(Call<Programs> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Failure "+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        return programId;
    }

    private void getProgramsFromServer() {

        Log.d(TAG, "getProgramsFromServer: From getProgramFromServer");
        Call<List<Programs>> call =ApiClient.getProgramsService().getAllPrograms();
        call.enqueue(new Callback<List<Programs>>() {
            @Override
            public void onResponse(Call<List<Programs>> call, Response<List<Programs>> response) {
                if(response.isSuccessful()){
                    programsArrayList = new ArrayList<>(response.body());
                    for(int i = 0; i< programsArrayList.size(); i++){
                        p.add(programsArrayList.get(i).getProgramName());
                    }
                   programAdapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item,p);
                    // Specify the layout to use when the list of choices appears
                    programAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    // Apply the adapter to the spinner
                    programsSpinner.setAdapter(programAdapter);

                }
            }

            @Override
            public void onFailure(Call<List<Programs>> call, Throwable t) {

            }
        });

    }

}
