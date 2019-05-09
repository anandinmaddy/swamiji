package com.sastra.im037.sastraprakasika.mediautil;

public class MediaItem {
	String title="";
	String artist="";
	String album="";
	String path="";
	String duration="";
	long albumId=345;
	String composer="";
	String album_img;
	String alpha_letter="";
	String type="";
	String classname="";
	String custom="";

	public  MediaItem(){

	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getCustom() {
		return custom;
	}

	public void setCustom(String custom) {
		this.custom = custom;
	}

	public String getAlpha_letter() {
		return alpha_letter;
	}

	public void setAlpha_letter(String alpha_letter) {
		this.alpha_letter = alpha_letter;
	}

	@Override
	public String toString() {
		return title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public long getAlbumId() {
		return albumId;
	}

	public void setAlbumId(long albumId) {
		this.albumId = albumId;
	}

	public String getComposer() {
		return composer;
	}

	public void setComposer(String composer) {
		this.composer = composer;
	}

	public String getAlbum_img() {
		return album_img;
	}

	public void setAlbum_img(String album_img) {
		this.album_img = album_img;
	}
}
