package etu1989.model;
import etu1989.annotation.*;
import etu1989.framework.*;
public class Emp{
    String nom;
    String prenom;
    Integer etu;

    public void setEtu(Integer etu) {
        this.etu = etu;
    }
    public Integer getEtu() {
        return etu;
    }
    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    
    public Emp () { }

    @Url(key="Emp-testEmp")
    public ModelView testEmp(){
        ModelView mv= new ModelView("Vaovao.jsp");
        mv.addItem("randy", this);

        return mv;
    }

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