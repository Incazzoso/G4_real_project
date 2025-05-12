package g4.group.allCardUtilities;

import java.io.*;

public class OptionManager {
    private File data;
    private float vm;
    private float ve;

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
    public void saveOpt(float vm, float ve) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(data))) {
            System.out.println("Salvataggio in corso...");
            writer.write("volumeMusica;volumeEffeti\n"); // Intestazione CSV
            writer.write(vm + ";" + ve + "\n");
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
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Errore nel caricamento del profilo!");
        }
        System.out.println("caricamento profilo fatto!");
    }
}
