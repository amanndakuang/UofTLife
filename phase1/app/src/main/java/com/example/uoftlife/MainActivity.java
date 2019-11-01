package com.example.uoftlife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserManager.loadUsers(this);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        ImageView homeView = findViewById(R.id.homeView);
        setBtnStart();
        setBtnSetting();
        setBtnRanking();

    }

    private void logout() {
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.logout_info))
                .setPositiveButton(R.string.logout, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setBtnStart() {
        findViewById(R.id.btnStart).setOnClickListener((view) -> {
            startActivity(new Intent(MainActivity.this, LevelOne.class));
        });

    }

    private void setBtnSetting() {
        findViewById(R.id.btnSetting).setOnClickListener((view) -> {
            startActivity(new Intent(MainActivity.this, LevelTwoPage.class));
        });
    }

    private void setBtnRanking() {
        findViewById(R.id.btnRanking).setOnClickListener((view) -> {
            startActivity(new Intent(MainActivity.this, GameLevelThree.class));
        });
    }
}


