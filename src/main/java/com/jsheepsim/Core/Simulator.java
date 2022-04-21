package com.jsheepsim.Core;

import com.JEngine.Game.Visual.Scenes.JScene;
import com.jsheepsim.Grass;

public class Simulator {
    private JScene scene;
    private Animal[] animals;
    private Grass[] grass;
    // Grid is 16x16, 32x32 pixels per square
    public Simulator(String sceneName) {
        this.scene = new JScene(500, sceneName);
    }

    public JScene getScene() {
        return scene;
    }

    public void setScene(JScene scene) {
        this.scene = scene;
    }
}
