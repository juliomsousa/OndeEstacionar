package br.com.juliomsousa.ondeestacionar.viewcontroller;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

import br.com.juliomsousa.ondeestacionar.R;
import br.com.juliomsousa.ondeestacionar.model.Local;

public class DetalhesActivity extends AppCompatActivity {

    Local local;

    TextView txtNome;
    TextView txtEndereco;
    TextView txtNumero;
    TextView txtCidade;

    TextView txtPreco1Hora;
    TextView txtPrecoHoraAdicional;
    TextView txtPreco6Horas;
    TextView txtPrecoDiaria;

    TextView legMensalista;
    TextView legManobrista;
    TextView legSeguro;

    ImageView imgMensalista;
    ImageView imgSeguro;
    ImageView imgManobrista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        getSupportActionBar().setTitle("Detalhes");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        local = (Local) intent.getSerializableExtra("d");


        txtNome = (TextView) findViewById(R.id.txtNome);
        txtEndereco  = (TextView) findViewById(R.id.txtEndereco);
        txtNumero = (TextView) findViewById(R.id.txtNumero);
        txtCidade = (TextView) findViewById(R.id.txtCidade);

        txtPreco1Hora = (TextView) findViewById(R.id.txtPreco1Hora);
        txtPrecoHoraAdicional = (TextView) findViewById(R.id.txtPrecoHoraAdicional);
        txtPreco6Horas = (TextView) findViewById(R.id.txtPreco6Horas);
        txtPrecoDiaria = (TextView) findViewById(R.id.txtPrecoDiaria);

        // legendas das imagens
        legMensalista  = (TextView) findViewById(R.id.lblMensalista);
        legSeguro = (TextView) findViewById(R.id.lblSeguro);
        legManobrista = (TextView) findViewById(R.id.lblManobrista);

        imgMensalista = (ImageView) findViewById(R.id.imgMensalista);
        imgSeguro = (ImageView) findViewById(R.id.imgSeguro);
        imgManobrista = (ImageView) findViewById(R.id.imgManobrista);

        preencherDados();

    }

    private void preencherDados() {

        txtNome.setText(local.getNome());
        txtEndereco.setText(local.getEndereco());
        txtNumero.setText(local.getNumero());
        txtCidade.setText(local.getCidade());

        txtPreco1Hora.setText("R$ " + local.getPrecoAte1Hora().toString() + 0);
        txtPrecoHoraAdicional.setText("R$ " + local.getPrecoHoraAdicional().toString() + 0);
        txtPreco6Horas.setText("R$ " + local.getPreco6Horas().toString() + 0);
        txtPrecoDiaria.setText("R$ " + local.getPrecoDiaria().toString() + 0);

        if (local.getVagasMensalista()) {
            imgMensalista.setImageResource(R.drawable.ic_calendar);
            legMensalista.setText("Mensalista");
        }

        if (local.getTemSeguro()) {
            imgSeguro.setImageResource(R.drawable.ic_secure);
            legSeguro.setText("Seguro");
        }

        if (local.getTemManobrista()) {
            imgManobrista.setImageResource(R.drawable.ic_valet);
            legManobrista.setText("Manobrista");
        }
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
