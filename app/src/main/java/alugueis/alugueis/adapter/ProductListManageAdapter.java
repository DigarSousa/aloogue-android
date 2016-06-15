package alugueis.alugueis.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import alugueis.alugueis.R;
import alugueis.alugueis.model.Product;
import alugueis.alugueis.EditProductAct;

public class ProductListManageAdapter extends BaseAdapter {

    private List<Product> productList;
    private List<Product> removedProducts;
    private DialogInterface.OnClickListener dialogDelete;
    private Context context;
    private Activity fromActivity;
    private LayoutInflater vi;

    public ProductListManageAdapter(Context context, List<Product> productList, Activity fromActivity) {
        this.productList = productList;
        this.context = context;
        this.fromActivity = fromActivity;

        removedProducts = new ArrayList<>();
    }


    public class Holder
    {
        EditText productName;    //Nome do filme
        ImageButton editButton;     //Sinopse
        ImageButton deleteButton;  //Imagem do filme
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder = null;
        final int pos = position;
        Product product = null;

        if (convertView == null) {

            this.vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.adapter_product_manage_list, null);
            holder = new Holder();

            product = new Product();
            holder.productName = (EditText) convertView.findViewById(R.id.productName);
            holder.productName.setEnabled(false);
            holder.editButton = (ImageButton) convertView.findViewById(R.id.editButton);
            holder.deleteButton = (ImageButton) convertView.findViewById(R.id.deleteButton);

            convertView.setTag(holder);
            holder.deleteButton.setTag(position);
            holder.editButton.setTag(position);
            holder.productName.setTag(productList.get(position));

            //Listeners
            holder.editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Integer tag = (Integer) view.getTag();
                    Intent it = new Intent(context, EditProductAct.class);
                    it.putExtra("products", productList.toArray());
                    it.putExtra("position", tag);
                    fromActivity.startActivityForResult(it, 1);
                }
            });

            holder.deleteButton.setOnClickListener(new View.OnClickListener() {
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
        else {
            holder = (Holder) convertView.getTag();
        }

        product = (Product) holder.productName.getTag();
        holder.productName.setText(product.getDescription());

        return convertView;
    }

    private void newDialogBuilder(final int position) {
        this.dialogDelete = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:

                        removedProducts.add(productList.remove(position));
                        ProductListManageAdapter.this.notifyDataSetChanged();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
    }

    @Override
    public int getCount() {
        return this.productList.size();
    }

    @Override
    public Product getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public List getRemovedProducts() {
        List removed = new ArrayList();
        for (Product product : removedProducts) {
            if (product.getId() != null) {
                removed.add(product);
            }
        }
        return removed;
    }
}

