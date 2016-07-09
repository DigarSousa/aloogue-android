package alugueis.alugueis.services.product;

import alugueis.alugueis.model.Product;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface ProductRest {
    @POST("product")
    Call<Product> save(@Body Product product);

    @GET("product/place/{placeId}")
    Call<List<Product>> get(@Path("placeId") Long placeId);

    @DELETE("product/{id}")
    Call<ResponseBody> delete(@Path("id") Long productId);
}
