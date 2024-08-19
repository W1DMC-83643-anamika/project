package com.projectdmc.empapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class AdminDashboardActivity extends AppCompatActivity {

    private Button btnDepartment, btnTotalTask, btnCompletedTask, btnPendingTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        // Initialize buttons
        btnDepartment = findViewById(R.id.btnDepartment);
        btnTotalTask = findViewById(R.id.btnTotalTask);
        btnCompletedTask = findViewById(R.id.btnCompletedTask);
        btnPendingTask = findViewById(R.id.btnPendingTask);

        // Set up button click listeners
        btnDepartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Department button click
                Intent intent = new Intent(AdminDashboardActivity.this, DepartmentActivity.class);
                startActivity(intent);
            }
        });

        btnTotalTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Total Task button click
                Intent intent = new Intent(AdminDashboardActivity.this, TotalTaskActivity.class);
                startActivity(intent);
            }
        });

        btnCompletedTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Completed Task button click
                Intent intent = new Intent(AdminDashboardActivity.this, CompletedTaskActivity.class);
                startActivity(intent);
            }
        });

        btnPendingTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Pending Task button click
                Intent intent = new Intent(AdminDashboardActivity.this, PendingTaskActivity.class);
                startActivity(intent);
            }
        });
    }
}
