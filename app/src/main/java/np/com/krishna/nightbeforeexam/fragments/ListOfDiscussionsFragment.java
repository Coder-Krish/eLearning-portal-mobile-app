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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import np.com.krishna.nightbeforeexam.ApiClients.ApiClient;
import np.com.krishna.nightbeforeexam.R;
import np.com.krishna.nightbeforeexam.adapters.MyDiscussionsRecyclerAdapter;
import np.com.krishna.nightbeforeexam.adapters.NotesRecyclerAdapter;
import np.com.krishna.nightbeforeexam.models.DiscussionForum;
import np.com.krishna.nightbeforeexam.models.Notes;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class ListOfDiscussionsFragment extends Fragment implements MyDiscussionsRecyclerAdapter.RecyclerViewDiscussionsClickListener {

    private static  final int MY_PERMISSIONS_REQUEST = 100;
    private  int PICK_IMAGE_FROM_GALLERY_REQUEST = 1;

    private EditText discussionContent;
    private Button btnCreate;
    private ImageView selectImage;

    private String token;
    RecyclerView recyclerView;
    private Long subjectId;
    private ArrayList<DiscussionForum> discussionForumArrayList;
    private MyDiscussionsRecyclerAdapter myDiscussionsRecyclerAdapter;
    MyDiscussionsRecyclerAdapter.RecyclerViewDiscussionsClickListener recyclerViewDiscussionsClickListener;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_of_discussions, container, false);

        subjectId = getArguments().getLong("subjectId");
        Log.d(TAG, "onCreateView: from discussions "+subjectId);

        discussionContent = v.findViewById(R.id.discussionContent);
        selectImage = v.findViewById(R.id.selectImage);
        btnCreate = v.findViewById(R.id.btnCreate);


        selectImage.setOnClickListener(new View.OnClickListener() {
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

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = discussionContent.getText().toString().trim();
                if(content != null){
                    JSONObject obj=new JSONObject();
                    try {
                        obj.put("content",content);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    RequestBody discussion = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(obj));
                    Call<ResponseBody> call = ApiClient.getDiscusionsService().createDiscussionWithContentOnly(token, subjectId,discussion);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if(response.isSuccessful()){
                                Log.d(TAG, "onResponse: discussion created");
                                initializaData(subjectId);
                            }else{
                                Log.d(TAG, "onResponse: cannot create discussion");
                                Toast.makeText(getContext(), "Sorry something is wrong", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.d(TAG, "onFailure: Cannot connect to the server");
                            Toast.makeText(getContext(), "Sorry cannot to the server", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });

        recyclerView = v.findViewById(R.id.listOfDiscussions);
        discussionForumArrayList = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        initializaData(subjectId);

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

    private void uploadFile(String imagePath){

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        RequestBody discussionRequestBody = null;
        if(discussionContent.getText().toString().trim()!=null) {

            JSONObject obj = new JSONObject();
            try {
                obj.put("content",discussionContent.getText().toString().trim());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            discussionRequestBody =  RequestBody.create(MediaType.parse("text/plain"), String.valueOf(obj));
        }
        File file = new File(imagePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part contentFile = MultipartBody.Part.createFormData("file",file.getName(), requestFile);

        Call<ResponseBody> call = ApiClient.getDiscusionsService().createDiscussion(token,subjectId,discussionRequestBody,contentFile);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "onResponse: Discussions created");
                    initializaData(subjectId);
                }else{
                    Log.d(TAG, "onResponse: sorry cannot perform this action");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: Sorry cannot connect to the server");
            }
        });

    }


    private void initializaData(Long subjectId) {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token","");

        Call<List<DiscussionForum>> call = ApiClient.getDiscusionsService().getDiscussionsBySubjectId(token,subjectId);
        call.enqueue(new Callback<List<DiscussionForum>>() {
            @Override
            public void onResponse(Call<List<DiscussionForum>> call, Response<List<DiscussionForum>> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "onResponse: getting discussions");

                    discussionForumArrayList = new ArrayList<>(response.body());
                    setonClickListener();
                    myDiscussionsRecyclerAdapter = new MyDiscussionsRecyclerAdapter(getContext(),discussionForumArrayList,recyclerViewDiscussionsClickListener);
                    recyclerView.setAdapter(myDiscussionsRecyclerAdapter);
                }else{
                    Log.d(TAG, "onResponse: Sorry cannot get discussions");
                    Toast.makeText(getContext(), "This subejct has no discussions created yet.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<DiscussionForum>> call, Throwable t) {
                Log.d(TAG, "onFailure: Sorry cannot connect to the server.");
                Toast.makeText(getContext(), "Something went wrong try again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setonClickListener() {
        recyclerViewDiscussionsClickListener = new MyDiscussionsRecyclerAdapter.RecyclerViewDiscussionsClickListener() {
            @Override
            public void onclick(View v, int position) {
               DiscussionDetailsFragment discussionDetailsFragment = new DiscussionDetailsFragment();
               Bundle bundle = new Bundle();
               bundle.putParcelable("data",discussionForumArrayList.get(position));
               discussionDetailsFragment.setArguments(bundle);
               getFragmentManager().beginTransaction().replace(R.id.fragment_container, discussionDetailsFragment).addToBackStack(null).commit();
            }
        };
    }

    @Override
    public void onclick(View v, int position) {
        Log.d(TAG, "onclick: discussions clicked");
    }
}
