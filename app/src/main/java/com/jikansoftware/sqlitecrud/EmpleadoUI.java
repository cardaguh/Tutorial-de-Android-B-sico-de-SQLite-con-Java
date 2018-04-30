package com.jikansoftware.sqlitecrud;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jikansoftware.model.Empleado;

public class EmpleadoUI extends Activity {
    private static final int INCLUIR = 0;

    Empleado empleado;
    EditText txtNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacto);

        try {

            final Bundle data = (Bundle) getIntent().getExtras();
            int lint = data.getInt("tipo");
            if(lint == INCLUIR){
                empleado = new Empleado();
            } else {
                empleado = (Empleado) data.getSerializable("usuario");
            }

            txtNombre= (EditText) findViewById(R.id.edtNome);

            txtNombre.setText(empleado.getNombre());

        } catch (Exception e) {
            trace("Error : " + e.getMessage());
        }
    }

    public void btnInsertar_click(View view){

        try{
            Intent data = new Intent();
            empleado.setNombre(txtNombre.getText().toString());

            data.putExtra("empleado", empleado);
            System.out.println(empleado);
            setResult(Activity.RESULT_OK, data);
            finish(); // Hace onDestroy, mata la Actividad actual

        } catch (Exception e) {
            trace("Error : " + e.getMessage());
        }
    }

    public void btnCancelar_click(View view){
        try {
            setResult(Activity.RESULT_CANCELED);
            finish();

        } catch (Exception e) {
            trace("Erro : " + e.getMessage());
        }
    }

    public void toast(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void trace(String msg) { toast(msg);}
}
