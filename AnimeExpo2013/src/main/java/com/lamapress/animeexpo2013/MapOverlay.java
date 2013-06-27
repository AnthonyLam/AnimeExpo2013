package com.lamapress.animeexpo2013;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Tile;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileProvider;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MapOverlay implements TileProvider {

    private static final int TILE_WIDTH = 256;
    private static final int TILE_HEIGHT = 256;
    private static final int BUFFER_SIZE = 16 * 1024;

    LatLng ne;
    LatLng sw;
    LatLngBounds bound;
    public BitmapDescriptor ground;
    float angle;
    int level;

    private AssetManager managerAssets;

    public MapOverlay(AssetManager assets,int level){
        managerAssets = assets;
        this.level =level;
    }

    public void setBitMapRange(LatLng ne, LatLng sw,float angle ){
        bound = new LatLngBounds(sw,ne);
        this.angle = angle;
   }
    public void setBitMap(GoogleMap map,int resourceId,Context context,int reqWidth,int reqHeight){
        if(map != null)
        {
            ground = BitmapDescriptorFactory.fromBitmap(decodeSampleBitmapFromResource(context.getResources(),
                    resourceId,
                    reqWidth,reqHeight));
        }
    }

    public GroundOverlay setGround(GoogleMap map){
        return map.addGroundOverlay(new GroundOverlayOptions()
                .image(ground)
                .positionFromBounds(bound)
                .bearing(angle));
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,int reqWidth,int reqHeight){
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if(height > reqHeight || width > reqWidth){
            final int heightRatio = Math.round((float)height/(float)reqHeight);
            final int widthRatio = Math.round((float)width/(float)reqWidth);

            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampleBitmapFromResource(Resources res, int resId,
                                                        int reqWidth, int reqHeight){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res,resId,options);

        options.inSampleSize = calculateInSampleSize(options,reqWidth,reqHeight);

        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res,resId,options);
    }

    public int convertTmsGoogle(int yVal,int zoom){
        int yMax = 1 << zoom;
        yVal = yMax - yVal - 1;
        return yVal;
    }

    @Override
    public Tile getTile(int x,int y, int zoom){
        byte[] image = readTileImage(x,y,zoom);
        return image == null ? NO_TILE : new Tile(TILE_WIDTH,TILE_HEIGHT,image);
    }

    private byte[] readTileImage(int x, int y, int zoom){
        InputStream in = null;
        ByteArrayOutputStream buffer = null;

        try {
            in = managerAssets.open(getTileFilename(x, y, zoom));
            buffer = new ByteArrayOutputStream();

            int nRead;
            byte[] data = new byte[BUFFER_SIZE];

            while ((nRead = in.read(data, 0, BUFFER_SIZE)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();

            return buffer.toByteArray();
        } catch (IOException e) {
            //e.printStackTrace();
            return null;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        } finally {
            if (in != null) try { in.close(); } catch (Exception ignored) {}
            if (buffer != null) try { buffer.close(); } catch (Exception ignored) {}
        }
    }

    private String getTileFilename(int x, int y, int zoom){
        String folder = "";
        switch(level){
            case 0:{
                folder = "ax_map";
                break;
            }
            case 1:{
                folder = "mapone";
                break;
            }
            case 2:{
                folder = "maptwo";
                break;
            }
        }
        return "maps/" + folder + '/' + zoom + '/' + x + '/' + convertTmsGoogle(y,zoom) + ".png";
    }
}

