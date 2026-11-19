package dojosys.modelo;

public interface Graduavel {
    /** Promove para a faixa indicada conforme as regras de graduação do dojô. */
    void promoverFaixa(String novaFaixa);

    /** Registra um grau na faixa atual conforme as regras de graduação do dojô. */
    void adicionarGrau();
}
