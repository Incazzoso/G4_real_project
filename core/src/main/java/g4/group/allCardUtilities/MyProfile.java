package g4.group.allCardUtilities;

import java.io.*;
import java.util.ArrayList;

public class MyProfile {
    private Player myself;
    private BufferedReader reader;
    private File data;              //il luogo da dove vengono raccolte le carte


    public MyProfile(Player dataOfThePlayer){
        myself = dataOfThePlayer;
        data = new File("core/src/main/java/g4/group/Data/MyProfileData.csv");
        saveProfile();
    }

    public MyProfile(){
        myself = new Player("Default", new Hand(new ArrayList<Unit>()));
        data = new File("core/src/main/java/g4/group/Data/MyProfileData.csv");
        loadProfile();
    }

    public Player getMyself() {
        return myself;
    }

    //salva il tuo deck
    public void saveProfile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(data))) {
            System.out.println("Salvataggio in corso...");
            writer.write("name;health;damage;cost;description;canBurn;canPiercing;imagepath\n"); // Intestazione CSV

            for (Unit card : myself.getHand().getCards()) {
                writer.write(card.getName() + ";" +
                    card.getHealth() + ";" +
                    card.getDamage() + ";" +
                    card.getCost() + ";" +
                    card.getDescription() + ";" +
                    card.getEffect().canBurnOther() + ";" +
                    card.getEffect().canPiercing() + ";" +
                    card.getPath() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Errore nel salvataggio del profilo!");
        }
        System.out.println("Salvataggio completato");
    }

    //carica il tuo deck
    public void loadProfile() {
        try {
            data = new File("core/src/main/java/g4/group/Data/MyProfileData.csv");
            if (!data.exists()) {
                System.err.println("Profilo non trovato! Creane uno nuovo.");
                return;
            }
            System.out.println("caricamento profilo in corso...");
            reader = new BufferedReader(new FileReader(data));
            String line = reader.readLine(); // Salta l'intestazione
            while ((line = reader.readLine()) != null) {
                String[] token = line.split(";");

                if (token.length == 8) {
                    String name = token[0];
                    int health = Integer.parseInt(token[1]);
                    int damage = Integer.parseInt(token[2]);
                    int cost = Integer.parseInt(token[3]);
                    String description = token[4];
                    boolean canBurn = Boolean.parseBoolean(token[5]);
                    boolean canPiercing = Boolean.parseBoolean(token[6]);
                    String imgPath = token[7];

                    Effect effect = new Effect(canBurn, canPiercing);
                    Unit card = new Unit(name, health, damage, cost, effect, imgPath);
                    myself.getHand().addCard(card); // Aggiunge la carta al deck del giocatore
                } else {
                    System.err.println("Errore nel formato della linea CSV: " + line);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Errore nel caricamento del profilo!");
        }
        System.out.println("caricamento profilo fatto!");
    }

    public Hand getMyDeck() {
        return myself.getHand();
    }
}
