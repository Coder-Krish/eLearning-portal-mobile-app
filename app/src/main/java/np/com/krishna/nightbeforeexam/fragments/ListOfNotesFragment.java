package np.com.krishna.nightbeforeexam.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import np.com.krishna.nightbeforeexam.ApiClients.ApiClient;
import np.com.krishna.nightbeforeexam.R;
import np.com.krishna.nightbeforeexam.adapters.NotesRecyclerAdapter;
import np.com.krishna.nightbeforeexam.adapters.SubjectsRecyclerAdapter;
import np.com.krishna.nightbeforeexam.models.Notes;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class ListOfNotesFragment extends Fragment implements NotesRecyclerAdapter.RecyclerViewNotesClickListener {

    RecyclerView recyclerView;
    private ArrayList<Notes>notesArrayList;
    private NotesRecyclerAdapter notesRecyclerAdapter;
    private NotesRecyclerAdapter.RecyclerViewNotesClickListener recyclerViewNotesClickListener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_of_notes, container, false);

        Long subjectId = getArguments().getLong("subjectId");
        Log.d(TAG, "onCreateView: from list of notes "+subjectId);


        recyclerView = v.findViewById(R.id.listOfNotesRecyclerView);
        notesArrayList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        initializaData(subjectId);
        return v;
        }

    private void initializaData(Long subjectId) {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token","");

        Call<List<Notes>> call = ApiClient.getNotesService().getNotes(token,subjectId);
        call.enqueue(new Callback<List<Notes>>() {
            @Override
            public void onResponse(Call<List<Notes>> call, Response<List<Notes>> response) {
                if(response.isSuccessful()){
                    notesArrayList = new ArrayList<>(response.body());
                    setOnClickListener();
                    notesRecyclerAdapter = new NotesRecyclerAdapter(getContext(),notesArrayList,recyclerViewNotesClickListener);
                    recyclerView.setAdapter(notesRecyclerAdapter);
                    // recyclerView.setAdapter(new SubjectsRecyclerAdapter(getContext(),subjects, recyclerViewSubjectsClickListener));

                }else{
                    Toast.makeText(getContext(), "Error: Sorry we are looking into this", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Notes>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });

    }

    private void setOnClickListener() {
        recyclerViewNotesClickListener = new NotesRecyclerAdapter.RecyclerViewNotesClickListener() {
            @Override
            public void onClick(View v, int position) {
                Notes data = notesArrayList.get(position);
               // Log.d(TAG, "onClick: from notes view"+data.getAddedBy());

                DisplayNotesPdfViewer displayNotesPdfViewer = new DisplayNotesPdfViewer();
                Bundle bundle = new Bundle();
                bundle.putString("fileName", data.getFileName());
                displayNotesPdfViewer.setArguments(bundle);
                getChildFragmentManager().beginTransaction().replace(R.id.fragment_container, displayNotesPdfViewer).commit();

            }
        };
    }

    @Override
    public void onClick(View v, int position) {

    }
}
