public class Aluno extends Praticante {
    private boolean mensalidadeEmDia;

    public Aluno(String nome, String faixa, boolean mensalidadeEmDia) {
        super(nome, faixa);
        this.mensalidadeEmDia = mensalidadeEmDia;
    }

    @Override
    public void promoverFaixa(String novaFaixa) {
        this.faixa = novaFaixa;
        this.graus = 0;
        System.out.println("O aluno " + this.nome + " foi promovido para a faixa " + novaFaixa);
    }

    @Override
    public void adicionarGrau() {
        if (this.graus < 4) {
            this.graus++;
        }
    }

    @Override
    public void exibirResumo() {
        String status = mensalidadeEmDia ? "Regular" : "Atrasada";
        System.out.println("[ALUNO] " + nome + " | Faixa: " + faixa + " | Mensalidade: " + status);
    }
}