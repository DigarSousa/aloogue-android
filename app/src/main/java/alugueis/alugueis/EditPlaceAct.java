package alugueis.alugueis;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.IOException;
import java.util.zip.DataFormatException;

import alugueis.alugueis.model.Place;
import alugueis.alugueis.util.CompressionUtil;
import alugueis.alugueis.util.StaticUtil;

public class EditPlaceAct extends CreatePlaceAct {

    private Place place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        populateFields();

    }

    private void populateFields() {
        try {
            this.place = (Place) StaticUtil.readObject(getApplicationContext(), StaticUtil.PLACE);
            if (place != null && place.getId() != null) {

                //General
                cpfCnpjEditText.setText(place.getCpfCnpj());
                nameEditText.setText(place.getName());

                phoneEditText.setText(place.getPhone());

                //Address
                if (place.getAddress().getZipCode() == null || place.getAddress().getZipCode().isEmpty()) {
                    place.getAddress().setZipCode("00000000");
                } else {
                    zipCodeText.setText(place.getAddress().getZipCode());
                }
                addressEditText.setText(place.getAddress().getStreet());
                streetNumberEditText.setText(place.getAddress().getNumber().toString());
                neighbourhoodEditText.setText(place.getAddress().getNeighbourhood());
                cityEditText.setText(place.getAddress().getCity());
                StaticUtil.selectSpinnerValue(stateSpinner, place.getAddress().getStateFU());

                //Profile
                StaticUtil.selectSpinnerValue(businessInitialHourSpinner, place.getBusinessInitialHour());
                StaticUtil.selectSpinnerValue(businessFinalHourSpinner, place.getBusinessFinalHour());

                //Imagem
                byte[] userPic = place.getPicture();
                try {
                    if (userPic != null) {
                        userPic = CompressionUtil.decompress(userPic);
                        pictureImageView.setImageBitmap(BitmapFactory.decodeByteArray(userPic, 0, userPic.length));
                    }
                } catch (DataFormatException e) {
                    e.printStackTrace();
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "History").setIcon(R.drawable.ic_delete_white_24dp)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        //todo: colocar ação de delete no botão
        return true;
    }


    @Override
    public void onFinishTask(Object result) {
        Place place = (Place) result;
        try {
            StaticUtil.setOject(this, StaticUtil.PLACE, place);
            getDialogCoord().dismiss();
            Toast.makeText(getApplicationContext(), "Itens atualizados", Toast.LENGTH_SHORT).show();
            super.invalidateOptionsMenu(); //recarrega os items do menu do drawer
            hideItems(); //esconde items do menu de acordo com a necessidade
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(EditPlaceAct.this, MapAct.class);
        startActivity(intent);
    }
}