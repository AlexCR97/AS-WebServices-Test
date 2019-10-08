package com.example.webservicestest.ui.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.webservicestest.R;
import com.example.webservicestest.entidades.Usuario;
import com.example.webservicestest.negocios.casos.CUActualizarUsuario;
import com.example.webservicestest.negocios.casos.CUEliminarUsuario;
import com.example.webservicestest.negocios.casos.CUImagenUsuario;
import com.example.webservicestest.negocios.casos.CURegistrarUsuario;
import com.example.webservicestest.negocios.casos.CUSeleccionarUsuarioId;
import com.example.webservicestest.negocios.casos.CasoUso;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private ImageView ivImage;
    private EditText etId;
    private EditText etName;
    private EditText etLastName;
    private Button bBirthDate;
    private Button bSelect, bInsert, bUpdate, bDelete;
    private Button bListUsers;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);

        ivImage = findViewById(R.id.ivImage);
        etId = findViewById(R.id.etId);
        etName = findViewById(R.id.etName);
        etLastName = findViewById(R.id.etLastName);
        bBirthDate = findViewById(R.id.bBirthDate);
        bSelect = findViewById(R.id.bSelect);
        bInsert = findViewById(R.id.bInsert);
        bUpdate = findViewById(R.id.bUpdate);
        bDelete = findViewById(R.id.bDelete);
        bListUsers = findViewById(R.id.bListUsers);

        bBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBirthDate();
            }
        });

        bSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarUsuario();
            }
        });
        bInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();
            }
        });
        bUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarUsuario();
            }
        });
        bDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarUsuario();
            }
        });

        bListUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listarUsuarios();
            }
        });
    }

    private void setBirthDate() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            final Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    String birthDate = dayOfMonth + "/" + month + "/" + year;
                    bBirthDate.setText(birthDate);
                }
            }, year, month, day);

            datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    bBirthDate.setText("Click to enter birth date");
                }
            });

            datePickerDialog.show();
        }
        else {
            Toast.makeText(this, "API 24 or above required", Toast.LENGTH_SHORT).show();
        }
    }

    private void seleccionarUsuario() {
        String id = etId.getText().toString();

        if (id.isEmpty()) {
            Toast.makeText(this, "You must enter a valid id", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.show();

        CUSeleccionarUsuarioId cuSeleccionarUsuarioId = new CUSeleccionarUsuarioId(this, new CasoUso.EventoPeticionAceptada<Usuario>() {
            @Override
            public void alAceptarPeticion(Usuario usuario) {
                etName.setText(usuario.getNombre());
                etLastName.setText(usuario.getApellido());
                bBirthDate.setText(usuario.getFechaNacimiento());

                CUImagenUsuario cuImagenUsuario = new CUImagenUsuario(MainActivity.this, new CasoUso.EventoPeticionAceptada<Bitmap>() {
                    @Override
                    public void alAceptarPeticion(Bitmap bitmap) {
                        ivImage.setImageBitmap(bitmap);
                        progressDialog.hide();
                    }
                }, new CasoUso.EventoPeticionRechazada() {
                    @Override
                    public void alRechazarOperacion() {
                        progressDialog.hide();
                    }
                });

                cuImagenUsuario.enviarPeticion(usuario.getRutaImagen());

            }
        }, new CasoUso.EventoPeticionRechazada() {
            @Override
            public void alRechazarOperacion() {
                progressDialog.hide();
                Toast.makeText(MainActivity.this, "Request rejected", Toast.LENGTH_SHORT).show();
            }
        });

        cuSeleccionarUsuarioId.enviarPeticion(id);

        progressDialog.show();
    }

    private void registrarUsuario() {
        String id = etId.getText().toString();
        String name = etName.getText().toString();
        String lastName = etLastName.getText().toString();
        String birthDate = bBirthDate.getText().toString();
        String image = "";

        Usuario usuario = new Usuario.Builder()
                .setId(Integer.parseInt(id))
                .setNombre(name)
                .setApellido(lastName)
                .setFechaNacimiento(birthDate)
                .setRutaImagen(image)
                .build();

        progressDialog.show();

        /**/

        CURegistrarUsuario cuRegistrarUsuario = new CURegistrarUsuario(this, new CasoUso.EventoPeticionAceptada<String>() {
            @Override
            public void alAceptarPeticion(String s) {
                progressDialog.hide();

                if (s.equals("1"))
                    Toast.makeText(MainActivity.this, "Usuario registered!", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Error attempting to register user!", Toast.LENGTH_SHORT).show();
            }
        }, new CasoUso.EventoPeticionRechazada() {
            @Override
            public void alRechazarOperacion() {
                progressDialog.hide();
                Toast.makeText(MainActivity.this, "Request rejected", Toast.LENGTH_SHORT).show();
            }
        });

        cuRegistrarUsuario.enviarPeticion(usuario);
    }

    private void actualizarUsuario() {
        String id = etId.getText().toString();
        String name = etName.getText().toString();
        String lastName = etLastName.getText().toString();
        String birthDate = bBirthDate.getText().toString();
        String image = "";

        Usuario usuario = new Usuario.Builder()
                .setId(Integer.parseInt(id))
                .setNombre(name)
                .setApellido(lastName)
                .setFechaNacimiento(birthDate)
                .setRutaImagen(image)
                .build();

        progressDialog.show();

        /**/

        new CUActualizarUsuario(this, new CasoUso.EventoPeticionAceptada<String>() {
            @Override
            public void alAceptarPeticion(String s) {
                progressDialog.hide();

                if (s.equals("1"))
                    Toast.makeText(MainActivity.this, "Usuario updated!", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Error attempting to update user!", Toast.LENGTH_SHORT).show();
            }
        }, new CasoUso.EventoPeticionRechazada() {
            @Override
            public void alRechazarOperacion() {
                progressDialog.hide();
                Toast.makeText(MainActivity.this, "Request rejected", Toast.LENGTH_SHORT).show();
            }
        }).enviarPeticion(id, usuario);
    }

    private void eliminarUsuario() {
        int id = Integer.parseInt(etId.getText().toString());

        progressDialog.show();

        new CUEliminarUsuario(this, new CasoUso.EventoPeticionAceptada<String>() {
            @Override
            public void alAceptarPeticion(String s) {
                progressDialog.hide();

                if (s.equals("1"))
                    Toast.makeText(MainActivity.this, "Usuario deleted!", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Error attempting to delete user!", Toast.LENGTH_SHORT).show();
            }
        }, new CasoUso.EventoPeticionRechazada() {
            @Override
            public void alRechazarOperacion() {
                progressDialog.hide();
                Toast.makeText(MainActivity.this, "Request rejected", Toast.LENGTH_SHORT).show();
            }
        }).enviarPeticion(id);
    }

    private void listarUsuarios() {
        Intent intent = new Intent(this, ListarUsuariosActivity.class);
        startActivity(intent);
    }
}
