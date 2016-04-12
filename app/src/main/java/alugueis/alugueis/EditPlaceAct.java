package alugueis.alugueis;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.IOException;

import alugueis.alugueis.model.Place;
import alugueis.alugueis.util.StaticUtil;

public class EditPlaceAct extends CreatePlaceAct {

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
}