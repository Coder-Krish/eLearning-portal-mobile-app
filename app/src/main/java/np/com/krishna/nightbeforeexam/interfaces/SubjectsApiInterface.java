package np.com.krishna.nightbeforeexam.interfaces;

import java.util.List;

import np.com.krishna.nightbeforeexam.models.Posts;
import np.com.krishna.nightbeforeexam.models.Subjects;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface SubjectsApiInterface {


    @GET("subjects/listSubjectByProgramAndSemester/{program}/{semester}")
    Call<List<Subjects>> getSubjects(@Header("Authorization") String token, @Path("program") Long program, @Path("semester") Long semester);

}
