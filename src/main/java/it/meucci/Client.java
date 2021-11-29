package it.meucci;
import java.net.*;
import java.io.*;
import java.util.Scanner;


public class Client {
    private int porta; 
    private String indirizzoServer; 

    private Socket server; 

    //cose per la comunicazione
    private BufferedReader ricevi; //qui si riceverà ciò verrà inviato dal server
    private DataOutputStream invia; //inviare i messaggi al server

    public Client(int porta, String indirizzoServer) {
        this.porta = porta;
        this.indirizzoServer = indirizzoServer;
    }
    
    public void connetti(){
        try {
            System.out.println("Connessione al server...");
            server = new Socket(indirizzoServer, porta);
            
            ricevi = new BufferedReader(new InputStreamReader(server.getInputStream()));
            invia = new DataOutputStream(server.getOutputStream());
            
        } catch (IOException ex) {
            System.out.println("Errore nella connessione verso il server\n" + ex.getMessage());
        }
    }
    
    public void comunica(){

        System.out.println("-------------Inizio comunicazione-------------");
    
        Scanner tastiera = new Scanner(System.in);
        while(true){
        String input;
        int contatore = 0;
        
            try {
                System.out.println(ricevi.readLine());
                input = tastiera.nextLine();
                
                invia.writeBytes(input + '\n');
                
                if(contatore == 0)
                {
                    System.out.println(ricevi.readLine());
                    input = tastiera.nextLine();
                    invia.writeBytes(input + '\n');
                    contatore++;
                }
                if(contatore == 1)
                {
                    System.out.println(ricevi.readLine());
                    input = tastiera.nextLine();
                    invia.writeBytes(input + '\n');
                    contatore++;
                }
                if(contatore == 2)
                {
                    System.out.println(ricevi.readLine());
                    System.out.println(ricevi.readLine());
                    System.out.println(ricevi.readLine());
                    System.out.println(ricevi.readLine());
                    System.out.println(ricevi.readLine());
                    input = tastiera.nextLine();
                    invia.writeBytes(input + '\n');
                    contatore++;
                }
                if(input.equalsIgnoreCase("chiudi")){
                    break;
                }
                System.out.println(ricevi.readLine());
                System.out.println(ricevi.readLine());
                input = tastiera.nextLine();
                invia.writeBytes(input + '\n');                
                if(input.equals("y")|| input.equals("Y"))
                {
                    
                }
                else if(input.equals("n")|| input.equals("N"))
                {
                    System.out.println(ricevi.readLine());
                    break;
                }
                else
                {
                    System.out.println(ricevi.readLine());
                    System.out.println(ricevi.readLine());
                    break;
                }
                
            } catch (IOException ex) { System.out.println("Errore nella comunicazione \n " + ex.getMessage());}
        }
        tastiera.close();
        chiudi();
    
    }
    
    public void chiudi(){
        System.out.println("Chiusura in corso");
        try{
            ricevi.close();
            invia.close();
            server.close();
        }catch (IOException ex){System.out.println("Errore durante la chiusura\n" + ex.getMessage());}
    }
   
    
}
