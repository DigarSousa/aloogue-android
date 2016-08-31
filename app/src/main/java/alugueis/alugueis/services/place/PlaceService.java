package alugueis.alugueis.services.place;

import alugueis.alugueis.model.Place;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PlaceService {

    @GET("place")
    Call<Place> placeByUserId(@Query("userId") Long userId);

    @POST("place")
    Call<Place> save(@Body Place place);
}
