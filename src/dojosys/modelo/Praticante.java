package dojosys.modelo;

public abstract class Praticante implements Graduavel {
    private final String nome;
    private String faixa;
    private int graus;

    public Praticante(String nome, String faixa) {
        this.nome = nome;
        this.faixa = faixa;
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

    protected final void atualizarFaixa(String novaFaixa) {
        faixa = novaFaixa;
        graus = 0;
    }

    protected final void incrementarGrau() {
        graus++;
    }

    public abstract void exibirResumo();
}
