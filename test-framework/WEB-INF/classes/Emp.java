package etu1989.model;
import etu1989.annotation.*;
import etu1989.framework.*;
public class Emp{
    String nom;
    String prenom;
    public Emp () { }

    @Url(key="Emp-getAllEmp")
    public ModelView getAllEmp(){
        System.out.println("voila les employées");
        return new ModelView("test.jsp");
    }

    @Url(key="Emp-findEmp")
    public ModelView findEmp(){
        return new ModelView("index.jsp");
    }
    
}