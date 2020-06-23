package economysystem;

import org.bukkit.block.Block;

public class PlankWorkshop {

    int xpos, ypos, zpos;

    public PlankWorkshop(Block block) {
        xpos = block.getX();
        ypos = block.getY();
        zpos = block.getZ();
    }

    public int getX() {
        return xpos;
    }

    public int getY() {
        return ypos;
    }

    public int getZ() {
        return zpos;
    }

}
