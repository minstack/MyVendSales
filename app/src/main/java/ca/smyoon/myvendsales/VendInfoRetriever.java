package ca.smyoon.myvendsales;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;

/**
 * API 'controller' class where the basic Vend API endpoints to get store outlets, registers and
 * sales are handled. Retrieves JSON and either converts/maps to a POJO for convenient manipulation
 * of data in Java.
 *
 * @author Sung Min Yoon
 * @see VendOutlet
 * @see VendRegister
 * @see VendSale
 */
public class VendInfoRetriever {

    private final String VEND_API = "https://%s.vendhq.com/api/";
    private final String LIST_OUTLETS = "2.0/outlets";
    private final String LIST_REGISTERS = "2.0/registers";
    private final String SEARCH_SALES = "2.0/search?type=sales&date_from=%sT00:00:00Z&date_to=%sT23:59:59Z";

    private String storeApi = null;
    private String storeOutlets = null;
    private String storeRegisters = null;
    private String storeToken = null;
    private String storeSales = null;

    /**
     * The only constructor where by default, will require the token and store domain and initialize
     * all necessary API endpoint addresses.  Tokens and store domains must not be empty or null,
     * otherwise an exception will be thrown.
     *
     * @param token the personal token for the retailer to access api
     * @param vendDomain the retailer domain prefix
     */
    public VendInfoRetriever(String token, String vendDomain) throws IllegalArgumentException {

        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid Personal Token: Must not be empty.");
        }

        if (vendDomain == null || vendDomain.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid Store Domain: Must not be empty.");
        }

        this.storeToken = token.trim();

        initVars(vendDomain);

    }

    @SuppressWarnings("unchecked")
    public ArrayList<VendOutlet> getOutlets() throws Exception {

        return (ArrayList<VendOutlet>) (ArrayList<?>)
                this.getObjectListFromJson(
                        this.getOutletsJson(), VendOutlet.class);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<VendRegister> getRegisters() throws Exception {

        return (ArrayList<VendRegister>) (ArrayList<?>)
                this.getObjectListFromJson(
                        this.getRegistersJson(), VendRegister.class);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<VendSale> getSales() throws Exception {

        return (ArrayList<VendSale>) (ArrayList<?>)
                this.getObjectListFromJson(
                        this.getSalesJson(), VendSale.class);
    }

    public String getSalesJsonString() throws Exception {
        return getJson(this.storeSales);
    }

    public String getOutletsJsonString() throws Exception {
        return getJson(this.storeOutlets);
    }

    public String getRegisterJsonString() throws Exception {
        return getJson(this.storeRegisters);
    }

    private String getSalesJson() throws Exception {
        return getJson(this.storeSales);
    }

    private String getOutletsJson() throws Exception {
        return getJson(this.storeOutlets);
    }

    private String getRegistersJson() throws Exception {
        return getJson(this.storeRegisters);
    }

    private ArrayList<Object> getObjectListFromJson(String jsonString, Class classType) throws Exception {

        ArrayList<Object> tempList = new ArrayList<>();


        JsonParser parser = new JsonParser();
//        JsonObject o = parser.parse(jsonString).getAsJsonObject();
        // Log.d("json", jsonString);

        if (jsonString.contains("error")) {
            throw new Exception("Error Retrieving Data: " + parser.parse(jsonString).getAsJsonObject().get("error").getAsString());
        }

        JsonArray data = parser.parse(jsonString).getAsJsonObject().get("data").getAsJsonArray();

        for (JsonElement elem : data) {

            tempList.add(new Gson().fromJson(elem.toString(), classType));

        }


        return tempList;

    }

    private String getJson(String apiGet) throws Exception {
        try {
            URL myURL = new URL(apiGet);
            HttpURLConnection myURLConnection = (HttpURLConnection) myURL.openConnection();

            String bearer = "Bearer " + this.storeToken;
            //String bearer = "Bearer " + Base64.getEncoder().encodeToString(this.storeToken.getBytes());


            myURLConnection.setRequestProperty("Authorization", bearer);
            myURLConnection.setRequestMethod("GET");
            myURLConnection.setRequestProperty("Content-Type", "application/json");

            myURLConnection.connect();

            int responseCode = myURLConnection.getResponseCode();

            switch (responseCode) {
                case 200: {
                    // everything is fine, handle the response
                    InputStreamReader is = new InputStreamReader(myURLConnection.getInputStream());
                    BufferedReader br = new BufferedReader(is);

                    StringBuilder strResponse = new StringBuilder();
                    String response = null;

                    while ((response = br.readLine()) != null) {
                        strResponse.append(response + "\n");
                    }

                    br.close();

                    String strResponseJson = strResponse.toString();

                    // if (strResponseJson.contains("error")){
                    //error msg
                    //    return null;
                    //}
                    return strResponse.toString();
                }
                case 500: {
                    // server problems ?
                    Log.d("Response:", "500");
                    break;
                }
                case 403: {
                    // you have no authorization to access that resource
                    Log.d("Response:", "403");
                    break;
                }

            }

            myURLConnection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return null;
    }


    private void initVars(String vendDomain) {
        this.storeApi = String.format(this.VEND_API, vendDomain);
        this.storeOutlets = this.storeApi + this.LIST_OUTLETS;
        this.storeRegisters = this.storeApi + this.LIST_REGISTERS;
        this.storeSales = this.storeApi + this.SEARCH_SALES;
    }

    public void setSaleDates(String date_from, String date_to) {
        this.storeSales = String.format(this.storeSales, date_from, date_to);
    }


}
