package com.jsheepsim;

import com.JEngine.Game.Visual.JCamera;
import com.JEngine.Game.Visual.JWindow;
import com.JEngine.Game.Visual.Scenes.JScene;
import com.JEngine.PrimitiveTypes.Position.Vector3;
import com.JEngine.PrimitiveTypes.VeryPrimitiveTypes.JIdentity;
import com.JEngine.Utility.About.JAppInfo;
import com.JEngine.Utility.Settings.EnginePrefs;
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        setEnginePrefs();
        JScene scene = new JScene(500, "Sim1");
        JWindow window = new JWindow(scene, 1,"SheepSim",stage);

        JCamera sceneCamera = new JCamera(new Vector3(0,0,0), window, scene, null, new JIdentity("Main Camera", "camera"));

        window.setBackgroundColor(Color.web("#006400"));

        window.start();
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