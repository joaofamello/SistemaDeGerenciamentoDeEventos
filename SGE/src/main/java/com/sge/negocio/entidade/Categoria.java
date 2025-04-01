package com.sge.negocio.entidade;

import java.util.ArrayList;
import java.util.List;

public class Categoria {
private ArrayList<Evento> Corporativos;
private ArrayList<Evento> Sociais;
private ArrayList<Evento> Culturais;
private ArrayList<Evento> Esportivos;
private ArrayList<Evento> Cientificos;

public Categoria() {
    Corporativos = new ArrayList<>();
    Sociais = new ArrayList<>();
    Culturais = new ArrayList<>();
    Esportivos = new ArrayList<>();
    Cientificos = new ArrayList<>();
}

    public List<Evento> getCorporativos() {
        return Corporativos;
    }

    public List<Evento> getSociais() {
        return Sociais;
    }

    public List<Evento> getCulturais(){
        return Culturais;
    }

    public List<Evento> getEsportivos(){
        return Esportivos;
    }
    public List<Evento> getCientificos() {
        return Cientificos;
    }


}
