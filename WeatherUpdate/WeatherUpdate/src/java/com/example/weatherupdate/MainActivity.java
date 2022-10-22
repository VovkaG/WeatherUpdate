/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.text.Editable
 *  android.view.View
 *  android.view.inputmethod.InputMethodManager
 *  android.widget.EditText
 *  android.widget.LinearLayout
 *  android.widget.ScrollView
 *  android.widget.TextView
 *  android.widget.Toast
 *  androidx.appcompat.app.AppCompatActivity
 *  androidx.swiperefreshlayout.widget.SwipeRefreshLayout
 *  androidx.swiperefreshlayout.widget.SwipeRefreshLayout$OnRefreshListener
 *  com.android.volley.Request
 *  com.android.volley.Response
 *  com.android.volley.Response$ErrorListener
 *  com.android.volley.Response$Listener
 *  com.android.volley.VolleyError
 *  com.android.volley.toolbox.StringRequest
 *  com.android.volley.toolbox.Volley
 *  java.lang.CharSequence
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuffer
 *  java.lang.StringBuilder
 *  java.text.DecimalFormat
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.example.weatherupdate;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Editable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.text.DecimalFormat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity
extends AppCompatActivity {
    ScrollView DayInfoContainer;
    TextView DayInfoText;
    private final String appid = "15b083d4bd020ad7c9ffd09df01e6de1";
    private final String appid2 = "15b083d4bd020ad7c9ffd09df01e6de1";
    TextView cloudsText;
    TextView descriptionText;
    DecimalFormat df = new DecimalFormat("##.#");
    EditText etCity;
    EditText etCountry;
    TextView humidityText;
    LinearLayout infoContainer;
    TextView pressureText;
    SwipeRefreshLayout refreshLayout;
    TextView tempText;
    private final String url = "https://api.openweathermap.org/data/2.5/weather";
    private final String url2 = "https://api.openweathermap.org/data/2.5/forecast";
    TextView windText;

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            ((InputMethodManager)this.getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void DayInfo(View view) {
        this.infoContainer.setVisibility(8);
        this.DayInfoContainer.setVisibility(0);
        String string2 = this.etCity.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(1, "https://api.openweathermap.org/data/2.5/forecast?q=" + string2 + "&appid=" + "15b083d4bd020ad7c9ffd09df01e6de1", (Response.Listener)new Response.Listener<String>(){

            public void onResponse(String string2) {
                JSONObject jSONObject = new JSONObject(string2);
                JSONArray jSONArray = jSONObject.getJSONArray("list");
                JSONObject[] arrjSONObject = new JSONObject[10];
                JSONObject[] arrjSONObject2 = new JSONObject[10];
                JSONArray[] arrjSONArray = new JSONArray[10];
                int[] arrn = new int[10];
                double[] arrd = new double[10];
                int[] arrn2 = new int[10];
                String[] arrstring = new String[50];
                StringBuffer[] arrstringBuffer = new StringBuffer[50];
                String string3 = "";
                for (int i = 0; i < 8; ++i) {
                    arrjSONObject[i] = jSONArray.getJSONObject(i);
                    arrn[i] = arrjSONObject[i].optInt("pop");
                    arrjSONObject2[i] = arrjSONObject[i].getJSONObject("main");
                    arrd[i] = arrjSONObject2[i].getDouble("temp") - 273.15;
                    arrn2[i] = arrjSONObject2[i].getInt("humidity");
                    arrjSONArray[i] = arrjSONObject[i].getJSONArray("weather");
                    arrstring[i] = arrjSONArray[i].getJSONObject(0).getString("description");
                    JSONObject jSONObject2 = jSONObject;
                    arrstringBuffer[i] = new StringBuffer(arrjSONObject[i].getString("dt_txt"));
                    arrstringBuffer[i].delete(0, 10);
                    StringBuilder stringBuilder = new StringBuilder().append(string3).append((Object)arrstringBuffer[i]).append(" ").append(arrstring[i]).append("/// temp: ");
                    DecimalFormat decimalFormat = MainActivity.this.df;
                    JSONArray jSONArray2 = jSONArray;
                    string3 = stringBuilder.append(decimalFormat.format(arrd[i])).append("\u00b0C\n humidity : ").append(arrn2[i]).append("% rain ").append(arrn[i]).append("%\n\n").toString();
                    MainActivity.this.DayInfoText.setText((CharSequence)string3);
                    jSONArray = jSONArray2;
                    jSONObject = jSONObject2;
                }
                try {
                    MainActivity.this.closeKeyboard();
                    return;
                }
                catch (JSONException jSONException) {
                    jSONException.printStackTrace();
                    return;
                }
            }
        }, new Response.ErrorListener(){

            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText((Context)MainActivity.this.getApplicationContext(), (CharSequence)volleyError.toString().trim(), (int)0).show();
            }
        });
        Volley.newRequestQueue((Context)this.getApplicationContext()).add((Request)stringRequest);
    }

    public void clear(View view) {
        this.infoContainer.setVisibility(8);
        this.DayInfoContainer.setVisibility(8);
        this.etCity.setText(null);
    }

    public void getWeatherDetails(View view) {
        this.DayInfoContainer.setVisibility(8);
        this.infoContainer.setVisibility(0);
        String string2 = "";
        String string3 = this.etCity.getText().toString().trim();
        if (string3.equals((Object)"")) {
            this.infoContainer.setVisibility(8);
        } else {
            string2 = "https://api.openweathermap.org/data/2.5/weather?q=" + string3 + "&appid=" + "15b083d4bd020ad7c9ffd09df01e6de1";
        }
        StringRequest stringRequest = new StringRequest(1, string2, (Response.Listener)new Response.Listener<String>(){

            /*
             * Loose catch block
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            public void onResponse(String string2) {
                void var8_43;
                block24 : {
                    String string8;
                    String string3;
                    String string5;
                    String string4;
                    JSONObject jSONObject;
                    String string6;
                    double d2;
                    int n;
                    JSONObject jSONObject2;
                    String string7;
                    double d;
                    String string9 = "";
                    String string10 = "";
                    String string11 = "";
                    String string12 = "";
                    String string13 = "";
                    try {
                        jSONObject2 = new JSONObject(string2);
                        string5 = jSONObject2.getJSONArray("weather").getJSONObject(0).getString("description");
                        jSONObject = jSONObject2.getJSONObject("main");
                        d2 = jSONObject.getDouble("temp");
                    }
                    catch (JSONException jSONException) {}
                    double d3 = d2 - 273.15;
                    String string14 = string9;
                    try {
                        d = jSONObject.getDouble("feels_like");
                    }
                    catch (JSONException jSONException) {
                        string9 = string14;
                        break block24;
                    }
                    double d4 = d - 273.15;
                    String string15 = string10;
                    try {
                        n = jSONObject.getInt("pressure");
                    }
                    catch (JSONException jSONException) {
                        string10 = string15;
                        string9 = string14;
                        break block24;
                    }
                    float f = n;
                    String string16 = string11;
                    int n2 = jSONObject.getInt("humidity");
                    String string17 = string12;
                    JSONObject jSONObject3 = jSONObject2.getJSONObject("wind");
                    String string18 = string13;
                    String string19 = jSONObject3.getString("speed");
                    String string20 = jSONObject2.getJSONObject("clouds").getString("all");
                    jSONObject2.getJSONObject("sys").getString("country");
                    jSONObject2.getString("name");
                    String string21 = "Temp: " + MainActivity.this.df.format(d3) + " \u00b0C\nFeels Like: " + MainActivity.this.df.format(d4) + " \u00b0C";
                    try {
                        string3 = "Humidity: " + n2 + "%";
                    }
                    catch (JSONException jSONException) {
                        string9 = string21;
                        string10 = string15;
                        string11 = string16;
                        string12 = string17;
                        string13 = string18;
                        break block24;
                    }
                    try {
                        string7 = " Description: \n" + string5;
                    }
                    catch (JSONException jSONException) {
                        string9 = string21;
                        string10 = string15;
                        string11 = string16;
                        string12 = string17;
                        string13 = string18;
                        break block24;
                    }
                    try {
                        string4 = "Wind Speed: " + string19 + "m/s";
                    }
                    catch (JSONException jSONException) {
                        string10 = string15;
                        string11 = string16;
                        string13 = string18;
                        string12 = string7;
                        string9 = string21;
                        break block24;
                    }
                    try {
                        string8 = "Cloudiness: " + string20 + "%";
                    }
                    catch (JSONException jSONException) {
                        string10 = string4;
                        string11 = string16;
                        string13 = string18;
                        string12 = string7;
                        string9 = string21;
                        break block24;
                    }
                    try {
                        string6 = "Pressure: " + f + " hPa";
                    }
                    catch (JSONException jSONException) {
                        string10 = string4;
                        string11 = string8;
                        string13 = string18;
                        string12 = string7;
                        string9 = string21;
                        break block24;
                    }
                    try {
                        MainActivity.this.tempText.setText((CharSequence)string21);
                        MainActivity.this.humidityText.setText((CharSequence)string3);
                        MainActivity.this.windText.setText((CharSequence)string4);
                        MainActivity.this.cloudsText.setText((CharSequence)string8);
                        MainActivity.this.descriptionText.setText((CharSequence)string7);
                        MainActivity.this.pressureText.setText((CharSequence)string6);
                        MainActivity.this.closeKeyboard();
                        return;
                    }
                    catch (JSONException jSONException) {
                        string10 = string4;
                        string11 = string8;
                        string13 = string6;
                        string12 = string7;
                        string9 = string21;
                    }
                    break block24;
                    catch (JSONException jSONException) {
                        string10 = string15;
                        string11 = string16;
                        string9 = string14;
                        string12 = string17;
                        string13 = string18;
                    }
                    break block24;
                    catch (JSONException jSONException) {
                        string10 = string15;
                        string11 = string16;
                        string9 = string14;
                        string12 = string17;
                    }
                    break block24;
                    catch (JSONException jSONException) {
                        string10 = string15;
                        string11 = string16;
                        string9 = string14;
                    }
                }
                var8_43.printStackTrace();
            }
        }, new Response.ErrorListener(){

            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText((Context)MainActivity.this.getApplicationContext(), (CharSequence)volleyError.toString().trim(), (int)0).show();
            }
        });
        Volley.newRequestQueue((Context)this.getApplicationContext()).add((Request)stringRequest);
    }

    protected void onCreate(Bundle bundle) {
        SwipeRefreshLayout swipeRefreshLayout;
        super.onCreate(bundle);
        this.setContentView(2131427356);
        this.etCity = (EditText)this.findViewById(2131230908);
        this.tempText = (TextView)this.findViewById(2131231158);
        this.windText = (TextView)this.findViewById(2131231209);
        this.humidityText = (TextView)this.findViewById(2131230944);
        this.cloudsText = (TextView)this.findViewById(2131230846);
        this.descriptionText = (TextView)this.findViewById(2131230870);
        this.pressureText = (TextView)this.findViewById(2131231070);
        this.DayInfoText = (TextView)this.findViewById(2131231224);
        this.DayInfoContainer = (ScrollView)this.findViewById(2131231225);
        this.infoContainer = (LinearLayout)this.findViewById(2131230954);
        this.refreshLayout = swipeRefreshLayout = (SwipeRefreshLayout)this.findViewById(2131231220);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){

            public void onRefresh() {
                MainActivity.this.refreshLayout.setRefreshing(false);
            }
        });
    }

}

