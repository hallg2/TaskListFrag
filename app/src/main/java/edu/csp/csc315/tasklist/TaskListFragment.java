package edu.csp.csc315.tasklist;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskListFragment extends Fragment {

    private ArrayList<Task> tasks = new ArrayList<>();
    private TodoListAdapter adapter = null;
    private DatabaseHelper dbHelper = null;
    private ListView taskListView;
    private OnItemSelectedListener mListener;

    public TaskListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);

        DateFormat dateFormat = new SimpleDateFormat("mm/dd/yy");
        dbHelper = new DatabaseHelper(getActivity().getApplicationContext());
        tasks = dbHelper.getAll();
        adapter = new TodoListAdapter(getActivity().getApplicationContext(), tasks);
        taskListView = view.findViewById(R.id.taskListView);
        taskListView.setAdapter(adapter);
        taskListView.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Task t = tasks.get(position);
                mListener.onItemSelected(t);
                System.out.println("Executed!");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        adapter.notifyDataSetChanged();
        return view;
    }

    public void refreshList() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnItemSelectedListener){
            mListener = (OnItemSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnItemSelectedListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnItemSelectedListener {
        public void onItemSelected(Task task);
    }

}