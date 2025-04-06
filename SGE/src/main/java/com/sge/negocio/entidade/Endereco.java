package com.sge.negocio.entidade;

/**
 * Classe que representa o endereço de um evento.
 * Contém rua, bairro, numero, cep, cidade e estado.
 *
 * @author João Francisco
 */
public class Endereco {
    private String rua;
    private String bairro;
    private int numero;
    private String cep;
    private String cidade;
    private String estado;

    /**
     *Construtor da classe endereço.
     *
     * @param estado Estado do endereço.
     * @param cidade Cidade do endereço.
     * @param cep   CEP do endereço.
     * @param bairro Bairro do endereço.
     * @param rua Rua do endereço.
     * @param numero Numero do endereço.
     */
    public Endereco(String estado, String cidade, String cep, String bairro, String rua, int numero) {
        this.rua = rua;
        this.bairro = bairro;
        this.numero = numero;
        this.cep = cep;
        this.cidade = cidade;
        this.estado = estado;
    }

    /**
     * Retorna o endereço formatado em uma única string.
     *
     * @return Endreço completp formatado.
     */
    public String enderecoFormatado() {
        return rua + ";" + bairro + ";" + numero + ";" + cidade + ";" + estado + ";" + cep;
    }

    //getters e setters
    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
