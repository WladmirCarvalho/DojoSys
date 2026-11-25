package test;

import model.Aluno;
import model.Graduavel;
import model.Instrutor;
import model.Praticante;
import service.DojoManager;

public class DojoSysTest {
    public static void main(String[] args) {
        System.out.println("Iniciando testes unitários do DojoSys...");

        // 1. Teste do padrão Singleton
        DojoManager m1 = DojoManager.getInstancia();
        DojoManager m2 = DojoManager.getInstancia();
        assert m1 == m2 : "DojoManager deveria retornar sempre a mesma instância (Singleton).";

        // 2. Teste de Herança e Polimorfismo (Praticante e Graduavel)
        Praticante aluno = new Aluno("Carlos Gracie", "Azul", true);
        Praticante instrutor = new Instrutor("Hélio Gracie", "Preta", 5000.00);

        Graduavel[] graduaveis = {aluno, instrutor};
        for (Graduavel g : graduaveis) {
            g.adicionarGrau();
        }
        assert aluno.getGraus() == 1 : "Aluno deveria ter 1 grau.";
        assert instrutor.getGraus() == 1 : "Instrutor deveria ter 1 grau.";

        // 3. Teste de promoção de faixa (deve zerar os graus)
        aluno.promoverFaixa("Roxa");
        assert "Roxa".equals(aluno.getFaixa()) : "Faixa do aluno deveria ser Roxa.";
        assert aluno.getGraus() == 0 : "Graus deveriam zerar após promoção de faixa.";

        // 4. Teste de limite máximo de graus (exceção esperada)
        boolean lancouExcecaoGrau = false;
        try {
            for (int i = 0; i < 5; i++) {
                aluno.adicionarGrau();
            }
        } catch (IllegalStateException e) {
            lancouExcecaoGrau = true;
        }
        assert lancouExcecaoGrau : "Deveria lançar IllegalStateException ao passar de 4 graus.";

        // 5. Teste de salário negativo (exceção esperada)
        boolean lancouExcecaoSalario = false;
        try {
            ((Instrutor) instrutor).setSalario(-100.0);
        } catch (IllegalArgumentException e) {
            lancouExcecaoSalario = true;
        }
        assert lancouExcecaoSalario : "Deveria lançar IllegalArgumentException para salário negativo.";

        System.out.println("SUCESSO: Todos os testes unitários passaram!");
    }
}