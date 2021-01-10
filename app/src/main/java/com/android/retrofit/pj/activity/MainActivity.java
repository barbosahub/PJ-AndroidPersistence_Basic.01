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

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

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

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nome = edtNome.getText().toString();
                email = edtMail.getText().toString();
                cpf = edtCpf.getText().toString();

                if(!nome.isEmpty() && !cpf.isEmpty()) {
                    postById(nome,email,cpf);
                }else{
                    Toast.makeText(context, "Necessário Preencher", Toast.LENGTH_SHORT).show();
                }


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

    private void postById(String nome,String email, String cpf){

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        Retrofit retrofit=new Retrofit.Builder().baseUrl("https://apirest-basic.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create()).client(client).build();




        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setEmail(email);
        cliente.setCpf(cpf);
        IClienteApi iclienteApi =retrofit.create(IClienteApi.class);
        final Call<Cliente> call = iclienteApi.post(cliente);



        call.enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Sucesso!" + cliente.getNome(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Cliente> call, Throwable t) {

            }
        });






    }


}
