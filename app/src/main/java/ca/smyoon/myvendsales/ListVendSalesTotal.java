package ca.smyoon.myvendsales;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class ListVendSalesTotal extends AppCompatActivity {

    private String domain;
    private TextView header;
    private Intent mainIntent;
    private ArrayList<VendOutlet> outlets;
    private ArrayList<VendSale> sales;
    private HashMap<String, VendOutlet> idToOutlet = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_vend_sales_total);

        this.mainIntent = getIntent();

        this.header = findViewById(R.id.dateRange);
        //this.header.setText(mainIntent.getStringExtra("domain"));

        Type outletsListType = new TypeToken<List<VendOutlet>>() {}.getType();
        this.outlets = new Gson().fromJson(mainIntent.getStringExtra("outletsList"),outletsListType);

        Type salesListType = new TypeToken<List<VendSale>>() {}.getType();
        this.sales = new Gson().fromJson(mainIntent.getStringExtra("salesList"), salesListType);

        initIdToOutletMap();
    }

    private void initIdToOutletMap() {
        for (VendOutlet outlet : this.outlets) {
            this.idToOutlet.put(outlet.getId(), outlet);
        }
    }

    @Override
    protected void onStart() {

        super.onStart();
        calculateTotals();
        sortOutletsBySalesDesc();
        listOutlets();
        setHeaders();
    }

    private void sortOutletsBySalesDesc() {


        Collections.sort(this.outlets, new Comparator<VendOutlet>() {
            @Override
            public int compare(VendOutlet o1, VendOutlet o2) {
                //descending order just for the use of displaying in listview
                return -Double.compare(o1.getTotalSales(), o2.getTotalSales());
            }
        });
    }

    private void setHeaders() {
        final ActionBar actionBar = getSupportActionBar();
        String dateRange = mainIntent.getStringExtra("dateFrom") + " to "
                + mainIntent.getStringExtra("dateTo");
        this.domain = mainIntent.getStringExtra("domain");


        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        TextView title = new TextView(actionBar.getThemedContext());
        title.setText(this.domain.toUpperCase() + " Sales");

        title.setTextColor(Color.WHITE);
        title.setTextSize(20);
        title.setTypeface(null,Typeface.BOLD);
        actionBar.setCustomView(title);

        this.header.setText(dateRange);

        //this.header.setText(this.domain.toUpperCase() + " Sales: " + dateRange);
        //getActionBar().setTitle(this.domain.toUpperCase() + " Sales: " + dateRange);

    }

    private void calculateTotals() {

        for (VendSale sale : this.sales) {
            VendOutlet currOutlet = this.idToOutlet.get(sale.getOutlet_id());

            if (sale.getStatus().contains("VOIDED")) {
                continue;
            }
            currOutlet.addSaleTotal(sale.getTotal_price());
            currOutlet.incrementNumSales();
        }

    }

    private void listOutlets() {
        VendOutletsAdapter voa = new VendOutletsAdapter(ListVendSalesTotal.this, this.outlets);
        ((ListView)findViewById(R.id.lstVendOutlets)).setAdapter(voa);
    }


}
