package np.com.krishna.nightbeforeexam.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import np.com.krishna.nightbeforeexam.HelperClasses.ItemAnimation;
import np.com.krishna.nightbeforeexam.R;
import np.com.krishna.nightbeforeexam.models.Semesters;
import np.com.krishna.nightbeforeexam.models.Subjects;

public class SubjectsRecyclerAdapter extends RecyclerView.Adapter<SubjectsRecyclerAdapter.SubjectsViewHolder> {
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Subjects> subjectsArrayList;
    RecyclerViewSubjectsClickListener recyclerViewSubjectsClickListener;

    public SubjectsRecyclerAdapter(Context context, ArrayList<Subjects> subjectsArrayList, RecyclerViewSubjectsClickListener recyclerViewSubjectsClickListener) {
        this.context = context;
        this.subjectsArrayList = subjectsArrayList;
        this.recyclerViewSubjectsClickListener = recyclerViewSubjectsClickListener;
    }

    @NonNull
    @Override
    public SubjectsRecyclerAdapter.SubjectsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_subjects, parent, false);
        SubjectsRecyclerAdapter.SubjectsViewHolder subjectsViewHolder = new SubjectsRecyclerAdapter.SubjectsViewHolder(view,recyclerViewSubjectsClickListener);
        return subjectsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectsRecyclerAdapter.SubjectsViewHolder holder, int position) {

        holder.subjectsName.setText(subjectsArrayList.get(position).getSubjectsName());

        ItemAnimation.animateFadeIn(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return subjectsArrayList.size();
    }

    public class SubjectsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private CardView subjectsCardView;
        private TextView subjectsName;
        RecyclerViewSubjectsClickListener mRecyclerViewSubjectsClickListener;

        public SubjectsViewHolder(@NonNull View itemView, RecyclerViewSubjectsClickListener mRecyclerViewSubjectsClickListener) {
            super(itemView);
            subjectsCardView = itemView.findViewById(R.id.subjectCardView);
            subjectsName = itemView.findViewById(R.id.subjectName);
            this.mRecyclerViewSubjectsClickListener = mRecyclerViewSubjectsClickListener;


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mRecyclerViewSubjectsClickListener.onClick(itemView,getAdapterPosition());
        }
    }

    public interface RecyclerViewSubjectsClickListener{
        void onClick(View v, int position);
    }
}
