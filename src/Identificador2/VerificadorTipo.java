/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Identificador2;

/**
 * 
 * @author Estuardo Ramos
 */
public class VerificadorTipo {
    
    
     //Verifica si es un operador
    public static boolean esOperador(char signo){
        boolean esAgru=false;
        char parantesis='(';
        if (signo=='+' || signo=='-' || signo=='/' || signo=='*' || signo=='%') {
            esAgru=true;
        }
        return esAgru;
    }
    
    //Verifica si es signo de puntuacion
    public static boolean esSignoDePuntuacion(char sig){
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
    
    
    //Verfica si es signo de agrupacio
    public static boolean esSignoAgrupacion(char signo){
        boolean esAgru=false;
        char parantesis='(';
        if (signo=='(' || signo==')' || signo=='[' || signo==']' || signo=='{' || signo=='}') {
            esAgru=true;
        }
        return esAgru;
    }

}
