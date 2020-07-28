package economysystem.Objects;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Coinpurse {

    private String playerName = "defaultName";
    private int numCoins = 0;

    public void setPlayerName(String s) {
        playerName = s;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setCoins(int i) {
        numCoins = i;
    }

    public int getCoins() {
        return numCoins;
    }

    public void addCoins(int num) {
        numCoins = numCoins + num;
    }

    public boolean removeCoins(int num) {
        if ((numCoins - num) >= 0) {
            numCoins = numCoins - num;
            return true;
        }
        return false;
    }

    public boolean containsAtLeast(int num) {
        if (numCoins >= num) {
            return true;
        }
        return false;
    }

    public void save() {
        try {
            File saveFolder = new File("./plugins/MedievalEconomy/");
            if (!saveFolder.exists()) {
                saveFolder.mkdir();
            }
            File saveFolder2 = new File("./plugins/MedievalEconomy/Coinpurse-Records/");
            if (!saveFolder2.exists()) {
                saveFolder2.mkdir();
            }
            File saveFile = new File("./plugins/MedievalEconomy/Coinpurse-Records/" + playerName + ".txt");
            if (saveFile.createNewFile()) {
                System.out.println("Coinpurse Record for " +  playerName + " created.");
            } else {
                System.out.println("Coinpurse Record for " +  playerName + " already exists. Altering.");
            }

            FileWriter saveWriter = new FileWriter("./plugins/MedievalEconomy/Coinpurse-Records/" + playerName + ".txt");

            // actual saving takes place here
            saveWriter.write(playerName + "\n");
            saveWriter.write(numCoins + "\n");

            saveWriter.close();

            System.out.println("Successfully saved Coinpurse Record.");

        } catch (IOException e) {
            System.out.println("An error occurred saving a Coinpurse Record.");
        }
    }

    public void load(String filename) {
        try {
            File loadFile = new File("./plugins/MedievalEconomy/Coinpurse-Records/" + filename);
            Scanner loadReader = new Scanner(loadFile);

            // actual loading
            if (loadReader.hasNextLine()) {
                playerName = loadReader.nextLine();
            }

            if (loadReader.hasNextLine()) {
                numCoins = Integer.parseInt(loadReader.nextLine());
            }

            loadReader.close();
            System.out.println(filename + " successfully loaded.");
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred loading " + filename + ".");
        }
    }
}
