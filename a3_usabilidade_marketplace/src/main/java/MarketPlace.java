
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class MarketPlace extends JFrame {
    private final JButton btnComprar;
    private final JButton btnAdicionar;
    private JDialog dialogCadastro;
    private JTextField txtNome;
    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private final JLabel lblCarrinho;

    public MarketPlace() {
        setTitle("Meu Programa");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Botão Comprar
        btnComprar = new JButton("Comprar");
        btnComprar.addActionListener((ActionEvent e) -> {
            abrirDialogCadastro();
        });
        add(btnComprar);

        // Botão Adicionar ao Carrinho
        btnAdicionar = new JButton("Adicionar ao Carrinho");
        btnAdicionar.addActionListener((ActionEvent e) -> {
            adicionarProdutoAoCarrinho();
        });
        add(btnAdicionar);

        // Rótulo Carrinho
        lblCarrinho = new JLabel("Carrinho vazio");
        add(lblCarrinho);

        setVisible(true);
    }

    private void abrirDialogCadastro() {
        if (dialogCadastro == null) {
            dialogCadastro = new JDialog(this, "Cadastro de Usuário", true);
            dialogCadastro.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialogCadastro.setSize(300, 200);
            dialogCadastro.setLayout(new GridLayout(4, 2));

            JLabel lblNome = new JLabel("Nome:");
            txtNome = new JTextField();
            JLabel lblEmail = new JLabel("E-mail:");
            txtEmail = new JTextField();
            JLabel lblSenha = new JLabel("Senha:");
            txtSenha = new JPasswordField();
            JButton btnEnviar = new JButton("Enviar");
            btnEnviar.addActionListener((ActionEvent e) -> {
                cadastrarUsuario();
            });

            dialogCadastro.add(lblNome);
            dialogCadastro.add(txtNome);
            dialogCadastro.add(lblEmail);
            dialogCadastro.add(txtEmail);
            dialogCadastro.add(lblSenha);
            dialogCadastro.add(txtSenha);
            dialogCadastro.add(btnEnviar);

            dialogCadastro.setVisible(true);
        }
    }

    private void cadastrarUsuario() {
        String nome = txtNome.getText();
        String email = txtEmail.getText();
        String senha = new String(txtSenha.getPassword());

        // Lógica para guardar os dados do usuário no banco de dados
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            // Configuração da conexão com o banco de dados (SQLite)
            String url = "jdbc:sqlite:minhabasededados.db";
            connection = DriverManager.getConnection(url);
            // Preparação da declaração SQL para inserção dos dados do usuário
            String sql = "INSERT INTO usuarios (nome, email, senha) VALUES (?, ?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, nome);
            statement.setString(2, email);
            statement.setString(3, senha);
            // Execução da declaração SQL
            statement.executeUpdate();
            // Exemplo: Exibindo uma mensagem de sucesso
            JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!");
            dialogCadastro.dispose();
        } catch (SQLException ex) {
        } finally {
            // Fechar a conexão e os recursos relacionados
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException ex) {
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MarketPlace::new);
    }

    private void adicionarProdutoAoCarrinho() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
