package np.com.krishna.nightbeforeexam.interfaces;

import java.util.List;

import np.com.krishna.nightbeforeexam.models.Notes;
import np.com.krishna.nightbeforeexam.models.Subjects;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface NotesApiInterface {

    @GET("notes/listnotesbysubjectid/{subjectId}")
    Call<List<Notes>> getNotes(@Header("Authorization") String token, @Path("subjectId") Long subjectId);

}
