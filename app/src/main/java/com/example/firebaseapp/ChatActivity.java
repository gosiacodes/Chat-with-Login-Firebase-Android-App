package com.example.firebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener{

    List<String> chatMessages;
    ArrayAdapter<String> arrayAdapter;
    ListView listViewChat;
    EditText editTextChatMessage;
    TextView textViewUsername;
    Toolbar toolbarLogOut;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Log.d("lifecycle","onCreate chat invoked "+getApplicationContext());

        findViewById(R.id.buttonMyProfile).setOnClickListener(this);
        findViewById(R.id.buttonSendMessage).setOnClickListener(this);

        chatMessages = new ArrayList<>();
        listViewChat = findViewById(R.id.listViewChat);
        textViewUsername = findViewById(R.id.textViewUsername);
        editTextChatMessage = findViewById(R.id.editTextChatMessage);

        arrayAdapter = new ArrayAdapter<>(this, R.layout.chat_list_item, chatMessages);
        listViewChat.setAdapter(arrayAdapter);

        mAuth = FirebaseAuth.getInstance();

        toolbarLogOut = findViewById(R.id.toolbarLogOut);
        setSupportActionBar(toolbarLogOut);

        loadUserInformation();
    }

    private void loadUserInformation() {
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            if (user.getDisplayName() != null) {
                textViewUsername.setText(user.getDisplayName());
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("lifecycle","onStart chat invoked "+getApplication());

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
            case R.id.buttonSendMessage:
                chatMessages.add(editTextChatMessage.getText().toString());
                arrayAdapter.notifyDataSetChanged();
                editTextChatMessage.setText("");
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
