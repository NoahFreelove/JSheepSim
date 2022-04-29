package com.jsheepsim.Simulator;

import com.JEngine.Game.Visual.MousePointer;
import com.JEngine.Game.Visual.Scenes.SceneManager;
import com.JEngine.PrimitiveTypes.FlipFlop;
import com.JEngine.PrimitiveTypes.GameImage;
import com.JEngine.PrimitiveTypes.Position.Vector2;
import com.JEngine.PrimitiveTypes.Position.Vector3;

public class Pointer extends MousePointer {

    private WorldSimulator activeWorld;
    Vector2 windowSize;
    /**
     * Create a new cursor
     *
     * @param cursorIcon The image of the cursor
     */
    public Pointer(GameImage cursorIcon, WorldSimulator activeWorld) {
        super(cursorIcon);
        this.activeWorld = activeWorld;
    }

    FlipFlop flipFlop = new FlipFlop();

    @Override
    protected void onMouseReleased(){
        if(flipFlop.getState())
        {
            SceneManager.getActiveCamera().setZoom(new Vector2(1,1));
        }
        else
        {
            SceneManager.getActiveCamera().setZoom(new Vector2(2,2));
        }
    }

    @Override
    public void Update() {
        Vector2 offset = new Vector2(0, 0);
        offset.x = (windowSize.x / 2);
        offset.y = (windowSize.y / 2);
        //setPosition(new Vector3((float) getX()+offset.x, (float) getY()+offset.y, 0));
    }
    public WorldSimulator getActiveWorld() {
        return activeWorld;
    }
    public void setWorldSimulator(WorldSimulator activeWorld) {
        this.activeWorld = activeWorld;
        windowSize = activeWorld.getWindowSize();
    }
}
