package com.sge.fachada;

import com.sge.dados.usuarios.RepositorioUsuariosArrayList;
import com.sge.dados.eventos.RepositorioEventosArrayList;
import com.sge.negocio.NegocioEvento;
import com.sge.negocio.NegocioUsuario;

public class SGE {

    private NegocioUsuario negocioUsuario;
    private NegocioEvento negocioEvento;

    public SGE(){
        negocioEvento = new NegocioEvento(new RepositorioEventosArrayList());
        negocioUsuario = new NegocioUsuario(new RepositorioUsuariosArrayList());
    }

}
