package np.com.krishna.nightbeforeexam.interfaces;

import java.util.List;

import np.com.krishna.nightbeforeexam.models.Programs;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProgramsApiInterface {

    @GET("test/listprograms")
    Call<List<Programs>> getAllPrograms();

    @GET("test/getProgramByProgramName/{programName}")
    Call<Programs> getProgramByName(@Path("programName") String programName);
}
