package com.example.calculadorahistorial;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class Historial extends AppCompatActivity {

    private ListView listViewHistorial;
    private ArrayAdapter<String> adapter;
    private List<String> historialOperaciones;
    private Button btnVolverMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_historial);

        listViewHistorial = findViewById(R.id.ltView);
        btnVolverMain = findViewById(R.id.btnVolverMain);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("historial")) {
            historialOperaciones = intent.getStringArrayListExtra("historial");
            Log.d("HistorialActivity", "Historial recibido: " + historialOperaciones.toString());
        } else {
            historialOperaciones = new ArrayList<>();
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, historialOperaciones);
        listViewHistorial.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        btnVolverMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Historial.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}