package edu.csp.csc315.tasklist;

/*
 *      ToDoList.java - Week 10 Assignment           SQLite Database, ListView
 *      CSC 315 X0:     Mobile App Development      Spring 2018
 *      Created By:     Gary A. Hall Jr.            hallg2@csp.edu
 *      Written:        03 31 2018
 *      Revised On:     04 03 2018
 */


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import edu.csp.csc315.tasklist.TaskListFragment.OnItemSelectedListener;

public class MainActivity extends AppCompatActivity implements OnItemSelectedListener {

    private ArrayList<Task> tasks = new ArrayList<>();
    private ListView listView = null;
    private ListView taskListView = null;
    private TodoListAdapter adapter = null;
    private DatabaseHelper dbHelper = null;
    private FragmentManager fm;
    private FragmentTransaction transaction;
    private TaskListFragment firstFragment;
    private ControlFragment secondFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DateFormat dateFormat = new SimpleDateFormat("mm/dd/yy");
        dbHelper = new DatabaseHelper(this);
        tasks = dbHelper.getAll();
        adapter = new TodoListAdapter(this, tasks);
        //listView = findViewById(R.id.listView);
        //listView.setAdapter(adapter);

        firstFragment = new TaskListFragment();
        secondFragment = new ControlFragment();

        //firstFragment.setArguments(getIntent().getExtras());
        fm = getSupportFragmentManager();
        transaction = fm.beginTransaction();
        transaction.add(R.id.contentLayout, firstFragment, "tagList");
        transaction.commit();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayDialog();
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void repFrag(Fragment fragment) {
        fm = getSupportFragmentManager();
        transaction = fm.beginTransaction();
        transaction.replace(R.id.contentLayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        getSupportFragmentManager().executePendingTransactions();
    }

    //public void onItemSelected(Task task) {
    //    repFrag(secondFragment);
    //}

   // public void onItemUpdated(Task task) { repFrag(firstFragment); }

    @Override
    protected void onResume() {
        super.onResume();

        DateFormat dateFormat = new SimpleDateFormat("mm/dd/yy");
        dbHelper = new DatabaseHelper(this);
        tasks = dbHelper.getAll();
        adapter = new TodoListAdapter(this, tasks);
        adapter.notifyDataSetChanged();


    }

    private void displayDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.newtask_dialog, null);

        final EditText desc = (EditText) view.findViewById(R.id.dialog_description);
        final EditText dueDate = (EditText) view.findViewById(R.id.dialog_duedate);
        Button saveButton = (Button) view.findViewById(R.id.saveButton);
        Button cancelButton = (Button) view.findViewById(R.id.cancelButton);
        final TextView errorView = (TextView) view.findViewById(R.id.error_textview);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.setTitle("New Task");
        dialog.show();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {


                    Integer id = new Integer(0);
                    DateFormat dateFormat = new SimpleDateFormat("mm/dd/yy");
                    Task task = new Task(id, desc.getText().toString(),
                            dateFormat.parse(dueDate.getText().toString()));

                    dbHelper.addTask(task);
                    adapter.notifyDataSetChanged();

                    //getSupportFragmentManager().findFragmentByTag(tagList).onAddTask();
                    dialog.dismiss();

                } catch(Exception e) {
                    errorView.setText(dueDate.getText().toString() + " is not a valid date (format = mm/dd/yy");
                }

                DateFormat dateFormat = new SimpleDateFormat("mm/dd/yy");
                dbHelper = new DatabaseHelper(getApplicationContext());
                tasks = dbHelper.getAll();
                adapter = new TodoListAdapter(getApplicationContext(), tasks);
                taskListView = findViewById(R.id.taskListView);
                taskListView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private String getName(String name) {

        return name;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(Task task) {

        getSupportFragmentManager().beginTransaction().replace(R.id.contentLayout, new ControlFragment(), "modFrag").commit();
    }

}
