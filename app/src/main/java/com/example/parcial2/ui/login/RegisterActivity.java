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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailEdit, passEdit, passConfirmEdit;
    private Button btnVolverLogin;
    private MaterialButton btnCrear, btnRegisterGoogle;

    private FirebaseAuth auth;
    private GoogleSignInClient googleClient;
    private static final int RC_GOOGLE_SIGN_IN = 101; // Diferente del login

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
        btnRegisterGoogle = findViewById(R.id.btnRegisterGoogle);

        // --------------------------
        // Configurar Google Sign-In
        // --------------------------
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleClient = GoogleSignIn.getClient(this, gso);

        // --------------------------
        // Click en crear cuenta
        // --------------------------
        btnCrear.setOnClickListener(v -> registrarUsuario());

        // --------------------------
        // Click en volver a login
        // --------------------------
        btnVolverLogin.setOnClickListener(v -> finish());

        // --------------------------
        // Click en registrar con Google
        // --------------------------
        btnRegisterGoogle.setOnClickListener(v -> {
            Intent intent = googleClient.getSignInIntent();
            startActivityForResult(intent, RC_GOOGLE_SIGN_IN);
        });
    }

    // --------------------------
    // Registro con email/contraseña
    // --------------------------
    private void registrarUsuario() {
        if (Validaciones.campoVacio(emailEdit) || Validaciones.campoVacio(passEdit) || Validaciones.campoVacio(passConfirmEdit))
            return;
        if (Validaciones.emailInvalido(emailEdit)) return;
        if (Validaciones.contrasenaCorta(passEdit)) return;
        if (Validaciones.contrasenasNoCoinciden(passEdit, passConfirmEdit)) return;

        String email = emailEdit.getText().toString().trim();
        String pass = passEdit.getText().toString().trim();

        auth.createUserWithEmailAndPassword(email, pass)
                .addOnSuccessListener(a -> {
                    Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    finish();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error: " + (e != null ? e.getMessage() : "Error desconocido"), Toast.LENGTH_LONG).show());
    }

    // --------------------------
    // Manejar resultado de Google Sign-In
    // --------------------------
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_GOOGLE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Toast.makeText(this, "Error al registrarse con Google: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    // --------------------------
    // Autenticar con Firebase usando Google
    // --------------------------
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);

        auth.signInWithCredential(credential)
                .addOnSuccessListener(authResult -> {
                    FirebaseUser user = auth.getCurrentUser();
                    Toast.makeText(this, "Bienvenido " + (user != null ? user.getEmail() : ""), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    finish();
                })
                .addOnFailureListener(e -> {
                    String msg = e != null && e.getMessage() != null ? e.getMessage() : "Error desconocido";
                    Toast.makeText(this, "Error Firebase: " + msg, Toast.LENGTH_LONG).show();
                });
    }
}
