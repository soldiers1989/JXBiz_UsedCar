package com.etong.android.jxappcarassistant.gas_tation.baidu_map;

/**
 * @desc 覆盖物
 * @createtime 2016/9/23 0023--13:46
 * @Created by wukefan.
 */

import android.content.Context;
import android.os.Bundle;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.etong.android.jxappcarassistant.gas_tation.baidu_map.overlayutil.OverlayManager;

import java.util.ArrayList;
import java.util.List;

public class CA_MyPoiOverlay extends OverlayManager {

    private static final int MAX_POI_SIZE = 10;
    public BaiduMap baiduMap;
    public PoiResult poiResult = null;
    public Context mContext;
    public PoiSearch mPoiSearch;
    public List<Marker> markerList;

    public CA_MyPoiOverlay(BaiduMap baiduMap, Context context, PoiSearch poiSearch) {
        super(baiduMap);
        this.baiduMap = baiduMap;
        this.mContext = context;
        this.mPoiSearch = poiSearch;
    }

    public void setData(PoiResult poiResult) {
        this.poiResult = poiResult;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.getExtraInfo() != null) {

            if (markerList == null) {
                markerList = getAllMarker();
            }

            int index = marker.getExtraInfo().getInt("index");

            for (int i = 0; i < markerList.size(); i++) {
                if (i == index) {
                    markerList.get(i).setIcon(BitmapDescriptorFactory.fromAssetWithDpi("Icon_mark_blue.png"));
                    markerList.get(i).setToTop();//设置在图层最上面
                } else {
                    if (i + 1 < MAX_POI_SIZE) {
                        markerList.get(i).setIcon(BitmapDescriptorFactory.fromAssetWithDpi("Icon_mark" + (i + 1) + ".png"));
                    } else {
                        markerList.get(i).setIcon(BitmapDescriptorFactory.fromAssetWithDpi("Icon_mark_circle.png"));
                    }
                }
            }

            PoiInfo poi = poiResult.getAllPoi().get(index);
            // 详情搜索
            mPoiSearch.searchPoiDetail((new PoiDetailSearchOption())
                    .poiUid(poi.uid));
            return true;
        }
        return false;
    }

    public List<Marker> getAllMarker() {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (int i = 0; i < poiResult.getAllPoi().size(); i++) {
            builder = builder.include(poiResult.getAllPoi().get(i).location);
        }
        LatLngBounds latlngBounds = builder.build();
        return baiduMap.getMarkersInBounds(latlngBounds);
    }

    @Override
    public List<OverlayOptions> getOverlayOptions() {
        if ((this.poiResult == null)
                || (this.poiResult.getAllPoi() == null))
            return null;
        ArrayList<OverlayOptions> arrayList = new ArrayList<OverlayOptions>();
        int markerSize = 0;
        for (int i = 0; i < poiResult.getAllPoi().size(); i++) {
            if (this.poiResult.getAllPoi().get(i).location == null)
                continue;

            markerSize++;
            // 给marker加上标签
            Bundle bundle = new Bundle();
            bundle.putInt("index", i);
            if (markerSize < MAX_POI_SIZE) {
                arrayList.add(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromAssetWithDpi("Icon_mark"
                                + markerSize + ".png")).extraInfo(bundle)
                        .position(poiResult.getAllPoi().get(i).location));
            } else {
                arrayList.add(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromAssetWithDpi("Icon_mark_circle.png")).extraInfo(bundle)
                        .position(poiResult.getAllPoi().get(i).location));
            }
        }
        return arrayList;
    }

    /**
     * 往图片添加数字
     */
//  private Bitmap setNumToIcon(int num) {
//      BitmapDrawable bd = (BitmapDrawable) mContext.getResources().getDrawable(
//              R.mipmap.icon_gcoding);
//      Bitmap bitmap = bd.getBitmap().copy(Bitmap.Config.ARGB_8888, true);
//      Canvas canvas = new Canvas(bitmap);
//
//      Paint paint = new Paint();
//      paint.setColor(Color.WHITE);
//      paint.setAntiAlias(true);
//      int widthX;
//      int heightY = 0;
//      if (num < 10) {
//          paint.setTextSize(25);
//          widthX = 8;
//          heightY = 6;
//      } else {
//          paint.setTextSize(20);
//          widthX = 11;
//      }
//
//      canvas.drawText(String.valueOf(num),
//              ((bitmap.getWidth() / 2) - widthX),
//              ((bitmap.getHeight() / 2) + heightY), paint);
//      return bitmap;
//  }
    @Override
    public boolean onPolylineClick(Polyline polyline) {
        return false;
    }
}