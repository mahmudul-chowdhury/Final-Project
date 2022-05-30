package com.example.fassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RestApiActivity<RequestQueue> extends AppCompatActivity {

    TextView tvcity;
    TextView tvTemperature;
    TextView tvHumidity;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_api);
        tvcity=findViewById(R.id.tvCity);
        editText=findViewById(R.id.editText);
        tvTemperature=findViewById(R.id.tvTemperature);
        tvHumidity=findViewById(R.id.tvHumidity);
    }

    public void GetWeather(View view) {
        String API_KEY = "20aa29e05630b64d20891e3528bed91d";
        String CITY_NAME = editText.getText().toString();
        String url="https://api.openweathermap.org/data/2.5/weather?q=" +CITY_NAME+ "&appid=" + API_KEY ;
        RequestQueue queue = (RequestQueue) Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject object=response.getJSONObject("main");
                    String temperature=object.getString("temp");
                    Double temp=Double.parseDouble(temperature)-273.15;
                    tvTemperature.setText("Temperature " + temp.toString().substring(0,5) + " C");

                    String city=response.getString("name");
                    tvcity.setText("City: " + city);

                    JSONObject object4=response.getJSONObject("main");
                    String humidity=object4.getString("humidity");
                    tvHumidity.setText("Humidity " + humidity+ "%");

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RestApiActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        ((com.android.volley.RequestQueue) queue).add(request);
    }
}