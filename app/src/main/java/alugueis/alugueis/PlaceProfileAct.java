package alugueis.alugueis;

import alugueis.alugueis.adapter.ViewPageAdapter;
import alugueis.alugueis.model.Place;
import alugueis.alugueis.model.UserApp;
import alugueis.alugueis.util.CompressionUtil;
import alugueis.alugueis.util.ImageUtil;
import alugueis.alugueis.util.StaticUtil;
import alugueis.alugueis.view.RoundedImageView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.zip.DataFormatException;

public class PlaceProfileAct extends DashboardNavAct {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Utilizado para levar o layout da activity para o pai (nav drawer)
        getLayoutInflater().inflate(R.layout.activity_place_profile, frameLayout);

        context = getApplicationContext();
        getLogged();

        initializeToolbar();
        initializeComponents();
        initializeListeners();
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
                callIntent.setData(Uri.parse("tel:33377373"));
                startActivity(callIntent);
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

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        //Populando views
        try {
            Place place = (Place) StaticUtil.readObject(context, StaticUtil.PLACE);

            //Imagem
            byte[] userPic = place.getPicture();
            userPic = CompressionUtil.decompress(userPic);
            pictureImage.setImageBitmap(BitmapFactory.decodeByteArray(userPic, 0, userPic.length));
            //Nome
            placeNameText.setText(place.getName());
            //Address
            placeAddressText.setText(place.getAddress().toString());
            //Phone
            placePhoneText.setText(place.getPhone());
            //Work
            workText.setText(place.getBusinessInitialHour() + "h - " + place.getBusinessFinalHour() + "h");

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (DataFormatException e) {
            e.printStackTrace();
        }


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPageAdapter adapter = new ViewPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new PlaceInfoFgm(), getResources().getString(R.string.infoTab));

        PlaceProductsFgm placeProductsFgm = new PlaceProductsFgm();
        adapter.addFragment(placeProductsFgm, getResources().getString(R.string.productsTab));
        //// TODO: descomente as linhas abaixo para levar os produtos pro fragment (popule a lista antes).
        //Bundle args = new Bundle();
        //args.putSerializable("products", products);
        //placeProductsFgm.setArguments(args);

        viewPager.setAdapter(adapter);
    }
}
