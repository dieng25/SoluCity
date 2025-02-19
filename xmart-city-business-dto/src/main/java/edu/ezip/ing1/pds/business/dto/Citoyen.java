package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "citoyen")
public class Citoyen {
    private String telNum;
    private String nom;
    private String prenom;
    private String email;
    private String identifiant;
}

public Citoyen () {
}

public Citoyen(String telN)