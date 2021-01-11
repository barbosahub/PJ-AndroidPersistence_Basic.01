package com.android.retrofit.pj.interfaces;

import com.android.retrofit.pj.models.Cliente;
import com.android.retrofit.pj.models.Data;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface IClienteApi {

        @Headers({
                "Content-type: application/json"
        })

        @GET("api/clientes/{id}")
        Call<Cliente> findById (@Path("id") String id);

        @POST("api/clientes/")
        Call<Data> post(@Body Data data);

        @DELETE("api/clientes/{id}")
        Call<Cliente> deleteById (@Path("id") String id);

//        @POST("/api/clientes/")
//        @FormUrlEncoded
//        Call<Cliente> savePost(@Field("nome") String nome,
//                            @Field("email") String email,
//                            @Field("cpf") String cpf);


}
