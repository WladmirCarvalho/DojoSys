package dojosys.modelo;

public class Instrutor extends Praticante {
    private double salario;

    public Instrutor(String nome, String faixa, double salario) {
        super(nome, faixa);
        this.salario = salario;
    }

    @Override
    public void promoverFaixa(String novaFaixa) {
        atualizarFaixa(novaFaixa);
    }

    @Override
    public void adicionarGrau() {
        // Instrutores (geralmente pretas) têm regras diferentes de graus/dan, pode expandir depois
        incrementarGrau();
    }

    @Override
    public void exibirResumo() {
        System.out.println("[INSTRUTOR] " + getNome() + " | Faixa: " + getFaixa() + " | Responsável pelas aulas");
    }
}
