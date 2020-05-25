/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import static modelo.manejaArchivos.manejaArchivos;

/**
 *
 * @author Pedro
 */
public class database{
        
  private static final String archivo = "database.txt";
    
  List<String> lista = manejaArchivos(archivo);
    
    private String db = lista.get(0);
    /** usuario */
    private String user = lista.get(1);
    /** contraseña de MySql*/
  private String password = lista.get(2);
  /** Cadena de conexion */
  private String url = lista.get(3)+db;
  /** variable para trabajar con la conexion a la base de datos */
  private Connection conn = null;

   /** Constructor de clase */
   public database(){
       
       try{
         //obtenemos el driver de para mysql
         Class.forName("com.mysql.jdbc.Driver");
         //obtenemos la conexión
         conn = DriverManager.getConnection( this.url, this.user , this.password );         
      }catch(SQLException e){
         System.err.println( e.getMessage() );
      }catch(ClassNotFoundException e){
         System.err.println( e.getMessage() );
      }
   }


   public Connection getConexion()
   {
    return this.conn;
   }
    
}
