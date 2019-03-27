package com.example.im037.sastraprakasika.OnlinePlayer;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity
public class ItemSong {

	@PrimaryKey(autoGenerate = true)
	@NonNull
	int id;
	@ColumnInfo(name = "catId")

	String catId;

	@ColumnInfo(name = "catName")
	String catName="";

	@ColumnInfo(name = "artist")
	String artist="";

	@ColumnInfo(name = "url")
	String url="";

	@ColumnInfo(name = "imageBig")
	String imageBig="";

	@ColumnInfo(name = "imageSmall")
	String imageSmall="";

	@ColumnInfo(name = "title")
	String title="";

	@ColumnInfo(name = "duration")
	String duration="";

	@ColumnInfo(name = "description")
	String description="";

	@ColumnInfo(name = "totalRate")
	String totalRate="";

	@ColumnInfo(name = "track_id")
	String trackId="";

	@ColumnInfo(name = "averageRating")
	String averageRating="0";

	@ColumnInfo(name = "views")
	String views="";

	@ColumnInfo(name = "downloads")
	String downloads="";

	@ColumnInfo(name = "userRating")
	String userRating="";
	@ColumnInfo(name = "className")
	String className="";

	@ColumnInfo(name = "image")
	public String image;

	@ColumnInfo(name = "isSelected")
	Boolean isSelected = false;

	public ItemSong() {
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setCatId(String catId) {
		this.catId = catId;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setImageBig(String imageBig) {
		this.imageBig = imageBig;
	}

	public void setImageSmall(String imageSmall) {
		this.imageSmall = imageSmall;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setViews(String views) {
		this.views = views;
	}

	public void setDownloads(String downloads) {
		this.downloads = downloads;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getTrackId() {
		return trackId;
	}

	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}

	public ItemSong(int id, String catId, String catName, String artist, String url, String imageBig, String imageSmall, String title, String Duration, String Description, String totalRate, String averageRating, String views, String downloads,String cname) {
		this.id = id;
		this.catId = catId;
		this.catName = catName;
		this.artist = artist;
		this.url = url;
		this.imageBig = imageBig;
		this.imageSmall = imageSmall;
		this.title = title;
		this.duration = Duration;
		this.description = Description;
		this.totalRate = totalRate;
		this.averageRating = averageRating;
		this.views = views;
		this.downloads = downloads;
		this.className = cname;

	}

	public ItemSong(int id, String artist, String url, String image, String title, String Duration, String Description) {
		this.id = id;
		this.artist = artist;
		this.url = url;
		this.image = image;
		this.title = title;
		this.duration = Duration;
		this.description = Description;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public int getId() {
		return id;
	}
	
	public String getCatId() {
		return catId;
	}

	public String getArtist() {
		return artist;
	}
	
	public String getCatName() {
		return catName;
	}
	
	public String getUrl() {
		return url;
	}
	
	public String getImageBig() {
		return imageBig;
	}

	public String getImageSmall() {
		return imageSmall;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getDuration() {
		return duration;
	}
	
	public String getDescription() {
		return description;
	}

	public String getBitmap() {
		return image;
	}

	public String getTotalRate() {
		return totalRate;
	}

	public String getAverageRating() {
		return averageRating;
	}

	public String getViews() {
		return views;
	}

	public String getDownloads() {
		return downloads;
	}

	public String getUserRating() {
		return userRating;
	}

	public void setUserRating(String userRating) {
		this.userRating = userRating;
	}

	public void setAverageRating(String averageRating) {
		this.averageRating = averageRating;
	}

	public void setTotalRate(String totalRate) {
		this.totalRate = totalRate;
	}

	public Boolean getSelected() {
		return isSelected;
	}

	public void setSelected(Boolean selected) {
		isSelected = selected;
	}
}