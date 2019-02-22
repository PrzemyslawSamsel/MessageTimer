package com.example.messagetimer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class TemplatesFragment extends ListFragment
{

    //387 - stopwatch
    private long templateID;


    //444 page
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

//    @Override
//    public void onStart()
//    {
//        super.onStart();
//
//        View view = getView();
//
//        if(view != null)
//        {
//            TextView title = (TextView) view.findViewById(R.id.template);
//            MsgTemplates template = MsgTemplates.templates[(int) templateID];
//            title.setText(template.getContent());
//        }
//
//    }

//    public void setTemplate(long id)
//    {
//        this.templateID = id;
//    }

}
