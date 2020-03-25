package com.task.coronavirusupdate.models;

import android.text.SpannableStringBuilder;

public class CountryDetail {
    private String country;
    private SpannableStringBuilder others;

    public CountryDetail(String country, SpannableStringBuilder others) {
        this.country = country;
        this.others = others;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public SpannableStringBuilder getOthers() {
        return others;
    }

    public void setOthers(SpannableStringBuilder others) {
        this.others = others;
    }
}
