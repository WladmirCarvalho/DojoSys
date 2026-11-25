package ui;

import model.Aluno;
import model.Instrutor;
import model.Praticante;
import service.DojoManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DojoSysGUI extends JFrame {

    private static final String[] FAIXAS = {"Branca", "Azul", "Roxa", "Marrom", "Preta"};

    private final DojoManager dojo;
    private final DefaultTableModel tableModel;
    private final JTable tabela;

    public DojoSysGUI() {
        super("DojôSys — Sistema de Gestão de Jiu-Jitsu");
        this.dojo = DojoManager.getInstancia();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 480);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Cabeçalho
        JLabel titulo = new JLabel("DOJÔSYS — Gestão de Praticantes", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titulo.setBorder(BorderFactory.createEmptyBorder(12, 10, 4, 10));
        add(titulo, BorderLayout.NORTH);

        // Tabela central
        String[] colunas = {"Nome", "Tipo", "Faixa", "Graus", "Mensalidade / Salário"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabela = new JTable(tableModel);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setRowHeight(24);
        tabela.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));
        add(scrollPane, BorderLayout.CENTER);

        // Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 10));
        JButton btnCadastrarAluno = new JButton("Cadastrar Aluno");
        JButton btnCadastrarInstrutor = new JButton("Cadastrar Instrutor");
        JButton btnPromoverFaixa = new JButton("Promover Faixa");
        JButton btnAdicionarGrau = new JButton("Adicionar Grau");
        JButton btnRemoverGrau = new JButton("Remover Grau");
        JButton btnMensalidade = new JButton("Mensalidade");
        JButton btnAtualizar = new JButton("Atualizar Lista");

        painelBotoes.add(btnCadastrarAluno);
        painelBotoes.add(btnCadastrarInstrutor);
        painelBotoes.add(btnPromoverFaixa);
        painelBotoes.add(btnAdicionarGrau);
        painelBotoes.add(btnRemoverGrau);
        painelBotoes.add(btnMensalidade);
        painelBotoes.add(btnAtualizar);
        add(painelBotoes, BorderLayout.SOUTH);

        // Ações dos botões
        btnCadastrarAluno.addActionListener(e -> abrirCadastroAluno());
        btnCadastrarInstrutor.addActionListener(e -> abrirCadastroInstrutor());
        btnPromoverFaixa.addActionListener(e -> promoverFaixaSelecionado());
        btnAdicionarGrau.addActionListener(e -> adicionarGrauSelecionado());
        btnRemoverGrau.addActionListener(e -> removerGrauSelecionado());
        btnMensalidade.addActionListener(e -> alterarMensalidadeSelecionado());
        btnAtualizar.addActionListener(e -> carregarTabela());

        carregarTabela();
    }

    private void carregarTabela() {
        tableModel.setRowCount(0);
        try {
            List<Praticante> membros = dojo.listarPraticantes();
            for (Praticante p : membros) {
                String detalhe;
                if (p instanceof Aluno a) {
                    detalhe = a.isMensalidadeEmDia() ? "Em dia" : "Pendente";
                } else if (p instanceof Instrutor i) {
                    detalhe = String.format("R$ %.2f", i.getSalario());
                } else {
                    detalhe = "-";
                }
                tableModel.addRow(new Object[]{
                        p.getNome(), p.getTipo(), p.getFaixa(), p.getGraus(), detalhe
                });
            }
        } catch (RuntimeException ex) {
            mostrarErro("Erro ao carregar praticantes: " + ex.getMessage());
        }
    }

    private void abrirCadastroAluno() {
        JTextField txtNome = new JTextField();
        JComboBox<String> cbFaixa = new JComboBox<>(FAIXAS);
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0, 0, 4, 1);
        JSpinner spGraus = new JSpinner(spinnerModel);
        JCheckBox chkEmDia = new JCheckBox("Mensalidade em dia", true);

        cbFaixa.addActionListener(e -> {
            String f = (String) cbFaixa.getSelectedItem();
            int max = "Preta".equalsIgnoreCase(f) ? 6 : 4;
            spinnerModel.setMaximum(max);
            if ((int) spGraus.getValue() > max) {
                spGraus.setValue(max);
            }
        });

        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
        panel.add(new JLabel("Nome do aluno:"));
        panel.add(txtNome);
        panel.add(new JLabel("Faixa:"));
        panel.add(cbFaixa);
        panel.add(new JLabel("Graus atuais:"));
        panel.add(spGraus);
        panel.add(chkEmDia);

        int resultado = JOptionPane.showConfirmDialog(
                this, panel, "Cadastrar Aluno", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (resultado == JOptionPane.OK_OPTION) {
            String nome = txtNome.getText().trim();
            if (nome.isEmpty()) {
                mostrarAviso("O nome do aluno é obrigatório.");
                return;
            }
            try {
                dojo.cadastrarMembro(new Aluno(
                        nome,
                        (String) cbFaixa.getSelectedItem(),
                        (int) spGraus.getValue(),
                        chkEmDia.isSelected()
                ));
                carregarTabela();
                JOptionPane.showMessageDialog(this, "Aluno cadastrado com sucesso!");
            } catch (RuntimeException ex) {
                mostrarErro(ex.getMessage());
            }
        }
    }

    private void abrirCadastroInstrutor() {
        JTextField txtNome = new JTextField();
        JComboBox<String> cbFaixa = new JComboBox<>(FAIXAS);
        cbFaixa.setSelectedItem("Preta");
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0, 0, 6, 1);
        JSpinner spGraus = new JSpinner(spinnerModel);
        JTextField txtSalario = new JTextField();

        cbFaixa.addActionListener(e -> {
            String f = (String) cbFaixa.getSelectedItem();
            int max = "Preta".equalsIgnoreCase(f) ? 6 : 4;
            spinnerModel.setMaximum(max);
            if ((int) spGraus.getValue() > max) {
                spGraus.setValue(max);
            }
        });

        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
        panel.add(new JLabel("Nome do instrutor:"));
        panel.add(txtNome);
        panel.add(new JLabel("Faixa:"));
        panel.add(cbFaixa);
        panel.add(new JLabel("Graus atuais:"));
        panel.add(spGraus);
        panel.add(new JLabel("Salário (R$):"));
        panel.add(txtSalario);

        int resultado = JOptionPane.showConfirmDialog(
                this, panel, "Cadastrar Instrutor", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (resultado == JOptionPane.OK_OPTION) {
            String nome = txtNome.getText().trim();
            if (nome.isEmpty()) {
                mostrarAviso("O nome do instrutor é obrigatório.");
                return;
            }
            try {
                double salario = Double.parseDouble(txtSalario.getText().trim().replace(",", "."));
                if (salario < 0) {
                    mostrarAviso("O salário não pode ser negativo.");
                    return;
                }
                dojo.cadastrarMembro(new Instrutor(
                        nome,
                        (String) cbFaixa.getSelectedItem(),
                        (int) spGraus.getValue(),
                        salario
                ));
                carregarTabela();
                JOptionPane.showMessageDialog(this, "Instrutor cadastrado com sucesso!");
            } catch (NumberFormatException ex) {
                mostrarAviso("Digite um valor numérico válido para o salário.");
            } catch (RuntimeException ex) {
                mostrarErro(ex.getMessage());
            }
        }
    }

    private void promoverFaixaSelecionado() {
        String nome = obterNomeSelecionado();
        if (nome == null) return;

        String novaFaixa = (String) JOptionPane.showInputDialog(
                this,
                "Selecione a nova faixa para " + nome + ":",
                "Promover Faixa",
                JOptionPane.PLAIN_MESSAGE,
                null,
                FAIXAS,
                FAIXAS[0]
        );

        if (novaFaixa != null) {
            try {
                dojo.promoverFaixa(nome, novaFaixa);
                carregarTabela();
                JOptionPane.showMessageDialog(this, "Faixa de " + nome + " atualizada para " + novaFaixa + "!");
            } catch (RuntimeException ex) {
                mostrarAviso(ex.getMessage());
            }
        }
    }

    private void adicionarGrauSelecionado() {
        String nome = obterNomeSelecionado();
        if (nome == null) return;

        try {
            dojo.adicionarGrau(nome);
            carregarTabela();
            JOptionPane.showMessageDialog(this, "Grau adicionado para " + nome + "!");
        } catch (IllegalStateException | IllegalArgumentException ex) {
            mostrarAviso(ex.getMessage());
        } catch (RuntimeException ex) {
            mostrarErro(ex.getMessage());
        }
    }

    private void removerGrauSelecionado() {
        String nome = obterNomeSelecionado();
        if (nome == null) return;

        try {
            dojo.removerGrau(nome);
            carregarTabela();
            JOptionPane.showMessageDialog(this, "Grau removido de " + nome + "!");
        } catch (IllegalStateException | IllegalArgumentException ex) {
            mostrarAviso(ex.getMessage());
        } catch (RuntimeException ex) {
            mostrarErro(ex.getMessage());
        }
    }

    private void alterarMensalidadeSelecionado() {
        String nome = obterNomeSelecionado();
        if (nome == null) return;

        int escolha = JOptionPane.showConfirmDialog(
                this,
                "A mensalidade de " + nome + " está em dia?",
                "Controle de Mensalidade",
                JOptionPane.YES_NO_CANCEL_OPTION
        );

        if (escolha == JOptionPane.YES_OPTION || escolha == JOptionPane.NO_OPTION) {
            boolean emDia = (escolha == JOptionPane.YES_OPTION);
            try {
                dojo.atualizarMensalidade(nome, emDia);
                carregarTabela();
                JOptionPane.showMessageDialog(this, "Mensalidade atualizada com sucesso!");
            } catch (IllegalArgumentException ex) {
                mostrarAviso(ex.getMessage());
            } catch (RuntimeException ex) {
                mostrarErro(ex.getMessage());
            }
        }
    }

    private String obterNomeSelecionado() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            mostrarAviso("Selecione um praticante na tabela primeiro.");
            return null;
        }
        return (String) tableModel.getValueAt(linha, 0);
    }

    private void mostrarAviso(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Aviso", JOptionPane.WARNING_MESSAGE);
    }

    private void mostrarErro(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
        SwingUtilities.invokeLater(() -> new DojoSysGUI().setVisible(true));
    }
}