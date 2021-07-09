package np.com.krishna.nightbeforeexam.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import np.com.krishna.nightbeforeexam.HelperClasses.ItemAnimation;
import np.com.krishna.nightbeforeexam.R;
import np.com.krishna.nightbeforeexam.models.Semesters;

public class SemesterRecyclerAdapter extends RecyclerView.Adapter<SemesterRecyclerAdapter.SemesterViewHolder> {
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Semesters> semestersArrayList;
    private RecyclerViewClickListener mRecyclerViewClickListener;

    public SemesterRecyclerAdapter(Context context, ArrayList<Semesters> semesters, RecyclerViewClickListener recyclerViewClickListener) {
        this.context = context;
        this.semestersArrayList = semesters;
        this.mRecyclerViewClickListener = recyclerViewClickListener;
    }

    @NonNull
    @Override
    public SemesterRecyclerAdapter.SemesterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_semesters, parent, false);
        SemesterRecyclerAdapter.SemesterViewHolder semesterViewHolder = new SemesterRecyclerAdapter.SemesterViewHolder(view,mRecyclerViewClickListener);
        return semesterViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SemesterRecyclerAdapter.SemesterViewHolder holder, int position) {

        holder.semesters.setText(semestersArrayList.get(position).getSemesterName());

        ItemAnimation.animateFadeIn(holder.itemView, position);

    }

    @Override
    public int getItemCount() {
        return semestersArrayList.size();
    }

    public class SemesterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CardView semesterCardView;
        TextView semesters;
        RecyclerViewClickListener recyclerViewClickListener;


        public SemesterViewHolder(@NonNull View itemView, RecyclerViewClickListener recyclerViewClickListener) {
            super(itemView);
            semesterCardView = itemView.findViewById(R.id.semesterCardView);
            semesters = itemView.findViewById(R.id.semesterName);
            this.recyclerViewClickListener = recyclerViewClickListener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            recyclerViewClickListener.onCLick(itemView, getAdapterPosition());
        }
    }

    public interface RecyclerViewClickListener{
        void onCLick(View v, int position);
    }
}
