## Implementación

### Funciones Principales

1. **`colocarReinas(int reina, int[] tablero)`**:
   - Resuelve el problema utilizando backtracking.
   - Parámetros:
     - `reina`: Índice de la reina que se intenta colocar.
     - `tablero`: Arreglo que representa las posiciones de las reinas en cada fila.
   - Retorna `true` si encuentra una solución válida.

2. **`buenSitio(int r, int col, int[] tabl)`**:
   - Verifica si colocar la reina `r` en la columna `col` es seguro.
   - Comprueba conflictos en filas, columnas y diagonales.

---
