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
        ModelView mv= new ModelView("test.jsp");
        mv.addItem("nom", "Randy");
        mv.addItem("prenom", "Randy");
        mv.addItem("etu", "1989");

        return mv;
    }

    @Url(key="Emp-findEmp")
    public ModelView findEmp(){
        return new ModelView("index.jsp");
    }
    
}