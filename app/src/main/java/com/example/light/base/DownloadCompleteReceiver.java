package com.example.light.base;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.File;

public class DownloadCompleteReceiver extends BroadcastReceiver {
    private Long download_id1;
    private File filePath;
    public void getIdAndFile(Long id, File file) {
        download_id1 = id;
        filePath = file;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        long downloaded_id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,-1);


        if(download_id1 == downloaded_id){
            Log.e("Lee","下載完成："+"downloaded_id ="+downloaded_id);
            Log.e("Lee","FilePath ="+filePath.getPath());
        }
    }
}
