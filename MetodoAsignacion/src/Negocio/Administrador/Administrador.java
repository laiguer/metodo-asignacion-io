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
        int [] filasRayadas = new int[matriz.length];
        int [] columnasRayadas = new int[matriz.length];
        for (int i = 0; i < columnasRayadas.length; i++) {
            columnasRayadas[i] = 0;
            filasRayadas[i] = 0;
        }
        System.out.println("Rayas = "+rayar(matriz, filasRayadas, columnasRayadas));

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

    public int[][] restarValorMenorColumnas(int[][] matriz) {
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

    public String cantCerosFilasColumnas(int[][] matriz, int[] filasRayadas, int[] columnasRayadas) {
        String strCerosFilasColumnas = "";
        int cantCeros = 0;
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                if (matriz[i][j] == 0) {
                    if ((filasRayadas[i] == 0) && (columnasRayadas[j] == 0)) {
                        cantCeros++;
                    }
                }
            }
            if ( (filasRayadas[i] == 0) && ( cantCeros > 0) ) {
                strCerosFilasColumnas += "1" + i + cantCeros + "-";
            }
            cantCeros = 0;
        }
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                if (matriz[j][i] == 0) {
                    if ((filasRayadas[j] == 0) && (columnasRayadas[i] == 0)) {
                        cantCeros++;
                    }
                }
            }
            if (i == (matriz.length - 1)) {
                if ((columnasRayadas[i] == 0) && ( cantCeros > 0)) {
                    strCerosFilasColumnas += "2" + i + cantCeros;
                }
            } else {
                if ((columnasRayadas[i] == 0) && ( cantCeros > 0)) {
                    strCerosFilasColumnas += "2" + i + cantCeros + "-";
                }
            }
            cantCeros = 0;
        }
        return strCerosFilasColumnas;
    }

    public int rayar(int[][] matriz, int[] filasRayadas, int[] columnasRayadas) {
        String strCerosFilasColumnas = cantCerosFilasColumnas(matriz, filasRayadas, columnasRayadas);
        while (strCerosFilasColumnas.isEmpty() == false) {
            System.out.println(strCerosFilasColumnas);
            String[] cantCerosFilCol = strCerosFilasColumnas.split("-");
            String valor_temporal;
            int i, j;
            for (i = 0; i < cantCerosFilCol.length; i++) {
                for (j = i + 1; j < cantCerosFilCol.length; j++) {
                    if (numeroCeros(cantCerosFilCol[i]) < numeroCeros(cantCerosFilCol[j])) {
                        valor_temporal = cantCerosFilCol[j];
                        cantCerosFilCol[j] = cantCerosFilCol[i];
                        cantCerosFilCol[i] = valor_temporal;
                    }
                }
            }
            String filColRayar = cantCerosFilCol[0];
            String index = filColRayar.charAt(1) + "";
            if (filColRayar.charAt(0) == '1') {
                filasRayadas[Integer.parseInt(index)] = 1;
                System.out.println("Fila " + filColRayar.charAt(1) + " rayada.");
            } else {
                columnasRayadas[Integer.parseInt(index)] = 1;
                System.out.println("Columna " + filColRayar.charAt(1) + " rayada.");
            }
            strCerosFilasColumnas = cantCerosFilasColumnas(matriz, filasRayadas, columnasRayadas);
        }
        return cantRayas(filasRayadas, columnasRayadas);
    }

    public int numeroCeros(String info) {
        String numero = "";
        for (int i = 0; i < info.length(); i++) {
            if (i > 1) {
                numero += info.charAt(i);
            }
        }
        return Integer.parseInt(numero);
    }

    public int cantRayas(int[] filasRayadas, int[] columnasRayadas) {
        int cantRayas = 0;
        for (int i = 0; i < filasRayadas.length; i++) {
            if (filasRayadas[i] == 1) {
                cantRayas++;
            }
        }
        for (int i = 0; i < columnasRayadas.length; i++) {
            if (columnasRayadas[i] == 1) {
                cantRayas++;
            }
        }
        return cantRayas;
    }
}
