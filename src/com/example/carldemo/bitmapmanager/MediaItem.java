package com.example.carldemo.bitmapmanager;

import android.graphics.Bitmap;

public class MediaItem {
	
	public int id = -1;
	public int album_id = -1;
	public String title = "";
	public String artist = "";
	public String album = "";
	public String dataRes = "";
	public String childCount = "";
	public long date = 0;
	public int duration = 0;
	private long size;
	private String mimeType;
	private String displayName;
	private Bitmap bitmap;
	
	public MediaItem() {
		
	}
	
	public MediaItem(int id, int album_id, String title, String artist, String album, String dataRes) {
		setTitle(title);
		setArtist(artist);
		setAlbum(album);
		setRes(dataRes);
	}
	
	 public MediaItem(int id, String title, String album, String artist,
	            String displayName, String mimeType, String path, long size,
	            int duration, Bitmap bitmap) {
	        this.id = id;
	        this.title = title;
	        this.album = album;
	        this.artist = artist;
	        this.displayName = displayName;
	        this.mimeType = mimeType;
	        this.dataRes = path;
	        this.size = size;
	        this.duration = duration;
	        this.bitmap = bitmap;
	    }
	
	public String getShowString(){
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(
							"title = " + title+ "\n" + 
							"artist = " + artist + "\n" + 
							"album = " + album + "\n" + 						
							"childCount = " + childCount + "\n" + 
							"date = " + date);
		
		return stringBuffer.toString();
	}
		
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = (title != null ? title : "");
	}
	
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = (artist != null ? artist : "");
	}
	
	public void setAlbum(String album) {
		this.album = (album != null ? album : "");
	}
	public String getAlbum() {
		return album;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	
	public void setAlbumId(int album_id) {
		this.album_id = album_id;
	}
	public int getAlbumId() {
		return album_id;
	}
		
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public int getDuration() {
		return duration;
	}
	
	public String getchildCount() {
		return childCount;
	}
	public void setchildCount(String childCount) {
		this.childCount = (childCount != null ? childCount : "");
	}
		
	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}
	
	public void setRes(String res) {
		dataRes = (res != null ? res : "");
	}
	public String getRes() {
		return dataRes;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		MediaItem item = (MediaItem) o;
		if (item != null && item.dataRes != null) {
			return item.dataRes.equals(this.dataRes);
		}
		return super.equals(o);
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	

}
