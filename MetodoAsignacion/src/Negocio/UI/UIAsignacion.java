/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio.UI;

import Interfaz.FRPrincipal;
import Negocio.Administrador.Administrador;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

/**
 *
 * @author Marco
 */
public class UIAsignacion {

    Administrador administrador = new Administrador();

    public UIAsignacion() {
    }

    public Administrador getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }

    public void resolver(FRPrincipal ventana) {
        try {
            int filas = Integer.parseInt(ventana.getTxtFilas().getText());
            int columnas = Integer.parseInt(ventana.getTxtColumnas().getText());
            int nColumnasFicticias = 0;
            int nFilasFicticias = 0;
            if ((filas > 0) && (columnas > 0)) {
                columnas++;
                if (( (columnas - 1) - filas) != 0) {
                    if ((columnas - 1) < filas) {
                        nColumnasFicticias = filas - (columnas - 1);
                        columnas += nColumnasFicticias;
                    } else {
                        nFilasFicticias = (columnas - 1) - filas;
                        filas += nFilasFicticias;
                    }
                }
                final boolean [] columnasEditables = columnasEditar(columnas);
                ventana.getTablaCostos().setModel(new javax.swing.table.DefaultTableModel(
                        cantidadFilas(filas, columnas, nFilasFicticias, nColumnasFicticias),
                        cantidadColumnas(columnas, filas, nColumnasFicticias)) {

                    boolean[] canEdit = columnasEditables;

                    public boolean isCellEditable(int rowIndex, int columnIndex) {
                        return canEdit[columnIndex];
                    }
                });
                ventana.getTablaCostos().setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

                int indexColumna = 0;
                TableColumn columna = ventana.getTablaCostos().getColumnModel().getColumn(indexColumna);
                int ancho = 100;
                columna.setPreferredWidth(ancho);
                for (int i = 0; i < columnas; i++) {
                    ventana.getTablaCostos().getColumnModel().getColumn(i).setResizable(false);
                }
                
                ventana.getScrTablaCostos().setViewportView(ventana.getTablaCostos());
                ventana.setSize(630, 670);
                ventana.getBtnSolucionOptima().setVisible(true);
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Solo valores numericos y mayores a cero.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String[] cantidadColumnas(int cantColumnas, int cantFilas, int nColumnasFicticias) {
        String[] header = new String[cantColumnas];
        for (int i = 0; i < cantColumnas; i++) {
            if (i == 0) {
                header[i] = "";
            } else {
                if ( (i >= (cantColumnas - nColumnasFicticias)) && (nColumnasFicticias > 0)) {
                    header[i] = "Fict." + i;
                } else {
                    header[i] = "C" + i;
                }
            }
        }
        return header;
    }

    public Object[][] cantidadFilas(int cantFilas, int cantColumnas, int nFilasFicticias, int nColumnasFicticias) {
        Object[][] contenidoTabla = new Object[cantFilas][cantColumnas];
        for (int i = 0; i < cantFilas; i++) {
            for (int j = 0; j < cantColumnas; j++) {
                if (j == 0) {
                    if ((i >= (cantFilas - nFilasFicticias)) && (nFilasFicticias > 0)) {
                        contenidoTabla[i][j] = "Fict." + (i + 1);
                    } else {
                        contenidoTabla[i][j] = "F" + (i + 1);
                    }
                } else {
                    if ((i >= (cantFilas - nFilasFicticias)) && (nFilasFicticias > 0)) {
                        contenidoTabla[i][j] = 0;
                    } else {
                        if ((j >= (cantColumnas - nColumnasFicticias)) && (nColumnasFicticias > 0)) {
                            contenidoTabla[i][j] = 0;
                        } else {
                            contenidoTabla[i][j] = null;
                        }
                    }
                }
            }
        }
        return contenidoTabla;
    }

    public boolean [] columnasEditar(int cantColumnas){
        boolean [] retorno = new boolean [cantColumnas];
        for (int i = 0; i < retorno.length; i++) {
            if(i == 0)
                retorno[i] = false;
            else
                retorno[i] = true;
        }
        return retorno;
    }
}
