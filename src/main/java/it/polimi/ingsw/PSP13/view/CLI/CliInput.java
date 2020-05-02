package it.polimi.ingsw.PSP13.view.CLI;

import it.polimi.ingsw.PSP13.immutables.BuilderVM;
import it.polimi.ingsw.PSP13.immutables.MapVM;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.network.client_dispatching.MessageClientsInfoCV;
import it.polimi.ingsw.PSP13.view.Input;
import it.polimi.ingsw.PSP13.view.ObservableToController;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class
CliInput extends Input {

    private Scanner scanner;
    private String input;
    private MapPrinter mapPrinter;

    public CliInput(ObservableToController controller) {
        super(controller);
        scanner = new Scanner(System.in);
        mapPrinter = new MapPrinter();
    }

    @Override
    public void nicknameInput(boolean error)
    {
        if(!error)
            System.out.println("Insert your \u001B[1mNICKNAME\u001B[0m:");
        else
            System.out.println("\u001B[31mThe nickname you have chosen is not available for this match, please insert another nickname:\u001b[0m");
        input = scanner.nextLine();
        super.controller.notifyNickname(input);
    }


    @Override
    public void godSelectionInput(List<String> godsList, int godsNumber, boolean error)
    {
        String input;
        String pattern = "([a-zA-Z]{3,} *, *){"+(godsNumber-1)+"}([a-zA-Z]{3,})";

        if(error)
            System.out.println("\u001B[31mThere was and error with you last selection, please repeat\u001b[0m");

        Pattern p = Pattern.compile("([a-zA-Z]{3,} *, *)+([a-zA-Z]{3,})");
        System.out.println("Please \u001B[1mSELECT "+ godsNumber +" GODS\u001B[0m for this match");
        System.out.println("This is the list of all the available gods you can choose from:");
        System.out.print(godsList.get(0));
        for(int i=1;i<godsList.size();i++)
            System.out.print(", " + godsList.get(i));
        System.out.println("\nType the name of the gods you choose separated by a comma (e.g. Zeus, Athena, Apollo)");
        input = scanner.nextLine();
        while(!p.matcher(input).matches())
        {
            System.out.println("\u001B[31mWRONG FORMAT, correct is: Zeus, Athena, Apollo\u001b[0m");
            System.out.println("Type the name of the gods you choose separated by a comma (e.g. Zeus, Athena, Apollo)");

            input = scanner.nextLine();
        }

        super.controller.notifyGodSelection(input);
    }

    @Override
    public void effectInput(String god) {
        System.out.println("Do you want to \u001B[1mUSE THE EFFECT\u001B[0m of " + god +"?");
        input = scanner.nextLine();
        controller.notifyEffect(input);
    }

    @Override
    public void godInput(List<String> chosenGods, boolean error)
    {
        Pattern p = Pattern.compile("[a-zA-Z]{3,}");

        if(error)
            System.out.println("\u001B[31mThere was and ERROR with you last selection, please repeat\u001b[0m");

        System.out.println("This is the list of the available gods you can choose from for this match:");
        System.out.print(chosenGods.get(0));
        for(int i=1;i<chosenGods.size();i++)
            System.out.print(", " + chosenGods.get(i));
        System.out.print("\nChoose your \u001B[1mGOD\u001B[0m: ");
        input = scanner.nextLine();
        while(!p.matcher(input).matches())
        {
            System.out.println("\u001B[31mWRONG INPUT, chose your god\u001B[0m:");
            input = scanner.nextLine();
        }
        super.controller.notifyGod(input);
    }

    @Override
    public void builderSetUpInput( boolean firstCall, boolean error)
    {
        MapPrinter.setWaitOtherClients(false);
        Coords coords;

        MapPrinter.printMap();
        if(error)
            System.out.println("\u001B[31mYou can't place your builder there, choose again the position:\u001b[0m");
        else System.out.println("\u001B[1mPLACE YOUR BUILDER\u001B[0m on the map and type the position in the format \u001B[3mrow,column\u001B[0m:");

        if(firstCall)
            System.out.println("Choose the position of your first builder:");
        else
            System.out.println("Choose the position of your second builder:");
        coords = readCoords();
        MapPrinter.setWaitOtherClients(true);
        super.controller.notifySetupBuilder(coords);

    }

    /**
     * reads an input from console that is in a certain format
     * @return a Coords class based on user input
     */
    private Coords readCoords()
    {
        input = scanner.nextLine();
        Pattern p = Pattern.compile("\\d,\\d");

        while(!p.matcher(input).matches())
        {
            System.out.println("\u001B[31mWRONG FORMAT, correct is: x,y\u001b[0m");
            input = scanner.nextLine();
        }

        String[] dissection = input.split(",");
        int x = Integer.parseInt(dissection[0]);
        int y = Integer.parseInt(dissection[1]);
        return new Coords(x,y);
    }

    /**
     * asks the player to choose a build
     * @return a Coords class based on user input
     */
    public void chooseBuilder(String player)
    {
        MapPrinter.setWaitOtherClients(false);
        MapPrinter.printMap();
        System.out.println("It's your turn now");
        System.out.println("\u001B[1mSELECT A BUILDER\u001B[0m, type the coordinates of your builder in the format \u001B[3mrow,column\u001B[0m:");
        controller.notifyBuilderChoice(readCoords());
    }

    @Override
    public void moveInput(List<Coords> checkMoveCells, boolean error)
    {
        MapPrinter.setHighlightedCells(checkMoveCells);
        MapPrinter.printMap();

        if(error)
            System.out.println("\u001B[31mThere was an ERROR with your last selection\u001b[0m");

        System.out.println("Now you have to \u001B[1mMOVE A BUILDER\u001B[0m");
        System.out.println("You can choose a cell to build on only from the \u001B[33mHIGHLIGHTED CELLS\u001B[0m, type the cell coordinates in the format \u001B[3mrow,column\u001B[0m:");
        Coords coords = readCoords();

        controller.notifyMoveInput(coords);

    }

    @Override
    public void buildInput(List<Coords> checkBuildCells, boolean error)
    {
        MapPrinter.setWaitOtherClients(false);
        MapPrinter.setHighlightedCells(checkBuildCells);
        MapPrinter.printMap();

        if(error)
            System.out.println("\u001B[31mThere was an ERROR with your last selection\u001b[0m");

        System.out.println("Now have to \u001B[1mBUILD ON A CELL\u001B[0m");
        System.out.println("You can build only on the \u001B[33mHIGHLIGHTED CELLS\u001B[0m, type the position in the format \u001B[3mrow,column\u001B[0m:");
        Coords coords = readCoords();

        MapPrinter.setWaitOtherClients(true);
        controller.notifyBuildInput(coords);

    }

    @Override
    public void removeBlock(List<Coords> removableBlocks, boolean error)
    {
        MapPrinter.setHighlightedCells(removableBlocks);
        MapPrinter.printMap();

        if(error)
            System.out.println("\u001B[31mThere was an ERROR with your last selection\u001b[0m");

        System.out.println("Select the cell you want to \u001B[1mREMOVE A BLOCK\u001B[0m from");
        System.out.println("You can remove a block only from the \u001B[33mHIGHLIGHTED CELLS\u001B[0m, type the position in the format \u001B[3mrow,column\u001B[0m:");
        Coords coords = readCoords();

        controller.notifyRemoveInput(coords);
    }




    @Override
    public void updateMap(MapVM mapVM) { mapPrinter.updateMapCLI(mapVM); }

    @Override
    public void updateBuilders(BuilderVM builderVM){ mapPrinter.updateBuildersCLI(builderVM); }

    @Override
    public void notifyWin() { mapPrinter.notifyWin(); }

    @Override
    public void notifyLose() { mapPrinter.notifyLose(); }

    @Override
    public void setupClientsInfo(MessageClientsInfoCV messageClientsInfoCV) {
        MapPrinter.setClientsInfo(messageClientsInfoCV);
    }

    @Override
    public void printWaitGodsSelection(String challenger) {
        System.out.println("Please wait, " + challenger + " is choosing the gods... ... ...");
    }

    @Override
    public void printWaitGodSelection(String player) {
        System.out.println("Please wait, " + player + " is choosing his god... ... ...");
    }

    @Override
    public void printAssignedGod(String assignedGod) {
        System.out.println("Only one God available. " + assignedGod + " is assigned to you");
    }



}
