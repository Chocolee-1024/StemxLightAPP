package com.example.light.ui.map;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.light.R;

import org.osmdroid.tileprovider.IRegisterReceiver;
import org.osmdroid.tileprovider.modules.ArchiveFileFactory;
import org.osmdroid.tileprovider.modules.IArchiveFile;
import org.osmdroid.tileprovider.modules.OfflineTileProvider;
import org.osmdroid.tileprovider.tilesource.FileBasedTileSource;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.tileprovider.util.SimpleRegisterReceiver;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import java.io.File;
import java.util.Set;

public class OfflineMapFragment extends Fragment {
    private MapView mapView;
    private Context context;
    private File file = new File(Environment.getExternalStorageDirectory().getPath()+"/Download", "taiwan.mbtiles");
    public OfflineMapFragment(Activity context) {
        this.context = context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_offline_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view) {
        mapView = (MapView) view.findViewById(R.id.offline_map_fragment);
        initOfflineMapOverlays(mapView);
    }

    private void initOfflineMapOverlays(MapView mapView) {
        // 獲取地圖的最大和最小邊界
        final BoundingBox taiwanBounds = new BoundingBox(
                21.95, 122.9151, // 南西角座標：南緯20.5955度，東經122.9151度
                25.3581, 119.3277  // 北東角座標：北緯25.3581度，西經119.3277度
        );
        if (mapView.getOverlays().size()<=0) {
            mapViewOtherData(mapView);
            mapView.setTileSource(TileSourceFactory.MAPNIK);
            mapView.setDrawingCacheEnabled(true);
            mapView.setMinZoomLevel(9.9); // 地圖最大缩小範圍
            mapView.setScrollableAreaLimitDouble(taiwanBounds);
            mapView.getController().setZoom(9.9); // 預設地圖範圍
            mapView.getController().setCenter(new GeoPoint(21.95, 121));
            mapView.setUseDataConnection(true);
            mapView.setMultiTouchControls(true);// 觸控放大缩小
            mapView.getOverlayManager().getTilesOverlay().setEnabled(true);
        }
    }
    public void mapViewOtherData(MapView mapView){
        String fileName = "test.mbtiles";
        if (!file.exists()) {
            mapView.setTileSource(TileSourceFactory.MAPNIK);
        }else {
            fileName = fileName.substring(fileName.lastIndexOf(".") + 1);
            if (fileName.length() == 0)
                return;
            if (ArchiveFileFactory.isFileExtensionRegistered(fileName)) {
                try {
                    OfflineTileProvider tileProvider = new OfflineTileProvider((IRegisterReceiver) new SimpleRegisterReceiver(context), new File[] { file });
                    mapView.setTileProvider(tileProvider);

                    String source = "";
                    IArchiveFile[] archives = tileProvider.getArchives();
                    if (archives.length > 0) {
                        Set<String> tileSources = archives[0].getTileSources();
                        if (!tileSources.isEmpty()) {
                            source = tileSources.iterator().next();
                            mapView.setTileSource(FileBasedTileSource.getSource(source));
                        } else {
                            mapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
                        }

                    } else
                        mapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
                    mapView.invalidate();
                    return;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}