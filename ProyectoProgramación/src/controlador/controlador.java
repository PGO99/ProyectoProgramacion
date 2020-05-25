/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import modelo.modeloEmpleados;
import modelo.modeloProyectos;
import vista.interfaz;

/**
 *
 * @author Pedro
 */
public class controlador implements ActionListener, MouseListener{
    
    interfaz vista;
    
    modeloEmpleados modeloEmpleados = new modeloEmpleados();
    modeloProyectos modeloProyectos = new modeloProyectos();

    public enum controladorproyecto
    {
       //empleados
        
        BotonIntroducirEmpleados,
        BotonEliminarEmpleados,
        BotonModificarEmpleados,
        BotonAsociarEmpleados,
        ComboboxAsociar,
        BotonListarProyectos,
        ComboboxListarProyectos,
        BotonVerAsociaciones,
        //proyectos
        
        BotonIntroducirProyectos,
        BotonEliminarProyectos,
        BotonModificarProyectos,
        BotonListarEmpleados,
        ComboboxListarEmpleados
        
        
        
    }
    
    public controlador(interfaz vista) {
        this.vista = vista;
    }
    
    
    public void iniciar(){
        
        // Skin tipo WINDOWS
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            SwingUtilities.updateComponentTreeUI(vista);
            vista.setVisible(true);
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {}
       
        //declaracion de botones
        this.vista.BotonIntroducirEmpleados.setActionCommand( "BotonIntroducirEmpleados" );
        this.vista.BotonIntroducirEmpleados.addActionListener(this);
        this.vista.BotonModificarEmpleados.setActionCommand( "BotonModificarEmpleados" );
        this.vista.BotonModificarEmpleados.addActionListener(this);
        this.vista.BotonEliminarEmpleados.setActionCommand( "BotonEliminarEmpleados" );
        this.vista.BotonEliminarEmpleados.addActionListener(this);
        this.vista.BotonIntroducirProyectos.setActionCommand( "BotonIntroducirProyectos" );
        this.vista.BotonIntroducirProyectos.addActionListener(this);
        this.vista.BotonModificarProyectos.setActionCommand( "BotonModificarProyectos" );
        this.vista.BotonModificarProyectos.addActionListener(this);
        this.vista.BotonEliminarProyectos.setActionCommand( "BotonEliminarProyectos" );
        this.vista.BotonEliminarProyectos.addActionListener(this);
        this.vista.BotonAsociarEmpleados.setActionCommand( "BotonAsociarEmpleados" );
        this.vista.BotonAsociarEmpleados.addActionListener(this);
        this.vista.ComboboxAsociar.addMouseListener(this);
        this.vista.ComboboxAsociar.setModel(modeloEmpleados.getComboBoxProyectos());
        this.vista.BotonVerAsociaciones.setActionCommand( "BotonVerAsociaciones" );
        this.vista.BotonVerAsociaciones.addActionListener(this);
        this.vista.BotonListarEmpleados.setActionCommand( "BotonListarEmpleados");
        this.vista.BotonListarEmpleados.addActionListener(this);
        this.vista.ComboboxListarEmpleados.addMouseListener(this);
        this.vista.ComboboxListarEmpleados.setModel(modeloProyectos.getComboBoxProyectos());
        this.vista.ComboboxListarProyectos.addMouseListener(this);
        this.vista.ComboboxListarProyectos.setModel(modeloEmpleados.getComboBoxEmpleados());
        //declaracion de tablas
        
        this.vista.TablaEmpleados.addMouseListener(this);
        this.vista.TablaEmpleados.setModel(modeloEmpleados.getTablaEmpleados());
        this.vista.TablaAsociaciones.addMouseListener(this);
        this.vista.TablaAsociaciones.setModel(modeloEmpleados.getTablaAsociaciones());
        this.vista.TablaProyectos.addMouseListener(this);
        this.vista.TablaProyectos.setModel(modeloProyectos.getTablaProyectos());
        this.vista.TablaEmpleados1.addMouseListener(this);
        this.vista.TablaEmpleados1.setModel(modeloEmpleados.getTablaEmpleados());
        
    }
        
        
        

    @Override
    public void actionPerformed(ActionEvent me) {
        switch ( controlador.controladorproyecto.valueOf( me.getActionCommand() ) )
        {
            case BotonIntroducirEmpleados:
           
                //añade un nuevo empleado
                if ( this.modeloEmpleados.IntroducirEmpleados(
                        this.vista.CampoNIFEmpleados.getText() ,
                        this.vista.CampoNombreEmpleados.getText(),
                        this.vista.CampoApellidosEmpleados.getText(),
                        this.vista.CampoAñonacimientoEmpleados.getText()))
                       
                    {
                    //ocurrio un error
                    this.vista.TablaEmpleados.setModel( this.modeloEmpleados.getTablaEmpleados() );
                    JOptionPane.showMessageDialog(vista,"Empleado añadido correctamente.");
                    
                    this.vista.CampoNIFEmpleados.setText("");
                    this.vista.CampoNombreEmpleados.setText("");
                    this.vista.CampoApellidosEmpleados.setText("") ;
                    this.vista.CampoAñonacimientoEmpleados.setText("");
                  
                }else JOptionPane.showMessageDialog(vista,"Error: Datos incorrectos.");
                break;
    
        //modificar empleado
        
            case BotonModificarEmpleados:
        
               
        
                try {

                    if ( this.modeloEmpleados.ModificarEmpleados(
                        this.vista.CampoNIFEmpleados.getText() ,
                        this.vista.CampoNombreEmpleados.getText() ,
                        this.vista.CampoApellidosEmpleados.getText(),
                        this.vista.CampoAñonacimientoEmpleados.getText()))
                    {
                    //ocurrio un error
                    this.vista.TablaEmpleados.setModel( this.modeloEmpleados.getTablaEmpleados() );
                    JOptionPane.showMessageDialog(vista,"Se ha modificado la información del empleado.");
                    this.vista.CampoNIFEmpleados.setText("");
                    this.vista.CampoNombreEmpleados.setText("");
                    this.vista.CampoAñonacimientoEmpleados.setText("");
                    this.vista.CampoApellidosEmpleados.setText("") ;
                  
                        }else JOptionPane.showMessageDialog(vista,"Error: Datos incorrectos.");
                    } catch (SQLException ex) {
                    Logger.getLogger(controlador.class.getName()).log(Level.SEVERE, null, ex);
                    }       
        
                
            break;
         
         
 

        //controlador crear proyecto
          
            case BotonIntroducirProyectos:
                //Añade un nuevo Proyecto
                if ( this.modeloProyectos.IntroducirProyectos(
                        
                        this.vista.CampoTituloProyectos.getText(),
                        this.vista.CampoFechainicioProyectos.getText(),
                        this.vista.CampoFechafinProyectos.getText(),
                        this.vista.CampoDescripcionProyectos.getText()) ) 
               
                        {
                    //ocurrio un error
                    this.vista.TablaProyectos.setModel( this.modeloProyectos.getTablaProyectos() );
                    JOptionPane.showMessageDialog(vista,"Nuevo proyecto introducido.");   
                  
                        this.vista.CampoTituloProyectos.setText("");
                        this.vista.CampoFechainicioProyectos.setText("") ;
                        this.vista.CampoFechafinProyectos.setText("");
                        this.vista.CampoDescripcionProyectos.setText("") ;
                }else JOptionPane.showMessageDialog(vista,"Datos incorrectos.");
                break;
                
                //modificar proyectos
            
            case BotonModificarProyectos:
        
        
            try {
                if ( this.modeloProyectos.ModificarProyectos(
                    this.vista.CampoTituloProyectos.getText(),
                    this.vista.CampoFechainicioProyectos.getText(),
                    this.vista.CampoFechafinProyectos.getText(),
                    this.vista.CampoDescripcionProyectos.getText()))
                {
                //ocurrio un error
                this.vista.TablaProyectos.setModel( this.modeloProyectos.getTablaProyectos() );
                JOptionPane.showMessageDialog(vista,"Los datos del proyecto han sido modificados.");
                
                this.vista.CampoTituloProyectos.setText("");
                this.vista.CampoDescripcionProyectos.setText("");
                this.vista.CampoFechainicioProyectos.setText("") ;
                this.vista.CampoFechafinProyectos.setText("");
                
            }else JOptionPane.showMessageDialog(vista,"Datos incorrectos.");
            } catch (SQLException ex) {
            Logger.getLogger(controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        
                
            case BotonVerAsociaciones:
                //obtiene del modeloempleado los registros en un DefaultTableModel y lo asigna en la vista
                this.vista.TablaAsociaciones.setModel(this.modeloEmpleados.getTablaAsociaciones(this.vista.ComboboxListarProyectos.getSelectedItem().toString()));
        
        
            break;
        
            case BotonEliminarEmpleados:
            //Metodo para eliminar un empleado
                if ( this.modeloEmpleados.EliminarEmpleados(
                        this.vista.CampoNIFEmpleados.getText()))
                {
                    this.vista.TablaEmpleados.setModel( this.modeloEmpleados.getTablaEmpleados() );
                    JOptionPane.showMessageDialog(vista,"Empleado eliminado.");
                    this.vista.CampoNIFEmpleados.setText("");  


                }
                break;
                
            case BotonEliminarProyectos:
           //metodo para eliminar un proyecto
                if ( this.modeloProyectos.EliminarProyectos(
                        this.vista.CampoTituloProyectos.getText() ) )
                {
                    this.vista.TablaProyectos.setModel( this.modeloProyectos.getTablaProyectos() );
                    JOptionPane.showMessageDialog(vista,"Proyecto eliminado.");
                    this.vista.CampoTituloProyectos.setText("");  


                }
                break;
              
            case BotonAsociarEmpleados:
                //asocia a un empleado un proyecto
                if (this.modeloEmpleados.Asociar(this.vista.CampoNIFEmpleados.getText(), 
                        String.valueOf(this.vista.ComboboxAsociar.getSelectedItem()))) {
                    
                    this.vista.TablaProyectos.setModel( this.modeloProyectos.getTablaProyectos() );
                    JOptionPane.showMessageDialog(vista,"Exito: Empleado asociado al proyecto.");
                    this.vista.CampoNIFEmpleados.setText("");
                    this.vista.CampoNombreEmpleados.setText("") ;
                    this.vista.CampoApellidosEmpleados.setText("");
                    this.vista.CampoAñonacimientoEmpleados.setText("") ;
                }else{
                    JOptionPane.showMessageDialog(vista,"Error: datos incorrectos.");
                }   
                break;  
            /*case BotonListarProyectos:
                //obtiene del modeloProyectos los registros en un DefaultTableModel y lo asigna en la vista
                this.vista.TablaAsociaciones.setModel(this.modeloEmpleados.getTablaProyectos(this.vista.ComboboxListarProyectos.getSelectedItem().toString()));
                break;*/
                
                     
        }
    }
    
    /*public void TablaEmpleados (MouseEvent e) {
         int fila = this.vista.TablaEmpleados.rowAtPoint(e.getPoint());
        DefaultTableModel escogerEmp = modeloEmpleados.getTablaEmpleados();

        if (fila > -1) {
            this.vista.CampoNIFEmpleados.setText(String.valueOf(escogerEmp.getValueAt(fila, 0)));
            this.vista.CampoNombreEmpleados.setText(String.valueOf(escogerEmp.getValueAt(fila, 1)));
            this.vista.CampoApellidosEmpleados.setText(String.valueOf(escogerEmp.getValueAt(fila, 2)));
            this.vista.CampoAñonacimientoEmpleados.setText(String.valueOf(escogerEmp.getValueAt(fila, 3)));
            
        }
    }
       
    public void TablaProyectos (MouseEvent e) {
        int fila= this.vista.TablaProyectos.rowAtPoint(e.getPoint());
        DefaultTableModel escogerPro = modeloProyectos.getTablaProyectos();

        if (fila> -1) {
            this.vista.CampoTituloProyectos.setText(String.valueOf(escogerPro.getValueAt(fila, 0)));
            this.vista.CampoFechainicioProyectos.setText(String.valueOf(escogerPro.getValueAt(fila, 1)));
            this.vista.CampoFechafinProyectos.setText(String.valueOf(escogerPro.getValueAt(fila, 2)));
            this.vista.CampoDescripcionProyectos.setText(String.valueOf(escogerPro.getValueAt(fila, 3)));
        }
    }
    
     public void getTablaProyectos1 (MouseEvent e) {
        int fila= this.vista.TablaEmpleados.rowAtPoint(e.getPoint());
        DefaultTableModel escogerNIF = modeloEmpleados.getTablaEmpleados();

        if (fila> -1) {
           this.vista.CampoNIFEmpleados.setText(String.valueOf(escogerNIF.getValueAt(fila, 0)));
            this.vista.CampoTituloProyectos.setText(String.valueOf(escogerNIF.getValueAt(fila, 1)));
         
        }
    }
    
     public void getTablaEmpleados1 (MouseEvent e) {
        int fila= this.vista.TablaProyectos.rowAtPoint(e.getPoint());
        DefaultTableModel escogert = modeloProyectos.getTablaProyectos();

        if (fila> -1) {
            this.vista.CampoNIFEmpleados.setText(String.valueOf(escogert.getValueAt(fila, 0)));
            this.vista.CampoTituloProyectos.setText(String.valueOf(escogert.getValueAt(fila, 1)));
         
        }
    } */
    @Override
    public void mouseClicked(MouseEvent me) {
        if (me.getSource()==this.vista.TablaEmpleados) {
           if( me.getButton()== 1)//boton izquierdo
        {
             int filaEmpleados = this.vista.TablaEmpleados.rowAtPoint(me.getPoint());
             if (filaEmpleados > -1){                
                this.vista.CampoNIFEmpleados.setText( String.valueOf( this.vista.TablaEmpleados.getValueAt(filaEmpleados, 0) ));
                this.vista.CampoNombreEmpleados.setText(String.valueOf( this.vista.TablaEmpleados.getValueAt(filaEmpleados, 1) ));
                this.vista.CampoApellidosEmpleados.setText( String.valueOf( this.vista.TablaEmpleados.getValueAt(filaEmpleados, 2) ));
                this.vista.CampoAñonacimientoEmpleados.setText( String.valueOf( this.vista.TablaEmpleados.getValueAt(filaEmpleados, 3) ));
             }
        } 
        }
        
        if (me.getSource()==this.vista.TablaProyectos) {
            if( me.getButton()== 1)//boton izquierdo
                {
                int filaProyectos = this.vista.TablaProyectos.rowAtPoint(me.getPoint());
                if (filaProyectos > -1){          
                    this.vista.CampoTituloProyectos.setText(String.valueOf( this.vista.TablaProyectos.getValueAt(filaProyectos, 0) ));
                    this.vista.CampoFechainicioProyectos.setText( String.valueOf( this.vista.TablaProyectos.getValueAt(filaProyectos, 1) ));
                    this.vista.CampoFechafinProyectos.setText( String.valueOf( this.vista.TablaProyectos.getValueAt(filaProyectos, 2) ));
                    this.vista.CampoDescripcionProyectos.setText(String.valueOf( this.vista.TablaProyectos.getValueAt(filaProyectos, 3)));
                }
            }
        }
        
        
        
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }
    
    }

    