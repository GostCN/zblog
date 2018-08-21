package com.cchcz.blog.event;

import com.alibaba.fastjson.JSONObject;

public class VisitGeoEventBean {
    private String type = "visitor";
    private String address;
    private Geo location;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Geo getLocation() {
        return location;
    }

    public void setLocation(Geo location) {
        this.location = location;
    }

    public VisitGeoEventBean(String address, String lat, String lng) {
        this.address = address;
        this.location = new Geo(lat, lng);
    }

    public String toEventStr() {
        return "data:" + JSONObject.toJSONString(this) + "\n\n";
    }

    public String toJSONString() {
        return JSONObject.toJSONString(this);
    }

    class Geo {
        private String lat;
        private String lng;

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public Geo(String lat, String lng) {
            this.lat = lat;
            this.lng = lng;
        }
    }

}

