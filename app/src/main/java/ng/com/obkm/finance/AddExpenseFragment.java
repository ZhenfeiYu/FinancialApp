package ng.com.obkm.finance;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.app.AlertDialog;

import android.widget.TextView;
import androidx.annotation.NonNull;
//import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import ng.com.obkm.finance.CostBean;
import ng.com.obkm.finance.CostListAdapter;
import ng.com.obkm.finance.DatabaseHelper;
import ng.com.obkm.finance.MainActivity;
//import com.example.myapplication.DatabaseHelper;
//import com.example.myapplication.CostListAdapter;
//import com.example.myapplication.CostBean;
//import com.example.myapplication.MainActivity;
import ng.com.obkm.finance.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//import androidx.fragment.app.FragmentTransaction;
//import androidx.fragment.app.FragmentManager;
import java.util.ArrayList;
import java.util.List;

public class AddExpenseFragment extends Fragment {

    private List<CostBean> mCostBeanList;
    private DatabaseHelper mDatabaseHelper;
    private RecyclerView recyclerView;
    private CostListAdapter mAdapter;
    //private View rootView;

    //private val savedState = supportFragmentManager.saveFragmentInstanceState(fragment);
    public AddExpenseFragment() {

    }

    //    private static final String TAG = "AddExpenseFragment";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_expense, container, false);
//    public View onCreateView(@NonNull final LayoutInflater inflater,
//                             final ViewGroup container, Bundle savedInstanceState) {
//        if (rootView == null) {
//            rootView = inflater.inflate(R.layout.fragment_add_expenses, null);
//        } else {
//            ViewGroup parent = (ViewGroup) rootView.getParent();
//            if (parent != null) {
//                parent.removeView(rootView);
//            }
//
//        }
        setRetainInstance(true);
        //View view = inflater.inflate(R.layout.fragment_add_expenses, null);
        //View viewlist = inflater.inflate(R.layout.list_item, container, false);
        mDatabaseHelper = new DatabaseHelper(getActivity());
        mCostBeanList = new ArrayList<>();
        mDatabaseHelper = new DatabaseHelper(getActivity());
        mCostBeanList = new ArrayList<>();

        //View costlist = inflater.inflate(R.layout.fragment_add_expenses, container, false);
        initCostData();

        //View root = inflater.inflate(R.layout.fragment_add_expenses, container, false);
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                final LayoutInflater inflate = LayoutInflater.from(v.getContext());
                View viewDialog = inflate.inflate(R.layout.new_cost_data, null);
                final EditText title = (EditText) viewDialog.findViewById(R.id.et_cost_title);
                final Spinner category = (Spinner) viewDialog.findViewById(R.id.spinner);
                final EditText money = (EditText) viewDialog.findViewById(R.id.et_cost_money);
                final DatePicker date = (DatePicker) viewDialog.findViewById(R.id.dp_cost_date);

                ListView costList = getView().findViewById(R.id.lv_main);
                mAdapter = new CostListAdapter(getActivity(), mCostBeanList);
                costList.setAdapter(mAdapter);

                builder.setView(viewDialog);
                builder.setTitle("New Cost");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CostBean costBean = new CostBean();
                        costBean.costTitle = title.getText().toString();
                        costBean.costCategory = category.getSelectedItem().toString();
                        costBean.costMoney = money.getText().toString();
                        costBean.costDate = date.getYear() + "-" + (date.getMonth() + 1) + "-" +
                                date.getDayOfMonth();


                        mDatabaseHelper.insertCost(costBean);
                        mCostBeanList.add(costBean);
                        mAdapter.notifyDataSetChanged();

                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.create().show();
            }
        });
        return view;

    }


    private void initCostData() {

        Cursor cursor = mDatabaseHelper.getAllCostData();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                CostBean costBean = new CostBean();
                int dataColumnIndex = cursor.getColumnIndex("cost_title");
                costBean.costTitle = cursor.getString(dataColumnIndex + 0);
                costBean.costDate = cursor.getString(dataColumnIndex + 1);
                costBean.costMoney = cursor.getString(dataColumnIndex + 2);
                mCostBeanList.add(costBean);
            }
            cursor.close();
        }
    }
}
