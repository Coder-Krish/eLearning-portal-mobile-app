package np.com.krishna.nightbeforeexam.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import np.com.krishna.nightbeforeexam.R;

public class NotesFragment extends Fragment {



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notes,container,false);
        TextView facultyName = v.findViewById(R.id.facultyDisplay);
        AutoCompleteTextView semesterSpinner;
        semesterSpinner = v.findViewById(R.id.etSemester);
        EditText semesters = v.findViewById(R.id.etSemester);


        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);

        String faculty = sharedPreferences.getString("course","");
        System.out.println(faculty);

        facultyName.setText(faculty);

        String[] arrayOfSemesters = getResources().getStringArray(R.array.semesters);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(),
                    android.R.layout.simple_spinner_dropdown_item,arrayOfSemesters);
        semesterSpinner.setAdapter(adapter);


        return v;
    }


}
