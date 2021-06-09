package np.com.krishna.nightbeforeexam.activities;

import androidx.appcompat.app.AppCompatActivity;
import np.com.krishna.nightbeforeexam.interfaces.AuthenticationApiInterface;
import np.com.krishna.nightbeforeexam.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textViewResult= findViewById(R.id.text_view_result);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://jsonplaceholder.typicode.com")
                .baseUrl("http://192.168.1.102:8080/api/")
                .addConverterFactory(ScalarsConverterFactory.create()) //important
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        AuthenticationApiInterface jsonPlaceHolderApi = retrofit.create(AuthenticationApiInterface.class);
        Call<String> call = jsonPlaceHolderApi.message();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if(!response.isSuccessful()){
                    textViewResult.setText("Code: "+response.code());
                    return;
                }else {
                    String msg = response.body();
                    textViewResult.append(msg);
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                textViewResult.setText("Error:"+t.getMessage());
            }
        });
//        Call<List<Post>> call = jsonPlaceHolderApi.getPosts();
//        call.enqueue(new Callback<List<Post>>() {
//            @Override
//            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
//
//                if(!response.isSuccessful()){
//                    textViewResult.setText("Code: "+response.code());
//                    return;
//                }
//                List<Post>posts = response.body();
//                for(Post post : posts){
//                    String content = "";
//                    content += "ID: "+post.getId() + "\n";
//                    content += "User Id: "+post.getUserId() + "\n";
//                    content += "Title: " +post.getTitle() + "\n";
//                    content += "Text: " +post.getText() + "\n\n";
//
//                    textViewResult.append(content);
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Post>> call, Throwable t) {
//                textViewResult.setText(t.getMessage());
//            }
//        });


    }

    public void logOut(View view){

        Intent intoLogin= new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intoLogin);
        finish();
    }
}
