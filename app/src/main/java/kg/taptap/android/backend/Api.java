package kg.taptap.android.backend;

import kg.taptap.android.backend.dto.NikitaDTO;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface Api {

    @POST("api/message")
    Observable<NikitaDTO> login(@Body RequestBody body);
}
