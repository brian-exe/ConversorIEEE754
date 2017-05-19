package com.example.eaciar.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SaludoActivity extends AppCompatActivity {
    private TextView txtSaludo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String binario = new String();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saludo);


        //Localizar los controles
        txtSaludo = (TextView)findViewById(R.id.txtSaludo);

        //Recuperamos la información pasada en el intent
        Bundle bundle = this.getIntent().getExtras();

        //Construimos el mensaje a mostrar
        binario=Integer.toBinaryString(Integer.parseInt(bundle.getString("NOMBRE")));

        txtSaludo.setText(binario);
    }
}
