package alugueis.alugueis.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.ArrayList;
import alugueis.alugueis.R;
import alugueis.alugueis.model.Product;

public class ProductListAdapter extends ArrayAdapter<Product>{
    private ArrayList<Product> productList;
    private DialogInterface.OnClickListener dialogDelete;
    private Context context;
    private ArrayList<ViewHolder> holders;

    public ProductListAdapter(Context context, int textViewResourceId, ArrayList<Product> productList) {
        super(context, textViewResourceId, productList);
        this.productList = productList;
        this.context = context;
        holders = new ArrayList<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //final ViewHolder holder;
        View v = convertView;
        final int pos = position;

        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.adapter_product_list, null);

            //holder = new ViewHolder();

            if (productList.size() > 0) {
                //Product name
                final Product product = productList.get(position);
                final TextView productName = (TextView) v.findViewById(R.id.productName);
                productName.setEnabled(false);

                //Edit Button
                ImageButton editButton = (ImageButton) v.findViewById(R.id.editButton);

                //Delete button
                final ImageButton  deleteButton = (ImageButton) v.findViewById(R.id.deleteButton);
                deleteButton.setTag(position);

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
                        productName.setEnabled(true);
                        productName.requestFocus();
                    }
                });

                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());

                        Integer tag = (Integer) view.getTag();
                        newDialogBuilder(tag);

                        notifyDataSetChanged();

                        String yes = context.getResources().getString(R.string.yes);
                        String no = context.getResources().getString(R.string.no);

                        builder.setMessage(context.getResources().getString(R.string.areYouSureDelete))
                                .setPositiveButton(yes, dialogDelete)
                                .setNegativeButton(no, dialogDelete).show();

                    }
                });

            }
        }
        return v;
    }

    private void newDialogBuilder(final int position) {
        this.dialogDelete = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:


                        //ESSA PUTA ATUALIZA O ARRAY MAS ATUALIZA O LISTVIEW ERRADO.
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

    public static class ViewHolder
    {
        Product product;
        TextView productName;
        ImageButton editButton;
        ImageButton deleteButton;
        int position;
    }

}

