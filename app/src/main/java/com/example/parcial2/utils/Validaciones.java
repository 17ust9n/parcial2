package com.example.parcial2.utils;

import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;

public class Validaciones {

    // ==============================
    //  MÉTODOS DE VALIDACIÓN BÁSICA
    // ==============================

    public static boolean campoVacio(EditText editText) {
        if (TextUtils.isEmpty(editText.getText().toString().trim())) {
            editText.setError("Este campo es obligatorio");
            return true;
        }
        return false;
    }

    public static boolean emailInvalido(EditText editText) {
        String email = editText.getText().toString().trim();
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editText.setError("Correo inválido");
            return true;
        }
        return false;
    }

    public static boolean telefonoInvalido(EditText editText) {
        String telefono = editText.getText().toString().trim();
        if (!Patterns.PHONE.matcher(telefono).matches()) {
            editText.setError("Teléfono inválido");
            return true;
        }
        return false;
    }

    public static boolean edadInvalida(EditText editText) {
        String valor = editText.getText().toString().trim();
        try {
            int edad = Integer.parseInt(valor);
            if (edad <= 0 || edad > 120) {
                editText.setError("Edad inválida");
                return true;
            }
        } catch (NumberFormatException e) {
            editText.setError("Edad inválida");
            return true;
        }
        return false;
    }

    // ==============================
    //  VALIDACIONES PARA REGISTRO
    // ==============================

    public static boolean contrasenaCorta(EditText editText) {
        String pass = editText.getText().toString().trim();
        if (pass.length() < 6) {
            editText.setError("Mínimo 6 caracteres");
            return true;
        }
        return false;
    }

    public static boolean contrasenasNoCoinciden(EditText pass1, EditText pass2) {
        if (!pass1.getText().toString().equals(pass2.getText().toString())) {
            pass2.setError("Las contraseñas no coinciden");
            return true;
        }
        return false;
    }

    // ==============================
    //  MÉTODO GENERAL
    // ==============================

    public static boolean validarCamposVacios(EditText... campos) {
        boolean hayError = false;
        for (EditText c : campos) {
            if (campoVacio(c)) hayError = true;
        }
        return hayError;
    }
}
