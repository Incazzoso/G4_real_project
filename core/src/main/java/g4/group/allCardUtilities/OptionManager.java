package g4.group.allCardUtilities;

import java.io.*;

public class OptionManager {
    private File data;
    private float vm;
    private float ve;
    private String soundPath;

    public OptionManager() {
        data = new File("core/src/main/java/g4/group/Data/opt.csv");
    }
    public float getVm() {
        return vm;
    }
    public void setVm(float vm) {
        this.vm = vm;
    }
    public float getVe() {
        return ve;
    }
    public void setVe(float ve) {
        this.ve = ve;
    }
    public String getSoundPath() {
        return soundPath;
    }
    public void setSoundPath(String soundPath) {
        switch (soundPath){
            case "in the name of those who fallen":
                this.soundPath="assets/music/inNameOfThoseWhoFallen.mp3";
                break;
            case "a march to thrive this war without reason":
                this.soundPath="assets/music/a-march-to-thrive-this-war-without-reason.mp3";
                break;
            case "pinocchio's last lies":
                this.soundPath="assets/music/Pinocchio's-last-lies.mp3";
                break;
            case "che sia vita o morte":
                this.soundPath="assets/music/Che-Sia-Vita-O-Morte.mp3";
                break;
            default:
                System.out.println("non funzeca");
                break;
        }
    }
    public void setSoundPath2(String soundPath) {
        this.soundPath=soundPath;
    }

    public void saveOpt(float vm, float ve) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(data))) {
            System.out.println("Salvataggio in corso...");
            writer.write("volumeMusica;volumeEffeti;musicPath\n"); // Intestazione CSV
            writer.write(vm + ";" + ve + ";" + this.getSoundPath() + "\n");
        } catch (IOException e) {
            System.err.println("Errore nel salvataggio deI DATI!");
        }
        System.out.println("Salvataggio completato");
    }
    public void loadData() {
        try {
            data = new File("core/src/main/java/g4/group/Data/opt.csv");
            if (!data.exists()) {
                System.err.println("FILE non trovato!");
                return;
            }
            System.out.println("caricamento FILE in corso...");
            BufferedReader reader = new BufferedReader(new FileReader(data));
            String line = reader.readLine(); // Salta l'intestazione
            while ((line = reader.readLine()) != null) {
                String[] token = line.split(";");
                setVm(Float.parseFloat(token[0]));
                setVe(Float.parseFloat(token[1]));
                setSoundPath2(token[2]);
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Errore nel caricamento del profilo!");
        }
        System.out.println("caricamento profilo fatto!");
    }
}
