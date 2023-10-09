
package com.example.myapplication;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Menu extends AppCompatActivity {
    private EditText editTextName, editTextEmail;
    private Button buttonCreate, buttonRead, buttonUpdate, buttonDelete;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Inicializar vistas y base de datos
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        buttonCreate = findViewById(R.id.buttonCreate);
        buttonRead = findViewById(R.id.buttonRead);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonDelete = findViewById(R.id.buttonDelete);
        dbHelper = new DatabaseHelper(this);

        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Código para crear un nuevo registro en la base de datos
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("nombre", editTextName.getText().toString());
                values.put("email", editTextEmail.getText().toString());

                // Insertar los valores en la tabla de la base de datos
                long newRowId = db.insert("usuarios", null, values);

                if (newRowId != -1) {
                    // Éxito: el registro se insertó correctamente
                } else {
                    // Fallo: no se pudo insertar el registro
                }

                // Cerrar la conexión a la base de datos
                db.close();
            }
        });

        buttonRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Código para leer los registros de la base de datos y mostrarlos en un TextView
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                String[] projection = {"nombre", "email"};

                Cursor cursor = db.query("usuarios", projection, null, null, null, null, null);
                StringBuilder userData = new StringBuilder();

                while (cursor.moveToNext()) {
                    String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
                    String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                    userData.append("Nombre: ").append(nombre).append(", Email: ").append(email).append("\n");
                }

                cursor.close();
                db.close();

                // Mostrar los datos en el TextView
                TextView textViewUserData = findViewById(R.id.textViewUserData);
                textViewUserData.setText(userData.toString());
            }
        });
    }
}
