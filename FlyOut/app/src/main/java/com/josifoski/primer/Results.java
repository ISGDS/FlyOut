package com.josifoski.primer;


import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.Fragment;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.josifoski.primer.models.vrakja1;
import com.josifoski.primer.models.vrakjan;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class Results extends AppCompatActivity {
    private ListView lista;
    private Button kopce1;
    private Button kopce2;
    String komar0;
    String komar1;
    private TextView naslov;

    List<vrakjan> v=new ArrayList<vrakjan>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
        .cacheInMemory(true)
                .cacheOnDisk(true)
        .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
        .defaultDisplayImageOptions(defaultOptions)
        .build();
        ImageLoader.getInstance().init(config);




        Bundle data=getIntent().getExtras();

            lista=(ListView)findViewById(R.id.listView);
            kopce1=(Button)findViewById(R.id.button2);
            komar0 = data.getString("komar");
            komar1 = data.getString("komar1");
            naslov=(TextView)findViewById(R.id.textView7);
            naslov.setText(komar0);





            new JSONTask().execute("http://api.apixu.com/v1/forecast.json?key=471fcbaad4814df2a3a22426162104&q=" + komar0 + "&days=" + komar1);




    }


    public class JSONTask extends AsyncTask<String,String,List<vrakja1>> {
        @Override
        protected List<vrakja1> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String finalJson = buffer.toString();
                JSONObject parentObject = new JSONObject(finalJson);

                List<vrakja1> vr = new ArrayList<>();
                vrakja1 vrakja = new vrakja1();
                JSONObject location = new JSONObject(parentObject.getString("location"));
                JSONObject current = new JSONObject(parentObject.getString("current"));
                JSONObject forecast = new JSONObject(parentObject.getString("forecast"));

                vrakja.setIme(location.getString("name"));
                vrakja.setDrzava(location.getString("country"));


                vrakja.setSega(current.getDouble("temp_c"));
                vrakja.setVeter(current.getDouble("wind_kph"));
                vrakja.setVlaznost(current.getString("humidity"));
                vrakja.setOblaci(current.getString("cloud"));

                JSONObject condition = new JSONObject(current.getString("condition"));

                vrakja.setDesavanje(condition.getString("text"));



                JSONArray forecastday = new JSONArray(forecast.getString("forecastday"));
                /**JSONObject seg = forecastday.getJSONObject(0);
                JSONObject conditi = new JSONObject(seg.getString("day"));
                vrakja.setMax(conditi.getDouble("maxtemp_c"));
                vrakja.setMin(conditi.getDouble("mintemp_c"));
                vrakja.setAverage(conditi.getDouble("avgtemp_c"));
                vrakja.setMaxveter(conditi.getDouble("maxwind_kph"));
                JSONObject con = new JSONObject(conditi.getString("condition"));
                vrakja.setDedesavanje(con.getString("text"));
                vrakja.setSlika(con.getString("icon"));**/

                for (int i = 0; i < forecastday.length(); i++) {
                    JSONObject segasen = forecastday.getJSONObject(i);
                    JSONArray hour = new JSONArray(segasen.getString("hour"));

                    for (int j=0;j<hour.length();j++){
                        vrakjan vz=new vrakjan();
                        JSONObject segg = hour.getJSONObject(j);
                        vz.setDatav(segg.getString("time"));
                        vz.setAverage(segg.getDouble("temp_c"));
                        vz.setVeter(segg.getDouble("wind_kph"));
                        vz.setPrecip1(segg.getDouble("precip_mm"));
                        JSONObject c = new JSONObject(segg.getString("condition"));
                        vz.setDesavanje(c.getString("text"));
                        vz.setSlika(c.getString("icon"));

                        v.add(vz);
                    }

                    if(i>=1) {
                        vrakja = new vrakja1();
                    }
                    vrakja.setDatav(segasen.getString("date"));

                    JSONObject condit = new JSONObject(segasen.getString("day"));

                    vrakja.setMax(condit.getDouble("maxtemp_c"));
                    vrakja.setMin(condit.getDouble("mintemp_c"));
                    vrakja.setAverage(condit.getDouble("avgtemp_c"));
                    vrakja.setVeter(condit.getDouble("maxwind_kph"));
                    vrakja.setPrecip1(condit.getDouble("totalprecip_mm"));


                    JSONObject co = new JSONObject(condit.getString("condition"));

                    vrakja.setDesavanje(co.getString("text"));
                    vrakja.setSlika(co.getString("icon"));

                    vr.add(vrakja);
                }


                return vr;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null)
                    connection.disconnect();
                try {
                    if (reader != null)
                        reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<vrakja1> result) {
            super.onPostExecute(result);
            adapter ad = new adapter(getApplicationContext(), R.layout.row, result);
            lista.setAdapter(ad);

        }
    }
    public void k1(View view){
        adap ad = new adap(getApplicationContext(), R.layout.row, v);
        lista.setAdapter(ad);
    }
    public void k2(View view){
        Intent i=new Intent(this,Mapatata.class);
        startActivity(i);

    }
    public class adap extends ArrayAdapter{

        public List<vrakjan> v;
        private int resource;
        private LayoutInflater inflater;
        public adap(Context context, int resource, List<vrakjan> objects) {
            super(context, resource, objects);
            v=objects;
            this.resource=resource;
            inflater=(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null)
            {
                convertView=inflater.inflate(resource,null);
            }
            ImageView slika;
            ImageView znak;

            TextView data;
            TextView avg;
            TextView desavanje;
            TextView lokacija;
            TextView neznam;
            TextView nekakazeneso;

            znak=(ImageView)convertView.findViewById(R.id.imageView2);
            nekakazeneso=(TextView)convertView.findViewById(R.id.textView8);
            slika=(ImageView)convertView.findViewById(R.id.imageView);
            desavanje=(TextView)convertView.findViewById(R.id.textView);
            neznam=(TextView)convertView.findViewById(R.id.textView9);
            String d;
            data=(TextView)convertView.findViewById(R.id.textView10);
            avg=(TextView)convertView.findViewById(R.id.textView6);
            lokacija=(TextView)convertView.findViewById(R.id.textView5);
            d=String.valueOf(v.get(position).getAverage());
            avg.setText("Temperature: " + d);
            ImageLoader.getInstance().displayImage("http:" + v.get(position).getSlika(), slika);
            desavanje.setText(v.get(position).getDesavanje());
            lokacija.setText("Precipitation:"+v.get(position).getPrecip1()+" mm");
            neznam.setText("Wind speed:"+v.get(position).getVeter()+" kph");

            if(v.get(position).getPrecip1()<=10.0||v.get(position).getVeter()<=30.0){
                nekakazeneso.setText("No delays");
                znak.setImageResource(R.drawable.green);

            }else if(v.get(position).getPrecip1()<=12.0||v.get(position).getVeter()<=40.0){
                nekakazeneso.setText("Possible delay");
                znak.setImageResource(R.drawable.yellow);
            }else if (v.get(position).getPrecip1()<=20.0||v.get(position).getVeter()<=55.0){
                nekakazeneso.setText("Probably delay");
                znak.setImageResource(R.drawable.red);
            }


            data.setText(v.get(position).getDatav());







            return convertView;

        }
    }

  public class adapter extends ArrayAdapter {

       public List<vrakja1> vr;
       private int resource;
       private LayoutInflater inflater;
       public adapter(Context context, int resource, List<vrakja1> objects) {
           super(context, resource, objects);
           vr=objects;
           this.resource=resource;
           inflater=(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
       }

       @Override
       public View getView(int position, View convertView, ViewGroup parent) {
           if(convertView==null)
           {
               convertView=inflater.inflate(resource,null);
           }
           ImageView slika;
           ImageView znak;

           TextView data;
           TextView avg;
           TextView desavanje;
           TextView lokacija;
           TextView neznam;
           TextView nekakazeneso;

           znak=(ImageView)convertView.findViewById(R.id.imageView2);
           nekakazeneso=(TextView)convertView.findViewById(R.id.textView8);
           slika=(ImageView)convertView.findViewById(R.id.imageView);
           desavanje=(TextView)convertView.findViewById(R.id.textView);
           neznam=(TextView)convertView.findViewById(R.id.textView9);
           String d;
           data=(TextView)convertView.findViewById(R.id.textView10);
           avg=(TextView)convertView.findViewById(R.id.textView6);
           lokacija=(TextView)convertView.findViewById(R.id.textView5);
           d=String.valueOf(vr.get(position).getAverage());
           avg.setText("Temperature: " + d);
           ImageLoader.getInstance().displayImage("http:" + vr.get(position).getSlika(), slika);
           desavanje.setText(vr.get(position).getDesavanje());
           lokacija.setText("Precipitation:"+vr.get(position).getPrecip1()+" mm");
           neznam.setText("Wind speed:"+vr.get(position).getVeter()+" kph");

            if(vr.get(position).getPrecip1()<=10.0||vr.get(position).getVeter()<=30.0){
                nekakazeneso.setText("No delays");
                znak.setImageResource(R.drawable.green);

            }else if(vr.get(position).getPrecip1()<=12.0||vr.get(position).getVeter()<=40.0){
                nekakazeneso.setText("Possible delay");
                znak.setImageResource(R.drawable.yellow);
            }else if (vr.get(position).getPrecip1()<=20.0||vr.get(position).getVeter()<=55.0){
                nekakazeneso.setText("Probably delay");
                znak.setImageResource(R.drawable.red);
            }


           data.setText(vr.get(position).getDatav());







           return convertView;

       }
   }
}
