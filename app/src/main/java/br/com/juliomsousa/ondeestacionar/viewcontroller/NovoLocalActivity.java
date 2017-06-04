package br.com.juliomsousa.ondeestacionar.viewcontroller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import br.com.juliomsousa.ondeestacionar.R;
import br.com.juliomsousa.ondeestacionar.model.Local;

public class NovoLocalActivity extends AppCompatActivity {

    private LatLng latLng;
    Double latitude;
    Double longitude;

    private Address endereco;

    EditText edtNome;
    EditText edtEndereco;
    EditText edtNumero;
    EditText edtCidade;
    EditText edtPreco1Hora;
    EditText edtPrecoHoraAdicional;
    EditText edtPreco6Horas;
    EditText edtPrecoDiaria;
    CheckBox checkBoxMensalista;
    CheckBox checkBoxSeguro;
    CheckBox checkBoxManobrista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_local);

        getSupportActionBar().setTitle("Novo Local");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        StrictMode.ThreadPolicy.Builder regras = new StrictMode.ThreadPolicy.Builder();
        regras.detectAll();
        StrictMode.setThreadPolicy(regras.build());


        Intent intent = getIntent();

        latLng = intent.getExtras().getParcelable("latLng");

        this.latitude = latLng.latitude;
        this.longitude = latLng.longitude;


        getEndereco(this.latitude, this.longitude);


        edtNome = (EditText) findViewById(R.id.edtNome);

        edtEndereco = (EditText) findViewById(R.id.edtEndereco);

        edtNumero = (EditText) findViewById(R.id.edtNumero);

        edtCidade = (EditText) findViewById(R.id.edtCidade);

        edtPreco1Hora = (EditText) findViewById(R.id.edtPreco1Hora);

        edtPrecoHoraAdicional = (EditText) findViewById(R.id.edtPrecoHoraAdicional);

        edtPreco6Horas = (EditText) findViewById(R.id.edtPreco6Horas);

        edtPrecoDiaria = (EditText) findViewById(R.id.edtPrecoDiaria);

        checkBoxMensalista = (CheckBox) findViewById(R.id.checkBoxMensalista);

        checkBoxSeguro = (CheckBox) findViewById(R.id.checkBoxSeguro);

        checkBoxManobrista = (CheckBox) findViewById(R.id.checkBoxManobrista);

    }

    public void cadastrar(View view) {



        if (camposObrigatorios()) {

            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("locais");

            Local local = new Local();

            local.setLatitude(Double.parseDouble(String.valueOf(this.latitude)));
            local.setLongitude(Double.parseDouble(String.valueOf(this.longitude)));
            local.setAtivo(true);

            local.setNome(edtNome.getText().toString());

            local.setEndereco(edtEndereco.getText().toString());

            // nao obrigatorio
            local.setNumero(campoVazio(edtNumero) ? "S/N" : edtNumero.getText().toString());

            local.setCidade(edtCidade.getText().toString());

            local.setPrecoAte1Hora(Double.parseDouble(String.valueOf(edtPreco1Hora.getText())));
            local.setPrecoHoraAdicional(Double.parseDouble(String.valueOf(edtPrecoHoraAdicional.getText())));

            // nao obrigatorios
            local.setPreco6Horas(campoVazio(edtPreco6Horas) ? 0.0 : Double.parseDouble(String.valueOf(edtPreco6Horas.getText())));
            local.setPrecoDiaria(campoVazio(edtPrecoDiaria) ? 0.0 : Double.parseDouble(String.valueOf(edtPrecoDiaria.getText())));

            local.setVagasMensalista(checkBoxMensalista.isChecked());
            local.setTemSeguro(checkBoxSeguro.isChecked());
            local.setTemManobrista(checkBoxManobrista.isChecked());

            local.setAvaliacao(0.0);

            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put(myRef.push().getKey(), local.toMap());
            myRef.updateChildren(childUpdates);

            Toast.makeText(this, "Adicionado com sucesso", Toast.LENGTH_LONG).show();
            finish();


        } else {
            Toast.makeText(this, "Preencha todos os campos obrigatórios", Toast.LENGTH_LONG).show();
        }
    }

    private android.location.Address getEndereco (Double lat, Double lon) {

        // criação de uma AssyncTask
        GetEnderecoAsyncTask task = new GetEnderecoAsyncTask();
        // executa a thread/task
        task.execute();

        return endereco;
    }

    private Address consultarEndereco(Double lat, Double lon) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        android.location.Address end = null;

        try {
            List<Address> endereco = geocoder.getFromLocation(lat, lon, 1);
            end = endereco.get(0);

        } catch (IOException e) {
            Log.e("ERRO  getMaps", "Entrou no catch: " + e);
        }
        return end;
    }

    private class GetEnderecoAsyncTask extends AsyncTask<Void, Void, Address> {
        private ProgressDialog progresso;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progresso = ProgressDialog.show(NovoLocalActivity.this, "Aguarde", "Buscando informações");
        }

        @Override
        protected Address doInBackground(Void... params) {
            endereco = null;

            try {
                endereco = consultarEndereco(latitude, longitude);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return endereco;
        }

        @Override
        protected void onPostExecute(Address end) {
            if (end != null) {
                preencherCampos(end);
                progresso.dismiss();
            }
        }
    }

    private void preencherCampos(Address end) {

        edtEndereco.setText(end.getThoroughfare());

        edtNumero.setText(end.getFeatureName());

        edtCidade.setText(end.getLocality());
    }

    private boolean camposObrigatorios() {

        if(edtNome.getText().length() == 0
                || edtEndereco.getText().length() == 0
                || edtCidade.getText().length() == 0
                || edtPreco1Hora.getText().length() == 0
                || edtPrecoHoraAdicional.getText().length() == 0) {

            return false;
        } else {
            return true;
        }
    }

    private Boolean campoVazio(EditText campo) {

        if(campo.length() == 0)
            return true;
        else
            return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
