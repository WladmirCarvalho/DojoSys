public abstract class Praticante implements Graduavel {
    protected String nome;
    protected String faixa;
    protected int graus;

    public Praticante(String nome, String faixa) {
        this.nome = nome;
        this.faixa = faixa;
        this.graus = 0;
    }

    public String getNome() { return nome; }
    public String getFaixa() { return faixa; }
    public int getGraus() { return graus; }

    // Método abstrato que forçará o polimorfismo nas subclasses
    public abstract void exibirResumo(); 
}