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

    private int[][] matriz;
    private int[][] matrizOriginal;
    private int[][] matrizAsignaciones;
    private int[] filasRayadas;
    private int[] columnasRayadas;
    private int[] filasAsignadas;

    public Administrador() {
    }

    public int[] getColumnasRayadas() {
        return columnasRayadas;
    }

    public void setColumnasRayadas(int[] columnasRayadas) {
        this.columnasRayadas = columnasRayadas;
    }

    public int[] getFilasRayadas() {
        return filasRayadas;
    }

    public void setFilasRayadas(int[] filasRayadas) {
        this.filasRayadas = filasRayadas;
    }

    public int[][] getMatriz() {
        return matriz;
    }

    public void setMatriz(int[][] matriz) {
        this.matriz = matriz;
    }

    public int[][] getMatrizOriginal() {
        return matrizOriginal;
    }

    public void setMatrizOriginal(int[][] matrizOriginal) {
        this.matrizOriginal = matrizOriginal;
    }

    public int[][] getMatrizAsignaciones() {
        return matrizAsignaciones;
    }

    public void setMatrizAsignaciones(int[][] matrizAsignaciones) {
        this.matrizAsignaciones = matrizAsignaciones;
    }

    public int[] getFilasAsignadas() {
        return filasAsignadas;
    }

    public void setFilasAsignadas(int[] filasAsignadas) {
        this.filasAsignadas = filasAsignadas;
    }

    public String calcularSolOptima(int[][] matrizUI) {
        matriz = new int[matrizUI.length][matrizUI.length];
        matrizOriginal = new int[matrizUI.length][matrizUI.length];
        matrizAsignaciones = new int[matrizUI.length][matrizUI.length];
        for (int i = 0; i < matrizUI.length; i++) {
            for (int j = 0; j < matrizUI[i].length; j++) {
                matrizOriginal[i][j] = matrizUI[i][j];
                matriz[i][j] = matrizUI[i][j];
            }
        }
        resetearMatrizAsignaciones();
        filasAsignadas = new int[matriz.length];
        filasRayadas = new int[matriz.length];
        columnasRayadas = new int[matriz.length];
        imprimirMatriz(matriz);
        matriz = restarValorMenorFilas();
        matriz = restarValorMenorColumnas();
        imprimirMatriz(matriz);
        for (int i = 0; i < columnasRayadas.length; i++) {
            columnasRayadas[i] = 0;
            filasRayadas[i] = 0;
            filasAsignadas[i] = 0;
        }
        while (rayar() < matriz.length) {
            restarMenorNoRayado(numeroMenorNoRayado());
            System.out.println("menor no rayado: " + numeroMenorNoRayado());
            imprimirMatriz(matriz);
        }
        realizarAsignaciones();
        return resultado();
    }

    public void imprimirMatriz(int[][] m) {
        String valoresMatriz = "";
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                valoresMatriz += m[i][j] + " - ";
            }
            valoresMatriz += "\n";
        }
        System.out.println(valoresMatriz);
    }

    public int[][] restarValorMenorFilas() {
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

    public int[][] restarValorMenorColumnas() {
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

    public String cantCerosFilasColumnas() {
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
            if ((filasRayadas[i] == 0) && (cantCeros > 0)) {
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
                if ((columnasRayadas[i] == 0) && (cantCeros > 0)) {
                    strCerosFilasColumnas += "2" + i + cantCeros;
                }
            } else {
                if ((columnasRayadas[i] == 0) && (cantCeros > 0)) {
                    strCerosFilasColumnas += "2" + i + cantCeros + "-";
                }
            }
            cantCeros = 0;
        }
        return strCerosFilasColumnas;
    }

    public int rayar() {
        String strCerosFilasColumnas = cantCerosFilasColumnas();
        while (strCerosFilasColumnas.isEmpty() == false) {
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
                System.out.println("Fila " + Integer.parseInt(index) + " rayada");
            } else {
                columnasRayadas[Integer.parseInt(index)] = 1;
                System.out.println("Columna " + Integer.parseInt(index) + " rayada");
            }
            strCerosFilasColumnas = cantCerosFilasColumnas();
        }
        return cantRayas();
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

    public int cantRayas() {
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

    public int numeroMenorNoRayado() {
        int numeroMenor = 99999 * 99999;
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                if ((filasRayadas[i] == 0) && (columnasRayadas[j] == 0)) {
                    if (matriz[i][j] < numeroMenor) {
                        numeroMenor = matriz[i][j];
                    }
                }
            }
        }
        return numeroMenor;
    }

    public void restarMenorNoRayado(int numeroMenor) {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                if ((filasRayadas[i] == 0) && (columnasRayadas[j] == 0)) {
                    matriz[i][j] = matriz[i][j] - numeroMenor;
                }
                if ((filasRayadas[i] == 1) && (columnasRayadas[j] == 1)) {
                    matriz[i][j] = matriz[i][j] + numeroMenor;
                }
            }
        }
    }

    public void realizarAsignaciones() {
        int cantCerosFila = 0;
        int asignacion[] = new int[2];
        int buscarAsignacionesCol = 0;
        int bandera = faltanAsignaciones();
        System.out.println("Asignaciones faltan: " + bandera);
        while (bandera > 0) {
            buscarAsignacionesCol = bandera;
            for (int i = 0; i < matriz.length; i++) {
                for (int j = 0; j < matriz[i].length; j++) {
                    if ((matriz[i][j] == 0) && (filasAsignadas[i] != 1)) {
                        asignacion[0] = i;
                        asignacion[1] = j;
                        cantCerosFila++;
                    }
                }
                if (cantCerosFila == 1) {
                    matrizAsignaciones[asignacion[0]][asignacion[1]] = 1;
                    filasAsignadas[i] = 1;
                    disminuirMatriz(asignacion[0], asignacion[1]);
                }
                cantCerosFila = 0;
            }
            if (buscarAsignacionesCol == bandera) {
                for (int i = 0; i < matriz.length; i++) {
                    for (int j = 0; j < matriz[i].length; j++) {
                        if ((matriz[j][i] == 0) && (filasAsignadas[i] != 1)) {
                            asignacion[0] = i;
                            asignacion[1] = j;
                            cantCerosFila++;
                        }
                    }
                    if (cantCerosFila == 1) {
                        matrizAsignaciones[asignacion[0]][asignacion[1]] = 1;
                        filasAsignadas[i] = 1;
                        disminuirMatriz(asignacion[0], asignacion[1]);
                    }
                    cantCerosFila = 0;
                }
            }
            if (buscarAsignacionesCol == bandera) {
                for (int i = 0; i < filasAsignadas.length; i++) {
                    if (filasAsignadas[i] == 0) {
                        for (int j = 0; j < matriz[i].length; j++) {
                            if (matriz[i][j] == 0) {
                                matrizAsignaciones[i][j] = 1;
                                filasAsignadas[i] = 1;
                                disminuirMatriz(i, j);
                            }
                        }
                    }
                }
            }
            bandera = faltanAsignaciones();
        }
    }

    public void resetearMatrizAsignaciones() {
        for (int i = 0; i < matrizAsignaciones.length; i++) {
            for (int j = 0; j < matrizAsignaciones[i].length; j++) {
                matrizAsignaciones[i][j] = 0;
            }
        }
    }

    public void disminuirMatriz(int fila, int columna) {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                if ((i == fila) | (j == columna)) {
                    if (matriz[i][j] == 0) {
                        matriz[i][j] = -1;
                    }
                }
            }
        }

    }

    public int faltanAsignaciones() {
        int cantidad = 0;
        for (int i = 0; i < filasAsignadas.length; i++) {
            if (filasAsignadas[i] == 0) {
                cantidad++;
            }
        }
        return cantidad;
    }

    public String resultado() {
        String resultado = "Solucion Optima: \n\n";
        String asignacion = "\n\nReciben asignaciones: \n\n";
        resultado += "Z = ";
        int solucionOptima = 0;
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                if (matrizAsignaciones[i][j] == 1) {
                    if (i == (matriz.length - 1)) {
                        resultado += matrizOriginal[i][j] + "\n";
                    } else {
                        resultado += matrizOriginal[i][j] + " + ";
                    }
                    solucionOptima += matrizOriginal[i][j];
                    asignacion += "Fila: " + (i + 1) + " - Columna: " + (j + 1) + "\n";
                }
            }
        }
        resultado += "Z = " + solucionOptima;
        resultado += asignacion;
        return resultado;
    }
}
