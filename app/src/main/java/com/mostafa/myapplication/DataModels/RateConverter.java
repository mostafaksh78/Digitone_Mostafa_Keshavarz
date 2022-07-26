package com.mostafa.myapplication.DataModels;

import androidx.room.TypeConverter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RateConverter {
    @TypeConverter
    public String convertRateToLong(List<Rate> rates) {
        JSONArray array = new JSONArray();
        if (rates!=null) {
            for (int i = 0; i < rates.size(); i++) {
               array.put(rates.get(i).toString());
            }
        }
        return array.toString();
    }
    @TypeConverter
    public List<Rate> convertStringToRate(String time) {
        JSONArray array = new JSONArray();
        List<Rate> list = new ArrayList<>();
        try {
            array = new JSONArray(time);
            for (int i = 0; i < array.length(); i++) {
                String object = array.getString(i);
                list.add(new Rate(object));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
