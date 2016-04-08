package alugueis.alugueis;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.IOException;

import alugueis.alugueis.model.Place;
import alugueis.alugueis.util.StaticUtil;
import service.httputil.OnFinishTask;
import service.httputil.Service;

public class EditPlaceAct extends CreatePlaceAct {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "History").setIcon(R.drawable.ic_delete_white_24dp)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //todo: abrir tela, perguntar o usuario se quer deletar e blablabla... ai chama o serviço e depois manda pra tela de mapas chavosinho...
                try {
                    new Service(new OnFinishTask() {
                        @Override
                        public void onFinishTask(Object result) {
                            Intent it=new Intent(EditPlaceAct.this,MapAct.class);
                            startActivity(it);
                            EditPlaceAct.this.finish();

                        }
                    }).delete(StaticUtil.readObject(EditPlaceAct.this,StaticUtil.PLACE),Place.class);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
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