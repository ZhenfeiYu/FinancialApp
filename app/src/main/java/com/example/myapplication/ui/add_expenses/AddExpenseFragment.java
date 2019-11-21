package com.example.myapplication.ui.add_expenses;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.CostBean;
import com.example.myapplication.CostListAdapter;
import com.example.myapplication.DatabaseHelper;
import com.example.myapplication.R;

import java.util.List;

public class AddExpenseFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_add_expenses, container, false);


        return root;
    }
}