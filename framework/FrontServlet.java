package etu1989.framework.servlet;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.lang.reflect.Field;

import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import etu1989.framework.Mapping;
import etu1989.framework.*;
import etu1989.annotation.*;


public class FrontServlet extends HttpServlet{
    HashMap<String,Mapping> UrlMapping=  new HashMap<>();
    
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
        res.setContentType("text/plain");
        PrintWriter out = res.getWriter();
        out.println(req.getRequestURL());
        out.println(UrlMapping);
        processRequest(req, res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
        res.setContentType("text/plain");
        PrintWriter out = res.getWriter();
        out.println(req.getRequestURL());
        processRequest(req, res);
    }

    public void getAllFile() throws Exception{
        String packageName = "etu1989.model";
        URL root = Thread.currentThread().getContextClassLoader().getResource(packageName.replaceAll("[.]", "\\\\"));
        File packDir = new File(root.toURI());
        System.out.println(root.toURI());
        File[] inside = packDir.listFiles(file->file.getName().endsWith(".class"));
        List<Class> lists = new ArrayList<>();
        for (File f : inside) {
            String c = packageName+"."+f.getName().substring(0, f.getName().lastIndexOf("."));
            lists.add(Class.forName(c));
        }
        for ( Class c : lists) {
            Method[] methods = c.getDeclaredMethods();
            for(Method m : methods){
                if(m.isAnnotationPresent(etu1989.annotation.Url.class)){
                    Url url= m.getAnnotation(etu1989.annotation.Url.class);
                    if(! url.key().isEmpty() && url.key() != null){
                        Mapping map = new Mapping(c.getName() , m.getName());
                        this.UrlMapping.put(url.key(),map);
                    }
                }
            }
        }
        // String map = new File( root.getFile() ) + "\\";
        // FileFilter filter = new FileFilter() {
        //     public boolean accept(File f)
        //     {
        //         return f.getName().endsWith("class");
        //     }
        // };
        // // for(File f : new File(map.replace("%20", " ")).listFiles(filter)){
        // File ff= new File(map);
        // for(File f : ff.listFiles(filter)){
        
        //     String file = f.getName().replace(".class", "");
        //     Class<?> myClass = Class.forName(packageName+"."+file);
        //     Method[] methods = myClass.getMethods();
        //     for(Method method : methods){
        //         if(method.getAnnotation(etu1989.annotation.Url.class)!=null){
        //             String url = method.getAnnotation(etu1989.annotation.Url.class).key();
        //             String[] data = url.split("-");
        //             this.UrlMapping.put(url,new Mapping(data[0], data[1]));
        //         }
        //     }
        // }
    }

    public void init() throws ServletException{
        try{
            getAllFile();            
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    

    public void processRequest(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        out.println(request.getHttpServletMapping().getMatchValue());

        out.println(request.getRequestURI());
        String url=request.getRequestURI();
        //substring maka partie de phrase
        url=url.substring(request.getContextPath().length()+1);
        // Print all elements in this mappingurls
        out.println(UrlMapping.entrySet());
        for (Map.Entry<String, Mapping> entry : UrlMapping.entrySet()) {
            out.println(entry.getKey() + " " + entry.getValue().getClassName() + " " + entry.getValue().getMethod());
        }

        if(UrlMapping.containsKey(url)){
            Mapping test= UrlMapping.get(url);
            String methodeName= test.getMethod();
            try{

                Class<?> cl = Class.forName(test.getClassName());
                Method m= null;
                Method[] methods= cl.getDeclaredMethods();
                for (Method m1 :methods) {
                    if(m1.getName().equals(methodeName) && m1.isAnnotationPresent(Url.class) ){
                        m=m1;
                        break;
                    }
                }
                Object ob = cl.getConstructor().newInstance();
                Object ob1 = m.invoke(ob);
                if(ob1 instanceof ModelView){
                    ModelView mv=(ModelView)ob1;
                    RequestDispatcher rd= request.getRequestDispatcher(mv.getUrl());
                    HashMap<String,Object> hm= mv.getData();
                    for (Map.Entry<String, Object> entry : hm.entrySet()) {
                        request.setAttribute(entry.getKey() , entry.getValue());
                    }
                    rd.forward(request,response);

                } 

            }catch (Exception e){
                e.printStackTrace(out);
            }
        }
    }

}