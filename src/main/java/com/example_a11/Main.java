package com.example_a11;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }

    // Resolución del problema de las 8 reinas utilizando backtracking
    public static boolean colocarReinas(int reina, int[] tablero) {
        // Variable para indicar si se encontró una solución
        boolean objetivo = false;

        // Empezamos a colocar la reina en las columnas desde la primera
        int col = 0;
        while (col < 8 && !objetivo) { // Mientras queden columnas y no se haya alcanzado el objetivo
            if (buenSitio(reina, col, tablero)) { // Comprueba si es seguro colocar la reina en esta posición
                tablero[reina] = col; // Coloca la reina en esta columna

                if (reina == 7) { // Si colocamos la última reina
                    objetivo = true; // Solución encontrada
                } else {
                    objetivo = colocarReinas(reina + 1, tablero); // Intenta colocar la siguiente reina

                    if (!objetivo) { // Si no se encontró una solución
                        tablero[reina] = -1; // Retrocede (quita la reina de la posición actual)
                    }
                }
            }
            col++; // Prueba la siguiente columna
        }
        return objetivo; // Devuelve si se encontró una solución
    }

    // Define la dimensión para el tablero (9 en este caso)
    public static final int DIMENSION = 9;

    // Comprueba si es posible insertar un valor en una celda de un tablero sin
    // violar las reglas
    private static boolean esPosibleInsertar(int[][] tablero, int i, int j, int valor) {
        // Comprueba la columna
        for (int a = 0; a < DIMENSION; a++) {
            if (a != i && tablero[a][j] == valor) { // Si el valor ya está en la columna
                return false;
            }
        }

        // Comprueba la fila
        for (int k = 0; k < tablero.length; k++) {
            if (k != j && tablero[i][k] == valor) { // Si el valor ya está en la fila
                return false;
            }
        }

        return true; // El valor es válido en esta posición
    }

    // Resuelve un tablero utilizando backtracking
    public static boolean resolver(int[][] tablero) {
        int i = -1; // Índice de la fila
        int j = -1; // Índice de la columna
        boolean objetivo = false;

        // Busca una celda vacía (con valor 0)
        while (i < tablero.length && !objetivo) {
            j = -1;
            i++;
            while (j < tablero.length && !objetivo) {
                j++;
                if (tablero[i][j] == 0) { // Encuentra una celda vacía
                    objetivo = true;
                }
            }
        }

        if (!objetivo) { // Si no hay celdas vacías, el tablero está completo
            return true;
        }

        objetivo = false; // Reinicia la bandera objetivo
        int valor = -1;

        // Prueba valores del 1 al 9
        while (valor < 10 && !objetivo) {
            if (esPosibleInsertar(tablero, i, j, valor)) { // Comprueba si el valor es válido
                tablero[i][j] = valor; // Inserta el valor

                if (i == tablero.length - 1 && j == tablero.length - 1) { // Si estamos en la última celda
                    objetivo = true; // Solución encontrada
                } else {
                    objetivo = resolver(tablero); // Llama recursivamente para el siguiente paso

                    if (!objetivo) { // Si no se encuentra solución
                        tablero[i][j] = 0; // Retrocede
                    }
                }
            }
            valor++; // Prueba el siguiente valor
        }

        return objetivo; // Devuelve si se encontró solución
    }

    // Ensaya palabras en un laberinto siguiendo las reglas dadas
    public static boolean ensayarPalabras(char[][] laberinto, int posX, int posY, String cadena, int posCad) {
        boolean objetivo = false;

        // Comprueba si la posición es válida y la letra coincide con la cadena
        if (posX < laberinto.length && posY < laberinto.length && posX >= 0 && posY >= 0
                && cadena.charAt(posCad) == laberinto[posX][posY]) {

            laberinto[posX][posY] = ' '; // Marca la posición como visitada

            if (posX == posY && posX == laberinto.length - 1) { // Si llegó al final
                objetivo = true;
            }

            if (!objetivo) { // Intenta moverse en las cuatro direcciones
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

            if (!objetivo) { // Si no se encuentra solución, restaura la celda
                laberinto[posX][posY] = cadena.charAt(posCad);
            }
        }

        return objetivo; // Devuelve si se encontró solución
    }

    // Resuelve el laberinto buscando el camino hacia la salida
    public static boolean ensayar(char[][] laberinto, int posicionX, int posicionY) {
        boolean objetivo = false;
        int TAM = laberinto.length;

        // Comprueba si la posición es válida y no está bloqueada
        if (posicionX >= 0 && posicionX < TAM && posicionY >= 0 && posicionY < TAM
                && (laberinto[posicionX][posicionY] == ' ' || laberinto[posicionX][posicionY] == 'T')) {

            boolean teletransporte = laberinto[posicionX][posicionY] == 'T';
            laberinto[posicionX][posicionY] = 'C'; // Marca la posición como recorrida

            if (posicionX == TAM - 1 && posicionY == TAM - 1) { // Si llegó a la salida
                return true;
            }

            // Si hay teletransporte, busca la otra 'T'
            if (teletransporte) {
                int i = 0, j = 0;
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
                objetivo = ensayar(laberinto, i, j);
            }

            // Explora las cuatro direcciones
            if (!objetivo) {
                objetivo = ensayar(laberinto, posicionX + 1, posicionY); // Derecha
            }
            if (!objetivo) {
                objetivo = ensayar(laberinto, posicionX, posicionY + 1); // Abajo
            }
            if (!objetivo) {
                objetivo = ensayar(laberinto, posicionX - 1, posicionY); // Izquierda
            }
            if (!objetivo) {
                objetivo = ensayar(laberinto, posicionX, posicionY - 1); // Arriba
            }

            if (!objetivo) { // Retrocede
                laberinto[posicionX][posicionY] = 'I';
            }
        }

        return objetivo; // Devuelve si se encontró solución
    }

    // Comprueba si es seguro colocar una reina en una posición dada
    public static boolean buenSitio(int r, int col, int[] tabl) {
        int reiAant = 0;
        boolean continuar = true;

        // Comprueba si hay conflicto con reinas ya colocadas
        while (reiAant < r && continuar) {
            if (tabl[reiAant] == col) { // Misma columna
                continuar = false;
            } else if (Math.abs(reiAant - r) == Math.abs(tabl[reiAant] - col)) { // Diagonal
                continuar = false;
            }
            reiAant++;
        }

        return continuar; // Devuelve si es seguro colocar la reina
    }

}