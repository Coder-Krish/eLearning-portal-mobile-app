package np.com.krishna.nightbeforeexam.ApiClients;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import np.com.krishna.nightbeforeexam.interfaces.AuthenticationApiInterface;
import np.com.krishna.nightbeforeexam.interfaces.DiscussionsInterface;
import np.com.krishna.nightbeforeexam.interfaces.EmailVerificationApiInterface;
import np.com.krishna.nightbeforeexam.interfaces.FollowApiInterface;
import np.com.krishna.nightbeforeexam.interfaces.NotesApiInterface;
import np.com.krishna.nightbeforeexam.interfaces.PasswordApiInterface;
import np.com.krishna.nightbeforeexam.interfaces.PostsInterface;
import np.com.krishna.nightbeforeexam.interfaces.ProfileInterface;
import np.com.krishna.nightbeforeexam.interfaces.ProgramsApiInterface;
import np.com.krishna.nightbeforeexam.interfaces.SubjectsApiInterface;
import np.com.krishna.nightbeforeexam.interfaces.UserApiInterface;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClient {

    private static Retrofit getRetrofit(){


        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("http:192.168.1.102:8080/api/")
                .client(okHttpClient)
                .build();

        return retrofit;
    }


    public static AuthenticationApiInterface getUserService(){
        AuthenticationApiInterface userService = getRetrofit().create(AuthenticationApiInterface.class);
        return userService;
    }

    public static ProfileInterface getProfileService(){
        ProfileInterface profileService = getRetrofit().create(ProfileInterface.class);
        return profileService;
    }


    public static PostsInterface getPostsService(){
        PostsInterface postsInterface = getRetrofit().create(PostsInterface.class);
        return postsInterface;
    }
    public static DiscussionsInterface getDiscusionsService(){
        DiscussionsInterface discussionsInterface = getRetrofit().create(DiscussionsInterface.class);
        return discussionsInterface;
    }

    public static SubjectsApiInterface getSubjectsService(){
        SubjectsApiInterface subjectsApiInterface = getRetrofit().create(SubjectsApiInterface.class);
        return subjectsApiInterface;
    }

    public static NotesApiInterface getNotesService(){
        NotesApiInterface notesApiInterface = getRetrofit().create(NotesApiInterface.class);
        return notesApiInterface;
    }

    public static FollowApiInterface getFollowServices(){
        FollowApiInterface followApiInterface = getRetrofit().create(FollowApiInterface.class);
        return  followApiInterface;
    }

    public static PasswordApiInterface getPasswordServices(){
        PasswordApiInterface passwordApiInterface = getRetrofit().create(PasswordApiInterface.class);
                return passwordApiInterface;
    }

    public static EmailVerificationApiInterface getEmailVerificationServices(){
        EmailVerificationApiInterface emailVerificationApiInterface = getRetrofit().create(EmailVerificationApiInterface.class);
        return  emailVerificationApiInterface;
    }
    public static ProgramsApiInterface getProgramsService(){
        ProgramsApiInterface programsApiInterface = getRetrofit().create(ProgramsApiInterface.class);
        return programsApiInterface;
    }

    public static UserApiInterface getUser(){
        UserApiInterface userApiInterface = getRetrofit().create(UserApiInterface.class);
        return userApiInterface;
    }

}