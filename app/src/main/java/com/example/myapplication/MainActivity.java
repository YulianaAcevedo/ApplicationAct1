package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AppCompatActivity;





public class MainActivity extends AppCompatActivity {
    private EditText editTextEmail, editTextContraseña;
    private DatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextEmail = findViewById(R.id.editTextTextPersonName);
        editTextContraseña = findViewById(R.id.editTextTextPassword);
        dbHelper = new DatabaseHelper(this);
    }

    public void registro (View vista){
        Intent ventanaRegistro= new Intent(getApplicationContext(),RegisterActivity.class);
        startActivity(ventanaRegistro);

    }
    public void iniciarSesion(View view) {
        // Obtener los valores ingresados por el usuario
        String email = editTextEmail.getText().toString();
        String contraseña = editTextContraseña.getText().toString();

        // Verificar las credenciales en la base de datos
        if (verificarCredenciales(email, contraseña)) {
            // Credenciales válidas, redirigir al usuario a la parte de la aplicación deseada

            Intent intent = new Intent(this, Menu.class);
            startActivity(intent);
        } else {
            // Credenciales incorrectas, mostrar un mensaje de error
            Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean verificarCredenciales(String email, String contraseña) {
        // Consultar la base de datos para verificar las credenciales
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {"email", "contraseña"};
        String selection = "email = ? AND contraseña = ?";
        String[] selectionArgs = {email, contraseña};

        Cursor cursor = db.query("usuarios", projection, selection, selectionArgs, null, null, null);
        boolean credencialesCorrectas = cursor.getCount() > 0;

        // Cerrar el cursor y la base de datos
        cursor.close();
        db.close();

        return credencialesCorrectas;
    }
}