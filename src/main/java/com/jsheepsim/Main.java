package com.jsheepsim;

import com.JEngine.Game.Visual.GameCamera;
import com.JEngine.Game.Visual.GameWindow;
import com.JEngine.Game.Visual.Scenes.SceneManager;
import com.JEngine.PrimitiveTypes.Position.Vector3;
import com.JEngine.PrimitiveTypes.VeryPrimitiveTypes.Identity;
import com.JEngine.Utility.About.GameInfo;
import com.JEngine.Utility.Misc.GameUtility;
import com.JEngine.Utility.Settings.EnginePrefs;
import com.jsheepsim.Simulator.Pointer;
import com.jsheepsim.Simulator.WorldSimulator;
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static javafx.scene.input.KeyEvent.KEY_PRESSED;

public class Main extends Application {

    public static WorldSimulator[] worlds = new WorldSimulator[4]; // Holds different simulator instances

    public static int selectedWorld = 0; // current spectated world

    public static Pointer pointer; // create a new mouse cursor, so we can track its position
    public static GameCamera camera; // create a camera instance to render the scene

    @Override
    public void start(Stage stage) {
        setEngineSettings(); // set engine info

        // Create our simulator worlds
        // Sim name, World seed, XSize, YSize, TileSize, Ticks/Second
        WorldSimulator sim1 = new WorldSimulator("Sim 1", 0, 16,16,32,10);
        WorldSimulator sim2 = new WorldSimulator("Sim 2", 5, 24,24,32,3);
        WorldSimulator sim3 = new WorldSimulator("Sim 3", 25, 16,16,32,2);

        // World array helps us keep track of our world instances
        worlds[0] = sim1;
        worlds[1] = sim2;
        worlds[2] = sim3;

        // Start up the window
        GameWindow window = new GameWindow(sim1.getScene(), 1,"Sheep Simulator",stage);
        window.setTargetFPS(60);

        Text helpText = new Text(
                """
                Key Bindings:
                [1] - Sim 1
                [2] - Sim 2
                [3] - Sim 3
                
                F1 - Start/Unpause sim
                F2 - Pause sim
                F3 - Reset Sim
                F4 - Step Sim
                F5 - Reset Camera
                MouseClick - Toggle Zoom
                """
        );
        // Set text properties
        helpText.setFill(Color.WHITE);
        helpText.setStyle("-fx-font-weight: bold");
        helpText.setTranslateX(5);
        helpText.setTranslateY(10);
        // Add to screen
        window.parent.getChildren().add(helpText);

        // Init the camera. When the camera is created and the window is not initialized, the camera will start the window automatically
        camera = new GameCamera(new Vector3(0,0,0), window, sim1.getScene(), null, new Identity("Main Camera", "camera"));

        pointer = new Pointer(null);

        // Add the camera and pointer to every world which lets us track their position
        sim1.getScene().add(pointer);
        sim1.getScene().add(camera);
        sim2.getScene().add(pointer);
        sim2.getScene().add(camera);
        sim3.getScene().add(pointer);
        sim3.getScene().add(camera);

        // Set window key-binds
        window.getStage().addEventHandler(KEY_PRESSED, (e) -> {
            switch (e.getCode()) {
                case ESCAPE -> GameUtility.exitApp();
                case F1 -> Main.worlds[selectedWorld].startSimulation();
                case F2 -> Main.worlds[selectedWorld].pauseSimulation();
                case F3 -> Main.worlds[selectedWorld].reloadWorld();
                case F4 -> Main.worlds[selectedWorld].step();
                case DIGIT1 -> setSpectatedWorld(0);
                case DIGIT2 -> setSpectatedWorld(1);
                case DIGIT3 -> setSpectatedWorld(2);
            }
        });
        window.setBackgroundColor(Color.web("#006400"));

        // Start spectating the first world by default. This is a very useful method!
        setSpectatedWorld(0);

        // we want to start the console on a separate thread, so we can keep the game running as it infinitely looks for input
        Thread consoleThread = new Thread(() -> {
            Console console = new Console();
            console.startConsole();
        });
        consoleThread.start();
    }

    public static void main(String[] args) {
        launch();
    }

    /**
     * You can ignore this method, it's just used to set the engine settings
     */
    private static void setEngineSettings()
    {
        EnginePrefs.aggressiveGC = false;
        EnginePrefs.logDebug = false;
        EnginePrefs.logExtra = false;
        EnginePrefs.logImportant = true;
        EnginePrefs.logInfo = true;

        GameInfo.appName = "Sheep Simulator";
        GameInfo.authors = new String[]{"Noah Freelove"};
        GameInfo.appVersionMinor = 6;
        GameInfo.appVersionMajor = 1;
        GameInfo.buildID = "build 2022.05.01.1";
        GameInfo.year = 2022;

        GameInfo.logGameInfo(false);
    }

    /**
     * Focus on a specific world. Makes the camera render the world and all key presses will be sent to the world.
     * @param world world index in the worlds array
     */
    public static void setSpectatedWorld(int world)
    {
        selectedWorld = world;
        SceneManager.switchScene(worlds[selectedWorld].getScene());
        worlds[selectedWorld].adjustWindowSize();
    }

}