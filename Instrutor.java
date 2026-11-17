public class Instrutor extends Praticante {
    private double salario;

    public Instrutor(String nome, String faixa, double salario) {
        super(nome, faixa);
        this.salario = salario;
    }

    @Override
    public void promoverFaixa(String novaFaixa) {
        this.faixa = novaFaixa;
        this.graus = 0;
    }

    @Override
    public void adicionarGrau() {
        // Instrutores (geralmente pretas) têm regras diferentes de graus/dan, pode expandir depois
        this.graus++; 
    }

    @Override
    public void exibirResumo() {
        System.out.println("[INSTRUTOR] " + nome + " | Faixa: " + faixa + " | Responsável pelas aulas");
    }
}