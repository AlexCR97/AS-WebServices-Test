package com.example.webservicestest.external.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.webservicestest.R;
import com.example.webservicestest.domain.entities.User;
import com.example.webservicestest.external.web.WebService;
import com.example.webservicestest.external.web.WSInsertUser;
import com.example.webservicestest.external.web.WSSelectUser;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private ImageView ivImage;
    private EditText etId;
    private EditText etName;
    private EditText etLastName;
    private Button bBirthDate;
    private Button bSelect, bInsert, bUpdate, bDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivImage = findViewById(R.id.ivImage);
        etId = findViewById(R.id.etId);
        etName = findViewById(R.id.etName);
        etLastName = findViewById(R.id.etLastName);
        bBirthDate = findViewById(R.id.bBirthDate);
        bSelect = findViewById(R.id.bSelect);
        bInsert = findViewById(R.id.bInsert);
        bUpdate = findViewById(R.id.bUpdate);
        bDelete = findViewById(R.id.bDelete);

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

        WSSelectUser ws = new WSSelectUser(this, new WebService.RequestAcceptedListener<User>() {
            @Override
            public void onRequestAccepted(User response) {
                etName.setText(response.getName());
                etLastName.setText(response.getLastName());
                bBirthDate.setText(response.getBirthDate());
            }
        }, new WebService.RequestRejectedListener() {
            @Override
            public void onRequestRejected() {
                Toast.makeText(MainActivity.this, "Request rejected", Toast.LENGTH_SHORT).show();
            }
        });

        ws.sendRequest(Integer.parseInt(id));
    }

    private void insertUser() {
        String id = etId.getText().toString();
        String name = etName.getText().toString();
        String lastName = etLastName.getText().toString();
        String birthDate = bBirthDate.getText().toString();
        String image = "";

        User user = new User.Builder()
                .setId(Integer.parseInt(id))
                .setName(name)
                .setLastName(lastName)
                .setBirthDate(birthDate)
                .setImage(image)
                .build();

        WSInsertUser ws = new WSInsertUser(this, new WebService.RequestAcceptedListener<Void>() {
            @Override
            public void onRequestAccepted(Void response) {
                Toast.makeText(MainActivity.this, "User registered!", Toast.LENGTH_SHORT).show();
            }
        }, new WebService.RequestRejectedListener() {
            @Override
            public void onRequestRejected() {
                Toast.makeText(MainActivity.this, "Request rejected", Toast.LENGTH_SHORT).show();
            }
        });

        ws.sendRequest(user);
    }
}
