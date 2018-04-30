package com.jikansoftware.sqlitecrud;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.jikansoftware.dao.EmpleadoAdapter;
import com.jikansoftware.dao.EmpleadoDAO;
import com.jikansoftware.model.Empleado;

import java.util.List;

public class MainActivity extends ListActivity {
    private static final int INCLUIR = 0;
    private static final int ALTERAR = 1;

    private EmpleadoDAO empleadoDAO;
    List<Empleado> empleados;
    EmpleadoAdapter adapter;

    boolean blnShort = false;
    int posicion = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        empleadoDAO = new EmpleadoDAO(this);
        empleadoDAO.open();
        empleados = empleadoDAO.readAll();

        adapter = new EmpleadoAdapter(this, empleados);
        setListAdapter(adapter);

        registerForContextMenu(getListView());
    }


    public void onClick(View view){
        switch (view.getId()){
            case R.id.add:
                insertarEmpleado();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        Empleado empleado = null;

        try{
            super.onActivityResult(requestCode, resultCode, data);
            if(resultCode == Activity.RESULT_OK){
                empleado = (Empleado) data.getExtras().getSerializable("empleado");

                if(requestCode == INCLUIR){
                    if(!empleado.getNombre().equals("")){
                        empleadoDAO.open();
                        empleadoDAO.create(empleado);
                        empleados.add(empleado);
                    }
                }else if(requestCode == ALTERAR){
                    empleadoDAO.open();
                    empleados.set(posicion, empleado);
                }
                adapter.notifyDataSetChanged();
            }

        } catch (Exception e){
            trace("Error:" + e.getMessage());
        }
    }

    private void insertarEmpleado(){
        try {
            Intent it = new Intent(this, EmpleadoUI.class);
            it.putExtra("tipo", INCLUIR);
            startActivityForResult(it, INCLUIR);
        } catch (Exception e){
            trace("Error:" + e.getMessage());
        }
    }

    public void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void trace (String msg) { toast(msg);}


    @Override
    protected void onResume(){
        empleadoDAO.open();
        super.onResume();
    }

    @Override
    protected void onPause(){
        empleadoDAO.close();
        super.onPause();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        try{
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

            if(!blnShort){
                posicion = info.position;
            }
            blnShort = false;

            menu.setHeaderTitle("Seleccione: ");

            String[] menuItems = getResources().getStringArray(R.array.menu);
            for(int i = 0; i < menuItems.length; i++){
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }

        } catch (Exception e){
            trace("Error:" + e.getMessage());
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        Empleado empleado = null;

        try{
            int menuItemIndex = item.getItemId();
            empleado = (Empleado) getListAdapter().getItem(posicion);

            if(menuItemIndex == 0){
                Intent it = new Intent(this, EmpleadoUI.class);
                it.putExtra("tipo", ALTERAR);
                it.putExtra("empleado", empleado);
                startActivityForResult(it, ALTERAR);
            } else if(menuItemIndex == 1){
                empleadoDAO.delete(empleado);
                empleados.remove(empleado);
                adapter.notifyDataSetChanged();
            }

        }catch (Exception e){
            trace("Error:" + e.getMessage());
        }
        return true;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id){
        super.onListItemClick(l, v, position, id);

        posicion = position;
        blnShort = true;
        this.openContextMenu(l);
    }
}
