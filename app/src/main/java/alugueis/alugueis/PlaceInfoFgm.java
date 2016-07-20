package alugueis.alugueis;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import alugueis.alugueis.model.Place;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PlaceInfoFgm extends Fragment {
    private View view;
    private Unbinder unbinder;

    @BindView(R.id.placeAddressText)
    TextView placeAddressText;
    @BindView(R.id.workText)
    TextView workText;
    @BindView(R.id.placePhoneText)
    TextView placePhoneText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.place_info_framgment, container, false);
        unbinder = ButterKnife.bind(this, container);
        populatePlaceData();

        return view;
    }


    private void populatePlaceData() {
        Place place = (Place) getArguments().getSerializable("place");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}