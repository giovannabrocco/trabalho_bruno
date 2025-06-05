package controller;
import dao.CategoriaFinanceiraDAO;
import dao.CategoriaFinanceiraDAOImpl;
import dao.TransacaoDAO;
import dao.TransacaoDAOImpl;
import model.CategoriaFinanceira;
import model.Transacao;
import view.TelaGerenciadorFinanceiroView;
import view.InterfaceView;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.stream.Collectors;

public class GerenciadorFinanceiroController {


    private final TelaGerenciadorFinanceiroView view;
    private final TransacaoDAO transacaoDAO;
    private final CategoriaFinanceiraDAO categoriaDAO;
    private final InterfaceView telaPrincipal;

    private boolean categoriaViewAberta = false;
    private boolean atualizandoCombo = false;

    public GerenciadorFinanceiroController(InterfaceView telaPrincipal) {
        this.telaPrincipal = telaPrincipal;
        this.view = new TelaGerenciadorFinanceiroView(telaPrincipal);
        this.transacaoDAO = new TransacaoDAOImpl();
        this.categoriaDAO = new CategoriaFinanceiraDAOImpl();



        configurarComboBoxCategorias();
        view.adicionarListenerAdicionar(e -> adicionarTransacao());



        this.view.setVisible(true);
    }

    private void configurarComboBoxCategorias() {
        carregarCategorias();
        view.adicionarListenerCategoriaComboBox(e -> {
            if (atualizandoCombo) return;
            Object selecionado = view.getCategoriaSelecionadaNoCombo();
            if (selecionado != null && selecionado.equals("+Editar Categorias") && !categoriaViewAberta) {
            solicitarAberturaEdicaoCategorias();
            }
        });
    }


    private void solicitarAberturaEdicaoCategorias() {
        categoriaViewAberta = true;
        view.abrirJanelaEdicaoCategoriasSwing(criarListenerFechamentoCategoria());
    }


    private WindowAdapter criarListenerFechamentoCategoria() {
        return new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                handleCategoriaViewClosed();
            }
            @Override
            public void windowClosing(WindowEvent e) {
                handleCategoriaViewClosed();
            }
        };
    }



    private void handleCategoriaViewClosed() {
        categoriaViewAberta = false;
        carregarCategorias();
        if (view.getComboCategoriaItemCount() > 1) {
            view.setComboCategoriaSelectedIndex(0);
        }
    }

    private void carregarCategorias() {
        atualizandoCombo = true;
        try {
            List<CategoriaFinanceira> categorias = categoriaDAO.listar();
            List<String> nomesCategorias = categorias.stream()
                                                    .map(CategoriaFinanceira::getNome)
                                                    .collect(Collectors.toList());

            view.setCategorias(nomesCategorias);
            view.adicionarItemCategoria("+Editar Categorias");
            if (!categorias.isEmpty()) {
                view.setComboCategoriaSelectedIndex(0);
            } else {
                view.setComboCategoriaSelectedIndex(-1);
            }
        } catch (Exception e) {
            view.exibirMensagem("Erro ao carregar categorias: " + e.getMessage());
            e.printStackTrace();
        } finally {
            atualizandoCombo = false;
        }
    }




    private void adicionarTransacao() {
        String descricao = view.getDescricao();
        String valorStr = view.getValor();
        String data = view.getData();
        String tipo = view.getTipo();
        String categoriaNome = view.getCategoria();

        if ("+Editar Categorias".equals(categoriaNome)) {
          view.exibirMensagem("Por favor, selecione uma categoria válida.");
            return;
        }
        if (descricao.isEmpty() || valorStr.isEmpty() || data.isEmpty() || tipo == null || categoriaNome == null || categoriaNome.isEmpty()) {
           view.exibirMensagem("Todos os campos são obrigatórios.");
            return;
        }




        double valor;
        try {
            valor = Double.parseDouble(valorStr.replace(",", "."));
        } catch (NumberFormatException ex) {
            view.exibirMensagem("Por favor, insira um valor numérico válido.");
            return;
        }
        if (!data.matches("\\d{2}/\\d{2}/\\d{4}")) {
            view.exibirMensagem("A data deve estar no formato dd/mm/aaaa.");
            return;
        }


        Transacao transacao = new Transacao(tipo.toLowerCase(), categoriaNome, descricao, valor, data);
        boolean sucesso = transacaoDAO.salvar(transacao);



        if (sucesso) {
            view.exibirMensagem("Transação registrada com sucesso!");
            view.limparCampos();
            if (this.telaPrincipal != null) {
                try {
                    this.telaPrincipal.recarregarSaldoEDisplay();
                    this.telaPrincipal.atualizarTabelaUltimosRegistros();
                } catch (Exception ex) {
                    System.err.println("Erro ao atualizar a tela principal: " + ex.getMessage());
                }

            }
            view.dispose();
        } else {
            view.exibirMensagem("Erro ao registrar transação.");
        }
    }
}

