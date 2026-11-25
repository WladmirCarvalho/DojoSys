package model;

public abstract class Praticante implements Graduavel {
    protected String nome;
    protected String faixa;
    protected int graus;

    public Praticante(String nome, String faixa) {
        this(nome, faixa, 0);
    }

    public Praticante(String nome, String faixa, int graus) {
        this.nome = nome;
        this.faixa = faixa;
        this.graus = graus;
    }

    public String getNome() {
        return nome;
    }

    public String getFaixa() {
        return faixa;
    }

    public int getGraus() {
        return graus;
    }

    @Override
    public void promoverFaixa(String novaFaixa) {
        if (novaFaixa == null || novaFaixa.trim().isEmpty()) {
            throw new IllegalArgumentException("A nova faixa não pode ser vazia.");
        }
        this.faixa = novaFaixa;
        this.graus = 0; // Ao trocar de faixa, zera os graus
    }

    @Override
    public void adicionarGrau() {
        if (this.graus >= 4) {
            throw new IllegalStateException(nome + " já atingiu o limite máximo de 4 graus na faixa " + faixa + ".");
        }
        this.graus++;
    }

    public abstract String getTipo();
    public abstract void exibirResumo();
}