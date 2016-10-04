package com.danish.mavericksapp.Model;

import com.danish.mavericksapp.Utility.Constants;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by DaNii on 28/09/2016.
 */

public class User {
    private String firstName;
    private String lastName;

    public User() {
    }

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(JSONObject jsonObject) throws JSONException {
        if (!jsonObject.isNull(Constants.KeyValues.FIRST_NAME)) {
            setFirstName(jsonObject.getString(Constants.KeyValues.FIRST_NAME));
        }
        if (!jsonObject.isNull(Constants.KeyValues.LAST_NAME)) {
            setLastName(jsonObject.getString(Constants.KeyValues.LAST_NAME));
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
