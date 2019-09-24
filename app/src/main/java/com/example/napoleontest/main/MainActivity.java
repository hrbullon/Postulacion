package com.example.napoleontest.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.napoleontest.R;
import com.example.napoleontest.aplicattion.MasterApp;
import com.example.napoleontest.domain.model.PostUser;
import com.example.napoleontest.domain.repository.interfaces.SecurityRepository;
import com.example.napoleontest.fragment.AllPostFragment;
import com.example.napoleontest.fragment.FavoritesFragment;
import com.example.napoleontest.fragment.MyPostFragment;
import com.example.napoleontest.login.LoginActivity;
import com.example.napoleontest.networkservice.NetworkAPI;
import com.example.napoleontest.sqlite.SqliteController;
import com.yarolegovich.lovelydialog.LovelyDialogCompat;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static View view;
    String idUser, name, userName, userEmail;

    TextView txv_profile_name, txv_profile_email;

    NavigationView navigationView;
    @Inject
    public Retrofit mRetrofit;

    @Inject
    public SecurityRepository mSecurityRepository;

    SqliteController mSqliteController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MasterApp.getAppComponent().inject(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View navigationViewHeaderView = navigationView.getHeaderView(0);
        txv_profile_name = (TextView) navigationViewHeaderView.findViewById(R.id.txvnameuser);
        txv_profile_email = (TextView) navigationViewHeaderView.findViewById(R.id.txvemail);
        navigationView.setNavigationItemSelectedListener(this);

        getInitMainActivity();
        callToAllPost();
    }

    private void callToMyPost() {
        Bundle args = new Bundle();
        args.putString("idUser", idUser);
        getSupportActionBar().setTitle(R.string.mypost_title);
        Fragment myPostFragment = new MyPostFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().
                replace(R.id.contenedor,myPostFragment).
                addToBackStack(myPostFragment.getTag()).
                commit();
    }

    private void getInitMainActivity() {
        Bundle bundle=getIntent().getExtras();
        idUser = bundle.getString("idUser");
        name = bundle.getString("name");
        userName = bundle.getString("userName");
        userEmail = bundle.getString("userEmail");

        txv_profile_name.setText(name);
        txv_profile_email.setText(userEmail);
        //Toast.makeText(this, idUser + "  " + name + " " + userName + " " + userEmail,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
           borrarRecycler();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void borrarRecycler() {
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_myposts) {
            callToAllPost();
        } else if (id == R.id.nav_allpost) {
            callToMyPost();

        } else if (id == R.id.nav_favorite) {
            callToFavorite();

        } else if (id == R.id.nav_close) {
            close();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void close() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void callToFavorite() {
        Bundle args = new Bundle();
        args.putString("idUser", idUser);
        getSupportActionBar().setTitle(R.string.favorite_title);
        Fragment allPostFragment = new FavoritesFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().
                replace(R.id.contenedor,allPostFragment).
                addToBackStack(allPostFragment.getTag()).
                commit();

    }

    private void callToAllPost() {
        Bundle args = new Bundle();
        args.putString("idUser", idUser);
        getSupportActionBar().setTitle(R.string.allpost_title);
        Fragment allPostFragment = new AllPostFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().
                replace(R.id.contenedor,allPostFragment).
                addToBackStack(allPostFragment.getTag()).
                commit();
    }
}
