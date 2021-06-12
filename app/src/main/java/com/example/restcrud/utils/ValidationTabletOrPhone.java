package com.example.restcrud.utils;

import android.content.Context;
import android.content.res.Configuration;

public class ValidationTabletOrPhone {

    public boolean validation(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

}
