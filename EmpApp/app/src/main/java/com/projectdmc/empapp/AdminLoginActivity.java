package com.projectdmc.empapp;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminLoginActivity extends AppCompatActivity {
    EditText edita_Email, edita_Password;
    TextView textRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        edita_Email = findViewById(R.id.edita_Email);
        edita_Password = findViewById(R.id.edita_Password);
        textRegister = findViewById(R.id.textRegister);

        textRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminLoginActivity.this, AdminRegisterActivity.class));
            }
        });
    }

    public void login(View view) {
        String a_email = edita_Email.getText().toString();
        String a_password = edita_Password.getText().toString();

        ContentResolver resolver = getContentResolver();
        Uri uri = Uri.parse("content://com.sunbeaminfo.admin.provider.AdminProvider/");
        Cursor cursor = resolver.query(uri, null, "a_email = ? and a_password = ?", new String[]{a_email, a_password}, null);

        if (cursor != null && cursor.moveToNext()) {
            startActivity(new Intent(this, AdminDashboardActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
        }

        if (cursor != null) {
            cursor.close();
        }
    }

    public void cancel(View view) {
        finish();
    }
}
