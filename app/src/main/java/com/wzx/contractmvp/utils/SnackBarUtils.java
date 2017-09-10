package com.wzx.contractmvp.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * 描述 TODO
 * Created by 王治湘 on 2017/9/4.
 * version 1.0
 */

public class SnackBarUtils {

    public static void showSnackBar(@NonNull View view, @NonNull String mesaage){
        Snackbar.make(view, mesaage, Snackbar.LENGTH_LONG).show();
    }

    public static void showSnackBar(@NonNull View view, @NonNull Context mcontext, @StringRes int mesaageId){
        Snackbar.make(view, mcontext.getString(mesaageId), Snackbar.LENGTH_LONG).show();
    }
}
