package economysystem.Objects;

import economysystem.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

public class Coinpurse {

    Main main = null;

    public Coinpurse(Main plugin) {
        main = plugin;
    }

    private UUID uuid = null;
    private int numCoins = 0;

    public void setPlayerUUID(UUID id) {
        uuid = id;
    }

    public UUID getPlayerUUID() {
        return uuid;
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

        // TODO: write new save method based on JSON

    }

    public void legacyLoad(String filename) {
        try {
            File loadFile = new File("./plugins/MedievalEconomy/Coinpurse-Records/" + filename);
            Scanner loadReader = new Scanner(loadFile);

            // actual loading
            if (loadReader.hasNextLine()) {
                String playerName = loadReader.nextLine();

                // TODO: find UUID based on player name and set it here
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
