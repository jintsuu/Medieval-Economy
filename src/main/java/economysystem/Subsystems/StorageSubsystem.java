package economysystem.Subsystems;

import economysystem.Objects.Coinpurse;
import economysystem.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class StorageSubsystem {

    Main main = null;

    public StorageSubsystem(Main plugin) {
        main = plugin;
    }

    public void save() {
        saveCoinpurseFilenames();
        saveCoinpurses();
    }

    public void load() {
        loadCoinpurses();
    }

    public void saveCoinpurseFilenames() {
        try {
            File saveFolder = new File("./plugins/MedievalEconomy/");
            if (!saveFolder.exists()) {
                saveFolder.mkdir();
            }
            File saveFile = new File("./plugins/MedievalEconomy/" + "coinpurse-record-filenames.txt");
            if (saveFile.createNewFile()) {
                System.out.println("Save file for coinpurse record filenames created.");
            } else {
                System.out.println("Save file for coinpurse record filenames already exists. Overwriting.");
            }

            FileWriter saveWriter = new FileWriter(saveFile);

            // actual saving takes place here
            for (Coinpurse purse : main.coinpurses) {
                saveWriter.write(purse.getPlayerName() + ".txt" + "\n");
            }

            saveWriter.close();

        } catch (IOException e) {
            System.out.println("An error occurred while saving coinpurse record filenames.");
        }
    }

    public void saveCoinpurses() {
        for (Coinpurse purse : main.coinpurses) {
            purse.save();
        }
    }

    public void loadCoinpurses() {
        try {
            System.out.println("Attempting to load coinpurse records...");
            File loadFile = new File("./plugins/MedievalEconomy/" + "coinpurse-record-filenames.txt");
            Scanner loadReader = new Scanner(loadFile);

            // actual loading
            while (loadReader.hasNextLine()) {
                String nextName = loadReader.nextLine();
                Coinpurse temp = new Coinpurse();
                temp.load(nextName);

                // existence check
                boolean exists = false;
                for (int i = 0; i < main.coinpurses.size(); i++) {
                    if (main.coinpurses.get(i).getPlayerName().equalsIgnoreCase(temp.getPlayerName())) {
                        main.coinpurses.remove(i);
                    }
                }

                main.coinpurses.add(temp);
            }

            loadReader.close();
            System.out.println("Coinpurse records successfully loaded.");
        } catch (FileNotFoundException e) {
            System.out.println("Error loading the coinpurse records!");
        }
    }

}
