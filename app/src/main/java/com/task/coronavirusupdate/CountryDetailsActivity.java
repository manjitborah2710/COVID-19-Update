package com.task.coronavirusupdate;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.task.coronavirusupdate.adapters.CustomRVAdapter;
import com.task.coronavirusupdate.models.CountryDetail;
import com.task.coronavirusupdate.utils.Scrape;

import java.util.ArrayList;
import java.util.List;

public class CountryDetailsActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    TextView linkToSite,totalListings;
    RecyclerView mainRV;
    List<CountryDetail> mainList,masterList;
    CustomRVAdapter adapter;
    SearchView searchView;
    Spinner dateSelector;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_details);
        context=this;


        searchView=findViewById(R.id.search_bar);
        totalListings=findViewById(R.id.total_listings);
        linkToSite=findViewById(R.id.link_to_worldometer);
        linkToSite.setMovementMethod(LinkMovementMethod.getInstance());
        mainRV=findViewById(R.id.main_rv);
        mainRV.setLayoutManager(new LinearLayoutManager(this));
        mainList=new ArrayList<>();
        masterList=new ArrayList<>();
        adapter=new CustomRVAdapter(this,mainList);
        mainRV.setAdapter(adapter);


        searchView.setOnQueryTextListener(this);
        dateSelector=findViewById(R.id.date_selector);

        final String date[]=new String[]{"Today","Yesterday"};
        dateSelector.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,date));
        dateSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadData(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        loadData(0);
    }

    void loadData(final int time) {

        final AlertDialog dialog = getDialog();
        dialog.show();

        AsyncTask<Void, Void, List<CountryDetail>> getData = new AsyncTask<Void, Void, List<CountryDetail>>() {
            @Override
            protected List<CountryDetail> doInBackground(Void... voids) {
                return Scrape.getData(time);
            }

            @Override
            protected void onPostExecute(List<CountryDetail> countryDetails) {
                if(countryDetails.size()==0){
                    dialog.dismiss();
                    new AlertDialog.Builder(context).setTitle("Could not load data").setMessage("Retry?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            loadData(time);
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).create().show();
                }
                populateList(countryDetails);
                totalListings.setText("Total : "+countryDetails.size());
                dialog.dismiss();
            }
        };
        getData.execute();
    }

    AlertDialog getDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(false).setView(LayoutInflater.from(this).inflate(R.layout.loading_dialog,null,false));
        return builder.create();
    }


    void populateList(List<CountryDetail> countryDetailList){

        mainList.clear();
        masterList.clear();
        for(CountryDetail el:countryDetailList){
            if(el.getCountry()!=null && !el.getCountry().trim().equals("")) {
                mainList.add(el);
                masterList.add(el);
//                Log.d("mn","Bubu"+el.getCountry()+"Manjit");
            }
        }
        adapter.notifyDataSetChanged();
        Toast.makeText(getApplicationContext(),"Info updated",Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.options_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.refresh){
//            dateSelector.setSelection(0);
            loadData(0);
            return true;
        }
        else if(item.getItemId()==R.id.go_to_top){
            mainRV.scrollToPosition(0);
        }
        else if(item.getItemId()==R.id.go_to_down){
            mainRV.scrollToPosition(mainList.size()-1);
        }
        else if(item.getItemId()==R.id.about){
            startActivity(new Intent(CountryDetailsActivity.this,About.class));
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        mainList.clear();
        for(CountryDetail el:masterList){
            if(el.getCountry().toLowerCase().contains(newText.toLowerCase())){
                mainList.add(el);
            }
        }
        totalListings.setText("Total : "+mainList.size());
        adapter.notifyDataSetChanged();
        return false;
    }

}
