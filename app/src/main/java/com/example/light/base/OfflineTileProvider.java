package com.example.light.base;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.Tile;
import com.google.android.gms.maps.model.TileProvider;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

public class OfflineTileProvider implements TileProvider {

    private static final String TILE_PATH_FORMAT = "C:/Android Studio/Light/app/src/main/res/raw/taiwanemap.mbtiles";

    private final AssetManager assetManager;

    public OfflineTileProvider(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    @Override
    public Tile getTile(int x, int y, int zoom) {
        InputStream in = null;
        ByteArrayOutputStream buffer = null;

        try {
            // 組合出圖塊的本地路徑
            String tilePath = String.format(Locale.getDefault(), TILE_PATH_FORMAT, zoom, x, y);

            // 讀取圖塊數據
            in = assetManager.open(tilePath);
            buffer = new ByteArrayOutputStream();
            int nRead;
            byte[] data = new byte[16384];
            while ((nRead = in.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
            byte[] tileBytes = buffer.toByteArray();

            // 創建並返回 Tile 物件
            return new Tile(256, 256, tileBytes);
        } catch (IOException e) {
            return null;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (buffer != null) {
                    buffer.close();
                }
            } catch (IOException ignored) {
            }
        }
    }
}
