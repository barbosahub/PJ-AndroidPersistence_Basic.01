package com.android.retrofit.pj.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
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


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ProgressDialog dialog;
    Spinner spinner;
    Context context;
    TextView txtId;
    EditText edtId;
    EditText edtNome;
    EditText edtMail;
    EditText edtCpf;
    Button btnSalvar;
    Integer strSpinner;
    ListView listView;

    String id;
    String nome;
    String email;
    String cpf;

    TextView txtlvId;
    TextView txtlvName;
    TextView txtlvCpf;
    TextView txtlvEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
        components();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,R.array.spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = edtId.getText().toString();
                nome = edtNome.getText().toString();
                email = edtMail.getText().toString();
                cpf = edtCpf.getText().toString();

                switch (strSpinner) {
                    case 0:
                        postById(nome,email,cpf);
                        break;
                    case 1:
                        findById(id);
                        break;
                    case 2:
                        putById(nome,email,cpf);
                        break;
                    case 3:
                        deleteById(id);
                        break;
                }
            }
        });

        TextView txtApi = (TextView) findViewById(R.id.txtApi);
        TextView txtGit = (TextView) findViewById(R.id.txtGit);
        txtApi.setMovementMethod(LinkMovementMethod.getInstance());
        txtGit.setMovementMethod(LinkMovementMethod.getInstance());
    }

    /*-------------------------------------------------------------     CRUD     */

    //@GET
    private void findById(String id) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://apirest-basic.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create()).client(client).build();

        IClienteApi iclienteApi = retrofit.create(IClienteApi.class);
        final Call<Cliente> call = iclienteApi.findById(id);
        dialog = new ProgressDialog(MainActivity.this);
        dialog.setMessage("Carregando...");
        dialog.setCancelable(false);
        dialog.show();
        call.enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                if (dialog.isShowing()) dialog.dismiss();
                if (response.isSuccessful()) {
                    Cliente c = response.body();
                    edtId.setText(c.getData().getId());
                    edtNome.setText(c.getData().getNome());
                    edtMail.setText(c.getData().getEmail());
                    edtCpf.setText(c.getData().getCpf());
                } else {
                    Toast.makeText(MainActivity.this, "error:" + response.message(), Toast.LENGTH_SHORT).show();
                    clear();
                }
            }
            @Override
            public void onFailure(Call<Cliente> call, Throwable t) {
                if (dialog.isShowing()) dialog.dismiss();
                Toast.makeText(MainActivity.this, "error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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

        dialog = new ProgressDialog(MainActivity.this);
        dialog.setMessage("Carregando...");
        dialog.setCancelable(false);
        dialog.show();
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if (dialog.isShowing()) dialog.dismiss();
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Cadastro Realizada!", Toast.LENGTH_SHORT).show();

                    edtId.setText(data.getId());
                } else {
                    Toast.makeText(MainActivity.this, "error:" + response.message(), Toast.LENGTH_SHORT).show();
                }
                clear();
            }
            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                if (dialog.isShowing()) dialog.dismiss();
                Toast.makeText(MainActivity.this, "error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //@PUT
    private void putById(String nome,String email, String cpf){

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        Retrofit retrofit=new Retrofit.Builder().baseUrl("https://apirest-basic.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create()).client(client).build();

        IClienteApi iclienteApi =retrofit.create(IClienteApi.class);
        final Call <Cliente> call = iclienteApi.findById(edtId.getText().toString());
        dialog = new ProgressDialog(MainActivity.this);
        dialog.setMessage("Carregando...");
        dialog.setCancelable(false);
        dialog.show();

        Data data = new Data();
        data.setNome(nome);
        data.setEmail(email);
        data.setCpf(cpf);

        final Call<Data> callpost = iclienteApi.put(edtId.getText().toString(),data);

            callpost.enqueue(new Callback<Data>() {
                @Override
                public void onResponse(Call<Data> callpost, Response<Data> response) {
                    if (dialog.isShowing()) dialog.dismiss();
                    if (response.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Dados atualizados!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "error:" + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<Data> callpost, Throwable t) {
                    if (dialog.isShowing()) dialog.dismiss();
                    Toast.makeText(MainActivity.this, "error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }

    //@DELETE
    private void deleteById(String id) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://apirest-basic.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create()).client(client).build();

        IClienteApi iclienteApi = retrofit.create(IClienteApi.class);
        final Call<Cliente> call = iclienteApi.deleteById(id);
        dialog = new ProgressDialog(MainActivity.this);
        dialog.setMessage("Carregando...");
        dialog.setCancelable(false);
        dialog.show();
        call.enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Cadastro Removido!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "error:" + response.message(), Toast.LENGTH_SHORT).show();
                }
                if (dialog.isShowing()) dialog.dismiss();
                clear();
            }
            @Override
            public void onFailure(Call<Cliente> call, Throwable t) {
                if (dialog.isShowing()) dialog.dismiss();
            }
        });
    }

    /*-------------------------------------------------------------     LAYOUT     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        layout(position);
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void visibleComponents(){
        txtId.setVisibility(View.VISIBLE);
        txtlvName.setVisibility(View.VISIBLE);
        txtlvCpf.setVisibility(View.VISIBLE);
        txtlvEmail.setVisibility(View.VISIBLE);


        edtId.setVisibility(View.VISIBLE);
        edtNome.setVisibility(View.VISIBLE);
        edtMail.setVisibility(View.VISIBLE);
        edtCpf.setVisibility(View.VISIBLE);

        edtId.setEnabled(true);
        edtNome.setEnabled(true);
        edtMail.setEnabled(true);
        edtCpf.setEnabled(true);
    }

    private void components(){
        txtId = (TextView) findViewById(R.id.txtlvId);
        edtNome = (EditText) findViewById(R.id.edtName);
        edtMail = (EditText) findViewById(R.id.edtMail);
        edtCpf = (EditText)findViewById(R.id.edtCpf);
        edtId = (EditText) findViewById(R.id.edtId);
        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        spinner = (Spinner)findViewById(R.id.spinner);

        txtlvId = (TextView) findViewById(R.id.txtlvId);
        txtlvName = (TextView) findViewById(R.id.txtlvName);
        txtlvEmail = (TextView) findViewById(R.id.txtlvMail);
        txtlvCpf = (TextView) findViewById(R.id.txtlvCpf);
    }

    private void clear(){
        edtId.setText("");
        edtNome.setText("");
        edtMail.setText("");
        edtCpf.setText("");
    }

    public void layout(int position){
        strSpinner = position;
        if (position == 0){
            clear(); visibleComponents();
            txtId.setVisibility(View.GONE);
            edtId.setVisibility(View.GONE);
        }
        else if (position == 1){
            clear(); visibleComponents();
            edtNome.setEnabled(false);
            edtCpf.setEnabled(false);
            edtMail.setEnabled(false);
        }
        else if (position == 2){
            clear(); visibleComponents();
        }
        else if (position == 3){
            clear(); visibleComponents();
            txtlvName.setVisibility(View.GONE);
            txtlvEmail.setVisibility(View.GONE);
            txtlvCpf.setVisibility(View.GONE);

            edtNome.setVisibility(View.GONE);
            edtMail.setVisibility(View.GONE);
            edtCpf.setVisibility(View.GONE);

        }

    }

}



