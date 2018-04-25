package edu.csp.csc315.tasklist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper{

    private final static String DBNAME = "tasks";
    private final static int DBVERSION = 1;

    public DatabaseHelper (Context context) {
        super (context, DBNAME, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE task_table " +
                "(id INTEGER primary key autoincrement, taskname TXT, duedate TXT)");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tasks");
        onCreate(db);
    }

    public int addTask(Task task) {
        ContentValues values = new ContentValues();
        DateFormat dateFormat = new SimpleDateFormat("mm/dd/yy");
        Date date = task.getDueDate();
        String dt = new String(date.toString());
        values.put("taskname", task.getDescription());
        values.put("duedate", dt);

        SQLiteDatabase db = this.getWritableDatabase();
        long id = db.insert("task_table", null, values);
        db.close();
        return (int) id;
    }

    public void removeTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        Integer id = task.getId();
        String i = new String(id.toString());
        db.delete("task_table", "id = ?", new String[] {i});
        db.close();
    }

    public void updateTask(Task task) {

        ContentValues values = new ContentValues();
        Date date = task.getDueDate();
        String dt = new String(date.toString());
        Integer id = task.getId();
        String i = new String(id.toString());
        values.put("taskname", task.getDescription());
        values.put("duedate", dt);

        SQLiteDatabase db = this.getWritableDatabase();
        db.update("task_table", values, "id = ?", new String[] {i});
        db.close();
    }

    public ArrayList<Task> getAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Task> tasks = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM task_table", null);
        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {
            Integer n = cursor.getInt(0);
            String t = cursor.getString(1);
            String d = cursor.getString(2);
            Date dt = new Date(d);
            Task newTask = new Task(n, t, dt);
            tasks.add(newTask);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return tasks;
    }
}
