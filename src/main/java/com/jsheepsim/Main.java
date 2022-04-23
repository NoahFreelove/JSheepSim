package com.jsheepsim;

import com.JEngine.Game.Visual.JCamera;
import com.JEngine.Game.Visual.JWindow;
import com.JEngine.PrimitiveTypes.Position.Vector3;
import com.JEngine.PrimitiveTypes.VeryPrimitiveTypes.JIdentity;
import com.JEngine.Utility.About.JAppInfo;
import com.JEngine.Utility.Settings.EnginePrefs;
import com.jsheepsim.Core.WorldSimulator;
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        setEnginePrefs();
        WorldSimulator sim1 = new WorldSimulator("Sim 1", 0);
        JWindow window = new JWindow(sim1.getScene(), 1,"SheepSim",stage);
        window.setTargetFPS(60);
        new JCamera(new Vector3(0,0,0), window, sim1.getScene(), null, new JIdentity("Main Camera", "camera"));

        window.setBackgroundColor(Color.web("#006400"));
        sim1.startSimulation();
    }

    public static void main(String[] args) {
        launch();
    }

    private static void setEnginePrefs()
    {
        EnginePrefs.aggressiveGC = false;
        EnginePrefs.logDebug = false;
        EnginePrefs.logExtra = true;
        EnginePrefs.logImportant = true;
        EnginePrefs.logInfo = true;

        JAppInfo.appName = "SheepSim";
        JAppInfo.authors = new String[]{"Noah Freelove"};
        JAppInfo.appVersionMinor = 1;
        JAppInfo.appVersionMajor = 0;
        JAppInfo.buildID = "2022.04.21";
        JAppInfo.year = 2022;

        JAppInfo.logAppInfo(false);
    }
}