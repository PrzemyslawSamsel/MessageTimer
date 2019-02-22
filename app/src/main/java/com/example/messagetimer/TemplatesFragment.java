package com.example.messagetimer;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TemplatesFragment extends ListFragment implements AdapterView.OnItemClickListener
{
    private long templateID;
    private MsgTemplate templatesHandler = new MsgTemplate();
    private View inflatedView;


//dlg.show();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        this.inflatedView = inflater.inflate(R.layout.fragment_templates, container, false);
        Button button = (Button) inflatedView.findViewById(R.id.add_template);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (
                        inflater.getContext(),
                        android.R.layout.simple_list_item_1,
                        templatesHandler.templates
                );
        setListAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //templatesHandler.addTemplate();
            }

        });

        return this.inflatedView;
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

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.add_template_dialog, null))
                // Add action buttons
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        TemplatesFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }


}
