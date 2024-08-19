package com.projectdmc.empapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "EmployeeTaskManagement.db";
    public static final String EMPLOYEE_TABLE_NAME = "employee";
    public static final String EMPLOYEE_COLUMN_ID = "e_id";
    public static final String EMPLOYEE_COLUMN_NAME = "e_name";
    public static final String EMPLOYEE_COLUMN_EMAIL = "e_email";
    public static final String EMPLOYEE_COLUMN_PASSWORD = "e_password";
    public static final String EMPLOYEE_COLUMN_GENDER = "e_gender";
    public static final String EMPLOYEE_COLUMN_ROLE = "role";

    public static final String TASK_TABLE_NAME = "task";
    public static final String TASK_COLUMN_ID = "t_id";
    public static final String TASK_COLUMN_NAME = "t_name";
    public static final String TASK_COLUMN_DESC = "t_desc";
    public static final String TASK_COLUMN_ISSUE_DATE = "t_issue_date";
    public static final String TASK_COLUMN_DEADLINE = "t_deadline";
    public static final String TASK_COLUMN_STATUS = "t_status";
    public static final String TASK_COLUMN_EMPLOYEE_ID = "e_id"; // Foreign key to employee
    public static final String TASK_COLUMN_RECEIVER = "t_receiver";
    public static final String TASK_COLUMN_SENDER = "t_sender";
    public static final String TASK_COLUMN_DISPLAY = "t_display";

    public static final String ADMIN_DATABASE_NAME = "AdminManagement.db";
    public static final String ADMIN_TABLE_NAME = "admin";
    public static final String ADMIN_COLUMN_ID = "a_id";
    public static final String ADMIN_COLUMN_NAME = "a_name";
    public static final String ADMIN_COLUMN_EMAIL = "a_email";
    public static final String ADMIN_COLUMN_PASSWORD = "a_password";
    public static final String ADMIN_COLUMN_ROLE = "role";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + EMPLOYEE_TABLE_NAME + " (" +
                        EMPLOYEE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        EMPLOYEE_COLUMN_NAME + " TEXT, " +
                        EMPLOYEE_COLUMN_EMAIL + " TEXT, " +
                        EMPLOYEE_COLUMN_PASSWORD + " TEXT, " +
                        EMPLOYEE_COLUMN_GENDER + " TEXT, " +
                        EMPLOYEE_COLUMN_ROLE + " TEXT)"
        );

        db.execSQL(
                "CREATE TABLE " + TASK_TABLE_NAME + " (" +
                        TASK_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        TASK_COLUMN_NAME + " TEXT, " +
                        TASK_COLUMN_DESC + " TEXT, " +
                        TASK_COLUMN_ISSUE_DATE + " TEXT, " +
                        TASK_COLUMN_DEADLINE + " TEXT, " +
                        TASK_COLUMN_STATUS + " TEXT, " +
                        TASK_COLUMN_EMPLOYEE_ID + " INTEGER, " +
                        TASK_COLUMN_RECEIVER + " TEXT, " +
                        TASK_COLUMN_SENDER + " TEXT, " +
                        TASK_COLUMN_DISPLAY + " TEXT, " +
                        "FOREIGN KEY(" + TASK_COLUMN_EMPLOYEE_ID + ") REFERENCES " + EMPLOYEE_TABLE_NAME + "(" + EMPLOYEE_COLUMN_ID + "))"
        );

        db.execSQL(
                "CREATE TABLE " + ADMIN_TABLE_NAME + " (" +
                        ADMIN_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        ADMIN_COLUMN_NAME + " TEXT, " +
                        ADMIN_COLUMN_EMAIL + " TEXT, " +
                        ADMIN_COLUMN_PASSWORD + " TEXT, " +
                        ADMIN_COLUMN_ROLE + " TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + EMPLOYEE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TASK_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ADMIN_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertEmployee(String name, String email, String password, String gender, String role) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EMPLOYEE_COLUMN_NAME, name);
        contentValues.put(EMPLOYEE_COLUMN_EMAIL, email);
        contentValues.put(EMPLOYEE_COLUMN_PASSWORD, password);
        contentValues.put(EMPLOYEE_COLUMN_GENDER, gender);
        contentValues.put(EMPLOYEE_COLUMN_ROLE, role);
        long result = db.insert(EMPLOYEE_TABLE_NAME, null, contentValues);
        db.close();
        return result != -1;
    }

    public boolean insertAdmin(String name, String email, String password, String role) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ADMIN_COLUMN_NAME, name);
        contentValues.put(ADMIN_COLUMN_EMAIL, email);
        contentValues.put(ADMIN_COLUMN_PASSWORD, password);
        contentValues.put(ADMIN_COLUMN_ROLE, role);
        long result = db.insert(ADMIN_TABLE_NAME, null, contentValues);
        db.close();
        return result != -1;
    }

    public Cursor getEmployeeByEmailPassword(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(
                "SELECT * FROM " + EMPLOYEE_TABLE_NAME +
                        " WHERE " + EMPLOYEE_COLUMN_EMAIL + "=? AND " + EMPLOYEE_COLUMN_PASSWORD + "=?",
                new String[]{email, password}
        );
    }

    public Cursor getAdminByEmailAndPassword(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(
                "SELECT * FROM " + ADMIN_TABLE_NAME +
                        " WHERE " + ADMIN_COLUMN_EMAIL + "=? AND " + ADMIN_COLUMN_PASSWORD + "=?",
                new String[]{email, password}
        );
    }

    public Cursor getTaskById(int taskId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(
                "SELECT " + TASK_COLUMN_NAME + ", " + TASK_COLUMN_DEADLINE + ", " +
                        TASK_COLUMN_DESC + ", " + TASK_COLUMN_STATUS +
                        " FROM " + TASK_TABLE_NAME +
                        " WHERE " + TASK_COLUMN_ID + "=?",
                new String[]{String.valueOf(taskId)}
        );
    }

    public int updateTaskStatus(int taskId, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TASK_COLUMN_STATUS, status);
        int rowsAffected = db.update(TASK_TABLE_NAME, contentValues, TASK_COLUMN_ID + "=?",
                new String[]{String.valueOf(taskId)});
        db.close();
        return rowsAffected;
    }

    public int getCompletedTaskCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM " + TASK_TABLE_NAME +
                        " WHERE " + TASK_COLUMN_STATUS + " = ?",
                new String[]{"Completed"}
        );

        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }

        cursor.close();
        return count;
    }

    public int getPendingTaskCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM " + TASK_TABLE_NAME +
                        " WHERE " + TASK_COLUMN_STATUS + " = ?",
                new String[]{"Pending"}
        );

        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }

        cursor.close();
        return count;
    }

    public int getTotalTaskCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TASK_TABLE_NAME, null);
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }

    public long insertDepartment(String departmentName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("d_name", departmentName);
        return db.insert("department", null, contentValues);
    }

    public int updateDepartment(int departmentId, String departmentName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("d_name", departmentName);
        return db.update("department", contentValues, "d_id=?", new String[]{String.valueOf(departmentId)});
    }

    public int deleteDepartment(int departmentId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("department", "d_id=?", new String[]{String.valueOf(departmentId)});
    }

    public Cursor getAllDepartments() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM department", null);
    }

    public int getDepartmentIdByName(String departmentName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT d_id FROM department WHERE d_name=?", new String[]{departmentName});
        int id = -1;
        if (cursor.moveToFirst()) {
            id = cursor.getInt(0);
        }
        cursor.close();
        return id;
    }


}


