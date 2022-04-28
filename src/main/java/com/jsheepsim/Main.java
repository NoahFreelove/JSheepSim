package com.jsheepsim;

import com.JEngine.Game.Visual.GameCamera;
import com.JEngine.Game.Visual.GameWindow;
import com.JEngine.Game.Visual.Scenes.SceneManager;
import com.JEngine.PrimitiveTypes.Position.Vector3;
import com.JEngine.PrimitiveTypes.VeryPrimitiveTypes.Identity;
import com.JEngine.Utility.About.GameInfo;
import com.JEngine.Utility.Misc.GameUtility;
import com.JEngine.Utility.Settings.EnginePrefs;
import com.jsheepsim.Simulator.WorldSimulator;
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import static javafx.scene.input.KeyEvent.KEY_PRESSED;

public class Main extends Application {

    public static WorldSimulator[] worlds = new WorldSimulator[4];
    public static int selectedWorld = 0;

    @Override
    public void start(Stage stage) {
        setEnginePrefs();
        // Sim name, World seed, XSize, YSize, TileSize, Ticks/Second
        WorldSimulator sim1 = new WorldSimulator("Sim 1", 0, 16,16,32,10);
        WorldSimulator sim2 = new WorldSimulator("Sim 2", 5, 24,24,32,3);
        WorldSimulator sim3 = new WorldSimulator("Sim 3", 2, 8,8,32,2);

        worlds[0] = sim1;
        worlds[1] = sim2;
        worlds[2] = sim3;

        GameWindow window = new GameWindow(sim1.getScene(), 1,"SheepSim",stage);
        window.setTargetFPS(60);

        new GameCamera(new Vector3(0,0,0), window, sim1.getScene(), null, new Identity("Main Camera", "camera"));

        window.getStage().addEventHandler(KEY_PRESSED, (e) -> {
            switch (e.getCode()) {
                case ESCAPE -> GameUtility.exitApp();
                case F1 -> Main.worlds[selectedWorld].startSimulation();
                case F2 -> Main.worlds[selectedWorld].pauseSimulation();
                case F3 -> Main.worlds[selectedWorld].reloadWorld();
                case F4 -> Main.worlds[selectedWorld].step();
                case DIGIT1 -> switchWorld(0);
                case DIGIT2 -> switchWorld(1);
                case DIGIT3 -> switchWorld(2);
            }
        });
        window.setBackgroundColor(Color.web("#006400"));
        switchWorld(0);

        Thread consoleThread = new Thread(() -> {
            Console console = new Console();
            console.console();
        });
        consoleThread.start();
    }

    public static void main(String[] args) {
        launch();
    }

    private static void setEnginePrefs()
    {
        EnginePrefs.aggressiveGC = false;
        EnginePrefs.logDebug = false;
        EnginePrefs.logExtra = false;
        EnginePrefs.logImportant = true;
        EnginePrefs.logInfo = false;

        GameInfo.appName = "Sheep Simulator";
        GameInfo.authors = new String[]{"Noah Freelove"};
        GameInfo.appVersionMinor = 0;
        GameInfo.appVersionMajor = 1;
        GameInfo.buildID = "build 2022.04.28";
        GameInfo.year = 2022;

        GameInfo.logGameInfo(true);
    }

    public static void switchWorld(int world)
    {
        selectedWorld = world;
        SceneManager.switchScene(worlds[selectedWorld].getScene());
        worlds[selectedWorld].adjustWindowSize();
    }

}