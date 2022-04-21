package com.jsheepsim;

import com.JEngine.Game.Visual.JCamera;
import com.JEngine.Game.Visual.JWindow;
import com.JEngine.Game.Visual.Scenes.JScene;
import com.JEngine.PrimitiveTypes.Position.Vector3;
import com.JEngine.PrimitiveTypes.VeryPrimitiveTypes.JIdentity;
import com.JEngine.Utility.About.JAppInfo;
import com.JEngine.Utility.Settings.EnginePrefs;
import com.jsheepsim.Core.Simulator;
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        setEnginePrefs();
        Simulator sim1 = new Simulator("Sim 1");
        JWindow window = new JWindow(sim1.getScene(), 1,"SheepSim",stage);
        window.setTargetFPS(30);
        JCamera sceneCamera = new JCamera(new Vector3(0,0,0), window, sim1.getScene(), null, new JIdentity("Main Camera", "camera"));

        window.setBackgroundColor(Color.web("#006400"));

    }

    public static void main(String[] args) {
        launch();
    }

    private static void setEnginePrefs()
    {
        EnginePrefs.aggressiveGC = false;
        EnginePrefs.logAnnoyance = false;
        EnginePrefs.logExtra = false;
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