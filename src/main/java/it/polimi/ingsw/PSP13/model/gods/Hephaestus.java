package it.polimi.ingsw.PSP13.model.gods;

import it.polimi.ingsw.PSP13.controller.TurnHandler;
import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.board.Level;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Coords;

public class Hephaestus extends Turn {

    /**
     * Builds a level in the specified position if useEffect == false,
     * 2 level otherwise
     * @param builder builder that is currently building
     * @param buildingPosition coordinates of the cell where the builder wants to build
     */
    @Override
    public void build(Builder builder, Coords buildingPosition){
        int currentLevelHeight = match.getHeight(buildingPosition);
        if (currentLevelHeight <= 1) {
            boolean useEffect = turnHandler.getInputUseEffect("Hephaestus");
            if (useEffect) {
                match.setCellLevel(buildingPosition, Level.findLevelByHeight(currentLevelHeight+2));
            } else {
                match.setCellLevel(buildingPosition, Level.findLevelByHeight(currentLevelHeight+1));
            }
        } else { super.build(builder, buildingPosition); }
        match.notifyMap();
    }



}
