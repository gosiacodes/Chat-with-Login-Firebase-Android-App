package com.example.firebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener{

    ListView listViewChat;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        findViewById(R.id.buttonMyProfile).setOnClickListener(this);
        listViewChat = findViewById(R.id.listViewChat);

        mAuth = FirebaseAuth.getInstance();

        Toolbar toolbarLogOut = findViewById(R.id.toolbarLogOut);
        setSupportActionBar(toolbarLogOut);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent (this, MainActivity.class));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonMyProfile:
                startActivity(new Intent(this, ProfileActivity.class));
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuLogOut:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
        return true;
    }
}
