    package com.kretek.erab.managementwarung;

    import android.content.DialogInterface;
    import android.content.Intent;
    import android.net.Uri;
    import android.os.Bundle;
    import android.app.Fragment;
    import android.app.FragmentTransaction;
    import android.support.annotation.NonNull;
    import android.support.annotation.Nullable;
    import android.support.v4.view.MenuItemCompat;
    import android.support.v7.app.AlertDialog;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.support.design.widget.NavigationView;
    import android.support.v4.view.GravityCompat;
    import android.support.v4.widget.DrawerLayout;
    import android.support.v7.app.ActionBarDrawerToggle;
    import android.support.v7.app.AppCompatActivity;
    import android.support.v7.widget.Toolbar;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.widget.ImageButton;
    import android.support.v7.widget.SearchView;
    import android.widget.Toast;

    import com.kretek.erab.managementwarung.model.Session;



    public class UserActivity extends AppCompatActivity
            implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener {

        public Session session;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            if (savedInstanceState == null) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.contentFrame, new fragmentIndex());
                ft.commit();
            }
            setContentView(R.layout.activity_user);

            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            session = new Session(this);
        }

        @Override
        public void onBackPressed() {
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.user, menu);

            final MenuItem searchItem = menu.findItem(R.id.search);
            final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
            searchView.setOnQueryTextListener(this);

            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement

            switch (id) {
                case R.id.about:
                    LayoutInflater inflater = getLayoutInflater();
                    View v = inflater.inflate(R.layout.dialog_about, null);

                    ImageButton gotoFacebook = v.findViewById(R.id.iconFacebook);
                    ImageButton gotoGmail = v.findViewById(R.id.iconGmail);
                    ImageButton gotoChatango = v.findViewById(R.id.iconChatango);

                    gotoFacebook.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent gotoFacebook = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/hackerbaru"));
                            startActivity(gotoFacebook);
                        }
                    });

                    gotoGmail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent gotoGmail = new Intent(Intent.ACTION_VIEW, Uri.parse("generationoftroll@gmail.com"));
                            gotoGmail.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
                            gotoGmail.putExtra(Intent.EXTRA_EMAIL, new String[]{"generationoftroll@gmail.com"});
                            startActivity(gotoGmail);
                        }
                    });

                    gotoChatango.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent gotoChatango = new Intent(Intent.ACTION_VIEW, Uri.parse("https://themazterpvsz.chatango.com"));
                            startActivity(gotoChatango);
                        }
                    });
                    AlertDialog.Builder a = new AlertDialog.Builder(this);
                    a.setTitle("About");
                    a.setView(v);
                    a.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    a.show();
                    break;
                case R.id.logout:
                    session.logOut();
                    Intent back = new Intent(this, AuthActivity.class);
                    back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    Toast.makeText(getApplicationContext(), "Berhasil logout", Toast.LENGTH_SHORT).show();
                    startActivity(back);
                    finish();
                    break;
            }
            return super.onOptionsItemSelected(item);
        }

        @SuppressWarnings("StatementWithEmptyBody")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            displaySelectedScreen(item.getItemId());

            return true;
        }

        private void displaySelectedScreen(int itemId) {

            //create fragment object
            Fragment fragment = null;

            switch (itemId) {
                case R.id.nav_index:
                    fragment = new fragmentIndex();
                    break;

                case R.id.nav_DataBarang:
                    fragment = new DataBarang();
                    break;

                case R.id.nav_LaporanKeuangan:
                    fragment = new LaporanKeuangan();
                    break;

                case R.id.nav_kontakSales:
                    fragment = new ContactSales();
                    break;

                case R.id.nav_accountSetting:
                    fragment = new Settings();
            }

            if (fragment != null) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.contentFrame, fragment);
                ft.commit();
            }

            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);

        }

        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    }
