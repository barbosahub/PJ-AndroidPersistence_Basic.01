package com.android.retrofit.pj.interfaces;

import com.android.retrofit.pj.models.Cliente;
import com.android.retrofit.pj.models.Data;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface IClienteApi {

        @Headers({ "Content-type: application/json"  })

        @GET("api/clientes")
        Call<Cliente> findList(@Query("page") String page);

        @GET("api/clientes/{id}")
        Call<Cliente> findById (@Path("id") String id);

        @POST("api/clientes/")
        Call<Data> post(@Body Data data);

        @PUT("api/clientes/{id}")
        Call<Data> put(@Path("id") String id, @Body Data data);

        @DELETE("api/clientes/{id}")
        Call<Cliente> deleteById (@Path("id") String id);

}
