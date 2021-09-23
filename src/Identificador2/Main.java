package Identificador2;

import java.util.Scanner;



/**
 * Main
 */
public class Main {

    String palabra;
    int posicion = 0;
    int matriz[][] = new int[5][3];
    int estadosFinalizacion[] = new int[3];
    String descripcionFinalizacion[] = new String[3];
    int estadoActual = 0;

    // filas s1 --> 1 s2 -> 2
    // \digito --> 1
    // . --> 2
    // no pertenece a mi alfabeto -1
    {
        matriz[0][0] = 1;
        matriz[0][1] = -1;
        matriz[0][2] = 4;
        matriz[1][0] = 1;
        matriz[1][1] = 2;
        matriz[1][2] = -1;
        matriz[2][0] = 3;
        matriz[2][1] = -1;
        matriz[2][2] = -1;
        matriz[3][0] = 3;
        matriz[3][1] = -1;
        matriz[3][2] = -1;
        matriz[4][0] = 4;
        matriz[4][1] = -1;
        matriz[4][2] = 4;
       

        //numero entero
        estadosFinalizacion[0]=1;
        descripcionFinalizacion[0]="Numero entero";
        //numero flotante
        estadosFinalizacion[1]=3;
        descripcionFinalizacion[1]="Numero double";
        
        //Identifiacdor
        estadosFinalizacion[2]=4;
        descripcionFinalizacion[2]="Identificador";
    }

    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese la palabra: ");
        palabra = scanner.nextLine();

        while (posicion < palabra.length()) 
            getToken();
        /*
         * for (char caracter : palabra.toCharArray()) { System.out.println(caracter); }
         */

        //scanner.close();
    }

    //revisa movimiento den la matriz
    public int getSiguienteEstado(int estadoActual, int caracter) {
        int resultado = -1;
        //System.out.println("hey hey hey " + caracter);
        if (caracter >= 0 && caracter <= 2 && estadoActual!=-1) {
            resultado = matriz[estadoActual][caracter];
            //System.out.println("el matris el resul es "+resultado);
            //////////////////    0     y 2
        }
        return resultado;
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
                if (Character.isLetter(caracter)) {
                    resultado = 2;
                   
                    //System.out.print("leyendo letra");
                }
            }
        }
        //System.out.println(" caracter = "+resultado);
        return resultado;
    }

    public String getEstadoAceptacion(int i){
        String res = "Error";
        int indice = 0;
        for (int estadoAceptacion : estadosFinalizacion) {
            
            if (estadoAceptacion == i){
                res = descripcionFinalizacion[indice];
                break;
            }else {
                res="error dato";
            }
            indice++;
        }

        return res;
    }

    public void getToken() {
        estadoActual = 0;
        boolean seguirLeyendo = true;
        char tmp;
        String token = "";

        while ((seguirLeyendo) && (posicion < palabra.length())) {
            //System.out.println("tmp en while "+tmp);
            if (Character.isSpaceChar(tmp = palabra.charAt(posicion))) {
                seguirLeyendo = false;
            } else {
                // para mi automata
                int estadoTemporal = getSiguienteEstado(estadoActual, getIntCaracter(tmp));
                System.out.println("Estado actual " + estadoActual + " caracter "+ tmp + " transicion a "+estadoTemporal);
                token+=tmp;
                estadoActual = estadoTemporal;

                System.out.println(tmp);
            }
            posicion++;
        }
        System.out.println("*********Termino en el estado "+ getEstadoAceptacion(estadoActual) + " token actual : "+token);
        // verificar el estado de aceptación

    }
}