package economysystem.Objects;

import economysystem.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Coinpurse {

    Main main = null;

    public Coinpurse(Main plugin) {
        main = plugin;
    }

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

            FileWriter saveWriter = new FileWriter("./plugins/MedievalEconomy/Coinpurse-Records/" + playerName + ".txt");

            // actual saving takes place here
            saveWriter.write(playerName + "\n");
            saveWriter.write(numCoins + "\n");

            saveWriter.close();

        } catch (IOException e) {
            System.out.println(main.getConfig().getString("coinpurseSaveErrorText"));
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
        } catch (FileNotFoundException e) {
            System.out.println(main.getConfig().getString("coinpurseLoadErrorText") + filename);
        }
    }
}
