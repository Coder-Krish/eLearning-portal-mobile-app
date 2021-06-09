package np.com.krishna.nightbeforeexam.activities;


import androidx.appcompat.app.AppCompatActivity;
import np.com.krishna.nightbeforeexam.ApiClients.ApiClient;
import np.com.krishna.nightbeforeexam.R;
import np.com.krishna.nightbeforeexam.models.LoginResponse;
import np.com.krishna.nightbeforeexam.models.SignupRequest;
import np.com.krishna.nightbeforeexam.models.SignupResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class RegisterActivity extends AppCompatActivity {

    private EditText fullname,username,password,email,phone,institution,course,gender,address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final TextView tRegistered= findViewById(R.id.tRegistered);
       // EditText etFullName=findViewById(R.id.etFullName);
        Button btnRegister=findViewById(R.id.btnRegister);
        AutoCompleteTextView spinner = findViewById(R.id.etGender);

        fullname = findViewById(R.id.etFullname);
        username = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPassword);
        email = findViewById(R.id.etEmail);
        phone = findViewById(R.id.etPhone);
        institution = findViewById(R.id.etInstitution);
        course = findViewById(R.id.etProgram);
        gender = findViewById(R.id.etGender);
        address = findViewById(R.id.etAddress);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);



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
                signupRequest.setFullname(fullname.getText().toString());
                signupRequest.setUsername(username.getText().toString());
                signupRequest.setPassword(password.getText().toString());
                signupRequest.setEmail(email.getText().toString());
                signupRequest.setInstitution(institution.getText().toString());
               // signupRequest.setCourse(course.getText().toString());
                Long programId = Long.parseLong(course.getText().toString());
                signupRequest.setProgramId(programId);
//                signupRequest.setPhone(Long.parseLong(phone.getText().toString()));
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
                if(TextUtils.isEmpty(course.getText().toString().trim())){
                    course.setError("Course is required");
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
                if(password.length()<8){
                    password.setError("Password must have a length of 8 characters or more");
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
                    }

                    @Override
                    public void onFailure(Call<SignupResponse> call, Throwable t) {
                        Toast.makeText(RegisterActivity.this, "Failure: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });





    }

}
