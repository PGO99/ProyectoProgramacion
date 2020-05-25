/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;


import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author Pedro
 */
public class modeloProyectos extends database{
    
    private String titulo;
    private String fechainicio;
    private String fechafin;
    private String descripcion;
    
    
    public modeloProyectos ( String titulo, String fechainicio, String fechafin, String descripcion ){
        this.titulo = titulo;
        this.fechainicio = fechainicio;
        this.fechafin = fechafin;
        this.descripcion = descripcion;
    }
    
    
    public boolean IntroducirProyectos(String titulo, String fechainicio, String fechafin, String descripcion)
    {
        //Metodo para validar los datos
         if( ValidarDatosProyectos( titulo,  fechainicio, fechafin,  descripcion ) )   
        {
            //Se arma la consulta
           String q=" INSERT INTO Proyectos (titulo, fechainicio , fechafin , descripcion )"
                    + "VALUES ('"+ titulo + "','" + fechainicio + "','" + fechafin + "','" + descripcion + "') ";
            //se ejecuta la consulta
            try {
               try (PreparedStatement pstm = this.getConexion().prepareStatement(q)) {
                   pstm.execute();
               }
                return true;
            }catch(SQLException e){
                System.err.println( e.getMessage() );
            }
            return false;
       
    }
         return false;
    }
    
    public boolean EliminarProyectos(String titulo)
    {
         boolean res=false;
        
        String q = " DELETE FROM Proyectos WHERE titulo='" + titulo + "'";
       
         try {
            PreparedStatement pstm = this.getConexion().prepareStatement(q);
            pstm.execute();
            pstm.close();
            res=true;
         }catch(SQLException e){
            System.err.println( e.getMessage() );
        }
        return res;
    }
    
    public boolean ModificarProyectos(String titulo, String fechainicio, String fechafin, String descripcion) throws SQLException{
           //Metodo para validar los datos
           if( ValidarDatosProyectos( titulo, fechainicio, fechafin,  descripcion ) )
        {
            //se arma la consulta
            String q=" UPDATE Proyectos SET titulo='" + titulo + "', fechainicio='"
                        + fechainicio + "', fechafin='" + fechafin + "', descripcion='" + descripcion + "' WHERE titulo='"+titulo+"'";
            //se ejecuta la consulta
        try {
                 PreparedStatement pstm = this.getConexion().prepareStatement(q);
                pstm.execute();
                pstm.close();
                return true;
            }catch(SQLException e){
                System.err.println( e.getMessage() );
            }
            return false;
        }
         return false;
    }
    
    public DefaultTableModel getTablaEmpleados(String titulo)
    {
      DefaultTableModel tablemodel = new DefaultTableModel();
      int registros = 0;
      String[] columNames = {"NIF","nombre","apellidos","año nacimiento"};
      
      try{
         PreparedStatement pstm = this.getConexion().prepareStatement( "SELECT count(*) as total FROM Empleados");
         ResultSet res = pstm.executeQuery();
         res.next();
         registros = res.getInt("total");
         res.close();
      }catch(SQLException e){
         System.err.println( e.getMessage() );
      }
    //se crea una matriz con tantas filas y columnas que necesite
      try{
          //realizamos la consulta sql y llenamos los datos en la matriz "Object[][] data"
         PreparedStatement pstm = this.getConexion().prepareStatement("SELECT * FROM Empleados");
         ResultSet res = pstm.executeQuery();
         int i=0;    
         Object[][] data = new String[registros][4];
            while(res.next()){
                data[i][0] = res.getString( "NIF" );
                data[i][1] = res.getString( "nombre" );
                data[i][2] = res.getString( "apellidos" );
                data[i][3] = res.getString( "anionacimiento" );
            i++;
         }
         res.close();
         //se añade la matriz de datos en el DefaultTableModel
         tablemodel.setDataVector(data, columNames );
         }catch(SQLException e){
            System.err.println( e.getMessage() );
        }
        return tablemodel;
    }
    
    public DefaultTableModel getTablaProyectos()
    {
      DefaultTableModel tablemodel = new DefaultTableModel();
      int registros = 0;
      String[] columNames = {"titulo","fechainicio","fechafin","descripcion"};
    
      try{
         PreparedStatement pstm = this.getConexion().prepareStatement( "SELECT count(*) as total FROM Proyectos");
         ResultSet res = pstm.executeQuery();
         res.next();
         registros = res.getInt("total");
         res.close();
      }catch(SQLException e){
         System.err.println( e.getMessage() );
      }
    //se crea una matriz con tantas filas y columnas que necesite
    
      try{
          //realizamos la consulta sql y llenamos los datos en la matriz "Object[][] data"
         PreparedStatement pstm = this.getConexion().prepareStatement("SELECT * FROM Proyectos");
         ResultSet res = pstm.executeQuery();
         int i=0;   
         Object[][] data = new String[registros][4];
         while(res.next()){
                data[i][0] = res.getString("titulo");
                data[i][1] = res.getString("fechainicio");
                data[i][2] = res.getString("fechafin");
                data[i][3] = res.getString("descripcion");
            i++;
         }
         res.close();
         
         tablemodel.setDataVector(data, columNames );
         }catch(SQLException e){
            System.err.println( e.getMessage() );
        }
        return tablemodel;
    }
    
    /*public  DefaultTableModel getTablaEmpleadosProyectos(String NIF)
    {
      DefaultTableModel tablemodel = new DefaultTableModel();
      int registros = 0;
      String[] columNames = {"NIF","nombre", "titulo"};
      
      try{
      
         PreparedStatement pstm = this.getConexion().prepareStatement( "SELECT count(*) as total FROM Empleados");
         ResultSet res = pstm.executeQuery();
         res.next();
         registros = res.getInt("total");
         res.close();
      }catch(SQLException e){
         System.err.println( e.getMessage() );
      }
    //se crea una matriz con tantas filas y columnas que necesite
    
      try{
           
         PreparedStatement pstm = this.getConexion().prepareStatement("SELECT titulo, NIF FROM Empleados INNER JOIN Proyectos ON Asociaciones.NIF = Empleados.NIF JOIN Proyectos ON Asociaciones.titulo = Proyectos.titulo WHERE Asociaciones.NIF= '" +NIF+ "'");
         ResultSet res = pstm.executeQuery();
         int i=0;
         Object[][] data = new String[registros][3];
         while(res.next()){
                data[i][0] = res.getString(1);
                data[i][1] = res.getString(2);
                data[i][2] = res.getString(3);

            i++;
         }
         res.close();
         //se añade la matriz de datos en el DefaultTableModel
         tablemodel.setDataVector(data, columNames );
         }catch(SQLException e){
            System.err.println( e.getMessage() );
        }
        return tablemodel;
    }*/
    
    public DefaultComboBoxModel getComboBoxProyectos(){
      String nombre;
      DefaultComboBoxModel cbm = new DefaultComboBoxModel();
	ArrayList<String> elementos=new ArrayList<String>();
	try {
	getConexion();
	PreparedStatement pstm = getConexion().prepareStatement("SELECT titulo FROM Proyectos");
	ResultSet res = pstm.executeQuery();
	while (res.next()) {
	nombre = res.getString("titulo");
	elementos.add(nombre);
	}
	pstm.close();
	} catch (Exception e) {
	e.printStackTrace();
	}
	return new DefaultComboBoxModel(elementos.toArray());    
    }

    
    private boolean ValidarDatosProyectos(String titulo, String fechainicio , String fechafin, String descripcion) {
             
         if( titulo.equals("") || fechainicio.equals("") || fechafin.equals("") || descripcion.equals("")){
            return false;
        }else return true;
        
    } 

    public modeloProyectos() {
    }
    
    public String getTitulo() {
        return titulo;
    }

    public String getFechainicio() {
        return fechainicio;
    }

    public String getFechafin() {
        return fechafin;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setFechainicio(String fechainicio) {
        this.fechainicio = fechainicio;
    }

    public void setFechafin(String fechafin) {
        this.fechafin = fechafin;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    
}

