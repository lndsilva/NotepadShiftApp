package br.com.local.notepadshiftapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import br.com.local.notepadshiftapp.api.NotaAPI;
import br.com.local.notepadshiftapp.model.Nota;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;

public class MainActivity extends AppCompatActivity {

    private EditText etTitulo;
    private EditText etTexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTitulo = (EditText) findViewById(R.id.etTitulo);
        etTexto = (EditText) findViewById(R.id.etTexto);

        Stetho.initializeWithDefaults(this);

    }

    private Retrofit getRetrofit() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        return new Retrofit.Builder()
                .baseUrl("https://notepadcloudshiftlaercio.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();


    }

    public void pesquisar(View v) {
        NotaAPI api = getRetrofit().create(NotaAPI.class);
        api.buscarNota(etTitulo.getText().toString())
                .enqueue(new Callback<Nota>() {
                    @Override
                    public void onResponse(Call<Nota> call, Response<Nota> response) {
                       if(response.isSuccessful()){
                        etTexto.setText(response.body().getDescricao());
                    }}

                    @Override
                    public void onFailure(Call<Nota> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Ops! ", Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void salvar(View v) {
        
    }
}
