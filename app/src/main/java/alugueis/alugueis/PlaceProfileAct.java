package alugueis.alugueis;

import alugueis.alugueis.model.UserApp;
import alugueis.alugueis.util.StaticUtil;
import alugueis.alugueis.view.RoundedImageView;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Pedreduardo on 22/12/2015.
 */
public class PlaceProfileAct extends DashboardNavAct {

    private UserApp loggedUserApp;
    private ImageView bannerImage;
    private RoundedImageView pictureImage;
    private Button callButton;
    private TextView placeAddressText;
    private TextView workText;
    private Context context;

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
            loggedUserApp = (UserApp) StaticUtil.getObject(context, StaticUtil.LOGGED_USER);
        }catch(Exception ex){}
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
        placeAddressText = (TextView) findViewById(R.id.placeAddressText);
        workText = (TextView) findViewById(R.id.workText);

        if (loggedUserApp.getPicture() != null) {
            byte[] userPic = loggedUserApp.getPicture();

            //Bitmap userPicBitmap = BitmapFactory.decodeByteArray(userPic, 0, userPic.length);
            //Bitmap bluredBackground = ImageUtil.fastblur(userPicBitmap, this, 25);
            //bluredBackground = ImageUtil.adjustBrightness(bluredBackground, -50);
            //bluredBackground = ImageUtil.adjustedContrast(bluredBackground, -10);

            pictureImage.setImageBitmap(BitmapFactory.decodeByteArray(userPic, 0, userPic.length));
            //bannerImage.setImageBitmap(bluredBackground);
        }

        if (loggedUserApp.getAddressApp() != null) {
            placeAddressText.setText(loggedUserApp.getAddressApp().toString());
        }

        workText.setText("De 08h às 15h");

    }

}
