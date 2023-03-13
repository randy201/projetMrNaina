package etu1989.framework.servlet;
public class Mapping{
    String className;
    String Method;
    public void setClassName(String className) {
        this.className = className;
    }
    public void setMethod(String method) {
        Method = method;
    public String getClassName(){
        return className;
    }
    public String getMethod() {
        return Method;
    }

    public Mapping(){}
}