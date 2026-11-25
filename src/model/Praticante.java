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
        int limite = getLimiteGraus();
        if (graus < 0 || graus > limite) {
            throw new IllegalArgumentException(
                "Para a faixa " + faixa + ", os graus devem estar entre 0 e " + limite + "."
            );
        }
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

    public int getLimiteGraus() {
        return "Preta".equalsIgnoreCase(this.faixa) ? 6 : 4;
    }

    @Override
    public void promoverFaixa(String novaFaixa) {
        if (novaFaixa == null || novaFaixa.trim().isEmpty()) {
            throw new IllegalArgumentException("A nova faixa não pode ser vazia.");
        }
        this.faixa = novaFaixa;
        this.graus = 0;
    }

    @Override
    public void adicionarGrau() {
        int limite = getLimiteGraus();
        if (this.graus >= limite) {
            throw new IllegalStateException(
                nome + " já atingiu o limite máximo de " + limite + " graus na faixa " + faixa + "."
            );
        }
        this.graus++;
    }

    @Override
    public void removerGrau() {
        if (this.graus <= 0) {
            throw new IllegalStateException(nome + " já está com 0 graus na faixa " + faixa + ".");
        }
        this.graus--;
    }

    public abstract String getTipo();
    public abstract void exibirResumo();
}