package controllers;

import java.lang.reflect.Array;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;



public class Channel {

    //ime, id, seznam_uporabnikov

    private String Channel_name;
    private Integer Channel_id;
    private Integer Channel_admin_id;
    private List<Integer> Channel_user_id_list ;


    public Channel(String Channel_name, Integer Channel_id, Integer Channel_admin_id) {
        List<Integer> list = new ArrayList<Integer>(Channel_admin_id);

        this.Channel_name = Channel_name;
        this.Channel_id = Channel_id;
        this.Channel_admin_id = Channel_admin_id;
        this.Channel_user_id_list = list;

    }


    public Integer getAdminId() {
        return Channel_admin_id;
    }

    public List<Integer> getChannel_user_id_list() {
        return Channel_user_id_list;
    }

    public String getChannel_name() {
        return Channel_name;
    }

    public Integer getChannel_id() {
        return Channel_id;
    }

    public Integer getChannel_admin_id() {
        return Channel_admin_id;
    }

    public void setChannel_admin_id(Integer channel_admin_id) {
        Channel_admin_id = channel_admin_id;
    }

    public void setChannel_id(Integer channel_id) {
        Channel_id = channel_id;
    }

    public void setChannel_name(String channel_name) {
        Channel_name = channel_name;
    }

    public void setChannel_user_id_list(List<Integer> channel_user_id_list) {
        Channel_user_id_list = channel_user_id_list;
    }

}