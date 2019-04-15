package com.example.takaapp;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextView.OnEditorActionListener {

    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;
    private ImageView imgMenu;
    private EditText edtSearch;
    private RecyclerView recycle_category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgMenu = findViewById(R.id.imgMenu);

        imgMenu.setOnClickListener(this);

        dl = findViewById(R.id.dl);

        recycle_category = findViewById(R.id.recycle_category);

        abdt = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
        abdt.setDrawerIndicatorEnabled(true);
        dl.addDrawerListener(abdt);
        abdt.syncState();

        imgMenu = findViewById(R.id.imgMenu);
        edtSearch = findViewById(R.id.edtSearch);
        edtSearch.setOnEditorActionListener(this);


        NavigationView nav_view = findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.profile) {
                    Toast.makeText(MainActivity.this, "My Profile", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.settings) {
                    Toast.makeText(MainActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.editprofile) {
                    Toast.makeText(MainActivity.this, "Edit Profile", Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        });
        List<User> list = new ArrayList<>();
        for (int i =0; i < 10; i++){
            list.add(new User("Đức Anh", "https://cdn.pixabay.com/photo/2018/02/09/21/46/rose-3142529_960_720.jpg"));
        }
        AdapterCategory ac = new AdapterCategory(list);
        recycle_category.setAdapter(ac);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return abdt.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgMenu:
                dl.openDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        Toast.makeText(MainActivity.this, edtSearch.getText().toString(), Toast.LENGTH_LONG).show();
        return false;
    }
}
