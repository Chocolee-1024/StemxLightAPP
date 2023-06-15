package com.example.light.ui.map;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.light.R;
import com.example.light.base.DownloadCompleteReceiver;

import java.io.File;

public class MapsActivity extends AppCompatActivity {

    private Toolbar offline_map_toolbar;
    private Button offline_map_button;
    private Button online_map_button;
    private OfflineMapFragment offlineMapFragment;
    private GoogleMapFragment googleMapFragment;
    private Activity activity = this;
    private File file = new File(Environment.getExternalStorageDirectory().getPath()+"/Download", "taiwan.mbtiles");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        init();

    }
    private void init() {
        setupUI();
        googleMapFragment = new GoogleMapFragment(this);
        getSupportFragmentManager().beginTransaction().add(R.id.map_frameLayout,googleMapFragment).commit();
    }

    private void setupUI() {
        setupToolbar();
        setupButton();
    }

    /**設定Toolbar*/
    private void setupToolbar() {
        offline_map_toolbar = (Toolbar) findViewById(R.id.offline_map_toolbar);
        offline_map_toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        offline_map_toolbar.setNavigationOnClickListener(this::backIconClicked);
    }

    private void setupButton() {
        online_map_button = (Button) findViewById(R.id.online_map_button);
        offline_map_button = (Button) findViewById(R.id.offline_map_button);

        offline_map_button.setOnClickListener(this::offlineMapClicked);
        online_map_button.setOnClickListener(this::onlineMapClicked);
    }


    private void offlineMapClicked(View view) {
        if (!file.exists()) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setPositiveButton("下載", downloadClicked)
                    .setNegativeButton("取消", cancelClicked)
                    .setMessage("請先下載離線地圖!!")
                    .show();

        } else {
            if(offlineMapFragment == null) {
                offlineMapFragment = new OfflineMapFragment(this);
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.map_frameLayout,offlineMapFragment).commit();
        }
    }

    private void onlineMapClicked(View view) {
        if(googleMapFragment == null) {
            googleMapFragment = new GoogleMapFragment(this);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.map_frameLayout,googleMapFragment).commit();


    }
    /**back點擊*/
    private void backIconClicked(View view) {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    private DialogInterface.OnClickListener downloadClicked = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            Uri uri = Uri.parse("https://data.moi.gov.tw/MoiOD/System/DownloadFile.aspx?DATA=4BE02238-E336-4F91-91F3-869FBA4CF4DB");
            DownloadManager downloadManager = (DownloadManager) MapsActivity.this.getSystemService(DOWNLOAD_SERVICE);
            DownloadManager.Request request = new DownloadManager.Request(uri);


            request.setAllowedOverRoaming(true);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setTitle("TaiwanMapDownload");
            request.setDestinationUri(Uri.fromFile(file));

            long  download_id = downloadManager.enqueue(request);

            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(download_id);
            Cursor cursor = downloadManager.query(query);

            IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
            DownloadCompleteReceiver receiver = new DownloadCompleteReceiver();
            receiver.getIdAndFile(download_id,file);
            registerReceiver(receiver,filter);

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    boolean downloading = true;
                    while (downloading) {
                        DownloadManager.Query query = new DownloadManager.Query();
                        query.setFilterById(download_id);
                        Cursor cursor = downloadManager.query(query);

                        if (cursor != null && cursor.moveToFirst()) {
                            int downloadedBytesIndex = cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);
                            int totalBytesIndex = cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);

                            long downloadedBytes = cursor.getLong(downloadedBytesIndex);
                            long totalBytes = cursor.getLong(totalBytesIndex);

                            int progress = (int) ((downloadedBytes * 100) / totalBytes);

                            activity.runOnUiThread(()->{
                                offline_map_button.setText(progress + "%");
                                offline_map_button.setEnabled(false);
                            });
                            // 使用下载进度值进行操作
                            Log.d("Lee", "Progress: " + progress + "%");

                            int statusIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                            int status = cursor.getInt(statusIndex);
                            if (status == DownloadManager.STATUS_SUCCESSFUL) {
                                downloading = false;
                            }
                        } else {
                            downloading = false;
                            activity.runOnUiThread(()->{
                                offline_map_button.setEnabled(true);
                            });
                        }
                        if (cursor != null) {
                            cursor.close();
                        }

                        try {
                            // 添加适当的延迟，避免查询频率过高
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    activity.runOnUiThread(()->{
                        offline_map_button.setText("離線地圖");
                    });
                }
            });
            thread.start();
        }
    };

    private DialogInterface.OnClickListener cancelClicked = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    };


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}