package com.pfg.asset.dto;

import java.io.Serializable;

public class TrackInfo implements Serializable{
	private static final long serialVersionUID = 1L;

	private String trackName;
	private String trackDescription;
	
	public TrackInfo() {}
	
	public TrackInfo(String trackName, String desc) {
		this.trackName = trackName;
		this.trackDescription = desc;
	}

	public String getTrackName() {
		return trackName;
	}

	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}

	public String getTrackDescription() {
		return trackDescription;
	}

	public void setTrackDescription(String trackDescription) {
		this.trackDescription = trackDescription;
	}

	@Override
	public String toString() {
		return "TrackInfo [trackName=" + trackName + ", trackDescription=" + trackDescription + "]";
	}

}
