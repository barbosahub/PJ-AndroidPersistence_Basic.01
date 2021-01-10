package com.android.retrofit.pj.interfaces;

import com.android.retrofit.pj.models.Cliente;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface IClienteApi {

        @Headers({
                "Content-type: application/json"
        })

        @GET("api/clientes/{id}")
        Call<Cliente> find (@Path("id") String id);

        @POST("api/clientes/")
        Call<Cliente> post(@Body Cliente cliente);

        @POST("/api/clientes/")
        @FormUrlEncoded
        Call<Cliente> savePost(@Field("nome") String nome,
                            @Field("email") String email,
                            @Field("cpf") String cpf);


}
