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
        matriz = restarValorMenorFilas();
        matriz = restarValorMenorColumnas();
        for (int i = 0; i < columnasRayadas.length; i++) {
            columnasRayadas[i] = 0;
            filasRayadas[i] = 0;
            filasAsignadas[i] = 0;
        }

        while (rayar() < matriz.length) {
            restarMenorNoRayado(numeroMenorNoRayado());
        }
        realizarAsignaciones();
        return resultado();
    }

    public int rayar() {
        int[] filasRayadasAux = new int[filasRayadas.length];
        int[] columnasRayadasAux = new int[columnasRayadas.length];
        int rayasFil = rayarFilasPrioridad();
        for (int i = 0; i < columnasRayadasAux.length; i++) {
            filasRayadasAux[i] = filasRayadas[i];
            columnasRayadasAux[i] = columnasRayadas[i];
        }
        int rayasCol = rayarColumnasPrioridad();

        if (rayasFil < rayasCol) {
            for (int i = 0; i < columnasRayadasAux.length; i++) {
                filasRayadas[i] = filasRayadasAux[i];
                columnasRayadas[i] = columnasRayadasAux[i];
            }

            return rayasFil;
        } else {

            return rayasCol;
        }

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

    public String cantCerosFilasColumnasF() {
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
                strCerosFilasColumnas += "1" + i + "/" + cantCeros + "-";
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
                    strCerosFilasColumnas += "2" + i + "/" + cantCeros;
                }
            } else {
                if ((columnasRayadas[i] == 0) && (cantCeros > 0)) {
                    strCerosFilasColumnas += "2" + i + "/" + cantCeros + "-";
                }
            }
            cantCeros = 0;
        }
        return strCerosFilasColumnas;
    }

    public String cantCerosFilasColumnasC() {
        String strCerosFilasColumnas = "";
        int cantCeros = 0;
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                if (matriz[j][i] == 0) {
                    if ((filasRayadas[j] == 0) && (columnasRayadas[i] == 0)) {
                        cantCeros++;
                    }
                }
            }
            if ((columnasRayadas[i] == 0) && (cantCeros > 0)) {
                strCerosFilasColumnas += "2" + i + "/" + cantCeros + "-";
            }
            cantCeros = 0;
        }
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                if (matriz[i][j] == 0) {
                    if ((filasRayadas[i] == 0) && (columnasRayadas[j] == 0)) {
                        cantCeros++;
                    }
                }
            }
            if (i == (matriz.length - 1)) {
                if ((filasRayadas[i] == 0) && (cantCeros > 0)) {
                    strCerosFilasColumnas += "1" + i + "/" + cantCeros;
                }
            } else {
                if ((filasRayadas[i] == 0) && (cantCeros > 0)) {
                    strCerosFilasColumnas += "1" + i + "/" + cantCeros + "-";
                }
            }
            cantCeros = 0;
        }
        return strCerosFilasColumnas;
    }

    public int rayarColumnasPrioridad() {
        resetearRayas();
        String strCerosFilasColumnas = cantCerosFilasColumnasC();
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

            String s = "";
            for (int k = 0; k < cantCerosFilCol.length; k++) {
                s += cantCerosFilCol[k] + "-";

            }

            String filColRayar = cantCerosFilCol[0];
            String n = "";
            int c = 0;
            char ch = '0';
            while ((c < filColRayar.length()) && (ch != '/')) {
                if (c > 0) {
                    n += filColRayar.charAt(c);
                }
                c = c + 1;
                ch = filColRayar.charAt(c);
            }
            String index = n + "";
            if (filColRayar.charAt(0) == '1') {
                filasRayadas[Integer.parseInt(index)] = 1;
            } else {
                columnasRayadas[Integer.parseInt(index)] = 1;
            }
            strCerosFilasColumnas = cantCerosFilasColumnasC();
        }
        return cantRayas();
    }

    public int rayarFilasPrioridad() {
        resetearRayas();
        String strCerosFilasColumnas = cantCerosFilasColumnasF();
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

            String s = "";
            for (int k = 0; k < cantCerosFilCol.length; k++) {
                s += cantCerosFilCol[k] + "-";

            }

    
            String filColRayar = cantCerosFilCol[0];
            String n = "";
            int c = 0;
            char ch = '0';
            while ((c < filColRayar.length()) && (ch != '/')) {
                if (c > 0) {
                    n += filColRayar.charAt(c);
                }
                c = c + 1;
                ch = filColRayar.charAt(c);
            }
            String index = n + "";
            if (filColRayar.charAt(0) == '1') {
                filasRayadas[Integer.parseInt(index)] = 1;
            } else {
                columnasRayadas[Integer.parseInt(index)] = 1;

            }
            strCerosFilasColumnas = cantCerosFilasColumnasF();
        }

        return cantRayas();
    }

    public int numeroCeros(String info) {
        String numero = "";
        int bandera = 0;
        for (int i = 0; i < info.length(); i++) {
            if (bandera > 0) {
                numero += info.charAt(i);
            }
            if (info.charAt(i) == '/') {
                bandera = 1;
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
        int numeroMenor = 99999;
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
        while (bandera > 0) {
            buscarAsignacionesCol = bandera;
      
            for (int i = 0; i < matriz.length; i++) {
                for (int j = 0; j < matriz[i].length; j++) {
                    if ((matriz[i][j] == 0) && (filasAsignadas[i] != 1)) {
                        asignacion[0] = i;
                        asignacion[1] = j;
                        cantCerosFila = cantCerosFila + 1;
                    }
                }
                if (cantCerosFila == 1) {
                    matrizAsignaciones[asignacion[0]][asignacion[1]] = 1;
                    filasAsignadas[i] = 1;
                    System.out.println("Asigne 1 "+asignacion[0]+" - "+asignacion[1]);
                    disminuirMatriz(asignacion[0], asignacion[1]);

                    bandera = faltanAsignaciones();

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
                   
                        bandera = faltanAsignaciones();
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
            
                                bandera = faltanAsignaciones();
                            }
                        }
                    }
                }
                bandera = asignarCasoEspecial(bandera);
            }
        }
    }

    public int asignarCasoEspecial(int bandera){
        int cantCeros = 0;
        for (int i = 0; i < matriz.length; i++) {
                for (int j = 0; j < matriz[i].length; j++) {
                    if ((matriz[i][j] == 0) && (filasAsignadas[i] != 1)) {
                        cantCeros++;
                    }
                }
            }
        int filaAsignar = 0;
        if(cantCeros == 0){
            for (int i = 0; i < filasAsignadas.length; i++) {
                if( filasAsignadas[i] == 0 )
                    filaAsignar = i;
            }
        }

        for (int i = 0; i < matriz.length; i++) {
                for (int j = 0; j < matriz[i].length; j++) {
                    if ( (i == filaAsignar) && (matriz[i][j] != -1) ) {
                        matrizAsignaciones[i][j] = 1;
                        filasAsignadas[filaAsignar] = 1;
                        disminuirMatriz(i, j);
                        bandera = faltanAsignaciones();
                    }
                }
            }
        return bandera;
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
                        matriz[i][j] = -1;
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
        String planteoFO = "Metodo Hungaro\n\n";
        planteoFO += "Minimizar\n\n";
        planteoFO += "Funcion Objetivo: \n";
        String planteoSA = "\n\nSujeto A: \n";
        String restFilas = "";
        String restColumnas = "";
        int solucionOptima = 0;
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                if (matrizAsignaciones[i][j] == 1) {
                    asignacion += "Fila: " + (i + 1) + " - Columna: " + (j + 1) + "\n";
                    solucionOptima += matrizOriginal[i][j];

                    if (i == (matriz.length - 1)) {
                        resultado += matrizOriginal[i][j] + "(" + "1" + ")" + "\n";
                    } else {
                        resultado += matrizOriginal[i][j] + "(" + "1" + ")" + " + ";
                    }
                }
                if( (i == (matriz.length - 1)) && (j == (matriz[i].length - 1)) )
                    planteoFO += matrizOriginal[i][j] + "X" + (i+1) + (j+1);
                else
                    planteoFO += matrizOriginal[i][j] + "X" + (i+1) + (j+1) + " + ";
                if(j == (matriz[i].length - 1)){
                    restColumnas += "X"+(j+1)+(i+1);
                    restFilas += "X"+(i+1)+(j+1);
                }else{
                    
                    restColumnas += "X"+(j+1)+(i+1)+" + ";
                    restFilas += "X"+(i+1)+(j+1)+" + ";}
            }
            restFilas += " = 1 \n";
            restColumnas += " = 1 \n";
        }
        planteoSA += "Restricciones de columnas: \n";
        planteoSA += restColumnas+"\n";
        planteoSA += "Restricciones de filas: \n";
        planteoSA += restFilas+"\n\n";
        resultado += "Z = " + solucionOptima;
        resultado += asignacion;
        planteoFO += planteoSA;
        planteoFO += resultado;
        return planteoFO;
    }

    private void resetearRayas() {
        for (int i = 0; i < matriz.length; i++) {
            filasRayadas[i] = 0;
            columnasRayadas[i] = 0;
        }
    }
}
