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
import np.com.krishna.nightbeforeexam.adapters.SubjectsRecyclerAdapter;
import np.com.krishna.nightbeforeexam.models.Semesters;
import np.com.krishna.nightbeforeexam.models.Subjects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class DiscussionsSubjectFragement extends Fragment implements SubjectsRecyclerAdapter.RecyclerViewSubjectsClickListener {

    private String token;
    private Long programId;
    private RecyclerView recyclerView;
    private SubjectsRecyclerAdapter subjectsRecyclerAdapter;

    private ArrayList<Subjects> subjects;
    private SubjectsRecyclerAdapter.RecyclerViewSubjectsClickListener recyclerViewSubjectsClickListener;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_subjects, container, false);

        //String data = getArguments().getString("data");
        Semesters semesters = getArguments().getParcelable("data");
        Log.d(TAG, "onCreateView: "+ semesters.getId());
      /*  SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token","");
*/
        recyclerView = v.findViewById(R.id.subjectsRecyclerView);
        subjects = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        initializeData(semesters.getId());

        return v;
    }

    private void initializeData(Long semesterId) {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token","");
        Long programId = sharedPreferences.getLong("programId",0);
        String programName = sharedPreferences.getString("programName","");


        Call<List<Subjects>> call = ApiClient.getSubjectsService().getSubjects(token,programId,semesterId);
        call.enqueue(new Callback<List<Subjects>>() {
            @Override
            public void onResponse(Call<List<Subjects>> call, Response<List<Subjects>> response) {
                if(response.isSuccessful()){
                    subjects = new ArrayList<>(response.body());
                    setOnClickListener();
                    subjectsRecyclerAdapter = new SubjectsRecyclerAdapter(getContext(),subjects,recyclerViewSubjectsClickListener);
                    recyclerView.setAdapter(subjectsRecyclerAdapter);
                    // recyclerView.setAdapter(new SubjectsRecyclerAdapter(getContext(),subjects, recyclerViewSubjectsClickListener));

                }else{
                    Toast.makeText(getContext(), "Error: Sorry we are looking into this", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Subjects>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }

    private void setOnClickListener() {
        recyclerViewSubjectsClickListener = new SubjectsRecyclerAdapter.RecyclerViewSubjectsClickListener() {
            @Override
            public void onClick(View v, int position) {
                Log.d(TAG, "onCLick: subject clicked from setonclick");

                Subjects data = subjects.get(position);
                Long subjectId = data.getId();

                ListOfDiscussionsFragment listOfDiscussionsFragment = new ListOfDiscussionsFragment();
                Bundle bundle = new Bundle();
                bundle.putLong("subjectId",subjectId);
                listOfDiscussionsFragment.setArguments(bundle);

                getFragmentManager().beginTransaction().replace(R.id.fragment_container, listOfDiscussionsFragment).addToBackStack(null).commit();


            }
        };
    }

    @Override
    public void onClick(View v, int position) {
        Log.d(TAG, "onCLick: subject clicked");
    }


}
