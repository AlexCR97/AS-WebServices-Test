package com.example.webservicestest.ui.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.webservicestest.R;
import com.example.webservicestest.entidades.Usuario;
import com.example.webservicestest.servicios.ServicioWeb;
import com.example.webservicestest.servicios.implementaciones.SWListarUsuarios;
import com.example.webservicestest.ui.adaptadores.UsersAdapter;

import java.util.List;

public class ListUsersActivity extends AppCompatActivity {

    private ListView lvUsers;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_users);

        lvUsers = findViewById(R.id.lvUsers);

        progressDialog = new ProgressDialog(this);
        progressDialog.show();

        new SWListarUsuarios(this, new ServicioWeb.EventoPeticionAceptada<List<Usuario>>() {
            @Override
            public void alAceptarPeticion(List<Usuario> respuesta) {
                UsersAdapter usersAdapter = new UsersAdapter(ListUsersActivity.this, R.layout.item_user, respuesta);
                lvUsers.setAdapter(usersAdapter);

                progressDialog.hide();
            }
        }, new ServicioWeb.EventoPeticionRechazada() {
            @Override
            public void alRechazarPeticion() {
                Toast.makeText(ListUsersActivity.this, "Request rejected", Toast.LENGTH_SHORT).show();
                progressDialog.hide();
            }
        }).enviarPeticion();
    }
}
