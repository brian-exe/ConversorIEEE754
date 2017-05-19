package com.example.eaciar.myapplication;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by eaciar on 12/05/2017.
 */


public class Conversor {
    private Parser parser;
    private ArrayList<Character> acceptedDecimalChars = new ArrayList<Character>(Arrays.asList('0', '1','2','3','4','5','6','7','8','9','-',',','.'));
    private ArrayList<Character> acceptedBinaryChars = new ArrayList<Character>(Arrays.asList('1', '0', '-',',','.'));
    private ArrayList<Character> acceptedHexaChars = new ArrayList<Character>(Arrays.asList('A', 'B', 'C','D','E','F','0','1','2','3','4','5','6','7','8','9'));
    static final Integer MAX_BINARY_FLOAT = 15;

    public Conversor(){
        this.parser= new Parser();
    }

    public String convertToIEE754(String num){
        String numEnBinario,exponente,parteEntera,parteFlotante,mantisa;
        ArrayList<String> partes;
        String signo;
        boolean seguir =true;
        Integer i =0,exp = 0;
        String hexa;

        numEnBinario=convertToBinary(num);

        /*Obtengo el signo */
        if(num.charAt(0) =='-') {
            signo = "1";
        }
       else{
            signo = "0";
        }
        partes= obtenerPartes((numEnBinario.substring(1)));//Obtengo las partes sin el primer digito porque es el signo
        /*Obtengo el exponente*/
        parteEntera= partes.get(0);
        parteFlotante=partes.get(1);

        if(parteEntera.length()==1){
            if (parteEntera.charAt(0)=='1'){
                mantisa=parteFlotante;
                exp=0;
            }
            else {
                while (seguir) {
                    if (parteFlotante.charAt(i) == '1') {
                        exp = (i + 1) * (-1);
                        exp = exp + 127;
                        seguir = false;
                    }
                    i = i + 1;
                }
                mantisa=parteFlotante.substring(i);
            }
            exponente= Integer.toBinaryString(exp);
        }
        else{
            seguir=true;
            i=0;
            exp=parteEntera.length()-1;
            exp=exp+127;
            mantisa=parteEntera.substring(1)+parteFlotante;
            exponente= Integer.toBinaryString(exp);
        }

        while(mantisa.length()<23){
            mantisa=mantisa+'0';
        }

        while(exponente.length()<8){
            exponente='0'+exponente;
        }
        hexa=obtenerHexa(signo+exponente+mantisa);

        return hexa;
    }
    public String obtenerHexa(String num) {
        String resultado="",digits;
        for(Integer i=0;i<num.length();i=i+4){
            digits=num.substring(i,i+4);
            switch (digits){
                case "0000":
                    resultado=resultado+"0";
                    break;
                case "0001":
                    resultado=resultado+"1";
                    break;
                case "0010":
                    resultado=resultado+"2";
                    break;
                case "0011":
                    resultado=resultado+"3";
                    break;
                case "0100":
                    resultado=resultado+"4";
                    break;
                case "0101":
                    resultado=resultado+"5";
                    break;
                case "0110":
                    resultado=resultado+"6";
                    break;
                case "0111":
                    resultado=resultado+"7";
                    break;
                case "1000":
                    resultado=resultado+"8";
                    break;
                case "1001":
                    resultado=resultado+"9";
                    break;
                case "1010":
                    resultado=resultado+"A";
                    break;
                case "1011":
                    resultado=resultado+"B";
                    break;
                case "1100":
                    resultado=resultado+"C";
                    break;
                case "1101":
                    resultado=resultado+"D";
                    break;
                case "1110":
                    resultado=resultado+"E";
                    break;
                case "1111":
                    resultado=resultado+"F";
                    break;
            }
        }
        return resultado;
    }
    public String convertToBinary(String num){
        String binario="";
        ArrayList<String> partes;
        String parteEntera = "";
        String parteFlotante="";
        if (isCorrectDecimalFormat(num)){
            partes = obtenerPartes(num);
            parteEntera=obtenerBinarioDeParteEntera(partes.get(0));
            parteFlotante= obtenerBinarioDeParteflotante(partes.get(1));
            binario=parteEntera+'.'+parteFlotante;
        }
        else{
            binario="Formato incorrecto!!!";
        }
        return binario;
    }

    private String obtenerBinarioDeParteEntera(String pe) {
        String signo;
        String resultado;
        String parteEntera;
        parteEntera=pe;
        if (pe.charAt(0) =='-'){
            signo="-";
            parteEntera=pe.substring(1);
        }
        else {
            signo = "+";
        }
        resultado=signo+(Integer.toBinaryString(Integer.parseInt(parteEntera)));
        return resultado;
    }
    private String obtenerBinarioDeParteflotante(String pf) {
        String flotante ="0."+pf;
        Float num;
        num=Float.parseFloat(flotante);
        String resultado="";

        while ((num!=0)&&(resultado.length()<=MAX_BINARY_FLOAT)){
            num=num*2;

            if(num>=1){
                resultado=resultado+"1";
                num=num-1;
            }
            else{
                resultado=resultado+"0";
            }
        }
        return resultado;
    }

    private ArrayList<String> obtenerPartes (String n){
        ArrayList<String> resultado = new ArrayList<String>();
        String parteEntera = "";
        String parteFlotante = "";
        boolean entero=true;
        Integer i=0;
        Character comma =acceptedDecimalChars.get(11);
        Character dot =acceptedDecimalChars.get(12);

        while (entero){
            parteEntera=parteEntera+n.charAt(i);
            i=i+1;
            /*El siguiente if es para evitar que rompa por que se paso el indice*/
            if (n.length() ==i){
                entero=false;
            }
            else {
                entero=!((n.charAt(i)==comma) || (n.charAt(i)==dot));
            }
        }

        for (i =(parteEntera.length()+1);i<(n.length());i++){
            parteFlotante =parteFlotante +n.charAt(i);
        }
        resultado.add(parteEntera);
        resultado.add(parteFlotante);
        return resultado;
    }
    private boolean isCorrectBinaryFormat(String n){
        boolean correct = true;

        for (Integer i=0;i<(n.length());i++){
            if(!(isAcceptedBinaryChar(n.charAt(i)))) {
                correct = false;
            }
        }
        return correct;
    }
    private boolean isAcceptedBinaryChar(Character c){
        boolean correct = false;
        for (Integer i=0;i<(acceptedBinaryChars.size());i++) {
            if (acceptedBinaryChars.get(i) ==c){
                correct=true;
            }
        }

        return correct;
    }

    private boolean isCorrectDecimalFormat(String n){
        boolean correctChars = true;
        boolean correctOrderOfChars = true;
        boolean correctFormat =true;

        correctChars=checkDecimalChars(n);
        correctOrderOfChars=checkCorrectDecimalOrder(n);

        correctFormat= (correctOrderOfChars)&&(correctChars);

        return correctFormat;
    }

    protected boolean checkCorrectDecimalOrder(String n){
        boolean correctOrder =true;
        Character comma =acceptedDecimalChars.get(11);
        Character dot =acceptedDecimalChars.get(12);
        Character dash =acceptedDecimalChars.get(10);

        if ((n.charAt(0)== comma)||(n.charAt(0)== dot)){
            correctOrder=false;
        }
        for (Integer i=1;i<(n.length());i++){
            if (n.charAt(i) ==dash){
                correctOrder=false;
            }
        }
        return correctOrder;
    }

    protected boolean checkDecimalChars(String n){
        boolean correctChars =true;

        for (Integer i=0;i<(n.length());i++) {
            if (!(isAcceptedDecimalChar(n.charAt(i)))) {
                correctChars = false;
            }
        }
        return correctChars;
    }

    private boolean isAcceptedDecimalChar(Character c){
        boolean correct = false;
        for (Integer i=0;i<(acceptedDecimalChars.size());i++) {
            if (acceptedDecimalChars.get(i) ==c){
                correct=true;
            }
        }

        return correct;
    }

}
