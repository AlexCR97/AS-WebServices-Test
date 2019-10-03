package com.example.webservicestest.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.webservicestest.R;
import com.example.webservicestest.entities.User;
import com.example.webservicestest.services.WebService;
import com.example.webservicestest.services.usecases.ListUsersUC;
import com.example.webservicestest.ui.adapters.UsersAdapter;

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

        new ListUsersUC(this, new WebService.RequestAcceptedListener<List<User>>() {
            @Override
            public void onRequestAccepted(List<User> response) {
                UsersAdapter usersAdapter = new UsersAdapter(ListUsersActivity.this, R.layout.item_user, response);
                lvUsers.setAdapter(usersAdapter);

                progressDialog.hide();
            }
        }, new WebService.RequestRejectedListener() {
            @Override
            public void onRequestRejected() {
                Toast.makeText(ListUsersActivity.this, "Request rejected", Toast.LENGTH_SHORT).show();
                progressDialog.hide();
            }
        }).sendRequest();
    }
}
