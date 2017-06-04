package br.com.juliomsousa.ondeestacionar.viewcontroller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import br.com.juliomsousa.ondeestacionar.R;

public class AboutActivity extends AppCompatActivity {

    TextView txtAboutText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        getSupportActionBar().setTitle("Sobre");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        

        txtAboutText = (TextView) findViewById(R.id.txtAboutText);
        txtAboutText.setText(texto());

    }

    private String texto() {
        String texto;

        texto = "©2017 Onde Estacionar \nVersão 1.0 \nDesenvolvido por BlackCofee Apps";


        return texto;
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
