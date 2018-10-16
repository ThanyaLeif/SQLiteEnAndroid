package net.ivanvega.sqliteenandroid;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import net.ivanvega.sqliteenandroid.db.DAOUsuarios;
import net.ivanvega.sqliteenandroid.db.MiAdaptadorUsuariosConexion;


public class MainActivity extends AppCompatActivity {

    EditText txtBuscar;
    Button btnInsertar, btnBuscar;
    ListView lsv ;

    public final int INSERT_USER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtBuscar = findViewById(R.id.txtBuscar);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnInsertar = findViewById(R.id.btnInsertar);
        lsv = findViewById(R.id.lsv);

        final Context contexto = this;


        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarDatos(txtBuscar.getText().toString());
            }
        });

        btnInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent insertar = new Intent(contexto, UsuarioActivity.class);
                startActivityForResult(insertar, INSERT_USER);
            }
        });

        lsv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(contexto);

                //Este método da la opción de eliminar o modificar un registro en específico
                builder.setItems(
                        new CharSequence[]{"Modificar", "Eliminar"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Cursor c = (Cursor) parent.getItemAtPosition(position);
                                int id = c.getInt(0);

                                switch (which) {
                                    case 0:
                                        Intent intentActualizar = new Intent(contexto, UsuarioActivity.class);
                                        intentActualizar.putExtra("_id", id);

                                        startActivityForResult(intentActualizar, 1);
                                        break;
                                    case 1:
                                        DAOUsuarios ado = new DAOUsuarios(getBaseContext());

                                        if (ado.delete(id) > 0)
                                            Toast.makeText(contexto, "Usuario eliminado", Toast.LENGTH_SHORT).show();
                                        break;
                                }
                            }
                        });

                builder.show();
                return false;
            }
        });
    }

    public void cargarDatos(String buscar){
        DAOUsuarios daoUsuario = new DAOUsuarios(this);
        Cursor c = daoUsuario.getByName(buscar);

        //Esto lo copié del método btnCArgar
        SimpleCursorAdapter adaptador = new SimpleCursorAdapter(
                this, android.R.layout.simple_list_item_2, c,
                MiAdaptadorUsuariosConexion.COLUMNS_USUARIOS,
                new int[]{android.R.id.text1, android.R.id.text2},
                SimpleCursorAdapter.NO_SELECTION
        );

        lsv.setAdapter(adaptador);
    }


    public void btnCargar_click(View v){
        cargarDatos("");
    }

    //Este método recarga los valores del listview y del textbox
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        txtBuscar.setText("");
        cargarDatos("");
    }
}
