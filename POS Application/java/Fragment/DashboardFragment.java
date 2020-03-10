package com.example.project.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ReportFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.project.Activity.MainActivity;
import com.example.project.Adapter.MonthlyProfitAndLossStatisticAdapter;
import com.example.project.Model.ListMonthlyProfitAndLoss;
import com.example.project.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    List<ListMonthlyProfitAndLoss>
            listMonthlyProfitAndLosses;

    LineGraphSeries<DataPoint> series;

    LinearLayout
            btnPOS,
            btnSales,
            btnReports,
            btnOutlets,
            btnUsers,
            btnSettings;

    GraphView
            graphView;

    public DashboardFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity) getContext()).setMyTitle("Dashboard");

        btnPOS = getView().findViewById(R.id.btn_pos);
        btnSales = getView().findViewById(R.id.btn_sales);
        btnReports = getView().findViewById(R.id.btn_reports);
        btnOutlets = getView().findViewById(R.id.btn_outlets);
        btnUsers = getView().findViewById(R.id.btn_users);
        btnSettings = getView().findViewById(R.id.btn_settings);

        graphView = getView().findViewById(R.id.graph);

        listMonthlyProfitAndLosses = new ArrayList<>();

        graphView.getViewport().setMinX(0);
        graphView.getViewport().setMaxX(31);

        ((MainActivity) getContext()).getMysql("Data Profit And Loss Statistic", null, null, null, null,
                null, null, null, null, null, null, null, listMonthlyProfitAndLosses,
                null, null, null, null, null, null, null);

        btnPOS.setOnClickListener(goToPos);
        btnSales.setOnClickListener(goToSales);
        btnReports.setOnClickListener(goToReports);
        btnOutlets.setOnClickListener(goToOutlets);
        btnUsers.setOnClickListener(goToUsers);
        btnSettings.setOnClickListener(goToSettings);
        graphView.setOnClickListener(refreshStatistic);
    }

    private View.OnClickListener refreshStatistic = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            series = new LineGraphSeries<DataPoint>(generateData());
            graphView.addSeries(series);
//            Toast.makeText(getActivity(), String.valueOf(listMonthlyProfitAndLosses.get(29).getStatisticDay()), Toast.LENGTH_LONG).show();
        }
    };

    private View.OnClickListener goToPos = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new PosListOutletsFragment()).addToBackStack("Fragment").commit();
        }
    };

    private View.OnClickListener goToSales = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new SalesFragment()).addToBackStack("Fragment").commit();
        }
    };

    private View.OnClickListener goToReports = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new SalesReportsFragment()).addToBackStack("Fragment").commit();
        }
    };

    private View.OnClickListener goToOutlets = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new OutletsSettingFragment()).addToBackStack("Fragment").commit();
        }
    };

    private View.OnClickListener goToUsers = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new UsersSettingFragment()).addToBackStack("Fragment").commit();
        }
    };

    private View.OnClickListener goToSettings = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new SystemSettingFragment()).addToBackStack("Fragment").commit();
        }
    };

    private DataPoint[] generateData() {
        int size = listMonthlyProfitAndLosses.size();

        double x, y;
        DataPoint[] values = new DataPoint[size];
        for (int i = 0; i < size; i++) {
            if (listMonthlyProfitAndLosses.get(i).getStatisticProfit() == null) {
                x = 0;
                y = 0;
            } else {
                x = i;
                y = Double.valueOf(listMonthlyProfitAndLosses.get(29).getStatisticProfit());
            }
            DataPoint v = new DataPoint(x, y);
            values[i] = v;
        }
        return values;
    }
}
