package alugueis.alugueis;

import alugueis.alugueis.adapter.ViewPageAdapter;
import alugueis.alugueis.model.Place;
import alugueis.alugueis.model.UserApp;
import alugueis.alugueis.util.StaticUtil;
import alugueis.alugueis.view.RoundedImageView;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PlaceProfileAct extends DashboardNavAct {

    private UserApp loggedUserApp;
    private ImageView bannerImage;
    private RoundedImageView pictureImage;
    private Button callButton;
    private Context context;
    private ViewPager viewPager;
    private TextView placeNameText;
    private TextView placeAddressText;
    private TextView placePhoneText;
    private TextView workText;
    private Place place;


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

        if (b != null) {
            this.place = (Place) b.get("place");
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
                if (PlaceProfileAct.this.place != null) {
                    callIntent.setData(Uri.parse("tel:" + place.getPhone()));
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
            placeNameText.setText(place.getName());
        }
        setupViewPager();
    }

    private void setupViewPager() {
        ViewPageAdapter adapter = new ViewPageAdapter(getSupportFragmentManager());

        PlaceInfoFgm placeInfoFgm = new PlaceInfoFgm();
        adapter.addFragment(placeInfoFgm, getResources().getString(R.string.infoTab));
        Bundle args = new Bundle();
        args.putSerializable("place", this.place);
        placeInfoFgm.setArguments(args);


        ProductListFragment productListFragment = new ProductListFragment();
        adapter.addFragment(productListFragment, getResources().getString(R.string.productsTab));
        args = new Bundle();
        args.putSerializable("place", place);
        productListFragment.setArguments(args);

        viewPager.setAdapter(adapter);

    }
}
