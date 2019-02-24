package com.example.messagetimer;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddTemplateFragment extends DialogFragment
{
    private EditText textOfTemplate;
    private View rootView;

    public interface AddClassDialogListener
    {
        void onDialogPositiveClick(EditText editText);
        void onDialogNegativeClick();
    }

    AddClassDialogListener mListener;

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try
        {
            mListener = (AddClassDialogListener) activity;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString() + " must implement NoticeDialogListener");
        }
    }

    @Override
    public void onDetach()
    {
        // => avoid leaking
        mListener = null;
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_add_template, container, false);
        textOfTemplate = (EditText) rootView.findViewById(R.id.new_template);

        return rootView;
    }

    //Creating custom dialog based on XML
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.fragment_add_template, null);
        final EditText editText = linearLayout.findViewById(R.id.new_template);

        builder.setTitle(getResources().getString(R.string.add_template));

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(linearLayout);
                // Add action buttons
                builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        mListener.onDialogPositiveClick(editText);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        dismiss();
                        mListener.onDialogNegativeClick();
                    }
                });
        return builder.create();
    }

}
