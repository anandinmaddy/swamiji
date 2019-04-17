package com.example.im037.sastraprakasika.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class PlayList {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "player_id")
    String player_id;

    @ColumnInfo(name = "user_id")
    String user_id;

    @ColumnInfo(name = "player_name")
    String player_name;




    public String getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(String player_id) {
        this.player_id = player_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPlayer_name() {
        return player_name;
    }

    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }


}
