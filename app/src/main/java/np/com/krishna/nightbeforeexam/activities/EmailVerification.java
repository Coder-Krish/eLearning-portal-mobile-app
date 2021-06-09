package np.com.krishna.nightbeforeexam.activities;


import androidx.appcompat.app.AppCompatActivity;
import np.com.krishna.nightbeforeexam.R;

import android.os.Bundle;
import com.google.android.material.textview.MaterialTextView;


public class EmailVerification extends AppCompatActivity {

    private String email;
   private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification);




        MaterialTextView tView=findViewById(R.id.tEmailVerification);


    }
}
