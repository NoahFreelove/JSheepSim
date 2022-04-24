package com.jsheepsim;

import com.JEngine.Game.Visual.JCamera;
import com.JEngine.Game.Visual.JWindow;
import com.JEngine.Game.Visual.Scenes.JSceneManager;
import com.JEngine.PrimitiveTypes.Position.Vector3;
import com.JEngine.PrimitiveTypes.VeryPrimitiveTypes.JIdentity;
import com.JEngine.Utility.About.JAppInfo;
import com.JEngine.Utility.Misc.JUtility;
import com.JEngine.Utility.Settings.EnginePrefs;
import com.jsheepsim.Core.WorldSimulator;
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import static javafx.scene.input.KeyEvent.*;

public class Main extends Application {

    public static WorldSimulator[] worlds = new WorldSimulator[4];
    public static int selectedWorld = 0;

    @Override
    public void start(Stage stage) {
        setEnginePrefs();
        // Sim name, World seed, XSize, YSize, TileSize, Ticks/Second
        WorldSimulator sim1 = new WorldSimulator("Sim 1", 0, 16,16,32,1);
        WorldSimulator sim2 = new WorldSimulator("Sim 2", 5, 24,24,32,3);
        WorldSimulator sim3 = new WorldSimulator("Sim 3", 2, 32,32,32,0.8);
        WorldSimulator sim4 = new WorldSimulator("Sim 4", 2, 8,8,32,2);

        worlds[0] = sim1;
        worlds[1] = sim2;
        worlds[2] = sim3;
        worlds[3] = sim4;

        JWindow window = new JWindow(sim1.getScene(), 1,"SheepSim",stage);
        window.setTargetFPS(60);

        new JCamera(new Vector3(0,0,0), window, sim1.getScene(), null, new JIdentity("Main Camera", "camera"));

        window.getStage().addEventHandler(KEY_PRESSED, (e) -> {
            switch (e.getCode()) {
                case ESCAPE -> JUtility.exitApp();
                case F1 -> Main.worlds[selectedWorld].startSimulation();
                case F2 -> Main.worlds[selectedWorld].pauseSimulation();
                case DIGIT1 -> switchWorld(0);
                case DIGIT2 -> switchWorld(1);
                case DIGIT3 -> switchWorld(2);
                case DIGIT4 -> switchWorld(3);
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
        EnginePrefs.logImportant = false;
        EnginePrefs.logInfo = false;

        JAppInfo.appName = "SheepSim";
        JAppInfo.authors = new String[]{"Noah Freelove"};
        JAppInfo.appVersionMinor = 1;
        JAppInfo.appVersionMajor = 0;
        JAppInfo.buildID = "2022.04.21";
        JAppInfo.year = 2022;

        JAppInfo.logAppInfo(false);
    }

    public static void switchWorld(int world)
    {
        selectedWorld = world;
        JSceneManager.setActiveScene(worlds[selectedWorld].getScene());
        worlds[selectedWorld].adjustWindowSize();
    }

}