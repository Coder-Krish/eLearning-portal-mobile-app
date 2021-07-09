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
import np.com.krishna.nightbeforeexam.models.ProfilePicture;
import np.com.krishna.nightbeforeexam.models.Subjects;
import np.com.krishna.nightbeforeexam.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowAndFollowingRecyclerAdapter extends RecyclerView.Adapter<FollowAndFollowingRecyclerAdapter.FollowAndFollowingViewHolder> {
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<User> userArrayList;
    FollowAndFollowingRecyclerAdapter.RecyclerViewNameClickListener recyclerViewNameClickListener;

    public FollowAndFollowingRecyclerAdapter(Context context, ArrayList<User> userArrayList, FollowAndFollowingRecyclerAdapter.RecyclerViewNameClickListener recyclerViewNameClickListener) {
        this.context = context;
        this.userArrayList = userArrayList;
        this.recyclerViewNameClickListener = recyclerViewNameClickListener;
    }

    @NonNull
    @Override
    public FollowAndFollowingRecyclerAdapter.FollowAndFollowingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_follower_and_following, parent, false);
        FollowAndFollowingRecyclerAdapter.FollowAndFollowingViewHolder followAndFollowingViewHolder = new FollowAndFollowingRecyclerAdapter.FollowAndFollowingViewHolder(view,recyclerViewNameClickListener);
        return followAndFollowingViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FollowAndFollowingRecyclerAdapter.FollowAndFollowingViewHolder holder, int position) {

        holder.fullName.setText(userArrayList.get(position).getFullname());

        Long userId = userArrayList.get(position).getId();
        SharedPreferences sharedPreferences = this.context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token","");
        Call<ProfilePicture> call = ApiClient.getProfileService().loadProfilePicture(token,userId);
        call.enqueue(new Callback<ProfilePicture>() {
            @Override
            public void onResponse(Call<ProfilePicture> call, Response<ProfilePicture> response) {
                if(response.isSuccessful()){
                    try {
                        String fileName = response.body().getFileName();

                        String url = "http://192.168.1.102:8080/api/test/downloadFile/"+fileName;

                        Picasso.get().load(url).into(holder.userImage);


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
                Picasso.get().load(R.drawable.avatar).into(holder.userImage);
            }
        });


        ItemAnimation.animateFadeIn(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public class FollowAndFollowingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private CardView followAndFollowing;
        private ImageView userImage;
        private TextView fullName;
        FollowAndFollowingRecyclerAdapter.RecyclerViewNameClickListener mRecyclerViewNameClickListener;

        public FollowAndFollowingViewHolder(@NonNull View itemView, FollowAndFollowingRecyclerAdapter.RecyclerViewNameClickListener mRecyclerViewNameClickListener) {
            super(itemView);
            followAndFollowing = itemView.findViewById(R.id.followAndFollowing);
            userImage = itemView.findViewById(R.id.userImage);
            fullName = itemView.findViewById(R.id.name);
            this.mRecyclerViewNameClickListener = mRecyclerViewNameClickListener;


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mRecyclerViewNameClickListener.onClick(itemView,getAdapterPosition());
        }
    }

    public interface RecyclerViewNameClickListener{
        void onClick(View v, int position);
    }
}
