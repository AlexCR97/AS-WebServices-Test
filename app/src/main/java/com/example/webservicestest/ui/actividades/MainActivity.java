package com.example.webservicestest.ui.actividades;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.example.webservicestest.negocios.validadores.ValidadorUsuario;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    public static final int PETICION_CAMARA = 1;
    public static final int PETICION_GALERIA = 2;

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

        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarOpcionesImagen();
            }
        });

        bBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elegirFechaNacimiento();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case PETICION_CAMARA: {
                break;
            }

            case PETICION_GALERIA: {
                if (data == null)
                    return;

                Uri rutaImagen = data.getData();
                ivImage.setImageURI(rutaImagen);

                break;
            }
        }
    }

    private void mostrarOpcionesImagen() {
        final CharSequence[] opciones = {"Tomar foto", "Elegir de galeria", "Cancelar"};

        final AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Elige una opcion")
                .setItems(opciones, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {

                            // Tomar foto
                            case 0: {
                                abrirCamara();
                                break;
                            }

                            // Elegir de galeria
                            case 1: {
                                abrirGaleria();
                                break;
                            }

                            // Cancelar
                            case 2: {
                                dialog.dismiss();
                                break;
                            }
                        }
                    }
                });

        builder.show();
    }

    private void abrirCamara() {

    }

    private void abrirGaleria() {
        String action = Intent.ACTION_PICK;
        Intent intent = new Intent(action, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");

        startActivityForResult(Intent.createChooser(intent, "Seleccione imagen"), PETICION_GALERIA);
    }

    private void elegirFechaNacimiento() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            final Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    String birthDate = year + "-" + month + "-" + dayOfMonth;
                    bBirthDate.setText(birthDate);
                }
            }, year, month, day);

            datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    bBirthDate.setText("Fecha de nacimiento");
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

        progressDialog.setMessage("Buscando usuario...");
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
                        progressDialog.hide();

                        ivImage.setImageBitmap(bitmap);
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
    }

    private void registrarUsuario() {
        String id = etId.getText().toString();
        String name = etName.getText().toString();
        String lastName = etLastName.getText().toString();
        String birthDate = bBirthDate.getText().toString();
        String image = "";

        if (id.isEmpty()) {
            Toast.makeText(this, "Debes ingresar un id!", Toast.LENGTH_SHORT).show();
            return;
        }

        Usuario usuario = new Usuario.Builder()
                .setId(Integer.parseInt(id))
                .setNombre(name)
                .setApellido(lastName)
                .setFechaNacimiento(birthDate)
                .setRutaImagen(image)
                .build();

        ValidadorUsuario validadorUsuario = new ValidadorUsuario(usuario);

        if (!validadorUsuario.validar()) {
            Toast.makeText(this, validadorUsuario.ultimoError().mensajeError(), Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registrando usuario...");
        progressDialog.show();

        CURegistrarUsuario cuRegistrarUsuario = new CURegistrarUsuario(this, new CasoUso.EventoPeticionAceptada<String>() {
            @Override
            public void alAceptarPeticion(String s) {
                progressDialog.hide();

                if (s.equals("1"))
                    Toast.makeText(MainActivity.this, "Usuario registered!", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
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

        if (id.isEmpty()) {
            Toast.makeText(this, "Debes ingresar un id!", Toast.LENGTH_SHORT).show();
            return;
        }

        Usuario usuario = new Usuario.Builder()
                .setId(Integer.parseInt(id))
                .setNombre(name)
                .setApellido(lastName)
                .setFechaNacimiento(birthDate)
                .setRutaImagen(image)
                .build();

        ValidadorUsuario validadorUsuario = new ValidadorUsuario(usuario);

        if (!validadorUsuario.validar()) {
            Toast.makeText(this, validadorUsuario.ultimoError().mensajeError(), Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Actualizando datos...");
        progressDialog.show();

        new CUActualizarUsuario(this, new CasoUso.EventoPeticionAceptada<String>() {
            @Override
            public void alAceptarPeticion(String s) {
                progressDialog.hide();

                if (s.equals("1"))
                    Toast.makeText(MainActivity.this, "Usuario updated!", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Error al actualizar usuario!", Toast.LENGTH_SHORT).show();
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
        String idStr = etId.getText().toString();

        if (idStr.isEmpty()) {
            Toast.makeText(this, "Debes ingresar un id!", Toast.LENGTH_SHORT).show();
            return;
        }

        int id = Integer.parseInt(idStr);

        progressDialog.setMessage("Eliminando usuario...");
        progressDialog.show();

        new CUEliminarUsuario(this, new CasoUso.EventoPeticionAceptada<String>() {
            @Override
            public void alAceptarPeticion(String s) {
                progressDialog.hide();

                if (s.equals("1")) {
                    Toast.makeText(MainActivity.this, "Usuario eliminado!", Toast.LENGTH_SHORT).show();
                    limpiarCampos();
                }
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

    private void limpiarCampos() {
        etId.setText("");
        etName.setText("");
        etLastName.setText("");
        bBirthDate.setText("Fecha de nacimiento");
    }

    private void listarUsuarios() {
        Intent intent = new Intent(this, ListarUsuariosActivity.class);
        startActivity(intent);
    }
}
