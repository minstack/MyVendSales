package ca.smyoon.myvendsales;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class GetVendInfoActivity extends AppCompatActivity {

    ProgressBar vendSalesLoadBar;
    Button btnGetSales;
    EditText token, domain, dateFrom, dateTo;
    VendInfoRetriever vendApi;
    Intent mainIntent;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_vend_info);

        initVars();
        populateStoredInfo();
        initCalendars();

        this.mainIntent = new Intent(GetVendInfoActivity.this, ListVendSalesTotal.class);

        //having the state change here before the onClick is even fired
        //solves the delay of the progress bar showing up on UI after click
        btnGetSales.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                enableListVendSalesMode();

                return false;
            }
        });
    }

    private void populateStoredInfo() {
        Context tempContext = getApplicationContext();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(tempContext);

        String domain = sp.getString("domain", null);
        String token = sp.getString("token", null);

        if (domain != null) {
            this.domain.setText(domain);
        }

        if (token != null) {
            this.token.setText(token);
        }
    }

    private void initVars() {
        vendSalesLoadBar = findViewById(R.id.loadVendSalesBar);
        btnGetSales = findViewById(R.id.btnGetSalesTotal);
        token = findViewById(R.id.token);
        domain = findViewById(R.id.vendDomain);
        dateFrom = findViewById(R.id.dateFrom);
        dateTo = findViewById(R.id.dateTo);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initCalendars() {
        setCalenderToEditText(this.dateFrom);
        setCalenderToEditText(this.dateTo);
        disableTextBox(this.dateFrom);
        disableTextBox(this.dateTo);
    }

    private void disableTextBox(EditText tbox) {
        tbox.setFocusable(false);
        tbox.setLongClickable(false);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setCalenderToEditText(final EditText textbox) {
        final Calendar myCalendar = Calendar.getInstance();

        final String myFormat = "yyyy-MM-dd"; //set date format
        final SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);


                textbox.setText(sdf.format(myCalendar.getTime()));
            }

        };

        textbox.setText(sdf.format(new Date()));

        textbox.setText(sdf.format(myCalendar.getTime()));

        textbox.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                new DatePickerDialog(GetVendInfoActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        enableGetInfoMode();
    }

    @Override
    public void onPause() {
        super.onPause();
        enableListVendSalesMode();
    }

    @Override
    public void onResume() {
        super.onResume();
        enableGetInfoMode();
    }

   /* public void onClickMyVendSales(View v) {
        String errorMsg = "";

       // try {

         //   if (!validDates()) {
          //      errorMsg = "Date from must be same or before date to.";
          //  } else {
                //enableListVendSalesMode();

                try {

                    @SuppressLint("StaticFieldLeak") ArrayList<VendOutlet> outlets =  (ArrayList<VendOutlet>) new AsyncTask<Void, Void, Object[]>() {
                        @Override
                        protected void onPreExecute() {
                            enableListVendSalesMode();
                        }

                        @Override
                        protected Object[] doInBackground(Void... voids) {
                            return new Object[]{getVendInfo(), getVendSales()};
                        }

                        @Override
                        protected void onPostExecute(Object[] result) {
                            //outlets
                            if (result[0] == null) {
                                return;
                            }

                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mainIntent.putExtra("outletsList", getOutletsJsonString((ArrayList<VendOutlet>)result[0]));

                            mainIntent.putExtra("salesList", getSaleJsonString((ArrayList<VendSale>)result[1]));

                            startActivity(mainIntent);

                            enableGetInfoMode();
                        }

                    }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null).get()[0];

                    if (outlets == null) {
                        throw new Exception("Please check your network, domain and token.");
                    }

                    setIntentExtras();
                    saveUserInput(this.domain.getText().toString(), this.token.getText().toString());



                } catch (IllegalArgumentException e) {
                    errorMsg = e.getLocalizedMessage();
                } catch (IOException e) {
                    errorMsg = "Please check your network connection.";
                } catch (Exception e) {
                    errorMsg = e.getLocalizedMessage();
                }

      // // } catch (ParseException e) {
      //      errorMsg = "Something went terribly wrong, please contact support.";
  //
      //  }



        if (!errorMsg.isEmpty()) {
            Toast.makeText(GetVendInfoActivity.this, errorMsg, Toast.LENGTH_LONG).show();
            enableGetInfoMode();
        }



    }*/

    /*public void onClickMyVendSales(View v) {
        String errorMsg = "";

        // try {

        //   if (!validDates()) {
        //      errorMsg = "Date from must be same or before date to.";
        //  } else {
        //enableListVendSalesMode();

        try {
            if (getVendInfo() == null) {
                throw new Exception("Please check your network, domain and token.");
            }

            setIntentExtras();
            getVendSales();

            saveUserInput(this.domain.getText().toString(), this.token.getText().toString());
        } catch (IllegalArgumentException e) {
            errorMsg = e.getLocalizedMessage();
        } catch (IOException e) {
            errorMsg = "Please check your network connection.";
        } catch (Exception e) {
            errorMsg = e.getLocalizedMessage();
        }
        //}
        //} catch (ParseException e) {
        //     errorMsg = "Something went terribly wrong, please contact support.";
        // }

        if (!errorMsg.isEmpty()) {
            Toast.makeText(GetVendInfoActivity.this, errorMsg, Toast.LENGTH_LONG).show();
            enableGetInfoMode();
        }



        enableGetInfoMode();
    }*/

    public void onClickMyVendSales(View v) {
        String errorMsg = "";

        //enableListVendSalesMode();
        // try {

        //   if (!validDates()) {
        //      errorMsg = "Date from must be same or before date to.";
        //  } else {
        //enableListVendSalesMode();

        try {
            Exception retreiveError = getVendInfo();

            if (retreiveError != null) {
                throw retreiveError;
            }

            setIntentExtras();
            //getVendSales();

            saveUserInput(this.domain.getText().toString(), this.token.getText().toString());
        } catch (IllegalArgumentException e) {
            errorMsg = e.getLocalizedMessage();
        } catch (IOException e) {
            errorMsg = "Please check your network connection.";
        } catch (Exception e) {
            errorMsg = e.getLocalizedMessage();
        }
        //}
        //} catch (ParseException e) {
        //     errorMsg = "Something went terribly wrong, please contact support.";
        // }

        if (!errorMsg.isEmpty()) {
            Toast.makeText(GetVendInfoActivity.this, errorMsg, Toast.LENGTH_LONG).show();
            enableGetInfoMode();
        }


        // enableGetInfoMode();
    }

    //not sure
    private boolean validDates() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dateFrom = this.dateFrom.getText().toString();
        String dateTo = this.dateFrom.getText().toString();

        return sdf.parse(dateFrom).compareTo(sdf.parse(dateTo)) <= 0;
    }

    private void setIntentExtras() {
        setDates();
        setDomain(this.domain.getText().toString());
    }

    private void setDates() {
        String dateFrom = this.dateFrom.getText().toString();
        String dateTo = this.dateTo.getText().toString();
        this.vendApi.setSaleDates(dateFrom, dateTo);

        mainIntent.putExtra("dateFrom", dateFrom);
        mainIntent.putExtra("dateTo", dateTo);

    }

    private void saveUserInput(String domain, String token) {
        Context tempContext = getApplicationContext();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(tempContext);

        sp.edit().putString("domain", domain).apply();
        sp.edit().putString("token", token).apply();

    }

    private void enableListVendSalesMode() {
        vendSalesLoadBar.setVisibility(View.VISIBLE);
        btnGetSales.setVisibility(View.INVISIBLE);
        setEditTextFocusable(false);
    }

    private void enableGetInfoMode() {
        vendSalesLoadBar.setVisibility(View.INVISIBLE);
        btnGetSales.setVisibility(View.VISIBLE);
        enableEditable(true);
    }

    public void setEditTextFocusable(boolean focusable) {
        token.setFocusable(focusable);
        domain.setFocusable(focusable);
    }

    public void enableEditable(boolean focusable) {
        token.setFocusableInTouchMode(focusable);
        domain.setFocusableInTouchMode(focusable);
    }

/*    public ArrayList<VendOutlet> getVendInfo() {
        this.vendApi = new VendInfoRetriever(this.token.getText().toString()
                , this.domain.getText().toString());

        try {
            return this.vendApi.getOutlets();
        } catch (Exception e) {
            //showToast(e.getLocalizedMessage());
        }

        return null;
    }*/

    /*public ArrayList<VendOutlet> getVendInfo() throws Exception {
        this.vendApi = new VendInfoRetriever(this.token.getText().toString()
                , this.domain.getText().toString());

        return new RetrieveVendOutletsTask().execute(this.vendApi).get();
    }*/

    public Exception getVendInfo() throws Exception {
        this.vendApi = new VendInfoRetriever(this.token.getText().toString()
                , this.domain.getText().toString());

        RetrieveVendOutletsTask otask = new RetrieveVendOutletsTask();

        otask.execute(this.mainIntent, this.vendApi);

        return otask.getException();
    }


    private String getOutletsJsonString(ArrayList<VendOutlet> outlets) {
        return new Gson().toJson(outlets);
    }

    private String getSaleJsonString(ArrayList<VendSale> sales) {
        return new Gson().toJson(sales);
    }

/*    public ArrayList<VendSale> getVendSales() {
        try {
            return this.vendApi.getSales();
        }
        catch(Exception e) {
            //showToast(e.getLocalizedMessage());
        }

        return null;
    }*/

    private void showToast(String localizedMessage) {
        Toast.makeText(GetVendInfoActivity.this, localizedMessage, Toast.LENGTH_LONG).show();
    }

    public ArrayList<VendSale> getVendSales() throws Exception {
        return new RetreiveVendSalesTask().execute(this.vendApi).get();
    }

    public void setDomain(String domain) {
        mainIntent.putExtra("domain", domain);
    }

/*    private class RetrieveVendOutletsTask extends AsyncTask<VendInfoRetriever, Void, ArrayList<VendOutlet>> {

        @Override
        protected  void onPreExecute() {
            enableListVendSalesMode();
        }

        @Override
        protected ArrayList<VendOutlet> doInBackground(VendInfoRetriever... apiCall) {
            try {
                return apiCall[0].getOutlets();
            } catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(ArrayList<VendOutlet> outlets) {
            if (outlets == null) {
                return;
            }

            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            mainIntent.putExtra("outletsList", getOutletsJsonString(outlets));

            //i.putExtra("domain", this.domain.getText().toString());

            //startActivity(i);
        }

    }*/

 /*   private class RetreiveVendSalesTask extends AsyncTask<VendInfoRetriever, Void, ArrayList<VendSale>> {


        @Override
        protected ArrayList<VendSale> doInBackground(VendInfoRetriever... apiCall) {
            try {
                return apiCall[0].getSales();
            } catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(ArrayList<VendSale> sales) {
            if (sales == null) {
                return;
            }

            mainIntent.putExtra("salesList", getSaleJsonString(sales));

            //i.putExtra("domain", this.domain.getText().toString());

            startActivity(mainIntent);

            enableGetInfoMode();
        }

    }*/

    private class RetrieveVendOutletsTask extends AsyncTask<Object, Void, Boolean> {

        private Exception error;
        private String outletsJson;
        private String salesJson;
        private Intent mainIntent;
        private VendInfoRetriever vinfo;

        public Exception getException() {
            return error;
        }

        @Override
        protected void onPreExecute() {
            //enableListVendSalesMode();
            vendSalesLoadBar.setVisibility(View.VISIBLE);
            btnGetSales.setVisibility(View.INVISIBLE);
            setEditTextFocusable(false);
        }

        @Override
        protected Boolean doInBackground(Object... objs) {
            Boolean successful = false;
            this.mainIntent = (Intent) objs[0];
            this.vinfo = (VendInfoRetriever) objs[1];

            try {
                ArrayList<VendOutlet> vendOutlets = vinfo.getOutlets();

                if (vendOutlets == null) {
                    throw new Exception("Invalid token or store domain.");
                }


                this.outletsJson = getOutletsJsonString(vinfo.getOutlets());
                this.salesJson = getSaleJsonString(vinfo.getSales());
            } catch (Exception e) {
                this.error = new Exception("Invalid token or store domain.");
            }

            return this.error == null;
        }

        protected void onPostExecute(Boolean successful) {
            if (!successful) {
                Toast.makeText(GetVendInfoActivity.this, this.error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                enableGetInfoMode();
                return;
            }

//            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            mainIntent.putExtra("outletsList", this.outletsJson);
            mainIntent.putExtra("salesList", this.salesJson);

            //i.putExtra("domain", this.domain.getText().toString());

            startActivity(this.mainIntent);
            enableGetInfoMode();
        }

    }

   /* private class RetrieveVendOutletsTask extends AsyncTask<VendInfoRetriever, Void, ArrayList<VendOutlet>> {

        @Override
        protected  void onPreExecute() {
            enableListVendSalesMode();
        }

        @Override
        protected ArrayList<VendOutlet> doInBackground(VendInfoRetriever... apiCall) {
            try {
                return apiCall[0].getOutlets();
            } catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(ArrayList<VendOutlet> outlets) {
            if (outlets == null) {
                return;
            }

            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            mainIntent.putExtra("outletsList", getOutletsJsonString(outlets));

            //i.putExtra("domain", this.domain.getText().toString());

            //startActivity(i);
        }

    }*/

    private class RetreiveVendSalesTask extends AsyncTask<VendInfoRetriever, Void, ArrayList<VendSale>> {


        @Override
        protected ArrayList<VendSale> doInBackground(VendInfoRetriever... apiCall) {
            try {
                return apiCall[0].getSales();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<VendSale> sales) {
           /* if (sales == null) {
                return;
            }
*/
            mainIntent.putExtra("salesList", getSaleJsonString(sales));

            //i.putExtra("domain", this.domain.getText().toString());

            startActivity(mainIntent);


        }

    }
}
