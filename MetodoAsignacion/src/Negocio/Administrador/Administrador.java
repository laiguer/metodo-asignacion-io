/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Negocio.Administrador;

/**
 *
 * @author Marco
 */
public class Administrador {

    public Administrador() {
    }

    public void calcularSolOptima(int[][] matriz) {
        String valoresMatriz = "";
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
               valoresMatriz += matriz[i][j] +" - ";
            }
            valoresMatriz += "\n";
        }
        System.out.println(valoresMatriz);
    }



}
