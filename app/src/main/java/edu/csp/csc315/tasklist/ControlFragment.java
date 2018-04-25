package edu.csp.csc315.tasklist;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ControlFragment extends Fragment implements AdapterView.OnItemSelectedListener{


    private TaskListFragment.OnItemSelectedListener mListener;
    private ControlListener controListener;
    private Button updateButton;
    private EditText updateDescription;
    private EditText updateDuedate;

    private ArrayList<Task> tasks = new ArrayList<>();
    private ListView listView = null;
    private TodoListAdapter adapter = null;
    private DatabaseHelper dbHelper = null;
    private ListView taskListView;

    public ControlFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_control, container, false);

        updateButton = view.findViewById(R.id.updateButton);
        updateDescription = view.findViewById(R.id.updateDescription);
        updateDuedate = view.findViewById(R.id.updateDuedate);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskDesc = new String(updateDescription.getText().toString());
                controListener.onUpdate(updateDescription.getText().toString());
                //controListener.onUpdate(updateDuedate.getText().toString());
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            controListener = (ControlListener) context;
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public interface ControlListener {

        public void onUpdate(String content);

    }
}
