package com.projectdmc.empapp;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddTaskActivity extends AppCompatActivity {

    private EditText taskNameEditText, taskDescEditText, issueDateEditText, deadlineEditText;
    private Spinner employeeSpinner;
    private Button assignTaskButton;
    private DBHelper dbHelper;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        taskNameEditText = findViewById(R.id.taskNameEditText);
        taskDescEditText = findViewById(R.id.taskDescEditText);
        issueDateEditText = findViewById(R.id.issueDateEditText);
        deadlineEditText = findViewById(R.id.deadlineEditText);
        employeeSpinner = findViewById(R.id.employeeSpinner);
        assignTaskButton = findViewById(R.id.assignTaskButton);
        dbHelper = new DBHelper(this);
        calendar = Calendar.getInstance();

        loadEmployeesIntoSpinner();

        issueDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(issueDateEditText);
            }
        });

        deadlineEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(deadlineEditText);
            }
        });

        assignTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assignTask();
            }
        });
    }

    private void showDatePickerDialog(final EditText dateEditText) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(AddTaskActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dateEditText.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private void loadEmployeesIntoSpinner() {
        List<String> employeeList = new ArrayList<>();
        employeeList.add("Select Employee");

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT e_name FROM employee", null);
        if (cursor.moveToFirst()) {
            do {
                employeeList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, employeeList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        employeeSpinner.setAdapter(adapter);
    }

    private void assignTask() {
        String taskName = taskNameEditText.getText().toString();
        String taskDesc = taskDescEditText.getText().toString();
        String issueDate = issueDateEditText.getText().toString();
        String deadline = deadlineEditText.getText().toString();
        String assignedEmployee = employeeSpinner.getSelectedItem().toString();

        if (taskName.isEmpty() || issueDate.isEmpty() || deadline.isEmpty() || "Select Employee".equals(assignedEmployee)) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT e_id FROM employee WHERE e_name=?", new String[]{assignedEmployee});
        int employeeId = -1;
        if (cursor.moveToFirst()) {
            employeeId = cursor.getInt(0);
        }
        cursor.close();
        db.close();

        if (employeeId == -1) {
            Toast.makeText(this, "Employee not found", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase writeDb = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("t_name", taskName);
        values.put("t_desc", taskDesc);
        values.put("t_issue_date", issueDate);
        values.put("t_deadline", deadline);
        values.put("e_id", employeeId);
        values.put("t_receiver", assignedEmployee);
        values.put("t_sender", "Manager");
        values.put("t_status", "Pending");
        values.put("t_display", "Yes");

        // Logging the values
        Log.d("AddTaskActivity", "Inserting values: " + values.toString());

        long result = writeDb.insert("task", null, values);

        writeDb.close(); // Ensure database is closed after the operation

        if (result != -1) {
            Toast.makeText(this, "Task assigned successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Log.e("AddTaskActivity", "Failed to insert task. Error code: " + result);
            Toast.makeText(this, "Failed to assign task", Toast.LENGTH_SHORT).show();
        }
    }
}
