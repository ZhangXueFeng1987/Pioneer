package com.pioneer.mapper.po;

public class MapImagesGeoLocationPO {
	private String uuid;
	private String point;//sample:POINT(121.477467 31.246182)
	
	private String lng;
	private String lat;
	
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		String[] lngLat=point.substring((point.indexOf('(')+1),point.indexOf(')')).split(" ");
		this.setLng(lngLat[0]);
		this.setLat(lngLat[1]);
//		this.point = point;
	}
	
}
