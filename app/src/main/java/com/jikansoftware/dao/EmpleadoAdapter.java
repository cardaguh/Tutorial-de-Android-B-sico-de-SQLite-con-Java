package com.jikansoftware.dao;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.jikansoftware.model.Empleado;
import com.jikansoftware.sqlitecrud.R;

import java.util.List;

public class EmpleadoAdapter extends BaseAdapter{
    private Context context;

    private List<Empleado> empleados;
    private LayoutInflater inflater;

    public EmpleadoAdapter(Context context, List<Empleado> empleados){
        super();
        this.context = context;
        this.empleados = empleados;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void notifyDataSetChanged() {
        try {
            super.notifyDataSetChanged();
        } catch (Exception e){
            trace("Error: " + e.getMessage());
        }
    }

    private void trace(String msg){ toast(msg);}

    public void toast(String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();}

    @Override
    public int getCount() {
        return empleados.size();
    }

    // Quitar item de la lista
    public void remove(final Empleado empleado){ this.empleados.remove(empleado); }

    //Adicionar items a la lista
    public void add(final Empleado empleado){ this.empleados.add(empleado); }

    @Override
    public Object getItem(int position) {
        return empleados.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //crear vistas y llenar lo que falta
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try{
            Empleado empleado = empleados.get(position);

            ViewHolder holder;

            if(convertView == null){
                convertView = inflater.inflate(R.layout.contacto_row, null); //crear layout to inflate

                holder = new ViewHolder();
                holder.nombre = (TextView)convertView.findViewById(R.id.txtNome);

                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }

            holder.nombre.setText(empleado.getNombre());
            //faltan datos de la vista
            return convertView;

        } catch (Exception e) {
            trace("Error: " + e.getMessage());
        }
        return convertView;
    }

    static class ViewHolder {
        public TextView nombre;
    }
}
