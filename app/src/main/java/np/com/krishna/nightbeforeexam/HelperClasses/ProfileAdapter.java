package np.com.krishna.nightbeforeexam.HelperClasses;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import np.com.krishna.nightbeforeexam.fragments.MyDiscussionsFragment;
import np.com.krishna.nightbeforeexam.fragments.MyPostsFragment;
import np.com.krishna.nightbeforeexam.fragments.UserDiscussionFragment;
import np.com.krishna.nightbeforeexam.fragments.UserPostsFragment;

public class ProfileAdapter extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;
    Long userId;

    public ProfileAdapter(@NonNull FragmentManager fm, Context myContext, int totalTabs, Long userId) {
        super(fm);
        this.myContext = myContext;
        this.totalTabs = totalTabs;
        this.userId = userId;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                UserPostsFragment userPostsFragment = new UserPostsFragment(userId);
                return userPostsFragment;
            case 1:
                UserDiscussionFragment userDiscussionFragment = new UserDiscussionFragment(userId);
                return userDiscussionFragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
