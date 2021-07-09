package np.com.krishna.nightbeforeexam.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import np.com.krishna.nightbeforeexam.models.ProfilePicture;
import np.com.krishna.nightbeforeexam.models.RepliesOnDiscussion;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class DiscussionDetailsRecyclerAdapter extends RecyclerView.Adapter<DiscussionDetailsRecyclerAdapter.DiscussionDetailsViewholder> {

    Context context;
    ArrayList<RepliesOnDiscussion> repliesOnDiscussions;

    public DiscussionDetailsRecyclerAdapter(Context context, ArrayList<RepliesOnDiscussion> repliesOnDiscussions) {
        this.context = context;
        this.repliesOnDiscussions = repliesOnDiscussions;
//        Log.d(TAG, "DiscussionDetailsRecyclerAdapter: "+ repliesOnDiscussions.get(0).getUser().getUsername());
    }

    @NonNull
    @Override
    public DiscussionDetailsViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_replies_on_discussion, parent, false);
        DiscussionDetailsRecyclerAdapter.DiscussionDetailsViewholder discussionDetailsViewholder = new DiscussionDetailsRecyclerAdapter.DiscussionDetailsViewholder(view);
        return discussionDetailsViewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull DiscussionDetailsViewholder holder, int position) {
        if(repliesOnDiscussions!=null) {
            if (repliesOnDiscussions.get(position).getUser().getUsername() != null) {
                holder.repliedBy.setText(repliesOnDiscussions.get(position).getUser().getUsername());
            }
            if (repliesOnDiscussions.get(position).getRepliedAt() != null) {
                holder.repliedAt.setText(repliesOnDiscussions.get(position).getRepliedAt());
            }
            if (repliesOnDiscussions.get(position).getReply() != null) {
                holder.reply.setText(repliesOnDiscussions.get(position).getReply());
            }
            if (repliesOnDiscussions.get(position).getUser().getId() != null) {

                Long userId = repliesOnDiscussions.get(position).getUser().getId();
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
        return repliesOnDiscussions.size();
    }

    public class DiscussionDetailsViewholder extends RecyclerView.ViewHolder{

        private CardView repliesOnDiscussionCardView;
        private ImageView userProfile;
        private TextView repliedBy, repliedAt, reply;

        public DiscussionDetailsViewholder(@NonNull View itemView) {
            super(itemView);
            repliesOnDiscussionCardView = (CardView)itemView.findViewById(R.id.repliesOnDiscussionCardView);
            userProfile = (ImageView)itemView.findViewById(R.id.userProfileImage);
            repliedBy = (TextView)itemView.findViewById(R.id.repliedBy);
            repliedAt = (TextView)itemView.findViewById(R.id.repliedDate);
            reply = (TextView)itemView.findViewById(R.id.reply);
        }
    }

}
