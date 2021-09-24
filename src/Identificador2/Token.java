

package Identificador2;

/**
 * 
 * @author Estuardo Ramos
 */
public class Token {
    String nombre;
    String lexema;
    int fila;
    int columna;
    String posicion= fila+" , "+columna;

    public Token() {
    }
    
    

    public Token(String nombre, String lexema, int fila, int columna) {
        this.nombre = nombre;
        this.lexema = lexema;
        this.fila = fila;
        this.columna = columna;
    }

    public Token(String nombre, String lexema) {
        this.nombre = nombre;
        this.lexema = lexema;
    }

    @Override
    public String toString() {
        return "Token{" + "nombre=" + nombre + ", lexema=" + lexema + ", fila=" + fila + ", columna=" + columna + ", posicion=" + posicion + '}';
    }
    
    
    

}
