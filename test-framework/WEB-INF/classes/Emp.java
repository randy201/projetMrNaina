package etu1989.model;
import java.io.File;

import etu1989.annotation.*;
import etu1989.framework.*;
@Scope(name="singleton")
public class Emp{
    String nom;
    String prenom;
    Integer etu;
    File fichier;

    public void setFichier(File fichier) {
        this.fichier = fichier;
    }
    public File getFichier() {
        return fichier;
    }
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
    
    @Url(key="Emp-setNom")
    public ModelView sprint8(@Param(key="nom") String nom){
        ModelView Mv=new ModelView("sprint8.jsp");
        Mv.addItem("nomm", nom);
        return Mv;
    }

}