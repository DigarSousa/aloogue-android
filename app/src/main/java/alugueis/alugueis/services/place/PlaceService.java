package alugueis.alugueis.services.place;

import alugueis.alugueis.model.Place;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlaceService {

    @GET("place")
    Call<Place> placeByUserId(@Query("userId") Long userId);
}
