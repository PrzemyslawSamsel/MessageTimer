package com.example.messagetimer;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class TemplatesFragment extends ListFragment implements AdapterView.OnItemClickListener
{
    private long templateID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        String[] template = new String[MsgTemplate.templates.length];

        for(int i = 0; i< template.length; i++)
        {
            template[i] = MsgTemplate.templates[i].getContent();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (
                inflater.getContext(), android.R.layout.simple_list_item_1,
                template
                );
        setListAdapter(adapter);

        return inflater.inflate(R.layout.fragment_templates, container, false);
    }

//    @Override
//    public void onStart()
//    {
//        super.onStart();
//
//        //Gets the fragment's root view
//        View view = getView();
//
//        if(view != null)
//        {
//            TextView title = (TextView) view.findViewById(R.id.);
//            MsgTemplate templateshandler = MsgTemplate.templates[(int) templateID];
//            title.setText(templateshandler.getContent());
//        }
//    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,long id)
    {
        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
    }

    public void setTemplateID(long id)
    {
        this.templateID = id;
    }

}
