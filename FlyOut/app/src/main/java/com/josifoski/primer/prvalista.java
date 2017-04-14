package com.josifoski.primer;

import android.annotation.TargetApi;
import android.app.ListFragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.josifoski.primer.models.vrakja1;

import java.util.List;


public class prvalista extends Fragment {


    private ListView lista;
    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.prvalista,container,false);
        lista=(ListView)view.findViewById(R.id.listView2);
        return view;

    }


}
