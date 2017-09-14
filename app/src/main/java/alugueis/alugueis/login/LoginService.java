package alugueis.alugueis.login;

import alugueis.alugueis.model.User;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LoginService {
    @GET("user")
    Observable<User> login(@Query("email") String email, @Query("password") String password);

    @POST("user")
    Observable<User> save(@Body User user);
}
