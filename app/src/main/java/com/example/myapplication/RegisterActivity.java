package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private EditText editTextNombre, editTextApellidos, editTextEmail, editTextContraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper = new DatabaseHelper(this);

        editTextNombre = findViewById(R.id.editTextTextPersonName2);
        editTextApellidos = findViewById(R.id.editTextTextPersonName3);
        editTextEmail = findViewById(R.id.editTextTextPersonName4);
        editTextContraseña = findViewById(R.id.editTextTextPassword4);
    }

    public void onClickRegistrar(View view) {
        // Obtener una instancia de la base de datos
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Obtener los datos del usuario desde los EditText
        String nombre = editTextNombre.getText().toString();
        String apellidos = editTextApellidos.getText().toString();
        String email = editTextEmail.getText().toString();
        String contraseña = editTextContraseña.getText().toString();

        // Verificar si los campos están vacíos
        if (nombre.isEmpty() || apellidos.isEmpty() || email.isEmpty() || contraseña.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
        } else {
            // Crear un ContentValues para almacenar los datos
            ContentValues values = new ContentValues();
            values.put("nombre", nombre);
            values.put("apellidos", apellidos);
            values.put("email", email);
            values.put("contraseña", contraseña);

            // Insertar los datos en la tabla de usuarios
            long newRowId = db.insert("usuarios", null, values);

            // Verificar si la inserción fue exitosa
            if (newRowId != -1) {
                // Inserción exitosa, puedes mostrar un mensaje o redirigir a otra actividad
                Toast.makeText(this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();

            } else {
                // Error al insertar los datos en la base de datos
                Toast.makeText(this, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
            }

            // Cerrar la base de datos
            db.close();
        }
    }
}
