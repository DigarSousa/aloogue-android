package alugueis.alugueis.services.user;

import alugueis.alugueis.model.UserApp;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserService {
    @GET("userapp")
    Call<UserApp> login(@Query("email") String email, @Query("password") String password);

    @POST("userapp")
    Call<UserApp> save(@Body UserApp userApp);

}
