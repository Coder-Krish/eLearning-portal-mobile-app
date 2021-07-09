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
import np.com.krishna.nightbeforeexam.models.Notes;
import np.com.krishna.nightbeforeexam.models.Subjects;

public class NotesRecyclerAdapter extends RecyclerView.Adapter<NotesRecyclerAdapter.NotesViewHolder> {
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Notes> notesArrayList;
    NotesRecyclerAdapter.RecyclerViewNotesClickListener recyclerViewNotesClickListener;

    public NotesRecyclerAdapter(Context context, ArrayList<Notes> notesArrayList, RecyclerViewNotesClickListener recyclerViewNotesClickListener) {
        this.context = context;
        this.notesArrayList = notesArrayList;
        this.recyclerViewNotesClickListener = recyclerViewNotesClickListener;
    }

    @NonNull
    @Override
    public NotesRecyclerAdapter.NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_list_of_notes, parent, false);
        NotesRecyclerAdapter.NotesViewHolder notesViewHolder = new NotesRecyclerAdapter.NotesViewHolder(view,recyclerViewNotesClickListener);
        return notesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NotesRecyclerAdapter.NotesViewHolder holder, int position) {

        holder.notesName.setText(notesArrayList.get(position).getAddedTime());

        ItemAnimation.animateFadeIn(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return notesArrayList.size();
    }

    public class NotesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private CardView notesCardView;
        private TextView notesName;
        NotesRecyclerAdapter.RecyclerViewNotesClickListener mRecyclerViewNotesClickListener;

        public NotesViewHolder(@NonNull View itemView, NotesRecyclerAdapter.RecyclerViewNotesClickListener mRecyclerViewNotesClickListener) {
            super(itemView);
            notesCardView = itemView.findViewById(R.id.listOfNotesCardView);
            notesName = itemView.findViewById(R.id.notesName);
            this.mRecyclerViewNotesClickListener = mRecyclerViewNotesClickListener;


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mRecyclerViewNotesClickListener.onClick(itemView,getAdapterPosition());
        }
    }

    public interface RecyclerViewNotesClickListener{
        void onClick(View v, int position);
    }
}
