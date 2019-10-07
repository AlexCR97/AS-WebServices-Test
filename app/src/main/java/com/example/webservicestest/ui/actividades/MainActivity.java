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
import com.example.webservicestest.negocios.casos.CUSeleccionarUsuarioId;
import com.example.webservicestest.negocios.casos.CasoUso;
import com.example.webservicestest.servicios.ServicioWeb;
import com.example.webservicestest.servicios.implementaciones.SWEliminarUsuario;
import com.example.webservicestest.servicios.implementaciones.SWImagenUsuario;
import com.example.webservicestest.servicios.implementaciones.SWRegistrarUsuario;
import com.example.webservicestest.servicios.implementaciones.SWSeleccionarUsuarioId;
import com.example.webservicestest.servicios.implementaciones.UpdateUserUC;

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
                selectUser();
            }
        });
        bInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertUser();
            }
        });
        bUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser();
            }
        });
        bDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUser();
            }
        });

        bListUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listUsers();
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

    private void selectUser() {
        String id = etId.getText().toString();

        if (id.isEmpty()) {
            Toast.makeText(this, "You must enter a valid id", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.show();

        SWSeleccionarUsuarioId ws = new SWSeleccionarUsuarioId(this, new ServicioWeb.EventoPeticionAceptada<Usuario>() {
            @Override
            public void alAceptarPeticion(Usuario respuesta) {
                etName.setText(respuesta.getNombre());
                etLastName.setText(respuesta.getApellido());
                bBirthDate.setText(respuesta.getFechaNacimiento());

                SWImagenUsuario getUserImageUC = new SWImagenUsuario(MainActivity.this, new ServicioWeb.EventoPeticionAceptada<Bitmap>() {
                    @Override
                    public void alAceptarPeticion(Bitmap respuesta) {
                        ivImage.setImageBitmap(respuesta);
                        progressDialog.hide();
                    }
                }, new ServicioWeb.EventoPeticionRechazada() {
                    @Override
                    public void alRechazarPeticion() {
                        progressDialog.hide();
                    }
                });
                getUserImageUC.enviarPeticion(respuesta.getRutaImagen());

                progressDialog.hide();
            }
        }, new ServicioWeb.EventoPeticionRechazada() {
            @Override
            public void alRechazarPeticion() {
                progressDialog.hide();
                Toast.makeText(MainActivity.this, "Request rejected", Toast.LENGTH_SHORT).show();
            }
        });
        ws.enviarPeticion(Integer.parseInt(id));

        progressDialog.show();
    }

    private void seleccionarUsuario() {
        String id = etId.getText().toString();

        if (id.isEmpty()) {
            Toast.makeText(this, "You must enter a valid id", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.show();

        CUSeleccionarUsuarioId cu = new CUSeleccionarUsuarioId(this, new CasoUso.EventoPeticionAceptada<Usuario>() {
            @Override
            public void alAceptarPeticion(Usuario usuario) {
                etName.setText(usuario.getNombre());
                etLastName.setText(usuario.getApellido());
                bBirthDate.setText(usuario.getFechaNacimiento());
            }
        }, new CasoUso.EventoPeticionRechazada() {
            @Override
            public void alRechazarOperacion() {
                progressDialog.hide();
                Toast.makeText(MainActivity.this, "Request rejected", Toast.LENGTH_SHORT).show();
            }
        });

        cu.enviarPeticion(id);

        SWSeleccionarUsuarioId ws = new SWSeleccionarUsuarioId(this, new ServicioWeb.EventoPeticionAceptada<Usuario>() {
            @Override
            public void alAceptarPeticion(Usuario respuesta) {
                etName.setText(respuesta.getNombre());
                etLastName.setText(respuesta.getApellido());
                bBirthDate.setText(respuesta.getFechaNacimiento());

                SWImagenUsuario getUserImageUC = new SWImagenUsuario(MainActivity.this, new ServicioWeb.EventoPeticionAceptada<Bitmap>() {
                    @Override
                    public void alAceptarPeticion(Bitmap respuesta) {
                        ivImage.setImageBitmap(respuesta);
                        progressDialog.hide();
                    }
                }, new ServicioWeb.EventoPeticionRechazada() {
                    @Override
                    public void alRechazarPeticion() {
                        progressDialog.hide();
                    }
                });
                getUserImageUC.enviarPeticion(respuesta.getRutaImagen());

                progressDialog.hide();
            }
        }, new ServicioWeb.EventoPeticionRechazada() {
            @Override
            public void alRechazarPeticion() {
                progressDialog.hide();
                Toast.makeText(MainActivity.this, "Request rejected", Toast.LENGTH_SHORT).show();
            }
        });
        ws.enviarPeticion(Integer.parseInt(id));

        progressDialog.show();
    }

    private void insertUser() {
        String id = etId.getText().toString();
        String name = etName.getText().toString();
        String lastName = etLastName.getText().toString();
        String birthDate = bBirthDate.getText().toString();
        String image = "";

        Usuario user = new Usuario.Builder()
                .setId(Integer.parseInt(id))
                .setNombre(name)
                .setApellido(lastName)
                .setFechaNacimiento(birthDate)
                .setRutaImagen(image)
                .build();

        progressDialog.show();

        SWRegistrarUsuario ws = new SWRegistrarUsuario(this, new ServicioWeb.EventoPeticionAceptada<Boolean>() {
            @Override
            public void alAceptarPeticion(Boolean respuesta) {
                progressDialog.hide();

                if (respuesta)
                    Toast.makeText(MainActivity.this, "Usuario registered!", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Error attempting to register user!", Toast.LENGTH_SHORT).show();
            }
        }, new ServicioWeb.EventoPeticionRechazada() {
            @Override
            public void alRechazarPeticion() {
                progressDialog.hide();
                Toast.makeText(MainActivity.this, "Request rejected", Toast.LENGTH_SHORT).show();
            }
        });

        ws.enviarPeticion(user);
    }

    private void updateUser() {
        String id = etId.getText().toString();
        String name = etName.getText().toString();
        String lastName = etLastName.getText().toString();
        String birthDate = bBirthDate.getText().toString();
        String image = "";

        Usuario user = new Usuario.Builder()
                .setId(Integer.parseInt(id))
                .setNombre(name)
                .setApellido(lastName)
                .setFechaNacimiento(birthDate)
                .setRutaImagen(image)
                .build();

        progressDialog.show();

        UpdateUserUC uc = new UpdateUserUC(this, new ServicioWeb.EventoPeticionAceptada<Boolean>() {
            @Override
            public void alAceptarPeticion(Boolean respuesta) {
                progressDialog.hide();

                if (respuesta)
                    Toast.makeText(MainActivity.this, "Usuario updated!", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Error attempting to update user!", Toast.LENGTH_SHORT).show();
            }
        }, new ServicioWeb.EventoPeticionRechazada() {
            @Override
            public void alRechazarPeticion() {
                progressDialog.hide();
                Toast.makeText(MainActivity.this, "Request rejected", Toast.LENGTH_SHORT).show();
            }
        });

        uc.enviarPeticion(id, user);
    }

    private void deleteUser() {
        int id = Integer.parseInt(etId.getText().toString());

        progressDialog.show();

        SWEliminarUsuario uc = new SWEliminarUsuario(this, new ServicioWeb.EventoPeticionAceptada<Boolean>() {
            @Override
            public void alAceptarPeticion(Boolean respuesta) {
                progressDialog.hide();

                if (respuesta)
                    Toast.makeText(MainActivity.this, "Usuario deleted!", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Error attempting to delete user!", Toast.LENGTH_SHORT).show();
            }
        }, new ServicioWeb.EventoPeticionRechazada() {
            @Override
            public void alRechazarPeticion() {
                progressDialog.hide();
                Toast.makeText(MainActivity.this, "Request rejected", Toast.LENGTH_SHORT).show();
            }
        });

        uc.enviarPeticion(id);
    }

    private void listUsers() {
        Intent intent = new Intent(this, ListUsersActivity.class);
        startActivity(intent);
    }
}
