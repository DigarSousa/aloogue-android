package alugueis.alugueis;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import alugueis.alugueis.model.LoggedUser;
import alugueis.alugueis.util.ImageUtil;
import alugueis.alugueis.util.UserUtil;
import alugueis.alugueis.view.RoundedImageView;

/**
 * Created by Pedreduardo on 22/12/2015.
 */
public class PlaceProfileAct extends ActionBarActivity {

    private LoggedUser loggedUser;
    private Toolbar mainToolbar;
    private ImageView bannerImage;
    private RoundedImageView pictureImage;
    private Button callButton;
    private TextView placeAddressText;
    private TextView workText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_profile);

        this.loggedUser = UserUtil.getLogged();

        initializeToolbar();
        initializeComponents();
        initializeListeners();
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
        mainToolbar = (Toolbar) findViewById(R.id.mainToolbar);
        //TODO: Nome do perfil da loja
        mainToolbar.setTitle("Perfil da loja");
        setSupportActionBar(mainToolbar);
        mainToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);

        mainToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initializeComponents() {
        bannerImage = (ImageView) findViewById(R.id.bannerImage);
        pictureImage = (RoundedImageView) findViewById(R.id.pictureImage);
        callButton = (Button) findViewById(R.id.callButton);
        placeAddressText = (TextView) findViewById(R.id.placeAddressText);
        workText = (TextView) findViewById(R.id.workText);

        if(loggedUser.getPicture() != null){
            byte[] userPic = loggedUser.getPicture();

            Bitmap userPicBitmap = BitmapFactory.decodeByteArray(userPic, 0, userPic.length);
            Bitmap bluredBackground = ImageUtil.fastblur(userPicBitmap, this, 25);
            bluredBackground = ImageUtil.adjustBrightness(bluredBackground, -50);
            bluredBackground = ImageUtil.adjustedContrast(bluredBackground, -10);

            pictureImage.setImageBitmap(BitmapFactory.decodeByteArray(userPic, 0, userPic.length));
            bannerImage.setImageBitmap(bluredBackground);
        }

        if(loggedUser.getAddressApp() != null) {
            placeAddressText.setText(loggedUser.getAddressApp().makeAddress().toString());
        }

        workText.setText("De 08h às 15h");

    }

}
