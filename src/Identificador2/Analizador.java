package Identificador2;

import static Identificador2.VerificadorTipo.*;
import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

/**
 * Main
 */
public class Analizador {

    String palabra;
    int posicion = 0;
    int columTmp = 1;
    int matriz[][] = new int[9][6];
    int estadosFinalizacion[] = new int[7];
    String descripcionFinalizacion[] = new String[7];
    int estadoActual = 0;
    int contadorErrores = 0;
    Token token = new Token();
    Token tokensT[];
    ArrayList<Token> tokens = new ArrayList();
    ArrayList<Token> tokensError = new ArrayList();
    int i = 0;
    int fila = 1;
    int columna = 1;
    int filaF = 1;
    TipoDeDato tD;

    // filas s1 --> 1 s2 -> 2
    // \digito --> 1
    // . --> 2
    // no pertenece a mi alfabeto -1
    {
        matriz[0][0] = 1;
        matriz[0][1] = 6;
        matriz[0][2] = 4;
        matriz[0][3] = 6;
        matriz[0][4] = 7;
        matriz[0][5] = 8;

        matriz[1][0] = 1;
        matriz[1][1] = 2;
        matriz[1][2] = -1;

        matriz[2][0] = 3;
        matriz[2][1] = -1;
        matriz[2][2] = -1;

        matriz[3][0] = 3;
        matriz[3][1] = -1;
        matriz[3][2] = -1;

        matriz[4][0] = 5;
        matriz[4][1] = -1;
        matriz[4][2] = 4;

        matriz[5][0] = 5;
        matriz[5][2] = 4;
        matriz[6][0] = -1;

        //numero entero
        estadosFinalizacion[0] = 1;
        descripcionFinalizacion[0] = tD.ENTERO.getTipo();
        //numero decimal
        estadosFinalizacion[1] = 3;
        descripcionFinalizacion[1] = tD.DECIMAL.getTipo();

        //Identifiacdor
        estadosFinalizacion[2] = 4;
        descripcionFinalizacion[2] = tD.IDENTIFICADOR.getTipo();
        estadosFinalizacion[3] = 5;
        descripcionFinalizacion[3] = tD.IDENTIFICADOR.getTipo();

        //Signoo de puntucion
        estadosFinalizacion[4] = 6;
        descripcionFinalizacion[4] = tD.getPUNTUACION().getTipo();

        //Signo agrupacion
        estadosFinalizacion[5] = 7;
        descripcionFinalizacion[5] = tD.AGRUPACION.getTipo();
        //operadores
        estadosFinalizacion[6] = 8;
        descripcionFinalizacion[6] = tD.OPERADOR.getTipo();
    }

    /* public static void main(String[] args) {
        new Main();
    }*/
    public Analizador() {

    }

    //revisa movimiento den la matriz
    public int getSiguienteEstado(int estadoActual, int caracter) {
        int resultado = -1;
        
        if (caracter >= 0 && caracter <= 6 && estadoActual != -1) {
            resultado = matriz[estadoActual][caracter];
            
        }
        return resultado;
    }

    public enum TipoToken {
        IDENTIFICADOR,
        ENTERO,
        DECIMAL,
        AGRUPACION,
        PUNTUACION,
        OPERADOR,
        ERROR
    }

    //alfabeto
    public int getIntCaracter(char caracter) {
        int resultado = -1;

        if (Character.isDigit(caracter)) {
            resultado = 0;
        } else {
            if (caracter == '.') {
                resultado = 1;
            } else {
                if (caracter == 'ñ') {
                    resultado = -1;
                } else {
                    if (Character.isLetter(caracter)) {
                        resultado = 2;
                    } else {
                        if (esSignoAgrupacion(caracter)) {
                            resultado = 4;
                        } else {
                            if (esSignoDePuntuacion(caracter)) {
                                resultado = 3;
                            } else {
                                if (esOperador(caracter)) {
                                    resultado = 5;
                                }
                            }
                        }
                    }
                }
            }
        }
        //System.out.println(" caracter = "+resultado);
        return resultado;
    }

    public String getEstadoAceptacion(int i) {
        String res = "ERROR";
        int indice = 0;
        for (int estadoAceptacion : estadosFinalizacion) {

            if (estadoAceptacion == i) {
                res = descripcionFinalizacion[indice];
                break;
            } else {
                res = "ERROR";
            }
            indice++;
        }

        return res;
    }

    public ArrayList getToken(String palabra, JTextArea TOK, JTextArea estados) {

        ArrayList<String> lista = new ArrayList();

        while (posicion < palabra.length()) {
            estadoActual = 0;
            String token = "";
            boolean seguirLeyendo = true;
            char tmp;
            String estadoA = null;
            

            while ((seguirLeyendo) && (posicion < palabra.length())) {
                
                tmp = palabra.charAt(posicion);

                if (Character.isSpaceChar(tmp)) {
                    seguirLeyendo = false;
                    System.out.println("space");

                } else if (Character.isSpace(tmp)) {

                    seguirLeyendo = false;

                    columTmp = 0;
                    fila++;
                } else {
                    int estadoTemporal = getSiguienteEstado(estadoActual, getIntCaracter(tmp));
                    if (estadoTemporal == 0) {
                        estadoTemporal = -1;

                    }
                    //                                          4,0
                  String moviminetos = "S" + estadoActual + " --------- " + tmp + "-------->  S" + estadoTemporal + "\n";
                    estados.append(moviminetos);
                    token += tmp;
                    estadoActual = estadoTemporal;
                    estadoA = getEstadoAceptacion(estadoActual);

                    if (estadoActual == -1) {
                        seguirLeyendo = false;
                    }

                    //columTmp=posicion;
                    columna = columTmp;
                    filaF = fila;

                }
                columTmp++;
                posicion++;
            }
            if (estadoA != null) {

                Token tokenO = new Token(estadoA, token, filaF, columna);

                String msj = "***TOKEN " + estadoA + "  : " + token + "\n";

                if (estadoA.equalsIgnoreCase("ERROR")) {
                    Token tokenErrores = new Token(estadoA, token, fila, columTmp);
                    tokensError.add(tokenErrores);
                    contadorErrores++;
                }

                
                tokens.add(tokenO);
                lista.add(msj);
                TOK.append(msj);
                i++;

            }

            // verificar el estado de aceptación
        }
        System.out.println("Cantidad de errores: " + contadorErrores);
        return lista;
    }

    public int getContadorErrores() {
        return contadorErrores;
    }

    public Token[] getTokensT() {
        return tokensT;
    }

    public ArrayList<Token> getTokensError() {
        return tokensError;
    }

    public ArrayList<Token> getTokens() {
        return tokens;
    }

    public void setTokens(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

}
