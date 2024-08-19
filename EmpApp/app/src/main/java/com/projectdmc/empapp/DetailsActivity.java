package com.projectdmc.empapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DetailsActivity extends AppCompatActivity {

    private TextView taskTitleTextView, taskDeadlineTextView, taskDescTextView, taskStatusTextView;
    private Button updateStatusButton;
    private DBHelper dbHelper;
    private int taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Initialize views
        taskTitleTextView = findViewById(R.id.taskTitleTextView);
        taskDeadlineTextView = findViewById(R.id.taskDeadlineTextView);
        taskDescTextView = findViewById(R.id.taskDescTextView);
        taskStatusTextView = findViewById(R.id.taskStatusTextView);
        updateStatusButton = findViewById(R.id.updateStatusButton);

        dbHelper = new DBHelper(this);

        // Get task ID from Intent
        taskId = getIntent().getIntExtra("TASK_ID", -1);

        if (taskId != -1) {
            loadTaskDetails();
        } else {
            Toast.makeText(this, "Invalid Task ID", Toast.LENGTH_SHORT).show();
        }

        updateStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTaskStatus();
            }
        });
    }

    private void loadTaskDetails() {
        Cursor cursor = dbHelper.getTaskById(taskId);

        if (cursor.moveToFirst()) {
            String title = cursor.getString(0);
            String deadline = cursor.getString(1);
            String description = cursor.getString(2);
            String status = cursor.getString(3);

            taskTitleTextView.setText(title);
            taskDeadlineTextView.setText("Deadline: " + deadline);
            taskDescTextView.setText(description);
            taskStatusTextView.setText("Status: " + status);
        } else {
            Toast.makeText(this, "No task details found", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
    }

    private void updateTaskStatus() {
        String newStatus = "Completed"; // Or another status depending on your needs
        int rowsAffected = dbHelper.updateTaskStatus(taskId, newStatus);
        if (rowsAffected > 0) {
            Toast.makeText(this, "Task status updated", Toast.LENGTH_SHORT).show();
            taskStatusTextView.setText("Status: " + newStatus);
        } else {
            Toast.makeText(this, "Failed to update task status", Toast.LENGTH_SHORT).show();
        }
    }


}
