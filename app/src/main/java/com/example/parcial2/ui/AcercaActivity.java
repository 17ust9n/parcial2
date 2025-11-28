package com.example.parcial2.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.parcial2.R;

public class AcercaActivity extends AppCompatActivity {

    private Button btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca);

        btnVolver = findViewById(R.id.btnVolver);

        // Al hacer clic, regresar a MainActivity
        btnVolver.setOnClickListener(v -> {
            startActivity(new Intent(AcercaActivity.this, MainActivity.class));
            finish(); // opcional: evita volver a Acerca con la flecha atrás
        });
    }
}
