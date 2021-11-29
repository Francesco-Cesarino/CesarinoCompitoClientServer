package it.meucci;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;


public class Server {
    private int porta; //porta su cui si aspetta i client 
    
    private ServerSocket server; //Il server socket aspetterà l'arrivo dei client (del client)
    private Socket client; //il socket del client
    
    //cose per la comunicazione
    private BufferedReader ricevi; //qui si riceverà ciò verrà inviato dal client
    private DataOutputStream invia; //inviare i dati al client
    

    public Server(int porta) {
        this.porta = porta;
        try {
            server = new ServerSocket(porta);
        } catch (IOException ex) { System.out.println("Errore nell'inizializzazione del server\n" + ex.getMessage() ); }
    }
    
    public void connetti(){
        
        try {
            System.out.println("Server in attesa dell'arrivo di un client...");
            client = server.accept();
            System.out.println("Client arrivato: " + client.toString());
            
            ricevi = new BufferedReader(new InputStreamReader(client.getInputStream()));
            invia = new DataOutputStream(client.getOutputStream());
            
        } catch (IOException ex) {
            System.out.println("Errore nella connessione");  
            System.err.println(ex.getMessage());
        }
        
    }
    
    public void comunica(){
        String messaggio = "";
        System.out.println("Inizio comunicazione");
        
        
        try {
            invia.writeBytes("Benvenuto nel server, inviami un messaggio per iniziare" + '\n');
            ArrayList<String> numeri = new ArrayList<String>();
            while(true)
            {
                
            int contatore = 0;
                messaggio = ricevi.readLine();
                System.out.println("Il client ha inviato "+messaggio);
                if(contatore == 0)
                {
                    invia.writeBytes("Inserisci il primo numero: " + '\n');
                    messaggio = ricevi.readLine();
                    System.out.println("Il client ha inviato il primo numero: "+messaggio);
                    numeri.add(messaggio);
                    contatore++;
                }
                 if(contatore == 1)
                {
                    invia.writeBytes("Inserisci il secondo numero: " + '\n');
                    messaggio = ricevi.readLine();
                    System.out.println("Il client ha inviato il secondo numero "+messaggio);
                    numeri.add(messaggio);
                    contatore++;
                }
                if(contatore == 2)
                {
                    invia.writeBytes("Inserisci + per addizzione" + '\n');
                    invia.writeBytes("Inserisci - per sottrazzione" + '\n');
                    invia.writeBytes("Inserisci / per divisione" + '\n');
                    invia.writeBytes("Inserisci * per moltiplicazione" + '\n');
                    invia.writeBytes("Inserisci l'operazione da fare: " + '\n');
                    messaggio = ricevi.readLine();
                    System.out.println("Il client ha scelto l'operazione "+messaggio);
                    numeri.add(messaggio);
                    contatore++;

                }
                
                int a = Integer.parseInt(numeri.get(0));
                int b = Integer.parseInt(numeri.get(1));
                int risultato = 0;
                String operazione = numeri.get(2);
                if(operazione.equals("+"))
                {
                    risultato = a+b;
                    System.out.println("Il risultato dell'operazione e'"+risultato);
                    invia.writeBytes("Il risultato dell'operazione e'"+risultato + '\n');

                }
                else if(operazione.equals("-"))
                {
                    risultato = a-b;
                    System.out.println("Il risultato dell'operazione e'"+risultato);
                    invia.writeBytes("Il risultato dell'operazione e'"+risultato + '\n');
                }
                else if(operazione.equals("*"))
                {
                    risultato = a*b;
                    System.out.println("Il risultato dell'operazione e'"+risultato);
                    invia.writeBytes("Il risultato dell'operazione e'"+risultato + '\n');
                }
                else if(operazione.equals("/"))
                {
                    risultato = a/b;
                    System.out.println("Il risultato dell'operazione e'"+risultato);
                    invia.writeBytes("Il risultato dell'operazione e'"+risultato + '\n');
                }
                else
                {
                    System.out.println("invio 'Dati inseriti non validi'");
                    invia.writeBytes("Dati inseriti non validi "+ '\n');
                }
                System.out.println("invio richiesta chiusura o nuova operazione");
                invia.writeBytes("Vuoi effettuare un nuovo calcolo (Y/N)?"+ '\n');
                messaggio = ricevi.readLine();
                System.out.println("Il client ha scelto l'operazione "+messaggio);
                if(messaggio.equals("y")|| messaggio.equals("Y"))
                {
                    numeri.clear();
                }
                else if(messaggio.equals("n")|| messaggio.equals("N"))
                {
                    System.out.println("Inzio della chiusura");
                    invia.writeBytes("Arrivederci!!"+ '\n');
                    break;
                }
                else
                {
                    System.out.println("Scelta non valida, chiusura...");
                    invia.writeBytes("Arrivederci!! "+ '\n');
                    invia.writeBytes("Chiusura causa scelta non valida"+ '\n');
                }
            
            }
        } catch (IOException ex) { 
            System.out.println("Errore nella comunicazione"); 
            System.err.println(ex.getMessage());
            chiudi(); 
        }
        
        chiudi();
    }
    
    public void chiudi(){
        System.out.println("Chiusura in corso...");
        try {
            ricevi.close();
            invia.close();
            client.close();
            server.close();
        } catch (IOException ex) { 
            System.out.println("Errore durante la chiusura"); 
            
            System.err.println(ex.getMessage());
        }
    }
    
}