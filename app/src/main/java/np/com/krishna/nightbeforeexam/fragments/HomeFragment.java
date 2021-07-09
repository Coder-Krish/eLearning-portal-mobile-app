package np.com.krishna.nightbeforeexam.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.util.FileUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import np.com.krishna.nightbeforeexam.ApiClients.ApiClient;
import np.com.krishna.nightbeforeexam.activities.LoginActivity;
import np.com.krishna.nightbeforeexam.R;
import np.com.krishna.nightbeforeexam.adapters.AllPostsRecyclerAdapter;
import np.com.krishna.nightbeforeexam.adapters.MyPostsRecyclerAdapter;
import np.com.krishna.nightbeforeexam.models.MessageResponse;
import np.com.krishna.nightbeforeexam.models.Posts;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


public class HomeFragment extends Fragment implements AllPostsRecyclerAdapter.RecyclerViewAllPostsClickListener {

    private static  final int MY_PERMISSIONS_REQUEST = 100;
    private  int PICK_IMAGE_FROM_GALLERY_REQUEST = 1;
    ArrayList<Posts> posts;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    private EditText postContent;
    private Button btnPost;
    private ImageView chooseImage;

    private AllPostsRecyclerAdapter.RecyclerViewAllPostsClickListener recyclerViewAllPostsClickListener;

    private String token;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);


        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST);
        }

        swipeRefreshLayout = v.findViewById(R.id.swipeToRefresh);
        postContent = v.findViewById(R.id.postContent);
        btnPost = v.findViewById(R.id.btnPost);
        chooseImage = v.findViewById(R.id.chooseImage);

        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("images/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                        PICK_IMAGE_FROM_GALLERY_REQUEST);

            }
        });

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String post = postContent.getText().toString().trim();
                JSONObject obj=new JSONObject();
                try {
                    obj.put("content",post);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                RequestBody content = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(obj));
                Call<ResponseBody> call = ApiClient.getPostsService().createNewPost(token,content);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            Log.d(TAG, "onResponse: Post created");
                            initializeData();
                        }else{
                            Log.d(TAG, "onResponse: sorry cannot perform this action");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.d(TAG, "onFailure: Sorry cannot connect to the server");
                        Toast.makeText(getContext(), "Sorry cannot connect to the server", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


        FloatingActionButton btnFloating = v.findViewById(R.id.btnFloating);
        btnFloating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(getActivity())
                        .setTitle("Information")
                        .setMessage("Post functionality coming soon!")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create();
                dialog.show();
            }
        });
        recyclerView = v.findViewById(R.id.allPostsRecyclerView);
        posts = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        initializeData();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initializeData();
                swipeRefreshLayout.setRefreshing(false);
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
            uploadFile(imgPath);
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
        RequestBody postsContent = null;
        if(postContent.getText().toString().trim()!=null) {

            JSONObject obj = new JSONObject();
            try {
                obj.put("content",postContent.getText().toString().trim());
            } catch (JSONException e) {
                e.printStackTrace();
            }

             postsContent =  RequestBody.create(MediaType.parse("text/plain"), String.valueOf(obj));
        }
        File file = new File(imagePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part contentFile = MultipartBody.Part.createFormData("file",file.getName(), requestFile);

        Call<ResponseBody> call = ApiClient.getPostsService().createPost(token,postsContent,contentFile);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "onResponse: Post created");
                    initializeData();
                }else{
                    Log.d(TAG, "onResponse: sorry cannot perform this action");
                    Toast.makeText(getContext(), "Sorry something is wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: Sorry cannot connect to the server");
                Toast.makeText(getContext(), "Sorry cannot connect to the server", Toast.LENGTH_SHORT).show();
            }
        });

    }



    private void initializeData() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token","");
                Call<List<Posts>> call = ApiClient.getPostsService().getPostsOfMyFollowings(token);
                call.enqueue(new Callback<List<Posts>>() {
                    @Override
                    public void onResponse(Call<List<Posts>> call, Response<List<Posts>> response) {
                        if(response.isSuccessful()){
                            posts = new ArrayList<>(response.body());
                            setOnclickListener();
                            recyclerView.setAdapter(new AllPostsRecyclerAdapter(getContext(),posts, recyclerViewAllPostsClickListener));

                        }else{
                            Toast.makeText(getContext(), "Error: Sorry we are looking into this", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Posts>> call, Throwable t) {
                        Toast.makeText(getContext(), "Failure: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }

    private void setOnclickListener() {
        recyclerViewAllPostsClickListener = new AllPostsRecyclerAdapter.RecyclerViewAllPostsClickListener() {
            @Override
            public void onClick(View v, int position) {
                PostDetailsFragment postDetailsFragment = new PostDetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("postData",posts.get(position));
                postDetailsFragment.setArguments(bundle);

                getFragmentManager().beginTransaction().replace(R.id.fragment_container, postDetailsFragment).addToBackStack(null).commit();
            }
        };
    }

    @Override
    public void onClick(View v, int position) {
        Log.d(TAG, "onClick: Post Clicked");
    }
}
