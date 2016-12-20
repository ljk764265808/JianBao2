package com.example.k.jianbao2.been;

/**
 * Created by admin on 2016/11/28.
 */

public class Login {

    /**
     * status : 200
     * info : 成功
     * data : {"id":2,"name":"钟移动","state":0}
     * token : 13956272CE544101B36C7D37E91BE59E
     */

    private String status;
    private String info;
    /**
     * id : 2
     * name : 钟移动
     * state : 0
     */

    private DataBean data;
    private String token;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static class DataBean {
        private int id;
        private String name;
        private int state;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }
    }
}
