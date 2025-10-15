/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutosDAO {
    
    // Método para cadastrar um novo produto (Requisito 2 e 3)
    public boolean cadastrarProduto (ProdutosDTO produto){
        
        Connection conn = new conectaDAO().connectDB();
        PreparedStatement prep = null;
        
        // SQL para inserção. A coluna 'status' é inicializada como 'A Venda'
        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
        
        try {
            prep = conn.prepareStatement(sql);
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, "A Venda"); 
            
            prep.execute();
            return true; // Cadastro bem-sucedido
            
        } catch (SQLException erro) {
            System.out.println("Erro ao cadastrar: " + erro.getMessage());
            return false; // Falha no cadastro
            
        } finally {
            // Lógica de fechamento de recursos (idealmente em um método na conectaDAO)
            try {
                if (prep != null) prep.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }
    
    // Método para listar todos os produtos (Requisito 4)
    public List<ProdutosDTO> listarProdutos(){
        
        List<ProdutosDTO> lista = new ArrayList<>();
        Connection conn = new conectaDAO().connectDB();
        PreparedStatement prep = null;
        ResultSet resultset = null;
        
        String sql = "SELECT id, nome, valor, status FROM produtos";
        
        try {
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();
            
            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id")); 
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));
                
                lista.add(produto);
            }
        } catch (SQLException erro) {
            System.out.println("Erro ao listar produtos: " + erro.getMessage());
        } finally {
            // Lógica de fechamento de recursos
            try {
                if (resultset != null) resultset.close();
                if (prep != null) prep.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar conexão após listagem: " + e.getMessage());
            }
        }
        
        return lista;
    }
}
    
    
    
        


