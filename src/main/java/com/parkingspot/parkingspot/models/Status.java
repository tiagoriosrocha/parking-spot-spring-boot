package com.parkingspot.parkingspot.models;

public class Status {
    public String titulo;
    public String mensagem;
    public String classe;

    public Status(String titulo, String mensagem, String classe) {
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.classe = classe;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }
}
