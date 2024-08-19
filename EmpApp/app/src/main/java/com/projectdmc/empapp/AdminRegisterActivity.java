package com.projectdmc.empapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminRegisterActivity extends AppCompatActivity {
    EditText edita_Name, edita_Email, edita_Password, editRole;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register);

        edita_Name = findViewById(R.id.edita_Name);
        edita_Email = findViewById(R.id.edita_Email);
        edita_Password = findViewById(R.id.edita_Password);
        editRole = findViewById(R.id.editRole);

        dbHelper = new DBHelper(this);
    }

    public void register(View view) {
        String name = edita_Name.getText().toString();
        String email = edita_Email.getText().toString();
        String password = edita_Password.getText().toString();
        String role = editRole.getText().toString();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || role.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isInserted = dbHelper.insertAdmin(name, email, password, role);

        if (isInserted) {
            Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity after successful registration
        } else {
            Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show();
        }
    }

    public void cancel(View view) {
        finish(); // Close the activity when Cancel is pressed
    }
}
