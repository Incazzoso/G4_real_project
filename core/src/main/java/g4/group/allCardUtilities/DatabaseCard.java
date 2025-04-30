package g4.group.allCardUtilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DatabaseCard {
    private BufferedReader reader;
    private File database;              //il luogo da dove vengono raccolte le carte
    private ArrayList<Unit> cards;      //tutte le carte caricate per l'utilizzo
    public DatabaseCard(){

        //controllo se esiste il file
        database = new File("Database_Card.csv");
        if(database.exists()){
            cards = new ArrayList<Unit>();
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
                    String[] token = line.split(";");      //il csv usa il \t per separare i campi
                    if(token.length == 8){

                        //dati per la classe "Card"
                        String name = token[0];
                        int health = Integer.parseInt(token[1]);
                        int damage= Integer.parseInt(token[2]);
                        int cost = Integer.parseInt(token[3]);
                        String description = token[4];
                        Card card = new Card(name, health,damage,cost,description);

                        //dati per la classe "Effect"
                        boolean canBurn = Boolean.parseBoolean(token[5]);
                        boolean canPiercing = Boolean.parseBoolean(token[6]);

                        //dati per l'immagine
                        String imgPath = token[7];
                        Effect effect = new Effect(canBurn, canPiercing);


                        cards.add(new Unit(card, effect, imgPath));       //aggiunta al database
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
    public void showCards(){
        for(int i = 0; i < cards.size(); i++){
            System.out.println(i +": "+ cards.get(i).getName());
        }
    }

    public ArrayList<Unit> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Unit> cards) {
        this.cards = cards;
    }
}
