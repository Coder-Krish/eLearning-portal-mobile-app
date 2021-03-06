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
import np.com.krishna.nightbeforeexam.models.Posts;
import np.com.krishna.nightbeforeexam.models.ProfilePicture;
import np.com.krishna.nightbeforeexam.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllPostsRecyclerAdapter extends RecyclerView.Adapter<AllPostsRecyclerAdapter.AllPostsViewHolder> {
    Context context;
    ArrayList<Posts>posts;
    private RecyclerViewAllPostsClickListener recyclerViewAllPostsClickListener;
    public AllPostsRecyclerAdapter(Context context, ArrayList<Posts> posts, RecyclerViewAllPostsClickListener recyclerViewAllPostsClickListener){
        this.context = context;
        this.posts = posts;
        this.recyclerViewAllPostsClickListener = recyclerViewAllPostsClickListener;
    }

    @NonNull
    @Override
    public AllPostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_all_posts, parent, false);
        AllPostsRecyclerAdapter.AllPostsViewHolder allPostsViewHolder = new AllPostsRecyclerAdapter.AllPostsViewHolder(view, recyclerViewAllPostsClickListener);
        return allPostsViewHolder;
    }

    @Override
        public void onBindViewHolder(@NonNull AllPostsViewHolder holder, int position) {
        holder.postedByTV.setText(posts.get(position).getPostedBy());
        holder.postedDate.setText(posts.get(position).getPostedTime());
        holder.content.setText(posts.get(position).getContent());

        String fileName = posts.get(position).getFileName();
        if(fileName!=null){
            String url = "http://192.168.1.102:8080/api/test/downloadPostsImage/" + fileName;
            Picasso.get().load(url).into(holder.contentImageView);
        }
        SharedPreferences sharedPreferences = this.context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token","");
        Long userId = posts.get(position).getUser().getId();

        Call<ProfilePicture> call = ApiClient.getProfileService().loadProfilePicture(token,userId);
        call.enqueue(new Callback<ProfilePicture>() {
            @Override
            public void onResponse(Call<ProfilePicture> call, Response<ProfilePicture> response) {
                if(response.isSuccessful()){
                    try {
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
        return posts.size();
    }

    public class AllPostsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CardView myPostsCardView;
        TextView postedByTV;
        TextView postedDate;
        TextView content;
        ImageView contentImageView;
        ImageView userProfileImage;
        RecyclerViewAllPostsClickListener mRecyclerViewAllPostsClickListener;

        public AllPostsViewHolder(@NonNull View itemView, RecyclerViewAllPostsClickListener mRecyclerViewAllPostsClickListener) {
            super(itemView);
            myPostsCardView = itemView.findViewById(R.id.myPostsCardView);
            postedByTV = itemView.findViewById(R.id.postedBy);
            postedDate = itemView.findViewById(R.id.postedDate);
            content = itemView.findViewById(R.id.content);
            contentImageView = itemView.findViewById(R.id.contentImage);
            userProfileImage = itemView.findViewById(R.id.userProfileImage);
            this.mRecyclerViewAllPostsClickListener = mRecyclerViewAllPostsClickListener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            mRecyclerViewAllPostsClickListener.onClick(itemView,getAdapterPosition());
        }
    }

    public interface RecyclerViewAllPostsClickListener{
        void onClick(View v, int position);
    }

}
