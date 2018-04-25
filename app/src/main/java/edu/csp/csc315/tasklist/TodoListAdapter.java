package edu.csp.csc315.tasklist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TodoListAdapter extends ArrayAdapter {

    private Context context;
    private ArrayList<Task> data;
    private DatabaseHelper dbHelper;

    public TodoListAdapter(@NonNull Context context, @NonNull ArrayList<Task> objects) {
        super(context, 0, objects);
        this.context = context;
        this.data = objects;
        this.dbHelper = new DatabaseHelper(context.getApplicationContext());
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.task_layout, parent, false);

        TextView descView = view.findViewById(R.id.description);
        TextView dueDate = view.findViewById(R.id.duedate);
        Button doneButton = view.findViewById(R.id.doneButton);

        Task task = data.get(position);         // get data in task for display
        Integer i = new Integer(task.getId());  // set column 'id' as integer 'i'
        final String in = new String(i.toString()); // cast 'i' as string 'in'
        descView.setText(task.getDescription());
        DateFormat dateformat = new SimpleDateFormat("mm/dd/yy");
        dueDate.setText(dateformat.format(task.getDueDate()));

        doneButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                dbHelper = new DatabaseHelper(context.getApplicationContext());
                Task task = data.get(position);
                data.remove(position);
                dbHelper.removeTask(task);
                TodoListAdapter.this.notifyDataSetChanged();
            }
        });

        return view;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
}
