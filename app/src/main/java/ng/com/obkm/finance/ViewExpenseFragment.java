package ng.com.obkm.finance;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import lecho.lib.hellocharts.model.ComboLineColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;

import android.widget.Button;
import static ng.com.obkm.finance.R.id.chart;
import ng.com.obkm.finance.CostBean;

public class ViewExpenseFragment extends Fragment {

    public Button LineChartbtn;
    private LineChartView mChart;
    private Map<String,Integer>table = new TreeMap<>();
    private LineChartData mData;
    private ComboLineColumnChartData mchart;


    public ViewExpenseFragment() {

    }

    //    private static final String TAG = "AddExpenseFragment";
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_expenses, container, false);

        LineChartView mChart = view.findViewById(chart);
        List<CostBean> allDate = (List<CostBean>) getActivity().getIntent().getSerializableExtra("cost_list");
        generateValues(allDate);
//        generateData();
        LineChartbtn = getActivity().findViewById(R.id.LineChartBtn);
//        LineChartbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                View viewLineChart = inflater.inflate(R.layout.chart_view, null);
//                builder.setView(viewLineChart);
//            }
//        });
        return view;
    }

    private void generateData() {
        List<Line> lines = new ArrayList<>();
        List<PointValue> values = new ArrayList<>();
        int indexX = 0;
        for(Integer value:table.values()){
            values.add(new PointValue(indexX, value));
            indexX++;
        }
        Line line = new Line(values);
        line.setColor(ChartUtils.COLORS[0]);
        line.setShape(ValueShape.CIRCLE);
        line.setPointColor(ChartUtils.COLORS[1]);
        lines.add(line);
        mData = new LineChartData();
        mData.setLines(lines);
        mChart.setLineChartData(mData);


    }
    private void generateValues(List<CostBean> allDate) {
        if(allDate != null){
            for(int i =0; i< allDate.size();i++){
                CostBean costBean = allDate.get(i);
                String costDate = costBean.costDate;
                int costMoney  = Integer.parseInt(costBean.costMoney);
                if (!table.containsKey(costDate)){
                    table.put(costDate, costMoney);

                }
                else{
                    int originMoney = table.get(costDate);
                    table.put(costDate,originMoney+costMoney);
                }
            }
        }
    }
}