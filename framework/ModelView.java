package etu1989.framework;
public class ModelView {
    String url;
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
}