/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


/**
 *
 * @author Pedro
 */
public class modeloEmpleados extends database{

    private String NIF;
    private String nombre;
    private String apellidos;
    private String anionacimiento;
    
    
    public modeloEmpleados(String NIF, String nombre , String apellidos, String anionacimiento){
        this.NIF = NIF;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.anionacimiento = anionacimiento;
    }

    public modeloEmpleados() {
    }

    


    public boolean IntroducirEmpleados(String NIF, String nombre , String apellidos, String anionacimiento)
    {
        if( ValidarDatosEmpleados(nombre, apellidos, anionacimiento))
        {
            //Se arma la consulta
            String q=" INSERT INTO Empleados ( NIF , nombre , apellidos, anionacimiento  ) "
                    + "VALUES ( '" + NIF + "','" + nombre + "', '" + apellidos + "'," + anionacimiento + " ) ";
            //se ejecuta la consulta
            try {
              try (PreparedStatement pstm = this.getConexion().prepareStatement(q)){
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
    
      public boolean EliminarEmpleados(String NIF)
    {
         boolean res=false;
        //consulta
        String q = " DELETE FROM Empleados WHERE NIF='" + NIF + "'";
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
      
    public boolean ModificarEmpleados(String NIF, String nombre, String apellidos, String anionacimiento) throws SQLException{
            
           if( ValidarDatosEmpleados( nombre,
             apellidos,  anionacimiento ) )
        {
        //se arma la consulta
             String q=" UPDATE Empleados SET nombre='" + nombre + "', apellidos='"
                            + apellidos + "', anionacimiento='" + anionacimiento + "'WHERE NIF='"+NIF+"'";
             //se ejecuta
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
    
    public boolean Asociar(String NIF, String titulo)
    {
           String q=" INSERT INTO Asociaciones(NIF, titulo)"
                    + "VALUES ( '"+ NIF + "','" + titulo + "') ";
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
    
     public DefaultTableModel getTablaProyectos(String NIF)
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
    
    public DefaultTableModel getTablaEmpleados()
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
    
    public DefaultTableModel getTablaAsociaciones()
    {
      DefaultTableModel tablemodel = new DefaultTableModel();
      int registros = 0;
      String[] columNames = {"NIF","titulo"};
      
      try{
         PreparedStatement pstm = this.getConexion().prepareStatement( "SELECT count(*) as total FROM Asociaciones");
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
         PreparedStatement pstm = this.getConexion().prepareStatement("SELECT * FROM Asociaciones");
         ResultSet res = pstm.executeQuery();
         int i=0;    
         Object[][] data = new String[registros][2];
            while(res.next()){
                data[i][0] = res.getString( "NIF" );
                data[i][1] = res.getString( "titulo" );
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
    
    
    /*public  DefaultTableModel getTablaProyectosEmpleados(String titulo)
    {
      DefaultTableModel tablemodel = new DefaultTableModel();
      int registros = 0;
      String[] columNames = {"titulo","NIF","nombre"};
     
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
         PreparedStatement pstm = this.getConexion().prepareStatement("SELECT titulo, NIF FROM Asociaciones INNER JOIN Proyectos ON Asociaciones.titulo = Proyectos.titulo JOIN Actores ON Asociaciones.NIF = Empleados.NIF WHERE Asociaciones.titulo= '"+titulo+"'");
         ResultSet res = pstm.executeQuery();
         int i=0;
         Object[][] data = new String[registros][2];
         while(res.next()){
                data[i][0] = res.getString(1);
                data[i][1] = res.getString(2);
                data[i][1] = res.getString(3);
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

   public DefaultComboBoxModel getComboBoxEmpleados(){
      String n;
      DefaultComboBoxModel cbm = new DefaultComboBoxModel();
	ArrayList<String> elementos=new ArrayList<>();
	try {
	getConexion();
          try (PreparedStatement pstm = getConexion().prepareStatement("SELECT NIF FROM Empleados")) {
              ResultSet res = pstm.executeQuery();
              while (res.next()) {
                  n = res.getString("NIF");
                  elementos.add(n);
              } }
	} catch (SQLException e) {
	}
	return new DefaultComboBoxModel(elementos.toArray());    
    }
   
   public DefaultComboBoxModel getComboBoxProyectos(){
      String t;
      DefaultComboBoxModel cbm = new DefaultComboBoxModel();
	ArrayList<String> elementos=new ArrayList<String>();
	try {
	getConexion();
	PreparedStatement pstm = getConexion().prepareStatement("SELECT titulo FROM Proyectos");
	ResultSet res = pstm.executeQuery();
	while (res.next()) {
	t = res.getString("titulo");
	elementos.add(t);
	}
	pstm.close();
	} catch (Exception e) {
	e.printStackTrace();
	}
	return new DefaultComboBoxModel(elementos.toArray());    
    }

    private boolean ValidarDatosEmpleados( String nombre , String apellidos, String anionacimiento)
    {
        if(nombre.equals("") || apellidos.equals("") || anionacimiento.equals(""))
           return false;
        {return true;
        
        }
       
    }
    
     private boolean ValidarNIF (String NIF)
    {
        String mayuscula= "";
        
        if (NIF.length() !=9 || Character.isLetter(this.NIF.charAt(8)) == false)
       return false;     
        mayuscula = (this.NIF.substring(8)).toUpperCase();
            
        {return true;
    }
        
        
        
    }
     
     
     
    public void setNIF(String NIF) {
        this.NIF = NIF;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setAnionacimiento(String anionacimiento) {
        this.anionacimiento = anionacimiento;
    }
     
    public String getNIF() {
        return NIF;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getAnionacimiento() {
        return anionacimiento;
    }
    
    @Override
    public String toString(){
        return "Empleados{" + "nombre=" + nombre + ", apellidos=" + apellidos +", NIF=" + NIF +", anionacimiento=" + anionacimiento +'}';
    }



    

   
}
