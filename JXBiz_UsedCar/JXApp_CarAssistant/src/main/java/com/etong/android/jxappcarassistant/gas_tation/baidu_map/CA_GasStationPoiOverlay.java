package com.etong.android.jxappcarassistant.gas_tation.baidu_map;

/**
 * @desc 附近加油站覆盖物
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
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.etong.android.jxappcarassistant.gas_tation.baidu_map.overlayutil.OverlayManager;
import com.etong.android.jxappcarassistant.gas_tation.javabean.Gas_StationJavabean;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class CA_GasStationPoiOverlay extends OverlayManager {

    private static final int MAX_POI_SIZE = 10;
    public List<Gas_StationJavabean> mPositionList;
    public Context mContext;
    public BaiduMap baiduMap;
    public List<Marker> markerList;

    public CA_GasStationPoiOverlay(BaiduMap baiduMap, Context context) {
      super(baiduMap);
      this.mContext = context;
        this.baiduMap=baiduMap;
  }

  public void setData(List<Gas_StationJavabean> mPositionList) {
      this.mPositionList = mPositionList;
  }

  @Override
  public boolean onMarkerClick(Marker marker) {
      if (marker.getExtraInfo() != null) {

          if (markerList == null) {
              markerList = getAllMarker();
          }

          int index = marker.getExtraInfo().getInt("index");

          for (int i = 0; i < markerList.size(); i++) {
              if (i==index) {
                  markerList.get(i).setIcon(BitmapDescriptorFactory.fromAssetWithDpi("Icon_mark_blue.png"));
                  markerList.get(i).setToTop();//设置在图层最上面
              } else {
                  if (i+1 < MAX_POI_SIZE) {
                      markerList.get(i).setIcon(BitmapDescriptorFactory.fromAssetWithDpi("Icon_mark" + (i+1) + ".png"));
                  } else {
                      markerList.get(i).setIcon(BitmapDescriptorFactory.fromAssetWithDpi("Icon_mark_circle.png"));
                  }
              }
          }

          Gas_StationJavabean poi = mPositionList.get(index);

        //显示操作
          EventBus.getDefault().post(poi,"GasStationInfo");

          return true;
      }
      return false;
  }


    public List<Marker> getAllMarker() {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (int i = 0; i < mPositionList.size(); i++) {
            LatLng poi = new LatLng(Double.valueOf(mPositionList.get(i).getLat()), Double.valueOf(mPositionList.get(i).getLon()));
            builder = builder.include(poi);
        }
        LatLngBounds latlngBounds = builder.build();
        return baiduMap.getMarkersInBounds(latlngBounds);
    }

  @Override
  public List<OverlayOptions> getOverlayOptions() {
      if ((this.mPositionList == null)
              || (this.mPositionList.size() == 0))

          return null;
      ArrayList<OverlayOptions> arrayList = new ArrayList<OverlayOptions>();
      int markerSize = 0;
      for (int i = 0; i < mPositionList.size(); i++) {
          if (this.mPositionList.get(i).getPosition() == null)
              continue;

          markerSize++;
          // 给marker加上标签
          Bundle bundle = new Bundle();
          bundle.putInt("index", i);
          LatLng point = new LatLng(Double.valueOf(mPositionList.get(i).getLat()), Double.valueOf(mPositionList.get(i).getLon()));

          if(markerSize < MAX_POI_SIZE){
              arrayList.add(new MarkerOptions()
                      .icon(BitmapDescriptorFactory.fromAssetWithDpi("Icon_mark"
                              + markerSize + ".png")).extraInfo(bundle)
                      .position(point));
          }else {
              arrayList.add(new MarkerOptions()
                      .icon(BitmapDescriptorFactory.fromAssetWithDpi("Icon_mark_circle.png")).extraInfo(bundle)
                      .position(point));
          }
      }
      return arrayList;
  }


    @Override
    public boolean onPolylineClick(Polyline polyline) {
        return false;
    }
}