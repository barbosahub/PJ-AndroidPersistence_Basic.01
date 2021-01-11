package com.android.retrofit.pj.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.retrofit.R;
import com.android.retrofit.pj.interfaces.IClienteApi;
import com.android.retrofit.pj.models.Cliente;
import com.android.retrofit.pj.models.Data;


import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    public boolean isApiError;
    Context context;
    TextView txtId;
    EditText edtId;
    EditText edtNome;
    EditText edtMail;
    EditText edtCpf;
    Button btnSalvar;

    String nome;
    String email;
    String cpf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
        components();

       // findById(edtId.getText().toString());

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nome = edtNome.getText().toString();
                email = edtMail.getText().toString();
                cpf = edtCpf.getText().toString();

//                if(!nome.isEmpty() && !cpf.isEmpty()) {
//                    postById(nome,email,cpf);
//                }else{
//                    Toast.makeText(context, "Necessário Preencher", Toast.LENGTH_SHORT).show();
//                }

                //findById(edtId.getText().toString());
                //postById(nome,email,cpf);
                deleteById("");

            }
        });

    }

    private void components(){
        txtId = (TextView) findViewById(R.id.txtId);
        edtNome = (EditText) findViewById(R.id.edtName);
        edtMail = (EditText) findViewById(R.id.edtMail);
        edtCpf = (EditText)findViewById(R.id.edtCpf);
        edtId = (EditText) findViewById(R.id.edtId);
        btnSalvar = (Button) findViewById(R.id.btnSalvar);
    }
    private void clear(){
        edtId.setText("");
        edtNome.setText("");
        edtMail.setText("");
        edtCpf.setText("");
    }




    //@GET by Id
    private void findById(String id){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        Retrofit retrofit=new Retrofit.Builder().baseUrl("https://apirest-basic.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create()).client(client).build();

        IClienteApi iclienteApi =retrofit.create(IClienteApi.class);
        final Call <Cliente> call = iclienteApi.findById(id);

        call.enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                if (response.isSuccessful()) {
                    Cliente c = response.body();
                    edtId.setText(c.getData().getId());
                    edtNome.setText(c.getData().getNome());
                    edtMail.setText(c.getData().getEmail());
                    edtCpf.setText(c.getData().getCpf());
                }else{
                    Toast.makeText(MainActivity.this, "error:"+response.message(), Toast.LENGTH_SHORT).show();
                    clear();
                }
            }
            @Override
            public void onFailure(Call<Cliente> call, Throwable t) {
                Toast.makeText(MainActivity.this, "error:"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

//        call.enqueue(new Callback<Datum>() {
//            @Override
//            public void onResponse(Call<Datum> call, Response<Datum> response) {
//                                if (response.isSuccessful()) {
//                    Toast.makeText(MainActivity.this, "Sucesso!" + response.body(), Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(MainActivity.this, "else"+response.message(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Datum> call, Throwable t) {
//                Toast.makeText(MainActivity.this, "error"+t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });

//        call.enqueue(new Callback<Datum>>() {
//            @Override
//            public void onResponse(Call<Datum>> call, Response<Datum>> response) {
//                if (response.isSuccessful()) {
//                    Toast.makeText(MainActivity.this, "Sucesso!" + response.body(), Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(MainActivity.this, "else"+response.message(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Datum> call, Throwable t) {
//                Toast.makeText(MainActivity.this, "error"+t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//





    }
    //@POST
    private void postById(String nome,String email, String cpf) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://apirest-basic.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create()).client(client).build();


        Data data = new Data();
        data.setNome(nome);
        data.setEmail(email);
        data.setCpf(cpf);
        IClienteApi iclienteApi = retrofit.create(IClienteApi.class);
        final Call<Data> call = iclienteApi.post(data);

        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Cadastro Realizado!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "error:" + response.message(), Toast.LENGTH_SHORT).show();
                }
                clear();
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Toast.makeText(MainActivity.this, "error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        }
    //@DELETE by Id
    private void deleteById(String id) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://apirest-basic.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create()).client(client).build();

        IClienteApi iclienteApi = retrofit.create(IClienteApi.class);
        final Call<Cliente> call = iclienteApi.deleteById("5ffba851ae774f0eb2eefbc9");

        call.enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Cadastro Removido!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "error:aaa" + response.message(), Toast.LENGTH_SHORT).show();
                }
                clear();
            }

            @Override
            public void onFailure(Call<Cliente> call, Throwable t) {
                Toast.makeText(MainActivity.this, "error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }






}



