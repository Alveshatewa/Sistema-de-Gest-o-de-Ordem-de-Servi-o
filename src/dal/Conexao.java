

package dal;

import java.sql.*;

/**
 * Conexão com o Banco de Dados
 *
 * @author Alves Hatewa Versão 1.1
 */
public class Conexao {

    /**
     * Este é o método responsável pela conexão com o Banco de Dados.
     *
     * @return Conexão
     */

    public static Connection conector() {

        java.sql.Connection conexao;

        String driver = "com.mysql.jdbc.Driver";

        String url = "jdbc:mysql://216.172.184.157/torresc1_javaAlves?characterEncoding=utf-8";
        String user = "torresc1_User_alves";
        String password = "AlvesJAVA1234";

        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, password);
            return conexao;
        } catch (ClassNotFoundException | SQLException e) {
            
            return null;
        }
    }

}
