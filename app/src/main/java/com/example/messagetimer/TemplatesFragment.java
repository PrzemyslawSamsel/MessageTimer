package com.example.messagetimer;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TemplatesFragment extends ListFragment
{
    private long templateID;
    private View inflatedView;
    private MsgTemplate msgTemplate = new MsgTemplate();
    private ArrayAdapter<String> templateAdapter;
    private AlertDialog.Builder alertDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Setting up inflater for the fragment and button for adding templates
        this.inflatedView = inflater.inflate(R.layout.fragment_templates, container, false);
        Button button = (Button) inflatedView.findViewById(R.id.add_template);

        //Creates a list to display it in fragment
        templateAdapter = new ArrayAdapter<String>
                (
                        inflater.getContext(),
                        android.R.layout.simple_list_item_1,
                        msgTemplate.templates
                );
        setListAdapter(templateAdapter);

        //Setting listener for add template button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                FragmentManager fm = getFragmentManager();
                AddTemplateFragment dialogFragment = new AddTemplateFragment();
                dialogFragment.show(fm, getResources().getString(R.string.add_template));
            }

        });

        return this.inflatedView;
    }

    private void removeTemplateDialogCreation(final int position)
    {
        alertDialog = new AlertDialog.Builder(inflatedView.getContext());

        // Setting Dialog Title
        alertDialog.setTitle("Confirm Delete...");

        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want delete this?");

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog,int which)
            {
                //Deleting item when user long clicks on it
                templateAdapter.remove(templateAdapter.getItem(position));
                templateAdapter.notifyDataSetChanged();
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        ListView tmpList = (ListView) getActivity().findViewById(android.R.id.list);

        //For getting response when any of templates on the list is clicked
        super.onActivityCreated(savedInstanceState);
        tmpList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
            {
                //Move to the top fragment and move the template clicked on the list
                //So that user can see it in message
                Fragment frag = new MainFragment();

                //Replacing fragments
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, frag);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();

                Toast.makeText(getActivity(), templateAdapter.getItem(position),
                        Toast.LENGTH_LONG).show();
            }

        });

        tmpList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int position, long arg3)
            {
                //Create dialog asking for deleting template permission
                removeTemplateDialogCreation(position);

                // Showing Alert Message
                alertDialog.show();

                return true;
            }

        });
    }



    //Method called from MainActivity when AddTemplateFragment listener receives interaction with user
    //(by clicking on of buttons in Dialog)
    public void addTemplate(String template)
    {
        templateAdapter.add(template);
        templateAdapter.notifyDataSetChanged();
    }


    public void setTemplateID(long id)
    {
        this.templateID = id;
    }


}
