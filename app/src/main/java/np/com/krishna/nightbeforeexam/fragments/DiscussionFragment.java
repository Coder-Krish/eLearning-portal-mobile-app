package np.com.krishna.nightbeforeexam.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import np.com.krishna.nightbeforeexam.R;
import np.com.krishna.nightbeforeexam.models.DiscussionForum;

public class DiscussionFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<DiscussionForum> discussions;

    public DiscussionFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_discussion,container,false);


      //  recyclerView = view.findViewById(R.id.postsRecyclerView);
        discussions = new ArrayList<>();
    /*        myPostsRecyclerAdapter = new MyPostsRecyclerAdapter(getActivity().getApplicationContext(), posts);
            recyclerView.setAdapter(myPostsRecyclerAdapter);*/

      /*  LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);*/
        initializeData();

        return view;
    }

    private void initializeData() {


       // recyclerView.setAdapter(new MyDiscussionsRecyclerAdapter(getContext(), discussions));
    }


}
