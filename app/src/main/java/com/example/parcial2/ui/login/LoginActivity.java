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

public class LoginActivity extends AppCompatActivity {

    private EditText emailEdit, passEdit;
    private Button btnLogin, btnRegister;
    private MaterialButton btnLoginGoogle;

    private FirebaseAuth auth;
    private GoogleSignInClient googleClient;
    private static final int RC_GOOGLE_SIGN_IN = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // --------------------------
        // Comprobar si ya hay usuario logueado en Firebase
        // --------------------------
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            // Usuario ya logueado, vamos directo a MainActivity
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        // --------------------------
        // Comprobar si ya hay usuario logueado con Google
        // --------------------------
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            // Usuario ya había iniciado sesión con Google
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        // --------------------------
        // Configurar la interfaz
        // --------------------------
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        emailEdit = findViewById(R.id.email);
        passEdit = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        btnLoginGoogle = findViewById(R.id.btnLoginGoogle);

        if (emailEdit == null || passEdit == null || btnLogin == null || btnRegister == null || btnLoginGoogle == null) {
            Toast.makeText(this, "Error al cargar la interfaz de login", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        // --------------------------
        // Configurar Google Sign-In
        // --------------------------
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleClient = GoogleSignIn.getClient(this, gso);

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

        // --------------------------
        // Click en login Google
        // --------------------------
        btnLoginGoogle.setOnClickListener(v -> {
            Intent intent = googleClient.getSignInIntent();
            startActivityForResult(intent, RC_GOOGLE_SIGN_IN);
        });
    }


    private void iniciarSesion() {
        if (auth == null) {
            Toast.makeText(this, "Error de autenticación", Toast.LENGTH_SHORT).show();
            return;
        }

        String email = emailEdit.getText() != null ? emailEdit.getText().toString().trim() : "";
        String pass = passEdit.getText() != null ? passEdit.getText().toString().trim() : "";

        if (Validaciones.campoVacio(emailEdit) || Validaciones.campoVacio(passEdit)) return;
        if (Validaciones.emailInvalido(emailEdit)) return;

        auth.signInWithEmailAndPassword(email, pass)
                .addOnSuccessListener(a -> {
                    Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                })
                .addOnFailureListener(e -> {
                    String msg = e != null && e.getMessage() != null ? e.getMessage() : "Error desconocido";
                    Toast.makeText(this, "Error: " + msg, Toast.LENGTH_LONG).show();
                });
    }

    // --------------------------
    // Manejar resultado Google
    // --------------------------
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_GOOGLE_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Toast.makeText(this, "Error al iniciar con Google: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken){
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);

        auth.signInWithCredential(credential)
                .addOnSuccessListener(authResult -> {
                    FirebaseUser user = auth.getCurrentUser();
                    Toast.makeText(this, "Bienvenido " + (user != null ? user.getEmail() : ""), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                })
                .addOnFailureListener(e -> {
                    String msg = e != null && e.getMessage() != null ? e.getMessage() : "Error desconocido";
                    Toast.makeText(this, "Error Firebase: " + msg, Toast.LENGTH_LONG).show();
                });
    }
}
