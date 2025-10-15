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
    
    public boolean cadastrarProduto (ProdutosDTO produto){
        
        Connection conn = new conectaDAO().connectDB();
        PreparedStatement prep = null;
        
        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
        
        
        try {
            prep = conn.prepareStatement(sql);
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, "A Venda"); 
            
            prep.execute();
            return true; 
            
        } catch (SQLException erro) {
            System.out.println("Erro ao cadastrar: " + erro.getMessage());
            return false; 
            
        } finally {
            try {
                if (prep != null) prep.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }
    
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


    public boolean venderProduto(int id) {
        Connection conn = new conectaDAO().connectDB();
        PreparedStatement prep = null;
        String sql = "UPDATE produtos SET status = ? WHERE id = ?";

        try {
            prep = conn.prepareStatement(sql);
            prep.setString(1, "Vendido");
            prep.setInt(2, id);

            int rowsAffected = prep.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException erro) {
            System.out.println("Erro ao vender produto: " + erro.getMessage());
            return false;
        } finally {
            try {
                if (prep != null) prep.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                 System.out.println("Erro ao fechar conexão após venda: " + e.getMessage());
            }
        }
    }

    public List<ProdutosDTO> listarProdutosVendidos() {
        List<ProdutosDTO> lista = new ArrayList<>();
        Connection conn = new conectaDAO().connectDB();
        PreparedStatement prep = null;
        ResultSet resultset = null;

        String sql = "SELECT id, nome, valor, status FROM produtos WHERE status = 'Vendido'";

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
            System.out.println("Erro ao listar produtos vendidos: " + erro.getMessage());
        } finally {
            try {
                if (resultset != null) resultset.close();
                if (prep != null) prep.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                 System.out.println("Erro ao fechar conexão após listagem de vendidos: " + e.getMessage());
            }
        }
        return lista;
    }
}
    
    
        


