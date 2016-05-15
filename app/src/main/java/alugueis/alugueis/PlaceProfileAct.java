package alugueis.alugueis;

import alugueis.alugueis.adapter.ProductListManageAdapter;
import alugueis.alugueis.adapter.ViewPageAdapter;
import alugueis.alugueis.model.Place;
import alugueis.alugueis.model.Product;
import alugueis.alugueis.model.UserApp;
import alugueis.alugueis.util.CompressionUtil;
import alugueis.alugueis.util.StaticUtil;
import alugueis.alugueis.view.RoundedImageView;
import service.httputil.OnFinishTask;
import service.httputil.Service;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

public class PlaceProfileAct extends DashboardNavAct implements OnFinishTask{

    private UserApp loggedUserApp;
    private ImageView bannerImage;
    private RoundedImageView pictureImage;
    private Button callButton;
    private TabLayout tabLayout;
    private Context context;
    private ViewPager viewPager;
    private TextView placeNameText;
    private TextView placeAddressText;
    private TextView placePhoneText;
    private TextView workText;
    private Place place;
    private List<Product> products;
    private ProductListManageAdapter productAdapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_place_profile, frameLayout);

        context = getApplicationContext();
        getLogged();

        getExtras();

        initializeToolbar();
        initializeComponents();
        initializeListeners();


    }

    private void getExtras() {
        Intent in = getIntent();
        Bundle b = in.getExtras();

        if(b!=null)
        {
            this.place =(Place) b.get("place");
        }
    }

    private void getLogged() {
        try {
            loggedUserApp = (UserApp) StaticUtil.readObject(context, StaticUtil.LOGGED_USER);
        } catch (Exception ex) {
        }
    }

    private void initializeListeners() {
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                if(PlaceProfileAct.this.place != null) {
                    callIntent.setData(Uri.parse("tel:"+place.getPhone()));
                    startActivity(callIntent);
                }
            }
        });
    }

    private void initializeToolbar() {
        mainToolbar.setTitle("Perfil da loja");
    }

    private void initializeComponents() {
        bannerImage = (ImageView) findViewById(R.id.bannerImage);
        pictureImage = (RoundedImageView) findViewById(R.id.pictureImage);
        callButton = (Button) findViewById(R.id.callButton);
        placeNameText = (TextView) findViewById(R.id.placeNameText);
        placeAddressText = (TextView) findViewById(R.id.placeAddressText);
        placePhoneText = (TextView) findViewById(R.id.placePhoneText);
        workText = (TextView) findViewById(R.id.workText);

        if (this.place != null) {
            this.progressDialog = new ProgressDialog(PlaceProfileAct.this);
            this.progressDialog.setMessage("Carregando produtos...");
            new Service(this, progressDialog).find(Product.class, new Pair<String, Object>("id", place.getId())).execute();
        }


        if(this.place != null){
            placeNameText.setText(place.getName());
        }

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPageAdapter adapter = new ViewPageAdapter(getSupportFragmentManager());

        PlaceInfoFgm placeInfoFgm = new PlaceInfoFgm();
        adapter.addFragment(placeInfoFgm, getResources().getString(R.string.infoTab));
        Bundle args = new Bundle();
        args.putSerializable("place", this.place);
        placeInfoFgm.setArguments(args);



        PlaceProductsFgm placeProductsFgm = new PlaceProductsFgm();
        adapter.addFragment(placeProductsFgm, getResources().getString(R.string.productsTab));
        args = new Bundle();
        args.putSerializable("products", (Serializable) products);
        placeProductsFgm.setArguments(args);

        viewPager.setAdapter(adapter);
    }

    @Override
    public void onFinishTask(Object result) {

        this.products = (List<Product>) result;

        try {
            StaticUtil.setOject(this, StaticUtil.PRODUCT_LIST, products);
            List removedProducts = productAdapter != null ? productAdapter.getRemovedProducts() : new ArrayList();
            new Service(new OnFinishTask() {
                @Override
                public void onFinishTask(Object result) {
                    progressDialog.dismiss();
                }
            }, progressDialog).delete(removedProducts, Product.class).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }
}
