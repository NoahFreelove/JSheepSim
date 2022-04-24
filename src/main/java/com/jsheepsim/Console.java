package com.jsheepsim;

import com.JEngine.Utility.Misc.JUtility;
import com.jsheepsim.Animals.Animal;
import com.jsheepsim.Animals.Sheep;
import com.jsheepsim.Animals.Wolf;
import com.jsheepsim.Core.WorldSimulator;

import java.util.Scanner;

public class Console {
    String commandList =
                """
                help: show this help
                exit: exit the program (esc)
                clear: print some newlines to clear the console
                start: start world (F1)
                stop: pause world (F2)
                setworld: start watching a world (num keys)
                """;
    public void console(){
        System.out.println("Enter a command:");
        while (true){
            Scanner s = new Scanner(System.in);
            System.out.print("> ");
            String input = s.nextLine();
            processInput(input);
        }
    }

    void processInput(String input){
        input = input.toLowerCase().trim();

        switch (input) {
            case "exit" -> JUtility.exitApp();
            case "help" -> System.out.println(commandList);
            case "clear" -> System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            case "start" -> Main.worlds[Main.selectedWorld].startSimulation();
            case "stop" -> Main.worlds[Main.selectedWorld].pauseSimulation();
            case "setworld" -> changeWorldNum();
            case "worldstatus" -> worldStatus(-1);
            case "status" -> worldStatus(Main.selectedWorld);
        }

    }

    void changeWorldNum()
    {
        int worldNum = getWorldNum();
        if(worldNum != -1)
        {
            Main.switchWorld(worldNum);
        }
    }

    void worldStatus(int defaultNum)
    {
        int worldNum;
        if(defaultNum == -1)
        {
            worldNum = getWorldNum();
        }
        else {
            worldNum = defaultNum;
        }
        if(worldNum != -1)
        {
            WorldSimulator ws = Main.worlds[worldNum];
            Animal[] animalsAlive = ws.getWorldData().getAliveAnimals();
            Sheep[] sheepAlive = ws.getWorldData().getSheep();
            Wolf[] wolvesAlive = ws.getWorldData().getWolves();

            System.out.printf("World '%s' Status:%n", ws.getScene().getSceneName());
            System.out.printf("Day:%d%n", ws.getDay());
            System.out.printf("%d animals alive%n", animalsAlive.length);
            System.out.printf("%d sheep alive%n", sheepAlive.length);
            System.out.printf("%d wolves alive%n", wolvesAlive.length);
            System.out.println();

        }
    }

    int getWorldNum(){
        System.out.print("Enter a world number: ");
        Scanner s = new Scanner(System.in);
        int worldNum = s.nextInt();
        if(worldNum >= 0 && worldNum < Main.worlds.length)
        {
            return worldNum;
        }
        else
        {
            System.out.println("Invalid world number");
            return -1;
        }
    }
}