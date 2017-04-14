package com.josifoski.primer;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private EditText searchbar;
    private Spinner spinner;
    private List<String> list=new ArrayList<String>();
    Calendar c = Calendar.getInstance();
    int day_of_week = c.get(Calendar.DAY_OF_WEEK);
    private TextView data;


    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchbar=(EditText)findViewById(R.id.editText);
        data=(TextView)findViewById(R.id.textView11);
        data.setText("24-04-2016");
        spinner=(Spinner)findViewById(R.id.spinner);
        list.add("Today");
        list.add("Tomorrow");
        for(int i=0;i<8;i++){
            list.add("In "+(i+2)+" Days");
        }






        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.spinneritem, list);
        adapter.setDropDownViewResource(R.layout.spinneritem);
        spinner.setAdapter(adapter);



        button=(Button)findViewById(R.id.button);

    }

    public void komar(View view){
        boolean e=false;
        Intent i=new Intent(this,Results.class);
        if(searchbar.getText().toString().length()==0) {
            searchbar.setError("Enter city!");
            e=false;
        }else
        {
         String poraka = searchbar.getText().toString();
            i.putExtra("komar",poraka);
            e=true;
        }
        String meh="";
        int selected = spinner.getSelectedItemPosition();
        i.putExtra("komar1",meh+selected);


        if(e)
        startActivity(i);
    }
}
