package com.example.webservicestest.ui.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.webservicestest.R;
import com.example.webservicestest.entidades.Usuario;
import com.example.webservicestest.negocios.casos.CUListarUsuarios;
import com.example.webservicestest.negocios.casos.CasoUso;
import com.example.webservicestest.ui.adaptadores.UsuarioAdapter;

import java.util.List;

public class ListarUsuariosActivity extends AppCompatActivity {

    private ListView lvUsers;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_users);

        lvUsers = findViewById(R.id.lvUsers);

        progressDialog = new ProgressDialog(this);
        progressDialog.show();

        new CUListarUsuarios(this, new CasoUso.EventoPeticionAceptada<List<Usuario>>() {
            @Override
            public void alAceptarPeticion(List<Usuario> usuarios) {
                progressDialog.dismiss();

                UsuarioAdapter usersAdapter = new UsuarioAdapter(ListarUsuariosActivity.this, R.layout.item_user, usuarios);
                lvUsers.setAdapter(usersAdapter);
            }
        }, new CasoUso.EventoPeticionRechazada() {
            @Override
            public void alRechazarOperacion() {
                progressDialog.dismiss();

                Toast.makeText(ListarUsuariosActivity.this, "Request rejected", Toast.LENGTH_SHORT).show();
            }
        }).enviarPeticion();
    }
}
