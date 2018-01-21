package com.noscale.noscale_motocare.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import com.noscale.noscale_motocare.R;

/**
 * Created by kurniawanrizzki on 20/01/18.
 */

public class MLoadingDialog {

    private Dialog dialog;

    public MLoadingDialog(Context context) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.loading_dialog);
        dialog.setCanceledOnTouchOutside(false);
    }

    public void show () {
        dialog.show();
    }

    public void hide () {
        dialog.dismiss();
    }

}
