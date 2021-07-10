package np.com.krishna.nightbeforeexam.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import np.com.krishna.nightbeforeexam.ApiClients.ApiClient;
import np.com.krishna.nightbeforeexam.R;
import np.com.krishna.nightbeforeexam.fragments.AboutFragment;
import np.com.krishna.nightbeforeexam.fragments.DiscussionFragment;
import np.com.krishna.nightbeforeexam.fragments.NotificationFragment;
import np.com.krishna.nightbeforeexam.fragments.NotesFragment;
import np.com.krishna.nightbeforeexam.fragments.ProfileFragment;
import np.com.krishna.nightbeforeexam.fragments.SettingsFragment;
import np.com.krishna.nightbeforeexam.fragments.SearchFragment;
import np.com.krishna.nightbeforeexam.fragments.HelpFragment;
import np.com.krishna.nightbeforeexam.fragments.HomeFragment;
import np.com.krishna.nightbeforeexam.models.ProfilePicture;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;


public class StartActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigation;
    private Toolbar toolbar;
    private de.hdodenhof.circleimageview.CircleImageView imgProfile;
    private TextView username;
    private TextView email;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);

        mDrawerLayout= findViewById(R.id.drawerlayout);
        navigation=findViewById(R.id.navigation);

        navigation.setNavigationItemSelectedListener(this);
        toolbar=findViewById(R.id.toolbar);
      /*  View navView=navigation.inflateHeaderView(R.layout.navigation_header);*/
        View navView=navigation.getHeaderView(0);
        //View navView=(NavigationView)findViewById(R.layout.navigation_header);

        imgProfile= navView.findViewById(R.id.nav_header_profile);
        username= navView.findViewById(R.id.nav_header_username);
        email= navView.findViewById(R.id.nav_header_userEmail);


        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);

        String loggedInUserName = sharedPreferences.getString("username","");
        String loggedInEmail = sharedPreferences.getString("email","");
        Long userId = sharedPreferences.getLong("id",0);
        String token = sharedPreferences.getString("token","");

        username.setText(loggedInUserName);
        email.setText(loggedInEmail);

        Call<ProfilePicture> call = ApiClient.getProfileService().loadProfilePicture(token,userId);
        call.enqueue(new Callback<ProfilePicture>() {
            @Override
            public void onResponse(Call<ProfilePicture> call, Response<ProfilePicture> response) {
                if(response.isSuccessful()){

                    String fileName = response.body().getFileName();
                    String url = "http://192.168.1.102:8080/api/test/downloadFile/"+fileName;
                    Picasso.get().load(url).into(imgProfile);


                }else{
                    Picasso.get().load(R.drawable.avatar).into(imgProfile);
                }
            }

            @Override
            public void onFailure(Call<ProfilePicture> call, Throwable t) {
                Picasso.get().load(R.drawable.avatar).into(imgProfile);
                Toast.makeText(StartActivity.this, "Failure: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        setSupportActionBar(toolbar);






        mToggle=new ActionBarDrawerToggle(StartActivity.this,mDrawerLayout,toolbar,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        BottomNavigationView navbar=findViewById(R.id.bottom_nav_bar);
        navbar.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).addToBackStack(null).commit();


    }




    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.profile:
                //Toast.makeText(getApplicationContext(), "Profile fragment", Toast.LENGTH_SHORT).show();
               getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ProfileFragment()).addToBackStack(null).commit();
                break;
            case R.id.notes:
               // Toast.makeText(getApplicationContext(), "notes fragment", Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new NotesFragment()).addToBackStack(null).commit();
                break;
            case R.id.discussions:
                // Toast.makeText(getApplicationContext(), "notes fragment", Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new DiscussionFragment()).addToBackStack(null).commit();
                break;
            case R.id.settings:
               // Toast.makeText(getApplicationContext(), "settings fragment", Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new SettingsFragment()).addToBackStack(null).commit();
                break;
            case R.id.help:
               // Toast.makeText(getApplicationContext(), "help fragment", Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HelpFragment()).addToBackStack(null).commit();
                break;
            case R.id.about:
               // Toast.makeText(getApplicationContext(), "about fragment", Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new AboutFragment()).addToBackStack(null).commit();
                break;
            case R.id.logout:
                //Toast.makeText(getApplicationContext(), "logout fragment", Toast.LENGTH_SHORT).show()
                SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                sharedPreferences.edit().clear().commit();
                Intent intoLogin=new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intoLogin);
                finish();
                break;

        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public void onBackPressed() {

        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =

            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragmet=null;

                    switch (item.getItemId()) {
                        case R.id.home_nav:
                            selectedFragmet = new HomeFragment();
                            break;
                        case R.id.search_nav:
                            selectedFragmet = new SearchFragment();
                            break;
                        case R.id.notification_nav:
                            selectedFragmet = new DiscussionFragment();
                            break;

                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragmet).addToBackStack(null).commit();
                    return true;
                }

            };
}

