/**
 * 
 */
package com.example.carldemo.audioplayer;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * @author Peichen Xu
 * 
 */
public class AudioItem {

	private String id;
	private String path;
	private String name;
	private long size;
	private int duration;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the size
	 */
	public long getSize() {
		return size;
	}

	/**
	 * @param size
	 *            the size to set
	 */
	public void setSize(long size) {
		this.size = size;
	}

	/**
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * @param duration
	 *            the duration to set
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

	public static String toJson(AudioItem item) {
		return new Gson().toJson(item);
	}

	public static AudioItem fromJson(String itemString) {
		try {
			return new Gson().fromJson(itemString, AudioItem.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if (o == null) {
			return false;
		}
		AudioItem item = (AudioItem) o;
		return this.id.equals(item.id);
	}

}
