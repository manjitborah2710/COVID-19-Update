package com.task.coronavirusupdate.utils;

import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;

import com.task.coronavirusupdate.R;
import com.task.coronavirusupdate.models.CountryDetail;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Scrape {
    static List<CountryDetail> countryDetails=new ArrayList<>();
    public static List<CountryDetail> getData(int time){

        countryDetails.clear();

        String url="https://www.worldometers.info/coronavirus/";
        String table_id="main_table_countries_today";
        if(time==1){
            table_id="main_table_countries_yesterday";
        }
        try {
            Document document= Jsoup.connect(url).get();
            Element table=document.getElementById(table_id);

            Elements table_header=table.select("thead tr th");
            int first_val_col=0;
            for(Element e:table_header){
                if(e.text().toLowerCase().contains("country")) {
                    first_val_col=e.elementSiblingIndex();
                    Log.d("mn",first_val_col+" "+e.text());
                    break;
                }
            }

            for(Element tr:table.getElementsByTag("tr")){
                Elements table_data=tr.select("td");

                if(table_data.size()==0) continue;

                String country_name=table_data.get(first_val_col).text();
                SpannableStringBuilder others_name=new SpannableStringBuilder();
                for(int i=first_val_col+1;i<table_data.size();i++){

                    String header=table_header.get(i).text();

                    others_name.append(header+" : ");

                    String actual_data=table_data.get(i).text();

                    SpannableStringBuilder actual_data_str_builder=new SpannableStringBuilder(actual_data);

                    ForegroundColorSpan span=new ForegroundColorSpan(Color.BLUE);
                    if(header.toLowerCase().contains("death")){
                        span=new ForegroundColorSpan(Color.RED);
                    }
                    else if(header.toLowerCase().contains("recover")){
                        span=new ForegroundColorSpan(Color.GREEN);
                    }

                    actual_data_str_builder.setSpan(span,0,actual_data.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                    others_name.append(actual_data_str_builder.append("\n"));

                }
                countryDetails.add(new CountryDetail(country_name,others_name));

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return countryDetails;
    }
}
