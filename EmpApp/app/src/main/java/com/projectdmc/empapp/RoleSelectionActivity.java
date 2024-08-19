package com.projectdmc.empapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class RoleSelectionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_selection);

        Button employeeButton = findViewById(R.id.button_employee);
        Button adminButton = findViewById(R.id.button_admin);

        employeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to LoginActivity only if the role is employee
                Intent intent = new Intent(RoleSelectionActivity.this, LoginActivity.class);
                intent.putExtra("role", "employee");
                startActivity(intent);
            }
        });

        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to LoginActivity only if the role is admin
                Intent intent = new Intent(RoleSelectionActivity.this, AdminDashboardActivity.class);
                intent.putExtra("role", "admin");
                startActivity(intent);
            }
        });
    }
}
