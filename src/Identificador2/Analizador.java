package Identificador2;

import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;



/**
 * Main
 */
public class Analizador {

    String palabra;
    int posicion = 0;
    int matriz[][] = new int[9][6];
    int estadosFinalizacion[] = new int[7];
    String descripcionFinalizacion[] = new String[7];
    int estadoActual = 0;
    int contadorErrores = 0;

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
        estadosFinalizacion[0]=1;
        descripcionFinalizacion[0]="NUMERO ENTERO";
        //numero flotante
        estadosFinalizacion[1]=3;
        descripcionFinalizacion[1]="DECIMAL";
        
        //Identifiacdor
        estadosFinalizacion[2]=4;
        descripcionFinalizacion[2]="IDENTIFICADOR";
        estadosFinalizacion[3]=5;
        descripcionFinalizacion[3]="IDENTIFICADOR";
        
        //Signoo de puntucion
        estadosFinalizacion[4]=6;
        descripcionFinalizacion[4]="SIGNO DE PUNTUACION";
        
        //Signo agrupacion
        estadosFinalizacion[5]=7;
        descripcionFinalizacion[5]="SIGNO DE AGRUPACION";
        //operadores
        estadosFinalizacion[6]=8;
        descripcionFinalizacion[6]="OPERADOR";
    }

   /* public static void main(String[] args) {
        new Main();
    }*/

    public Analizador() {
        /* Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese la palabra: ");
        palabra = scanner.nextLine();*/

        
        /*
         * for (char caracter : palabra.toCharArray()) { System.out.println(caracter); }
         */

        //scanner.close();
    }

    //revisa movimiento den la matriz
    public int getSiguienteEstado(int estadoActual, int caracter) {
        int resultado = -1;
        //System.out.println("hey hey hey " + caracter);
        if (caracter >= 0 && caracter <= 6 && estadoActual!=-1) {
            resultado = matriz[estadoActual][caracter];
            //System.out.println("el matris el resul es "+resultado);
            //////////////////    0     y 6
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
                }else {
                if (esSignoAgrupacion(caracter)) {
                    resultado = 4;
                }else {
                if (esSignoDePuntuacion(caracter)) {
                    resultado = 3;
                }else {
                if (esOperador(caracter)) {
                    resultado = 5;
                }
            }
            }
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

    public ArrayList getToken(String palabra) {

        ArrayList<String> lista = new ArrayList();
        String token1 = "";
        while (posicion < palabra.length()) {
            estadoActual = 0;
            String token = "";
            boolean seguirLeyendo = true;
            char tmp;
            String estadoA = null;
            System.out.println("NO SIGUE LEYENDO 2");

            while ((seguirLeyendo) && (posicion < palabra.length())) {
                //System.out.println("tmp en while "+tmp);
                tmp = palabra.charAt(posicion);
                if (Character.isSpaceChar(tmp) || Character.isSpace(tmp)) {
                    seguirLeyendo = false;
                    System.out.println("NO SIGUE LEYENDO 1");

                } else {
                    // para mi automata

                    int estadoTemporal = getSiguienteEstado(estadoActual, getIntCaracter(tmp));
                    if (estadoTemporal == 0) {
                        estadoTemporal = -1;
                    }
                    //                                          4,0
                    System.out.println("Estado actual " + estadoActual + " caracter " + tmp + " transicion a " + estadoTemporal);
                    token += tmp;
                    estadoActual = estadoTemporal;
                    if (estadoActual == -1) {
                        seguirLeyendo = false;
                        contadorErrores++;
                    }
                    estadoA = getEstadoAceptacion(estadoActual);

                    System.out.println(tmp);

                }
                posicion++;
            }
            if (estadoA != null) {
                String msj = "***TOKEN " + estadoA + "  : " + token;
                System.out.println(msj);
                System.out.println("Cantidad de errores: "+contadorErrores);

                //System.out.println("*********Termino en el estado "+ getEstadoAceptacion(estadoActual) + " token actual : "+token);
                lista.add(msj);

            }

            // verificar el estado de aceptación
        }
        return lista;
    }
    
    public boolean esSignoDePuntuacion(char sig){
        boolean es=false;
        switch(sig){
            case '.':
                es=true;
                break;
            case ',':
                es=true;
                break;
            case ':':
                es=true;
                break;
            case ';':
                es=true;
                break;
        }
        return es;
    }
    
    
    public static boolean esSignoAgrupacion(char signo){
        boolean esAgru=false;
        char parantesis='(';
        if (signo=='(' || signo==')' || signo=='[' || signo==']' || signo=='{' || signo=='}') {
            esAgru=true;
        }
        return esAgru;
    }
    
    public static boolean esOperador(char signo){
        boolean esAgru=false;
        char parantesis='(';
        if (signo=='+' || signo=='-' || signo=='/' || signo=='*' || signo=='%') {
            esAgru=true;
        }
        return esAgru;
    }
    
    public void cargarArchivos(JTextArea cadenaTxt){
        JFileChooser fc = new JFileChooser();
        fc.showOpenDialog(null);
        File archivo = fc.getSelectedFile();
        try {
            FileReader fr = new FileReader(archivo);
            BufferedReader br= new BufferedReader(fr);
            String texto="";
            String linea="";
            while((linea=br.readLine()) != null) {
                texto+=linea+"\n";
            }
            cadenaTxt.setText(texto);
            JOptionPane.showMessageDialog(null, "Archivo cargado puede analizarlo");
        } catch (Exception e) {
        }
    }
}