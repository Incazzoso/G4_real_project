package g4.group;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DatabaseCard {
    private BufferedReader reader;
    private File database;              //il luogo da dove vengono raccolte le carte
    private ArrayList<Card> cards;      //tutte le carte caricate per l'utilizzo
    DatabaseCard(){

        //controllo se esiste il file
        database = new File("Database_Card.csv");
        if(database.exists()){

            //try catch per il reader
            try {
                reader = new BufferedReader(new FileReader(database));
            } catch (Exception e) {
                System.out.println("file inesistente, spostato o rimosso");
            }

            //legge e popola l'arrayList
            try{
                String line;
                line = reader.readLine(); //ATTENZONE: LA PRIMA VOLTA NON DEVE LEGGERE PERCHè NEL .CSV CI SONO DEGLI INDICI
                line = reader.readLine(); //riga sotto l'intestazione

                //leggi finchè c'è da leggere
                while(line != null){
                    String[] token = line.split("\t");      //il csv usa il \t per separare i campi
                    if(token.length == 5){
                        String name = token[0];
                        int health = Integer.parseInt(token[1]);
                        int damage= Integer.parseInt(token[2]);
                        int cost = Integer.parseInt(token[3]);
                        String description = token[4];
                        cards.add(new Card(name, health, damage, cost, description));       //aggiunta al database
                    }else{
                        System.err.println("riga formata in modo errato");
                    }
                    line = reader.readLine();
                }
                reader.close();     //chiude il reader
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }else{
            System.out.println("file inesistente, spostato o rimosso");
        }
    }
}
