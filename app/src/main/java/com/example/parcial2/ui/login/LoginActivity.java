package com.example.parcial2.ui.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.parcial2.R;
import com.example.parcial2.ui.MainActivity;
import com.example.parcial2.utils.Validaciones;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEdit, passEdit;
    private Button btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // --------------------------
        // Configurar la interfaz
        // --------------------------
        setContentView(R.layout.activity_login);

        emailEdit = findViewById(R.id.email);
        passEdit = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        if (emailEdit == null || passEdit == null || btnLogin == null || btnRegister == null) {
            Toast.makeText(this, "Error al cargar la interfaz de login", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        // --------------------------
        // Click en login
        // --------------------------
        btnLogin.setOnClickListener(v -> iniciarSesion());

        // --------------------------
        // Click en registrar
        // --------------------------
        btnRegister.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }

    private void iniciarSesion() {
        String email = emailEdit.getText() != null ? emailEdit.getText().toString().trim() : "";
        String pass = passEdit.getText() != null ? passEdit.getText().toString().trim() : "";

        if (Validaciones.campoVacio(emailEdit) || Validaciones.campoVacio(passEdit)) return;
        if (Validaciones.emailInvalido(emailEdit)) return;

        // Simulamos login exitoso para parcial
        Toast.makeText(this, "Inicio de sesión simulado exitoso", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }
}
