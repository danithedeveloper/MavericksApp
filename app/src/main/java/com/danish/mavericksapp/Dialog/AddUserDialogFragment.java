package com.danish.mavericksapp.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.danish.mavericksapp.Model.User;
import com.danish.mavericksapp.R;
import com.danish.mavericksapp.Utility.Constants;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by DaNii on 29/09/2016.
 */

public class AddUserDialogFragment extends DialogFragment implements View.OnClickListener {

    View customView;
    EditText mFirstNameET, mLastNameET;
    Button mCancelBtn, mAddBtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        customView = inflater.inflate(R.layout.add_user_dailog_fragment, null);

        mFirstNameET = (EditText) customView.findViewById(R.id.firstNameET);
        mLastNameET = (EditText) customView.findViewById(R.id.lastNameET);

        mCancelBtn = (Button) customView.findViewById(R.id.cancelBtn);
        mAddBtn = (Button) customView.findViewById(R.id.addBtn);
        mCancelBtn.setOnClickListener(this);
        mAddBtn.setOnClickListener(this);

        return customView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancelBtn:
                dismiss();
                break;
            case R.id.addBtn:
                View focusView = null;
                if (TextUtils.isEmpty(mFirstNameET.getText().toString())) {
                    mFirstNameET.requestFocus();
                    mFirstNameET.setError(getString(R.string.error_first_name));
                    return;
                }
                if (TextUtils.isEmpty(mLastNameET.getText().toString())) {
                    mLastNameET.requestFocus();
                    mLastNameET.setError(getString(R.string.error_last_name));
                    return;
                }
                String firstName = mFirstNameET.getText().toString();
                String lastName = mLastNameET.getText().toString();

                User user = new User(firstName,lastName);

                sendResult(Activity.RESULT_OK, user);
                dismiss();
                break;
            default:
                break;
        }
    }


    public static interface OnCompleteListener {
        public abstract void onComplete(int result, User user);
    }

    private OnCompleteListener mListener;

    // make sure the Activity implemented it
    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            this.mListener = (OnCompleteListener) activity;
        } catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }

    public void sendResult(int result, User user) {
        this.mListener.onComplete(result, user);
    }
}
