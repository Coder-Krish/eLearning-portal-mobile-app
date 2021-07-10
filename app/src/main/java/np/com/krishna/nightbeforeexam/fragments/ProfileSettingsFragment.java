package np.com.krishna.nightbeforeexam.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;
import np.com.krishna.nightbeforeexam.ApiClients.ApiClient;
import np.com.krishna.nightbeforeexam.R;
import np.com.krishna.nightbeforeexam.models.MessageResponse;
import np.com.krishna.nightbeforeexam.models.ProfilePicture;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class ProfileSettingsFragment extends Fragment {

    private static  final int MY_PERMISSIONS_REQUEST = 100;
    private  int PICK_IMAGE_FROM_GALLERY_REQUEST = 1;

    TextView textViewAddProfilePicture;
    TextView textViewChangeProfilePicture;
    TextView textViewChangeProfileDetails;
    TextView textViewProfileDetails;
    String token;
    private ProfilePicture profilePicture;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile_settings, container, false);



        textViewAddProfilePicture = v.findViewById(R.id.addProfilePicture);
        textViewChangeProfilePicture = v.findViewById(R.id.changeProfilePicture);
        textViewChangeProfileDetails = v.findViewById(R.id.updateProfileDetails);
        textViewProfileDetails = v.findViewById(R.id.viewProfileDetails);

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token","");
        Long userId = sharedPreferences.getLong("id",0);

        Call<ProfilePicture> call = ApiClient.getProfileService().checkProfilePictureStatus(token,userId);
        call.enqueue(new Callback<ProfilePicture>() {
            @Override
            public void onResponse(Call<ProfilePicture> call, Response<ProfilePicture> response) {
                profilePicture = response.body();
                if(response.body()!=null){
                    Log.d(TAG, "onResponse: Setting visibikity for update");
                    textViewChangeProfilePicture.setVisibility(View.VISIBLE);
                    textViewAddProfilePicture.setVisibility(View.GONE);
                }else{
                    Log.d(TAG, "onResponse: Setting visibility for add");
                    textViewChangeProfilePicture.setVisibility(View.GONE);
                    textViewAddProfilePicture.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ProfilePicture> call, Throwable t) {
                Log.d(TAG, "onFailure: Sorry cannot connect to the server");
            }
        });


        textViewAddProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Add picture clicked");
                Intent intent = new Intent();
                intent.setType("images/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                        PICK_IMAGE_FROM_GALLERY_REQUEST);
                
            }
        });

        textViewChangeProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: update picture clicked");
                Intent intent = new Intent();
                intent.setType("images/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                        PICK_IMAGE_FROM_GALLERY_REQUEST);
            }
        });

        textViewProfileDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new ForgotPasswordFragment()).addToBackStack(null).commit();
            }
        });

        return v;
        }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_FROM_GALLERY_REQUEST && resultCode == getActivity().RESULT_OK && data != null && data.getData() !=null){
            Uri uri = data.getData();
            String imgPath = getRealPathOfImage(uri);
            Log.d(TAG, "onActivityResult: "+imgPath);



            SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
            String token = sharedPreferences.getString("token","");
            Long userId = sharedPreferences.getLong("id",0);

            Call<ProfilePicture> call = ApiClient.getProfileService().checkProfilePictureStatus(token,userId);
            call.enqueue(new Callback<ProfilePicture>() {
                @Override
                public void onResponse(Call<ProfilePicture> call, Response<ProfilePicture> response) {
                    profilePicture = response.body();
                    if(response.body()!=null){
                        Log.d(TAG, "onResponse: Setting visibikity for update");
                        textViewChangeProfilePicture.setVisibility(View.VISIBLE);
                        textViewAddProfilePicture.setVisibility(View.GONE);
                    }else{
                        Log.d(TAG, "onResponse: Setting visibility for add");
                        textViewChangeProfilePicture.setVisibility(View.GONE);
                        textViewAddProfilePicture.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<ProfilePicture> call, Throwable t) {
                    Log.d(TAG, "onFailure: Sorry cannot connect to the server");
                }
            });



            if(profilePicture==null) {
                uploadFile(imgPath);
            }else{
                updateProfilePicture(imgPath);
            }
        }
    }

   

    private String getRealPathOfImage(Uri uri){
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getContext(), uri, projection,null,null,null);
        Cursor cursor = loader.loadInBackground();
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(columnIndex);

        cursor.close();
        return result;
    }

    private void uploadFile(String imagePath) {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        Long userId = sharedPreferences.getLong("id",0);

        File profile = new File(imagePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), profile);
        MultipartBody.Part profileImage = MultipartBody.Part.createFormData("profile",profile.getName(), requestFile);

        Call<MessageResponse> call = ApiClient.getProfileService().addProfilePicture(token,userId,profileImage);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().getMessage().equals("Profile Picture uploaded")) {
                        Log.d(TAG, "onResponse: Profile Picture added ");
                        getFragmentManager().beginTransaction().replace(R.id.fragment_container,new ProfileFragment()).addToBackStack(null).commit();
                    }
                }else{
                    Log.d(TAG, "onResponse: sorry cannot perform this action");
                    Toast.makeText(getContext(), "Sorry something is wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: Sorry cannot connect to the server");
                Toast.makeText(getContext(), "Sorry cannot connect to the server", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void updateProfilePicture(String imagePath) {
        //Log.d(TAG, "updateProfilePicture: update Profile Picture called ");
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        Long userId = sharedPreferences.getLong("id",0);

        File profile = new File(imagePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), profile);
        MultipartBody.Part profileImage = MultipartBody.Part.createFormData("profile",profile.getName(), requestFile);

        Call<MessageResponse> call = ApiClient.getProfileService().updateProfilePicture(token,userId,profileImage);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().getMessage().equals("Profile Picture uploaded")) {
                        Log.d(TAG, "onResponse: Profile Picture updated ");
                        getFragmentManager().beginTransaction().replace(R.id.fragment_container,new ProfileFragment()).addToBackStack(null).commit();

                    }
                }else{
                    Log.d(TAG, "onResponse: sorry cannot perform this action");
                    Toast.makeText(getContext(), "Sorry something is wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: Sorry cannot connect to the server");
                Toast.makeText(getContext(), "Sorry cannot connect to the server", Toast.LENGTH_SHORT).show();
            }
        });
    }
    }
