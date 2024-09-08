package com.example.calculadorahistorial;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText primerValor;
    private Button btnSumar, btnRestar, btnMultiplicar, btnDividir, btnReset, calcular, historial;
    private List<Double> numeros = new ArrayList<>();
    private List<String> operaciones = new ArrayList<>();
    private List<String> historialOperaciones = new ArrayList<>();
    private TextView resultadoFinal;
    private DecimalFormat decimalFormat = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        primerValor = (EditText) findViewById(R.id.txtPrimerValor);
        btnSumar = (Button) findViewById(R.id.btnSumar);
        btnRestar = (Button) findViewById(R.id.btnRestar);
        btnMultiplicar = (Button) findViewById(R.id.btnMultiplicar);
        btnDividir = (Button) findViewById(R.id.btnDividir);
        btnReset = (Button) findViewById(R.id.btnReset);
        calcular = (Button) findViewById(R.id.btnCalcular);
        historial = (Button) findViewById(R.id.btnHistorial);
        resultadoFinal = (TextView) findViewById(R.id.txtResultado);

        // Configurar botones de operaciones
        btnSumar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarNumeroYOperacion("+");
            }
        });

        btnRestar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarNumeroYOperacion("-");
            }
        });

        btnMultiplicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarNumeroYOperacion("*");
            }
        });

        btnDividir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarNumeroYOperacion("/");
            }
        });

        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcularResultado();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetearValores();
            }
        });
    }

    private void guardarNumeroYOperacion(String op) {
        String numeroIngresado = primerValor.getText().toString();
        if (!numeroIngresado.isEmpty()) {
            double numero = Double.parseDouble(numeroIngresado);
            numeros.add(numero);
            operaciones.add(op);
            primerValor.setText("");

        } else {
            Toast.makeText(this, "Por favor ingrese un número", Toast.LENGTH_SHORT).show();
        }
    }

    private void resetearValores() {
        numeros.clear();
        operaciones.clear();
        primerValor.setText("");
        resultadoFinal.setText("");
    }

    private void calcularResultado() {
        String numeroIngresado = primerValor.getText().toString();
        if (!numeroIngresado.isEmpty()) {
            double numero = Double.parseDouble(numeroIngresado);
            numeros.add(numero);
            primerValor.setText("");

            if (numeros.size() > 1) {
                double resultado = numeros.get(0);
                StringBuilder operacion = new StringBuilder(decimalFormat.format(numeros.get(0)));
                for (int i = 1; i < numeros.size(); i++) {
                    switch (operaciones.get(i - 1)) {
                        case "+":
                            resultado += numeros.get(i);
                            operacion.append(" + ").append(decimalFormat.format(numeros.get(i)));
                            break;
                        case "-":
                            resultado -= numeros.get(i);
                            operacion.append(" - ").append(decimalFormat.format(numeros.get(i)));
                            break;
                        case "*":
                            resultado *= numeros.get(i);
                            operacion.append(" * ").append(decimalFormat.format(numeros.get(i)));
                            break;
                        case "/":
                            if (numeros.get(i) != 0) {
                                resultado /= numeros.get(i);
                                operacion.append(" / ").append(decimalFormat.format(numeros.get(i)));
                            } else {
                                Toast.makeText(this, "No se puede dividir por cero", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            break;
                    }
                }

                // Formateo el resultado con 2 números decimales (#):
                DecimalFormat decimales = new DecimalFormat("#.##");
                resultadoFinal.setText("Resultado: " + decimalFormat.format(resultado));
                Toast.makeText(this, "El resultado es: " + decimalFormat.format(resultado), Toast.LENGTH_SHORT).show();
                operacion.append(" = ").append(decimalFormat.format(resultado));
                historialOperaciones.add(operacion.toString());
                numeros.clear();

            } else {
                Toast.makeText(this, "Por favor ingrese más números", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Por favor ingrese un número", Toast.LENGTH_SHORT).show();
        }

        historial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Historial.class);
                intent.putStringArrayListExtra("historial", (ArrayList<String>) historialOperaciones);
                startActivity(intent);
            }
        });

    }
}