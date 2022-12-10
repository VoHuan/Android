package com.example.knowyourgovernment;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;


import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.example.knowyourgovernment.Adapter.OfficialAdapter;

import com.example.knowyourgovernment.AsyncTask.OfficialLoader;
import com.example.knowyourgovernment.Model.Official;


@RequiresApi(api = Build.VERSION_CODES.M)
public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG= "OptionMenuExample";
    private SearchView searchView;
    private String lastQuery;
    private TextView tvViTriBang;

    private OfficialAdapter officialAdapter;
    private ListView lvoffcial;
    List<Official> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvoffcial = findViewById(R.id.lvOfficial);
        tvViTriBang = findViewById(R.id.tvViTriBang);

        if(checkInternetConnection()== false){
            noNetworkDialog("Stocks Cannot Be Update Without A Network Connection");
        }

        getItemListView();
    }







    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main_activity, menu);

        if (menu instanceof MenuBuilder) {
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }

        SearchManager searchManager = (SearchManager) getSystemService(this.SEARCH_SERVICE);
        this.searchView = (SearchView) menu.findItem(R.id.menuItem_search).getActionView();

        this.searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        // Need click "search" icon to expand SearchView.
        this.searchView.setIconifiedByDefault(true);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            // Typing search text.
            public boolean onQueryTextChange(String newText) {
                // This is your adapter that will be filtered
                Log.i(LOG_TAG, "onQueryTextChange: " + newText);
                return true;
            }

            // Press Enter to search (Or something to search).
            public boolean onQueryTextSubmit(String query) {
                // IMPORTANT!
                // Prevent onQueryTextSubmit() method called twice.
                // https://stackoverflow.com/questions/34207670
                searchView.clearFocus();

                //reset searchview
                searchView.setQuery("", false);
                searchView.setIconified(true);

                if(checkInternetConnection()== false){
                    noNetworkDialog("Stocks Cannot Be Update Without A Network Connection");
                    tvViTriBang.setText("No Data For Location");
                }

                OfficialLoader o = new OfficialLoader(MainActivity.this);
                o.execute(query);

                Log.i(LOG_TAG, "onQueryTextSubmit: " + query);
                return doSearch(query);
            }
        });

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_TAG, "SearchView.onSearchClickListener!" );
            }
        }) ;

        return super.onCreateOptionsMenu(menu);
    }


    private boolean doSearch(String query) {
        if (query == null || query.isEmpty()) {
            return false; // Cancel search.
        }
        this.lastQuery = query;
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menuItem_information)
        {
            openInformationActivity();
        }

        return true;
    }

    private void openInformationActivity() {
        Intent intent = new Intent(this, InformationActivity.class);
        startActivity(intent);
    }
    private boolean checkInternetConnection() {

        ConnectivityManager connManager =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);


        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();

        if (networkInfo == null) {
            //Toast.makeText(this, "No default network is currently active", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!networkInfo.isConnected()) {
           // Toast.makeText(this, "Network is not connected", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!networkInfo.isAvailable()) {
            //Toast.makeText(this, "Network not available", Toast.LENGTH_LONG).show();
            return false;
        }
       // Toast.makeText(this, "Network OK", Toast.LENGTH_LONG).show();
        return true;
    }
    public void updateOfficialData(ArrayList<Official> tempList)
    {
        list = new ArrayList<>();
        if(tempList.size()!=0)
        {
            list.addAll(tempList);
        }

        OfficialAdapter officialAdapter = new OfficialAdapter(MainActivity.this, list); //truyền list vào adapter
        lvoffcial.setAdapter(officialAdapter); //truyền adapter vào listview
    }
    public void getItemListView(){
        lvoffcial.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Official temp = list.get(position);

                Intent intent = new Intent(MainActivity.this,OfficialAvticity.class);
                Bundle bundle = new Bundle();

                bundle.putString("location", (String) tvViTriBang.getText());
                bundle.putSerializable("official", (Serializable) temp);

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
    public void noNetworkDialog(String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setIcon(R.drawable.no_network);
        builder.setTitle(R.string.networkErrorTitle);
        builder.setMessage(message);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}