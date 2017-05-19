package com.example.eaciar.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    private EditText txtNombre;
    private Button btnAceptar;
    private Switch sw;
    private TextView txtDet;
    private ToggleButton toggle;
    private Conversor conversor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Obtenemos una referencia a los controles de la interfaz
        txtNombre = (EditText)findViewById(R.id.txtNombre);
        btnAceptar = (Button)findViewById(R.id.btnAceptar);
        txtDet= (TextView)findViewById(R.id.txtDetails);
        toggle = (ToggleButton)findViewById(R.id.toggleButton);
        conversor=new Conversor();




        //Implementamos el evento click del botón
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String resultado = new String();

                if (toggle.isChecked()){
                    resultado="No Implementado";
                }
                else {
                    /*resultado=conversor.convertToBinary(txtNombre.getText().toString());*/
                    resultado=conversor.convertToIEE754(txtNombre.getText().toString());
                }

                txtDet.setText(resultado);
                /*
                //Creamos el Intent
                Intent intent =
                        new Intent(MainActivity.this, SaludoActivity.class);

                //Creamos la información a pasar entre actividades
                Bundle b = new Bundle();
                b.putString("NOMBRE", txtNombre.getText().toString());

                //Añadimos la información al intent
                intent.putExtras(b);

                //Iniciamos la nueva actividad
                startActivity(intent);*/
            }
        });

    }
}
