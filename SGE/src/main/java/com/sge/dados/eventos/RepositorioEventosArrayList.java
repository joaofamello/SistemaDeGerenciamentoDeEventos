package com.sge.dados.eventos;
import com.sge.negocio.entidade.Evento;
import java.util.ArrayList;
import java.util.List;

public class RepositorioEventosArrayList implements IRepositorioEventos {

    private ArrayList<Evento> eventos;

    public RepositorioEventosArrayList() { eventos = new ArrayList<Evento>(); }

    @Override
    public void inserir(Evento evento) {
        eventos.add(evento);
    }

    @Override
    public void alterar(Evento evento) {
        int index = eventos.indexOf(evento);
        eventos.set(index, evento);
    }

    @Override
    public void remover(Evento evento) {
        int index = eventos.indexOf(evento);
        eventos.remove(index);
    }

    public List<Evento> getEventos(){
        return eventos;
    }

}
