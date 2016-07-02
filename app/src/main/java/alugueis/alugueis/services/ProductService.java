package alugueis.alugueis.services;

import alugueis.alugueis.model.Product;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductService {
    @GET("product/teste")
    Call<Product> getProduct();
}
