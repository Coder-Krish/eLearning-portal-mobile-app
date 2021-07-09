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
import np.com.krishna.nightbeforeexam.models.Comments;
import np.com.krishna.nightbeforeexam.models.ProfilePicture;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostsDetailsRecyclerAdapter extends RecyclerView.Adapter<PostsDetailsRecyclerAdapter.PostsDetailsViewHolder> {

    Context context;
    private ArrayList<Comments> commentsArrayList;

    public PostsDetailsRecyclerAdapter(Context context, ArrayList<Comments> commentsArrayList) {
        this.context = context;
        this.commentsArrayList = commentsArrayList;
    }

    @NonNull
    @Override
    public PostsDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_replies_on_discussion, parent, false);
        PostsDetailsRecyclerAdapter.PostsDetailsViewHolder postsDetailsViewHolder = new PostsDetailsRecyclerAdapter.PostsDetailsViewHolder(view);
        return postsDetailsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostsDetailsViewHolder holder, int position) {

        if(commentsArrayList!=null) {
            if (commentsArrayList.get(position).getUser().getUsername() != null) {
                holder.commentedBy.setText(commentsArrayList.get(position).getUser().getUsername());
            }
            if (commentsArrayList.get(position).getCommentedAt() != null) {
                holder.commentedAt.setText(commentsArrayList.get(position).getCommentedAt());
            }
            if (commentsArrayList.get(position).getComment() != null) {
                holder.comment.setText(commentsArrayList.get(position).getComment());
            }
            if (commentsArrayList.get(position).getUser().getId() != null) {

                Long userId = commentsArrayList.get(position).getUser().getId();
                SharedPreferences sharedPreferences = this.context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                String token = sharedPreferences.getString("token", "");

                Call<ProfilePicture> call = ApiClient.getProfileService().loadProfilePicture(token, userId);
                call.enqueue(new Callback<ProfilePicture>() {
                    @Override
                    public void onResponse(Call<ProfilePicture> call, Response<ProfilePicture> response) {
                        if (response.isSuccessful()) {
                            try {
                                String fileName = response.body().getFileName();

                                String url = "http://192.168.1.102:8080/api/test/downloadFile/" + fileName;

                                Picasso.get().load(url).into(holder.userProfile);


                            } catch (Exception e) {
                                Toast.makeText(context, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            Toast.makeText(context, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<ProfilePicture> call, Throwable t) {
                        // loadUploadDirTV.setText("Failure: "+t.getMessage());
                        //Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        Picasso.get().load(R.drawable.avatar).into(holder.userProfile);
                    }
                });
            }

            ItemAnimation.animateFadeIn(holder.itemView, position);
            // Log.d(TAG, "onBindViewHolder: value geeting here "+ repliesOnDiscussions.get(position).getUser().getUsername());
        }

    }

    @Override
    public int getItemCount() {
        return commentsArrayList.size();
    }

    public class PostsDetailsViewHolder extends RecyclerView.ViewHolder {

        private CardView repliesOnDiscussionCardView;
        private ImageView userProfile;
        private TextView commentedAt, commentedBy, comment;

        public PostsDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            repliesOnDiscussionCardView = (CardView)itemView.findViewById(R.id.repliesOnDiscussionCardView);
            userProfile = (ImageView)itemView.findViewById(R.id.userProfileImage);
            commentedBy = (TextView)itemView.findViewById(R.id.repliedBy);
            commentedAt = (TextView)itemView.findViewById(R.id.repliedDate);
            comment= (TextView)itemView.findViewById(R.id.reply);
        }
    }
}
