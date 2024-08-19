package com.projectdmc.empapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    DBHelper dbHelper;
    EditText emailInput, passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DBHelper(this);
        emailInput = findViewById(R.id.input_email);
        passwordInput = findViewById(R.id.input_password);

        Button loginButton = findViewById(R.id.button_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                Cursor res = dbHelper.getEmployeeByEmailPassword(email, password);

                if (res != null && res.moveToFirst()) {
                    int roleIndex = res.getColumnIndex(DBHelper.EMPLOYEE_COLUMN_ROLE);

                    if (roleIndex != -1) {
                        String role = res.getString(roleIndex);
                        if ("emp".equals(role)) {
                            Intent intent = new Intent(LoginActivity.this, DetailsActivity.class);
                            startActivity(intent);
                        } else if ("manager".equals(role)) {
                            Intent intent = new Intent(LoginActivity.this, AddTaskActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "Invalid Role", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Role column not found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }


                if (res != null) {
                    res.close(); // Close cursor to avoid memory leaks
                }
            }
        });

        Button registerButton = findViewById(R.id.button_register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }
}
