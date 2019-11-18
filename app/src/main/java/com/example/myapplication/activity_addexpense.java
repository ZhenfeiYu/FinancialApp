package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class activity_addexpense extends AppCompatActivity {

//    private int type = 0;
//    final static int EDIT_MODE = 2;

    private List<CostBean> mCostBeanList;
    private DatabaseHelper mDatabaseHelper;
    private CostListAdapter mAdapter;

//    private static String[] category={""};
//
//    private TextView txtcategory_view;
//    private Spinner category_spinner;
//    private ArrayAdapter<String> category_adapter;
//
//    private String txtcategory = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addexpense);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDatabaseHelper = new DatabaseHelper(this);
        mCostBeanList = new ArrayList<>();
        ListView costList = findViewById(R.id.lv_main);
        initCostData();
        mAdapter = new CostListAdapter(this, mCostBeanList);
        costList.setAdapter(mAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(activity_addexpense.this);
                LayoutInflater inflate = LayoutInflater.from(activity_addexpense.this);
                View viewDialog = inflate.inflate(R.layout.new_cost_data, null);
                //final EditText title = (EditText)viewDialog.findViewById(R.id.et_cost_title);
                final Spinner category = (Spinner)viewDialog.findViewById(R.id.spinner);
                final EditText money = (EditText)viewDialog.findViewById(R.id.et_cost_money);
                final DatePicker date = (DatePicker) viewDialog.findViewById(R.id.dp_cost_date);


                builder.setView(viewDialog);
                builder.setTitle("New Cost");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CostBean costBean = new CostBean();
                        //costBean.costTitle = title.getText().toString();
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
    }

    private void initCostData() {

        Cursor cursor = mDatabaseHelper.getAllCostData();
        if(cursor!=null){
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



        if (id == R.id.action_charts) {
            Intent intent = new Intent(activity_addexpense.this,ChartsActivity.class);
            intent.putExtra("cost_list",(Serializable) mCostBeanList);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




}