package etu1989.framework;

import java.util.HashMap;

public class ModelView {
    String url;
    HashMap<String,Object> data= new HashMap<>();

    public HashMap<String, Object> getData() {
        return data;
    }
    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    
    public ModelView(){ }

    public ModelView(String url){
        setUrl(url);
    }
    public void addItem(String key,Object ob){
        this.getData().put(key, ob);
    }
}