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

public class RegisterActivity extends AppCompatActivity {

    private EditText emailEdit, passEdit, passConfirmEdit;
    private Button btnCrear, btnVolverLogin;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();

        emailEdit = findViewById(R.id.email);
        passEdit = findViewById(R.id.password);
        passConfirmEdit = findViewById(R.id.passwordConfirm);
        btnCrear = findViewById(R.id.btnCrear);
        btnVolverLogin = findViewById(R.id.btnVolverLogin);

        // Acción del botón "Crear Cuenta"
        btnCrear.setOnClickListener(v -> registrarUsuario());

        // Acción del botón "Volver a Login"
        btnVolverLogin.setOnClickListener(v -> finish());
    }

    private void registrarUsuario() {
        // Validaciones básicas
        if (Validaciones.campoVacio(emailEdit)) return;
        if (Validaciones.campoVacio(passEdit)) return;
        if (Validaciones.campoVacio(passConfirmEdit)) return;
        if (Validaciones.emailInvalido(emailEdit)) return;
        if (Validaciones.contrasenaCorta(passEdit)) return;
        if (Validaciones.contrasenasNoCoinciden(passEdit, passConfirmEdit)) return;

        String email = emailEdit.getText().toString().trim();
        String pass = passEdit.getText().toString().trim();

        // Crear usuario en Firebase
        auth.createUserWithEmailAndPassword(email, pass)
                .addOnSuccessListener(a -> {
                    Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    // Ir a MainActivity después de registrarse
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    finish(); // cerrar RegisterActivity
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show());
    }
}
