package com.example.light.ui.mostPost;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.light.R;
import com.example.light.ui.mostPost.MoresPostAdapter;
import com.example.light.ui.mostPost.MorsePostContract;

public class AbbreviationMorseCodeDialog {
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private RecyclerView abbreviation_recyclerview;
    private MoresPostAdapter moresPostAdapter;
    public AbbreviationMorseCodeDialog(MorsePostContract.view view,Activity activity) {

        dialogBuilder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.abbreviation_morse_code_dialog, null);
        abbreviation_recyclerview= dialogView.findViewById(R.id.abbreviation_morse_code_recyclerview);
        dialog = dialogBuilder.create();
        dialog.setView(dialogView);

        moresPostAdapter = new MoresPostAdapter(view);
        abbreviation_recyclerview.setLayoutManager(new LinearLayoutManager(activity));
        abbreviation_recyclerview.setAdapter(moresPostAdapter);




    }

    public void show() {
        dialog.show();
    }
    public void dismiss(){
        dialog.dismiss();
    }



}
