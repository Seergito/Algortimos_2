package com;

public class a11 {

    // Tablero ajedrez

    public static boolean colocarReinas(int reina, int[] tablero) {
        boolean objetivo = false;
        int col = 0;
        while (col < 8 && !objetivo) {
            if (buenSitio(reina, col, tablero)) {
                tablero[reina] = col;
                if (reina == 7) {
                    objetivo = true;
                } else {
                    objetivo = colocarReinas(reina + 1, tablero);
                    if (!objetivo) {
                        tablero[reina] = -1;
                    }
                }
                col++;
            }

        }
        return objetivo;
    }

    public static final int DIMENSION = 9;

    private static boolean esPosibleInsertar(int[][] tablero, int i, int j, int valor) {
        for (int a = 0; a < DIMENSION; a++) {
            if (a != i && tablero[a][j] == valor) {
                return false;
            }
        }

        for (int k = 0; k < tablero.length; k++) {
            if (k != j && tablero[i][k] == valor) {
                return false;
            }
        }

        return true;
    }

    public static boolean resolver(int[][] tablero) {
        int i = -1;
        int j = -1;
        boolean objetivo = false;
        while (i < tablero.length && !objetivo) {
            j = -1;
            i++;
            while (j < tablero.length && !objetivo) {
                j++;
                if (tablero[i][j] == 0) {
                    objetivo = true;
                }
            }
        }

        if (!objetivo) { // Se ha ecnontrado posicion vacio
            return true;
        }
        objetivo = false;
        int valor = -1;
        while (valor < 10 && !objetivo) {
            if (esPosibleInsertar(tablero, i, j, valor)) {
                tablero[i][j] = valor;
                if (i == tablero.length - 1 && j == tablero.length - 1) {
                    objetivo = true;
                } else {
                    objetivo = resolver(tablero);
                    if (!objetivo) {
                        tablero[i][j] = 0;
                    }
                }
            }
            valor++;
        }
        return objetivo;
    }

    public static boolean ensayarPalabras(char[][] laberinto, int posX, int posY, String cadena, int posCad) {
        boolean objetivo = false;
        if (posX < laberinto.length && posY < laberinto.length && posX >= 0 && posY >= 0
                && cadena.charAt(posCad) == laberinto[posX][posY]) {

            laberinto[posX][posY] = ' ';

            if (posX == posY && posX == laberinto.length - 1) {
                objetivo = true;
            }

            if (!objetivo) {
                objetivo = ensayarPalabras(laberinto, posX + 1, posY, cadena, (posCad + 1) % cadena.length());
            }

            if (!objetivo) {
                objetivo = ensayarPalabras(laberinto, posX - 1, posY, cadena, (posCad + 1) % cadena.length());
            }

            if (!objetivo) {
                objetivo = ensayarPalabras(laberinto, posX, posY + 1, cadena, (posCad + 1) % cadena.length());
            }
            if (!objetivo) {
                objetivo = ensayarPalabras(laberinto, posX, posY - 1, cadena, (posCad + 1) % cadena.length());

            }

            if (!objetivo) {
                laberinto[posX][posY] = cadena.charAt(posCad);
            }
        }
        return objetivo;

    }

    public static boolean ensayar(char[][] laberinto, int posicionX, int posicionY) {
        boolean objetivo = false;
        int TAM = laberinto.length;
        if (posicionX >= 0 && posicionX < TAM && posicionY >= 0 && posicionY < TAM
                && (laberinto[posicionX][posicionY] == ' ' || laberinto[posicionX][posicionY] == 'T')) { // Posicion
                                                                                                         // valida

            boolean teletransporte = laberinto[posicionX][posicionY] == 'T';
            // Da un paso
            laberinto[posicionX][posicionY] = 'C';

            if (posicionX == TAM - 1 && posicionY == TAM - 1) { // Final
                return true;
            }
            // Avanzar casilla
            if (teletransporte) {
                // buscar posicion de la otra T
                int i = 0;
                int j = 0;
                boolean encontrado = false;
                while (i < TAM && !encontrado) {
                    j = 0;
                    while (j < TAM && !encontrado) {
                        if (laberinto[i][j] == 'T') {
                            encontrado = true;
                        }
                        if (!encontrado) {
                            j++;
                        }
                    }
                    if (!encontrado) {
                        i++;
                    }
                }
                // Se supone que siempre se encontrara una T
                objetivo = ensayar(laberinto, i, j);
            }
            if (!objetivo) { // Derecha
                objetivo = ensayar(laberinto, posicionX + 1, posicionY);
            }
            if (!objetivo) { // Abajo
                objetivo = ensayar(laberinto, posicionX, posicionY + 1);
            }
            if (!objetivo) { // Izq
                objetivo = ensayar(laberinto, posicionX - 1, posicionY);
            }
            if (!objetivo) { // Arriba
                objetivo = ensayar(laberinto, posicionX, posicionY - 1);
            }
            if (!objetivo) { // Vuelta atras
                laberinto[posicionX][posicionY] = 'I';
            }
        }
        return objetivo;
    }

    public static boolean buenSitio(int r, int col, int[] tabl) {
        // Es amenaza colocar la reina "r" en la columna col?
        int reiAant = 0;
        boolean continuar = true;
        while (reiAant < r && continuar) {
            if (tabl[reiAant] == col) {
                continuar = false;
            } else if (Math.abs(reiAant - r) == Math.abs(tabl[reiAant] - col)) {
                continuar = false;
            }
            reiAant++;
        }

        return continuar;
    }

}
