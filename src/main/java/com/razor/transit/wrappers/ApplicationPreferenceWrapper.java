package com.razor.transit.wrappers;

import android.content.Context;

public class ApplicationPreferenceWrapper {

    private ApplicationPreferences preferences;

    public ApplicationPreferenceWrapper(final Context context) {
        this.preferences = new ApplicationPreferences(context);
    }

    public ApplicationPreferences getGeneralPreferences() {
        return preferences;
    }

}