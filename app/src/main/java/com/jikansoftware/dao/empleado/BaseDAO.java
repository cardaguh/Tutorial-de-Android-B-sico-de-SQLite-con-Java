package com.jikansoftware.dao.empleado;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDAO extends SQLiteOpenHelper {

    public static final String TABLE_EMPLEADO = "empleadoCRUD";
    public static final String EMPLEADO_ID = "_id";
    public static final String EMPLEADO_NOMBRE = "nombre";

    public static final String DATASE_NAME = "empleado.db";
    public static final int DATABASE_VERSION = 1;

    public static final String CREATE_EMPLEADO = "create table " + TABLE_EMPLEADO + " ( " +
            EMPLEADO_ID        + " integer primary key autoincrement, " +
            EMPLEADO_NOMBRE    + " text not null);";


    public BaseDAO(Context context) { super(context, DATASE_NAME, null, DATABASE_VERSION); }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("pass no " + this.toString());
        //getReadableDatabase().execSQL(CREATE_EMPLEADO);
        db.execSQL(CREATE_EMPLEADO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLEADO);
        onCreate(db);
    }
}
