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
       matriz = restarValorMenorFilas(matriz);
       matriz = restarValorMenorColumnas(matriz);
       
    }

    public void imprimirMatriz(int[][] matriz) {
        String valoresMatriz = "";
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                valoresMatriz += matriz[i][j] + " - ";
            }
            valoresMatriz += "\n";
        }
        System.out.println(valoresMatriz);
    }

    public int[][] restarValorMenorFilas(int[][] matriz) {
        int[] valoresMenores = new int[matriz.length];
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                if (j == 0) {
                    valoresMenores[i] = matriz[i][j];
                } else {
                    if (matriz[i][j] < valoresMenores[i]) {
                        valoresMenores[i] = matriz[i][j];
                    }
                }
            }
        }
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                matriz[i][j] = matriz[i][j] - valoresMenores[i];
            }
        }
        return matriz;
    }

    public int [][] restarValorMenorColumnas(int[][] matriz){
        int[] valoresMenores = new int[matriz[0].length];
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                if (j == 0) {
                    valoresMenores[i] = matriz[j][i];
                } else {
                    if (matriz[j][i] < valoresMenores[i]) {
                        valoresMenores[i] = matriz[j][i];
                    }
                }
            }
        }
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                matriz[j][i] = matriz[j][i] - valoresMenores[i];
            }
        }
        return matriz;
    }
    
}
