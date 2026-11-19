package dojosys.servico;

import dojosys.modelo.Praticante;

import java.util.ArrayList;
import java.util.List;

public class DojoManager {
    private static DojoManager instanciaUnica;
    private List<Praticante> membros;

    private DojoManager() {
        membros = new ArrayList<>();
    }

    public static DojoManager getInstancia() {
        if (instanciaUnica == null) {
            instanciaUnica = new DojoManager();
        }
        return instanciaUnica;
    }

    public void cadastrarMembro(Praticante p) {
        membros.add(p);
    }

    public void listarTodos() {
        // Uso de polimorfismo: chama o exibirResumo correto dependendo se é Aluno ou Instrutor
        for (Praticante p : membros) {
            p.exibirResumo(); 
        }
    }
}
