package etu1989.framework.servlet;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.util.*;
import java.lang.reflect.Field;

import java.io.*;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URL;

import etu1989.framework.*;
import etu1989.annotation.*;

@MultipartConfig
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
                Object ob = cl.getConstructor().newInstance();
                Method m= null;
                Method[] methods= cl.getDeclaredMethods();
                for (Method m1 :methods) {
                    if(m1.getName().equals(methodeName) && m1.isAnnotationPresent(Url.class) ){
                        m=m1;
                        break;
                    }
                }
                Parameter[] allParam = m.getParameters();
                Object[] obj =new Object[allParam.length];
                List<String> allparametre = Collections.list(request.getParameterNames());
                for (int i = 0; i < allParam.length; i++) {
                    Parameter p = allParam[i];
                    if(p.isAnnotationPresent(Param.class)){
                        Param param = p.getAnnotation(Param.class);
                        for (String inparam : allparametre) {
                            if(param.key().equals(inparam)){
                                String value = request.getParameter(inparam);
                                Object valTemp = value;
                                if(p.getType() == Integer.class){
                                    valTemp = Integer.parseInt(String.valueOf(value));
                                }else if(p.getType() == String.class){
                                    valTemp = value;
                                }else if(p.getType() == Double.class){
                                    valTemp = Double.parseDouble(value);
                                }else if(p.getType() == Boolean.class){
                                    valTemp = Boolean.parseBoolean(value);
                                }else if(p.getType() == Date.class){//sql.date
                                   valTemp = java.sql.Date.valueOf(value);
                                }
                                obj[i]= valTemp;
                                break;
                            }
                        }
                    }
                }



                Field[] allf= cl.getDeclaredFields();
                for (Field f : allf) {
                    for (String inparam : allparametre) {
                        if(f.getName().equals(inparam) && f.getType() != etu1989.framework.Upload.class){
                            String stock = f.getName();
                            stock = stock.substring(0, 1).toUpperCase() +stock.substring(1, stock.length()) ;
                            Method met = cl.getDeclaredMethod("set"+stock, f.getType());
                            String value = request.getParameter(inparam);
                            Object valTemp = value;
                            if(f.getType() == Integer.class){
                                valTemp = Integer.parseInt(String.valueOf(value));
                            }else if(f.getType() == String.class){
                                valTemp = value;
                            }else if(f.getType() == Double.class){
                                valTemp = Double.parseDouble(value);
                            }else if(f.getType() == Boolean.class){
                                valTemp = Boolean.parseBoolean(value);
                            }else if(f.getType() == Date.class){//sql.date
                               valTemp = java.sql.Date.valueOf(value);
                            }
                            met.invoke(ob, valTemp);
                            break;
                        }
                    }
                }


                try {
                    for (Field field : allf) {
                        if (field.getType() == etu1989.framework.Upload.class) {
                            String stock = field.getName();
                            stock = stock.substring(0, 1).toUpperCase() +stock.substring(1, stock.length()) ;
                            Method met = cl.getDeclaredMethod("set"+stock, field.getType());
                            Object objct = this.fileTraitement(request.getParts(), field);
                            met.invoke(ob, objct);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace(out);
                    e.printStackTrace();
                }

                Object ob1 = m.invoke(ob,obj);
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
    private String getFileName(jakarta.servlet.http.Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        String[] parts = contentDisposition.split(";");
        for (String partStr : parts) {
            if (partStr.trim().startsWith("filename"))
                return partStr.substring(partStr.indexOf('=') + 1).trim().replace("\"", "");
        }
        return null;
    }

    private Upload fillFileUpload(Upload file, jakarta.servlet.http.Part filepart) {
        try (InputStream io = filepart.getInputStream()) {
            ByteArrayOutputStream buffers = new ByteArrayOutputStream();
            byte[] buffer = new byte[(int) filepart.getSize()];
            int read;
            while ((read = io.read(buffer, 0, buffer.length)) != -1) {
                buffers.write(buffer, 0, read);
            }
            file.setFilename(this.getFileName(filepart));
            file.setData(buffers.toByteArray());
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Upload fileTraitement(Collection<jakarta.servlet.http.Part> files, Field field) {
        Upload file = new Upload();
        String name = field.getName();
        boolean exists = false;
        String filename = null;
        jakarta.servlet.http.Part filepart = null;
        for (jakarta.servlet.http.Part part : files) {
            if (part.getName().equals(name)) {
                filepart = part;
                break;
            }
        }
        file = this.fillFileUpload(file, filepart);
        return file;
    }

}