package np.com.krishna.nightbeforeexam.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import np.com.krishna.nightbeforeexam.ApiClients.ApiClient;
import np.com.krishna.nightbeforeexam.HelperClasses.ItemAnimation;
import np.com.krishna.nightbeforeexam.R;
import np.com.krishna.nightbeforeexam.models.DiscussionForum;
import np.com.krishna.nightbeforeexam.models.ProfilePicture;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyDiscussionsRecyclerAdapter extends RecyclerView.Adapter<MyDiscussionsRecyclerAdapter.MyDiscussionsViewHolder> {

    Context context;
    ArrayList<DiscussionForum> discussions;

    public MyDiscussionsRecyclerAdapter(Context context, ArrayList<DiscussionForum> discussions){
        this.discussions = discussions;
        this.context = context;
    }

    @NonNull
    @Override
    public MyDiscussionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_my_discussions, parent, false);
        MyDiscussionsViewHolder myDiscussionsViewHolder = new MyDiscussionsViewHolder(view);
        return myDiscussionsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyDiscussionsViewHolder holder, int position) {
        holder.postedByTV.setText(discussions.get(position).getPostedBy());
        holder.postedDate.setText(discussions.get(position).getPostedAt());
        if(discussions.get(position).getContent()!=null) {
            holder.content.setText(discussions.get(position).getContent());
        }
        String fileName = discussions.get(position).getFileName();
        if(fileName!=null) {
            String url = "http://192.168.1.102:8080/api/test/downloadDiscussionsImage/" + fileName;
            Picasso.get().load(url).into(holder.contentImageView);
        }

        SharedPreferences sharedPreferences = this.context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token","");
        Long userId = sharedPreferences.getLong("id",0);

        Call<ProfilePicture> call = ApiClient.getProfileService().loadProfilePicture(token,userId);
        call.enqueue(new Callback<ProfilePicture>() {
            @Override
            public void onResponse(Call<ProfilePicture> call, Response<ProfilePicture> response) {
                if(response.isSuccessful()){
                    try {
                        //System.out.println(response.body().getUploadDir());
                        String fileName = response.body().getFileName();

                        String url = "http://192.168.1.102:8080/api/test/downloadFile/"+fileName;

                        Picasso.get().load(url).into(holder.userProfileImage);


                    }catch(Exception e){
                        Toast.makeText(context, "Exception: "+e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }else{
                    Toast.makeText(context, "Error: "+response.code(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ProfilePicture> call, Throwable t) {
                // loadUploadDirTV.setText("Failure: "+t.getMessage());
                //Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Picasso.get().load(R.drawable.avatar).into(holder.userProfileImage);
            }
        });

        ItemAnimation.animateFadeIn(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return discussions.size();
    }

    public class MyDiscussionsViewHolder extends RecyclerView.ViewHolder{
        CardView myPostsCardView;
        TextView postedByTV;
        TextView postedDate;
        TextView content;
        ImageView contentImageView;
        ImageView userProfileImage;


        public MyDiscussionsViewHolder(@NonNull View itemView) {
            super(itemView);
            myPostsCardView = itemView.findViewById(R.id.myPostsCardView);
            postedByTV = itemView.findViewById(R.id.postedBy);
            postedDate = itemView.findViewById(R.id.postedDate);
            content = itemView.findViewById(R.id.content);
            contentImageView = itemView.findViewById(R.id.contentImage);
            userProfileImage = itemView.findViewById(R.id.userProfileImage);

        }
    }

}
