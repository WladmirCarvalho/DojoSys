package model;

public class Aluno extends Praticante {
    private boolean mensalidadeEmDia;

    public Aluno(String nome, String faixa, boolean mensalidadeEmDia) {
        super(nome, faixa);
        this.mensalidadeEmDia = mensalidadeEmDia;
    }

    public Aluno(String nome, String faixa, int graus, boolean mensalidadeEmDia) {
        super(nome, faixa, graus);
        this.mensalidadeEmDia = mensalidadeEmDia;
    }

    public boolean isMensalidadeEmDia() {
        return mensalidadeEmDia;
    }

    public void setMensalidadeEmDia(boolean mensalidadeEmDia) {
        this.mensalidadeEmDia = mensalidadeEmDia;
    }

    @Override
    public String getTipo() {
        return "ALUNO";
    }

    @Override
    public void exibirResumo() {
        String status = mensalidadeEmDia ? "Em dia" : "Pendente";
        System.out.printf("[ALUNO] %s | Faixa: %s (%d graus) | Mensalidade: %s%n",
                nome, faixa, graus, status);
    }
}