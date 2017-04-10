package alugueis.alugueis;

import alugueis.alugueis.abstractiontools.KeyTools;
import alugueis.alugueis.abstractiontools.StandardFragment;
import alugueis.alugueis.dialogs.DialogsUtil;
import alugueis.alugueis.dialogs.OnPlaceHourTouchListener;
import alugueis.alugueis.model.Place;
import alugueis.alugueis.model.UserApp;
import alugueis.alugueis.services.StdService;
import alugueis.alugueis.services.place.PlaceService;
import alugueis.alugueis.util.StaticUtil;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.*;

import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static alugueis.alugueis.abstractiontools.ButterKnifeViewControls.ENABLED;

public class PlaceFragment extends StandardFragment {
    private static final String TAG = "Place Fragment";
    private Unbinder unbinder;
    private List<View> views;
    private Menu menu;
    private Place place;
    private OnPlaceHourTouchListener startListener;
    private OnPlaceHourTouchListener finishtListener;

    @BindView(R.id.reduced_toolbar)
    Toolbar toolbar;

    @BindView(R.id.place_address)
    EditText placeAddress;

    @BindView(R.id.place_name)
    EditText placeName;

    @BindView(R.id.place_phone)
    EditText placePhone;

    @BindView(R.id.place_start_hour)
    EditText startHour;

    @BindView(R.id.place_finish_hour)
    EditText finisHour;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.place_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        views = new ArrayList<>();
        views.addAll(Arrays.asList(placeAddress, placeName, placePhone, startHour, finisHour));

        startListener = new OnPlaceHourTouchListener(getContext(), startHour);
        finishtListener = new OnPlaceHourTouchListener(getContext(), finisHour);

        setupListeners();
        return view;
    }

    private void setupListeners() {
        startHour.setOnFocusChangeListener(startListener);
        startHour.setOnTouchListener(startListener);
        finisHour.setOnFocusChangeListener(finishtListener);
        finisHour.setOnTouchListener(finishtListener);
    }

    private void removeListeners() {
        startHour.setOnFocusChangeListener(null);
        startHour.setOnTouchListener(null);
        finisHour.setOnFocusChangeListener(null);
        finisHour.setOnTouchListener(null);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        objectToView();
    }

    private void objectToView() {
        place = (Place) getArguments().getSerializable("place");
        if (place != null) {
            placeName.setText(place.getName());
            placePhone.setText(place.getPhone());
            startHour.setText(place.getStartHour());
            finisHour.setText(place.getFinishHour());
        } else {
            place = new Place();
        }
    }

    @Override
    public Toolbar getToolBar() {
        return toolbar;
    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        this.menu = menu;
        if (place != null) {
            updateOptionMenu(false);
            return;
        }
        updateOptionMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.edit_menu_toolbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_action:
                setupListeners();
                updateOptionMenu(true);
                break;

            case R.id.done_action:
                removeListeners();
                savePlace();
                updateOptionMenu(false);
                break;

            case android.R.id.home:
                getActivity().onBackPressed();

            default:
                return true;
        }
        return true;
    }

    private void savePlace() {
        final ProgressDialog progress = new ProgressDialog(getContext());
        progress.setMessage(getString(R.string.savingPlace));
        progress.show();

        PlaceService placeService ;
        try {
            placeService = StdService.createService(PlaceService.class, getContext());
            Call<Place> call = placeService.save(placeFromView());
            call.enqueue(new Callback<Place>() {
                @Override
                public void onResponse(Call<Place> call, Response<Place> response) {
                    try {
                        //todo: oferecer abrir tela de cadastro de produtos se loja nova
                        StaticUtil.setOject(getContext(), StaticUtil.PLACE, response.body());
                        ((MainActivity) getActivity()).invalidadeProductListVisibility();
                        progress.dismiss();
                    } catch (IOException e) {
                        onFailure(call, e);
                    }
                }

                @Override
                public void onFailure(Call<Place> call, Throwable t) {
                    Log.e(TAG, "Save place error", t);
                    progress.dismiss();
                }
            });

        } catch (ConnectException e) {
            Log.e(TAG, "Save place error", e);
            DialogsUtil.connectionError(getActivity());
        }
    }

    private Place placeFromView() {
        place.setName(placeName.getText().toString());
        place.setPhone(placePhone.getText().toString());
        place.setStartHour(startHour.getText().toString());
        place.setFinishHour(finisHour.getText().toString());
        place.setUserApp((UserApp) getArguments().getSerializable("user"));
        return place;
    }

    private void updateOptionMenu(Boolean isInEditMode) {
        menu.findItem(R.id.edit_action).setVisible(!isInEditMode);
        menu.findItem(R.id.done_action).setVisible(isInEditMode);
        ButterKnife.apply(views, ENABLED, isInEditMode);

        if (isInEditMode) {
            KeyTools.showInputMethod(getActivity(), placeName);
        } else {
            KeyTools.hideInputMethod(getActivity(), this.getView());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

}

