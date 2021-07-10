package np.com.krishna.nightbeforeexam.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import np.com.krishna.nightbeforeexam.R;

public class SettingsFragment extends Fragment {

    private TextView changePassword;
    private TextView profileSettings;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings,container,false);

        changePassword = v.findViewById(R.id.changePassword);
        profileSettings = v.findViewById(R.id.profileSettings);

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ChangePasswordFragment changePasswordFragment = new ChangePasswordFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, changePasswordFragment).addToBackStack(null).commit();

            }
        });

        profileSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileSettingsFragment()).addToBackStack(null).commit();
            }
        });
        return v;
    }


}
