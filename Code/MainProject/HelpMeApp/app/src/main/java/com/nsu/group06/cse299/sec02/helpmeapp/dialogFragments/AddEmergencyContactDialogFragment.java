package com.nsu.group06.cse299.sec02.helpmeapp.dialogFragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.nsu.group06.cse299.sec02.helpmeapp.R;
import com.nsu.group06.cse299.sec02.helpmeapp.models.EmergencyContact;

public class AddEmergencyContactDialogFragment extends DialogFragment {

    // ui
    private EditText mUsernameEditText, mPhoneNumberEditText;
    // model
    EmergencyContact mEmergencyContact;

    // interface to communicate with calling activity
    private InputListener mInputListener;

    public AddEmergencyContactDialogFragment(InputListener mInputListener) {
        this.mInputListener = mInputListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.add_emergency_contact_dialog, null))

                .setPositiveButton(R.string.add, (dialog, id) -> validateAndSetupModel())

                .setNegativeButton(R.string.cancel, (dialog, id) -> AddEmergencyContactDialogFragment.this.getDialog().cancel());

        return builder.create();
    }

    /**
     * UI elements of the dialog fragment
     * need to be setup after dialog has been created
     */
    @Override
    public void onResume() {
        super.onResume();

        init();
    }

    private void init() {

        mUsernameEditText = getDialog().findViewById(R.id.emergencyContactNameEditText);
        mPhoneNumberEditText = getDialog().findViewById(R.id.emergencyContactPhoneNumberEditText);

        mEmergencyContact = new EmergencyContact();
    }

    private void validateAndSetupModel() {

        String username = mUsernameEditText.getText().toString();
        String phoneNumber = mPhoneNumberEditText.getText().toString();

        mEmergencyContact.setName(username);
        mEmergencyContact.setPhoneNumber(phoneNumber);

        if(!mEmergencyContact.isNameValid() || !mEmergencyContact.isPhoneNumberValid()){

            showToast(getString(R.string.invalid_emergency_contact));
        }

        else mInputListener.validInputTaken(mEmergencyContact);
    }

    private void showToast(String message){

        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Interface to communicate with calling Activity
     */
    public interface InputListener{

        void validInputTaken(EmergencyContact emergencyContact);
    }
}
