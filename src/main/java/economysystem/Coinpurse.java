package economysystem;

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
        if (numCoins - num > 0) {
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

    }

    public void load(String filename) {

    }
}
