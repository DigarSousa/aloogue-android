package alugueis.alugueis.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import java.util.ArrayList;

import alugueis.alugueis.ManageProductsAct;
import alugueis.alugueis.R;
import alugueis.alugueis.model.Product;
import alugueis.alugueis.view.EditProductAct;

public class ProductListAdapter extends ArrayAdapter<Product>{

    private ArrayList<Product> productList;
    private DialogInterface.OnClickListener dialogDelete;
    private Context context;
    private Activity fromActivity;

    public ProductListAdapter(Context context, int textViewResourceId, ArrayList<Product> productList, Activity fromActivity) {
        super(context, textViewResourceId, productList);
        this.productList = productList;
        this.context = context;
        this.fromActivity = fromActivity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        final int pos = position;

        //if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.adapter_product_list, null);

            //holder = new ViewHolder();

            if (productList.size() > 0) {
                //Product name
                final Product product = productList.get(position);
                final EditText productName = (EditText) v.findViewById(R.id.productName);
                productName.setEnabled(false);

                //Edit Button
                ImageButton editButton = (ImageButton) v.findViewById(R.id.editButton);

                //Delete button
                final ImageButton  deleteButton = (ImageButton) v.findViewById(R.id.deleteButton);
                deleteButton.setTag(position);
                editButton.setTag(position);

                try {
                    if (productName != null)
                        productName.setText(String.valueOf(product.getDescription()));

                } catch (Exception e) {
                    e.printStackTrace();
                }

                //Listeners
                editButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Integer tag = (Integer) view.getTag();
                        Intent it = new Intent(context, EditProductAct.class);
                        it.putExtra("products", productList);
                        it.putExtra("position", tag);
                        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(it);
                    }
                });

                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());

                        Integer tag = (Integer) view.getTag();
                        newDialogBuilder(tag);

                        String yes = context.getResources().getString(R.string.yes);
                        String no = context.getResources().getString(R.string.no);

                        builder.setMessage(context.getResources().getString(R.string.areYouSureDelete))
                                .setPositiveButton(yes, dialogDelete)
                                .setNegativeButton(no, dialogDelete).show();

                    }
                });

            }
        //}
        return v;
    }

    private void newDialogBuilder(final int position) {
        this.dialogDelete = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:

                        productList.remove(position);
                        ProductListAdapter.this.notifyDataSetChanged();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
    }

    @Override
    public Product getItem(int position) {
        return productList.get(position);
    }
}

