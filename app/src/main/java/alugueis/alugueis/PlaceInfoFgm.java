package alugueis.alugueis;

/**
 * Created by Pedreduardo on 05/02/2016.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import alugueis.alugueis.model.Place;

public class PlaceInfoFgm extends Fragment{

    private View view;
    private TextView placeAddressText;
    private TextView workText;
    private TextView phone;

    public PlaceInfoFgm() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_place_info, container, false);
        initializeComponents();

        populatePlaceData();

        return view;
    }


    private void populatePlaceData(){

        //todo: Popular dados da loja aqui (:
        /*
        Bundle bundle = this.getArguments();
        Place place = (Place) bundle.getString("place");

        try {
            if (place.getPicture() != null) {
                byte[] userPic = place.getPicture();
                userPic = CompressionUtil.decompress(userPic);
                pictureImage.setImageBitmap(BitmapFactory.decodeByteArray(userPic, 0, userPic.length));
            }

            placeNameText.setText(place.getName());
            placeAddressText.setText(place.getAddress().toString());
            placePhoneText.setText(place.getPhone());
            workText.setText(place.getBusinessInitialHour() + "h - " + place.getBusinessFinalHour() + "h");


        } catch (IOException | ClassNotFoundException | DataFormatException e) {
            e.printStackTrace();
        }*/

    }

    private void initializeComponents() {
        placeAddressText = (TextView) view.findViewById(R.id.placeAddressText);
        workText = (TextView) view.findViewById(R.id.workText);
        workText.setText("De 08h às 15h"); // todo: tirar isso depois
        phone = (TextView) view.findViewById(R.id.placePhoneText);
    }


}