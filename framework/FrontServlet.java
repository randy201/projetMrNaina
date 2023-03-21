package etu1989.framework.servlet;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.util.HashMap;

import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import etu1989.annotation.url;
import etu1989.framework.Mapping;

public class FrontServlet extends HttpServlet{
    HashMap<String,Mapping> MappingUrls;
    
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
        res.setContentType("text/plain");
        PrintWriter out = res.getWriter();
        out.println(req.getRequestURL());
        processRequest(req, res);

    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
        res.setContentType("text/plain");
        PrintWriter out = res.getWriter();
        out.println(req.getRequestURL());
        processRequest(req, res);

    }

    public void init() throws ServletException {
        try {
            MappingUrls = new HashMap<String, Mapping>();
            String packageName = "etu1989.model";
            //File folder = new File(req.getRequestURL());
            URL root = Thread.currentThread().getContextClassLoader().getResource(packageName.replace(".", "/"));
            String map=new File(root.getFile())+"\\";
            System.out.println(map);
            System.out.println(new File(map).listFiles());

            for (File file : new File(root.getFile()).listFiles()) {
                if (file.getName().contains(".class")) {
                    String className = file.getName().replaceAll(".class", "");
                    Class<?> cls = Class.forName(packageName + "." + className);
                    for (Method method : cls.getDeclaredMethods()) {
                        if (method.isAnnotationPresent(url.class)) {
                            MappingUrls.put(method.getAnnotation(url.class).value(), new Mapping(cls.getName(), method.getName()));
                            
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        out.println(request.getHttpServletMapping().getMatchValue());
        // Print all elements in this mappingurls
        out.println(MappingUrls.entrySet());
        for (Map.Entry<String, Mapping> entry : MappingUrls.entrySet()) {
            out.println(entry.getKey() + " " + entry.getValue().getClassName() + " " + entry.getValue().getMethod());
        }
    }

}