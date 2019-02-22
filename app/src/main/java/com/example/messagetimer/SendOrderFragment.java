package com.example.messagetimer;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;


public class SendOrderFragment extends ListFragment
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        ArrayAdapter<String> templateAdapter = new ArrayAdapter<String>
                (
                        inflater.getContext(),
                        android.R.layout.simple_list_item_1,
                        getResources().getStringArray(R.array.templates)
                );

        setListAdapter(templateAdapter);

        return inflater.inflate(R.layout.fragment_templates_list, container, false);
    }

}
