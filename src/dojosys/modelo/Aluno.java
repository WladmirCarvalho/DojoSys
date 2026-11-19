package dojosys.modelo;

public class Aluno extends Praticante {
    private boolean mensalidadeEmDia;

    public Aluno(String nome, String faixa, boolean mensalidadeEmDia) {
        super(nome, faixa);
        this.mensalidadeEmDia = mensalidadeEmDia;
    }

    @Override
    public void promoverFaixa(String novaFaixa) {
        atualizarFaixa(novaFaixa);
        System.out.println("O aluno " + getNome() + " foi promovido para a faixa " + novaFaixa);
    }

    @Override
    public void adicionarGrau() {
        if (getGraus() < 4) {
            incrementarGrau();
        }
    }

    @Override
    public void exibirResumo() {
        String status = mensalidadeEmDia ? "Regular" : "Atrasada";
        System.out.println("[ALUNO] " + getNome() + " | Faixa: " + getFaixa() + " | Mensalidade: " + status);
    }
}
