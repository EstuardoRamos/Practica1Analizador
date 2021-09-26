

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
        this.posicion=fila+"+"+columna;
    }

    public Token(String nombre, String lexema) {
        this.nombre = nombre;
        this.lexema = lexema;
    }

    @Override
    public String toString() {
        return "Token{" + "nombre=" + nombre + ", lexema=" + lexema + ", fila=" + fila + ", columna=" + columna + ", posicion=" + posicion + '}';
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }
    
    
    

}
