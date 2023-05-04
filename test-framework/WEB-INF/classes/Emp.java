package etu1989.model;
import etu1989.annotation.*;
public class Emp{
    String nom;
    String prenom;

    @Url(key="Emp-getAllEmp")
    public void getAllEmp(){
        System.out.println("voila les employées");
    }

    @Url(key="Emp-findEmp")
    public void findEmp(){

    }
    
}