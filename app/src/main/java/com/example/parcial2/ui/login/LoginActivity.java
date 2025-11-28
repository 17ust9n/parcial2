package com.example.parcial2.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.parcial2.R;
import com.example.parcial2.ui.MainActivity;
import com.example.parcial2.utils.Validaciones;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEdit, passEdit;
    private Button btnLogin, btnRegister;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicializar Firebase Auth
        auth = FirebaseAuth.getInstance();

        // Vincular vistas
        emailEdit = findViewById(R.id.email);
        passEdit = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        // Click en login
        btnLogin.setOnClickListener(v -> iniciarSesion());

        // Click en registrar
        btnRegister.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }

    private void iniciarSesion() {
        String email = emailEdit.getText().toString().trim();
        String pass = passEdit.getText().toString().trim();

        // Validaciones
        if (Validaciones.campoVacio(emailEdit) || Validaciones.campoVacio(passEdit)) return;
        if (Validaciones.emailInvalido(emailEdit)) return;

        // Iniciar sesión con Firebase
        auth.signInWithEmailAndPassword(email, pass)
                .addOnSuccessListener(a -> {
                    Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                    // Ir a MainActivity
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish(); // Evitar volver al login al presionar atrás
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
    }
}
