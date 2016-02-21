package alugueis.alugueis.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import alugueis.alugueis.R;
import alugueis.alugueis.model.Product;
import alugueis.alugueis.EditProductAct;

public class ProductListManageAdapter extends ArrayAdapter<Product> {

    private List<Product> productList;
    private DialogInterface.OnClickListener dialogDelete;
    private Context context;
    private Activity fromActivity;

    public ProductListManageAdapter(Context context, int textViewResourceId, List<Product> productList, Activity fromActivity) {
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
        v = vi.inflate(R.layout.adapter_product_manage_list, null);

        //holder = new ViewHolder();

        if (productList.size() > 0) {
            //Product name
            final Product product = productList.get(position);
            final EditText productName = (EditText) v.findViewById(R.id.productName);
            productName.setEnabled(false);

            //Edit Button
            ImageButton editButton = (ImageButton) v.findViewById(R.id.editButton);

            //Delete button
            final ImageButton deleteButton = (ImageButton) v.findViewById(R.id.deleteButton);
            deleteButton.setTag(position);
            editButton.setTag(position);

            try {
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
                    it.putExtra("products", productList.toArray());
                    it.putExtra("position", tag);
                    fromActivity.startActivityForResult(it, 1);
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

    public void onActivityResult(int requestCode, int resultCode, Intent data, ArrayList<Product> products) {
        productList = products;
        ProductListManageAdapter.this.notifyDataSetChanged();
    }

    private void newDialogBuilder(final int position) {
        this.dialogDelete = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:

                        productList.remove(position);
                        ProductListManageAdapter.this.notifyDataSetChanged();
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

