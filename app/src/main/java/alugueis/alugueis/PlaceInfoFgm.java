package alugueis.alugueis;

/**
 * Created by Pedreduardo on 05/02/2016.
 */
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;
import java.util.zip.DataFormatException;

import alugueis.alugueis.model.Place;
import alugueis.alugueis.util.CompressionUtil;

public class PlaceInfoFgm extends Fragment{

    private View view;
    private TextView placeAddressText;
    private TextView workText;
    private TextView placePhoneText;

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

        Bundle bundle = this.getArguments();
        Place place = (Place) bundle.get("place");

        if(place != null) {
            placeAddressText.setText(place.getAddress().toString());
            placePhoneText.setText(place.getPhone());
            workText.setText(place.getBusinessInitialHour() + "h - " + place.getBusinessFinalHour() + "h");
        }

    }

    private void initializeComponents() {
        placeAddressText = (TextView) view.findViewById(R.id.placeAddressText);
        workText = (TextView) view.findViewById(R.id.workText);
        placePhoneText = (TextView) view.findViewById(R.id.placePhoneText);
    }


}