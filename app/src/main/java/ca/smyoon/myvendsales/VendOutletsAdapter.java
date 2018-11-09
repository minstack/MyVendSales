package ca.smyoon.myvendsales;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Customer adapter to be used to populate outlet and basic sales number.
 *
 * @author Sung Min Yoon
 * @see VendOutlet
 */
public class VendOutletsAdapter extends BaseAdapter {

    private ArrayList<VendOutlet> outlets = new ArrayList<>();
    private Context context;

    public VendOutletsAdapter(Context context, ArrayList<VendOutlet> outlets) {
        this.outlets = outlets;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.outlets.size();
    }

    @Override
    public Object getItem(int position) {
        return this.outlets.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.listview_item, parent, false);
        }

        VendOutlet tempOut = (VendOutlet) this.getItem(position);

        TextView txtOutName = convertView.findViewById(R.id.txtOutletName);
        TextView txtTotal = convertView.findViewById(R.id.txtTotalSales);
        TextView txtNumSales = convertView.findViewById(R.id.txtNumSales);

        String totalAmount = "%s%.2f(%s)";

        txtOutName.setText(tempOut.getName());
        txtTotal.setText(String.format(totalAmount, tempOut.getCurrency_symbol(), tempOut.getTotalSales(), tempOut.getCurrency()));
        txtNumSales.setText(String.format("Total sales: %s", tempOut.getNumSales()));


        return convertView;
    }
}
