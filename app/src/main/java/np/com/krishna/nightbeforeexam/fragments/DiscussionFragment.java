package np.com.krishna.nightbeforeexam.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import np.com.krishna.nightbeforeexam.R;
import np.com.krishna.nightbeforeexam.adapters.SemesterRecyclerAdapter;
import np.com.krishna.nightbeforeexam.models.DiscussionForum;
import np.com.krishna.nightbeforeexam.models.Semesters;

import static android.content.ContentValues.TAG;

public class DiscussionFragment extends Fragment implements SemesterRecyclerAdapter.RecyclerViewClickListener {

    TextView facultyName;
    RecyclerView recyclerView;
    ArrayList<DiscussionForum> discussions;
    private ArrayList<Semesters> semestersArrayList;
    private SemesterRecyclerAdapter semesterRecyclerAdapter;
    private SemesterRecyclerAdapter.RecyclerViewClickListener recyclerViewClickListener;

    public DiscussionFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_discussion,container,false);
        facultyName =  view.findViewById(R.id.facultyDisplay);
        semestersArrayList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.semesterRecyclerView);

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);

        String faculty = sharedPreferences.getString("programName","");


        facultyName.setText(faculty);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        initializeData();

        return view;
    }

    private void initializeData() {

        Semesters firstSem = new Semesters((long) 1, "First Semester");
        semestersArrayList.add(firstSem);
        Semesters secondSem = new Semesters((long) 2, "second Semester");
        semestersArrayList.add(secondSem);
        Semesters thirdSem = new Semesters((long) 3, "Third Semester");
        semestersArrayList.add(thirdSem);
        Semesters fourthSem = new Semesters((long) 4, "Fourth Semester");
        semestersArrayList.add(fourthSem);
        Semesters fifthSem = new Semesters((long) 5, "Fifth Semester");
        semestersArrayList.add(fifthSem);
        Semesters sixthSem = new Semesters((long) 6, "Sixth Semester");
        semestersArrayList.add(sixthSem);
        Semesters seventhSem = new Semesters((long) 7, "Seventh Semester");
        semestersArrayList.add(seventhSem);
        Semesters eighthSem = new Semesters((long) 8, "Eighth Semester");
        semestersArrayList.add(eighthSem);

        semesterRecyclerAdapter = new SemesterRecyclerAdapter(getContext(), semestersArrayList,this);
        recyclerView.setAdapter(semesterRecyclerAdapter);


    }


    @Override
    public void onCLick(View v, int position) {
        Log.d(TAG, "onCLick: Clicked");
        semestersArrayList.get(position);

        DiscussionsSubjectFragement discussionsSubjectFragement = new DiscussionsSubjectFragement();
        Bundle bundle = new Bundle();
        bundle.putParcelable("data", semestersArrayList.get(position));
        discussionsSubjectFragement.setArguments(bundle);

        getFragmentManager().beginTransaction().replace(R.id.fragment_container, discussionsSubjectFragement).addToBackStack(null).commit();

    }
}
