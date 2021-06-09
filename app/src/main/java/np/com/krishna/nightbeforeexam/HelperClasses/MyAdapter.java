package np.com.krishna.nightbeforeexam.HelperClasses;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import np.com.krishna.nightbeforeexam.fragments.MyDiscussionsFragment;
import np.com.krishna.nightbeforeexam.fragments.MyPostsFragment;

public class MyAdapter extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;

    public MyAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                MyPostsFragment myPostsFragment = new MyPostsFragment();
                return myPostsFragment;
            case 1:
                MyDiscussionsFragment myDiscussionsFragment = new MyDiscussionsFragment();
                return myDiscussionsFragment;

            default:
                return null;
        }
    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}
