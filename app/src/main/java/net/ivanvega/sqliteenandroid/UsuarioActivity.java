package net.ivanvega.sqliteenandroid;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.ivanvega.sqliteenandroid.db.DAOUsuarios;
import net.ivanvega.sqliteenandroid.db.Usuario;

public class UsuarioActivity extends AppCompatActivity {

    EditText txtNombre, txtEmail, txtTelefono, txtRedSocial, txtFechaNac;
    Button btnGuardar;

    //Detecta si es un update o si es un insert
    int id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        txtNombre = findViewById(R.id.txtNombre);
        txtEmail = findViewById(R.id.txtEmail);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtRedSocial = findViewById(R.id.txtRedSocial);
        txtFechaNac = findViewById(R.id.txtFechaNac);
        btnGuardar = findViewById(R.id.btnGuardar);

        final Context contexto = this;
        boolean editMode = false; //esto se necesita porque la pantalla se recicla para insertar y modificar

        //Aquí se detecta si se va a insertar o a editar un nuevo usuario
        id = getIntent().getIntExtra("_id", -1);

        //Si se manda a llamar con un valor diferente a -1 significa que se va a modificar
        if (id != -1) {

            //Aquí se cambia a modo edición
            editMode = true;


            //Se mandan llamar los datos del usuario con el id que se mandó
            DAOUsuarios ado = new DAOUsuarios(this);
            Usuario u = ado.get(id);

            txtNombre.setText(u.getNombre());
            txtTelefono.setText(u.getTelefono());
            txtEmail.setText(u.getEmail());
            txtRedSocial.setText(u.getRed_social());
            txtFechaNac.setText(u.getFecha_nac());
        }

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DAOUsuarios ado = new DAOUsuarios(getApplicationContext());


                //Si el id es igual a -1 significa que se va a agregar un nuevo usuario
                if (id == -1) {
                    long result = ado.add(
                            new Usuario(
                                    0,
                                    txtNombre.getText().toString(),
                                    txtTelefono.getText().toString(),
                                    txtEmail.getText().toString(),
                                    txtRedSocial.getText().toString(),
                                    txtFechaNac.getText().toString()
                            )
                    );


                    // Si se regresa algo diferente a 0 significa que se agregó correctamente a la base de datos
                    if (result > 0){
                        Toast.makeText(getBaseContext(), "Usuario agregado",
                                Toast.LENGTH_LONG).show();
                    }
                    // De lo contrario no se insertó correctamente
                    else{
                        Toast.makeText(getBaseContext(), "Error al agregar",
                                Toast.LENGTH_LONG).show();
                    }
                } else {

                    //Si el id es diferente de -1 se manda como modificación
                    ado.update(
                            new Usuario(
                                    id,
                                    txtNombre.getText().toString(),
                                    txtTelefono.getText().toString(),
                                    txtEmail.getText().toString(),
                                    txtRedSocial.getText().toString(),
                                    txtFechaNac.getText().toString()
                            )
                    );
                }

                //Cierra este activity al terminar
                finish();
            }
        });
    }

}
