package model;

public class Instrutor extends Praticante {
    private double salario;

    public Instrutor(String nome, String faixa, double salario) {
        super(nome, faixa);
        this.salario = salario;
    }

    public Instrutor(String nome, String faixa, int graus, double salario) {
        super(nome, faixa, graus);
        this.salario = salario;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        if (salario < 0) {
            throw new IllegalArgumentException("O salário não pode ser negativo.");
        }
        this.salario = salario;
    }

    @Override
    public String getTipo() {
        return "INSTRUTOR";
    }

    @Override
    public void exibirResumo() {
        System.out.printf("[INSTRUTOR] %s | Faixa: %s (%d graus) | Salário: R$ %.2f%n",
                nome, faixa, graus, salario);
    }
}