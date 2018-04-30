package com.jikansoftware.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.jikansoftware.dao.empleado.BaseDAO;
import com.jikansoftware.model.Empleado;

import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO {
    private SQLiteDatabase database;
    private BaseDAO baseDAO;

    private String[] columns = {BaseDAO.EMPLEADO_ID,
                                BaseDAO.EMPLEADO_NOMBRE};

    public EmpleadoDAO(Context context) { baseDAO = new BaseDAO(context);}

    public void open() throws SQLException{
        database = baseDAO.getWritableDatabase();
    }

    public void close() { baseDAO.close();}

    public long create(Empleado empleado){
        ContentValues values = new ContentValues();
        values.put(BaseDAO.EMPLEADO_NOMBRE, empleado.getNombre());

        return database.insert(BaseDAO.TABLE_EMPLEADO, null, values);
    }

    public Empleado read(Long id){
        Cursor cursor = database.query(BaseDAO.TABLE_EMPLEADO, columns, baseDAO.EMPLEADO_ID + "+ = +" + id, null, null, null, null);
        Empleado empleado = new Empleado();
        if(cursor.moveToFirst()){
            empleado.setId(cursor.getLong(0));
            empleado.setNombre(cursor.getString(1));
        }
        cursor.close();
        return empleado;
    }

    public List<Empleado> readAll() {
        List<Empleado> empleados = new ArrayList<>();
        Cursor cursor = database.query(BaseDAO.TABLE_EMPLEADO, columns, null, null, null, null, null);
        if(cursor.moveToFirst()){
            while (cursor.isAfterLast()){
                Empleado empleado = new Empleado(cursor.getLong(0),
                        cursor.getString(1));

                empleados.add(empleado);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return empleados;
    }

    public int update(Empleado empleado){
        Long id = empleado.getId();
        ContentValues values = new ContentValues();

        values.put(BaseDAO.EMPLEADO_NOMBRE, empleado.getNombre());
        return database.update(BaseDAO.TABLE_EMPLEADO, values, BaseDAO.EMPLEADO_ID + " = " + id, null);
    }

    public void delete(Empleado empleado) {
        long id = empleado.getId();
        database.delete(BaseDAO.TABLE_EMPLEADO, BaseDAO.EMPLEADO_ID + " = " + id, null);
    }
}
