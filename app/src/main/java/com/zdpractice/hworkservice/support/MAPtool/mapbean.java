package com.zdpractice.hworkservice.support.MAPtool;

import com.baidu.mapapi.model.LatLng;

/**
 * Created by 刘海风 on 2016/9/19.
 */

public class mapbean {
    private LatLng nodeLocation;
    private String nodeTitle;



    public void setNodeLocation(LatLng nodeLocation) {
        this.nodeLocation = nodeLocation;
    }

    public LatLng getNodeLocation() {
        return nodeLocation;
    }

    public String getNodeTitle() {
        return nodeTitle;
    }

    public void setNodeTitle(String nodeTitle) {
        this.nodeTitle = nodeTitle;
    }
}
