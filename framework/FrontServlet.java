package etu1989.framework.servlet;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.util.HashMap;
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
        URL root = Thread.currentThread().getContextClassLoader().getResource(packageName.replace(".", "/"));
        String map=new File(root.getFile())+"\\";
        FileFilter filter = new FileFilter() {
            public boolean accept(File f)
            {
                return f.getName().endsWith("class");
            }
        };
        for(File f : new File(map.replace("%20", " ")).listFiles(filter)){
            String file = f.getName().replace(".class", "");
            Class<?> myClass = Class.forName(packageName+"."+file);
            Method[] methods = myClass.getMethods();
            for(Method method : methods){
                if(method.getAnnotation(etu1989.annotation.Url.class)!=null){
                    String url = method.getAnnotation(etu1989.annotation.Url.class).key();
                    String[] data = url.split("-");
                    this.UrlMapping.put(url,new Mapping(data[0], data[1]));
                }
            }
        }
    }

    public void init() throws ServletException{
        try{
            getAllFile();            
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    // public void init() throws ServletException {
    //     try {
    //         UrlMapping = new HashMap<String, Mapping>();
    //         String packageName = "etu1989.model";
    //         //File folder = new File(req.getRequestURL());
    //         URL root = Thread.currentThread().getContextClassLoader().getResource(packageName.replace(".", "/"));
    //         String map=new File(root.getFile())+"\\";
    //         System.out.println(map);
    //         System.out.println(new File(map).listFiles());

    //         for (File file : new File(root.getFile()).listFiles()) {
    //             if (file.getName().contains(".class")) {
    //                 String className = file.getName().replaceAll(".class", "");
    //                 Class<?> cls = Class.forName(packageName + "." + className);
    //                 for (Method method : cls.getDeclaredMethods()) {
    //                     if (method.isAnnotationPresent(url.class)) {
    //                         UrlMapping.put(method.getAnnotation(url.class).value(), new Mapping(cls.getName(), method.getName()));
                            
    //                     }
    //                 }
    //             }
    //         }
    //     } catch (Exception e) {
    //         System.out.println(e);
    //     }
    // }

    public void processRequest(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException {
        // PrintWriter out = response.getWriter();
        // out.println(request.getHttpServletMapping().getMatchValue());
        // // Print all elements in this mappingurls
        // out.println(UrlMapping.entrySet());
        // for (Map.Entry<String, Mapping> entry : UrlMapping.entrySet()) {
        //     out.println(entry.getKey() + " " + entry.getValue().getClassName() + " " + entry.getValue().getMethod());
        // }
    }

}