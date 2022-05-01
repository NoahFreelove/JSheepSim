package com.jsheepsim.Simulator;

import com.JEngine.Game.Visual.GameCamera;
import com.JEngine.Game.Visual.MousePointer;
import com.JEngine.Game.Visual.Scenes.SceneManager;
import com.JEngine.PrimitiveTypes.FlipFlop;
import com.JEngine.PrimitiveTypes.GameImage;
import com.JEngine.PrimitiveTypes.Position.Direction;
import com.JEngine.PrimitiveTypes.Position.Vector2;
import com.JEngine.PrimitiveTypes.Position.Vector3;
import com.JEngine.Utility.Input;
import javafx.scene.input.KeyCode;

/**
 * Pointer class has functions related to the mouse cursor.
 */
public class Pointer extends MousePointer {

    public Pointer(GameImage cursorIcon) {
        super(cursorIcon);
    }

    FlipFlop flipFlop = new FlipFlop();

    // When the mouse is pressed, change the zoom level between 1 and 2
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

    // When you press F5, reset the camera details
    @Override
    public void onKeyPressed(KeyCode key) {
        if(key == KeyCode.F5){
            SceneManager.getActiveCamera().setPosition(new Vector3(0,0,0));
            SceneManager.getActiveCamera().setZoom(new Vector2(1,1));
            flipFlop.setState(false);
        }
    }

    // Will run every frame, not every simulation step
    @Override
    public void Update() {
        // Listen for key presses and move the camera accordingly
        GameCamera c = SceneManager.getActiveCamera();
        Vector3 pos = c.getPosition();
        if(Input.A_Pressed){
            c.setPosition(new Vector3(pos.x-10,pos.y, 0));
        }
        if(Input.D_Pressed){
            c.setPosition(new Vector3(pos.x+10,pos.y, 0));
        }
        if(Input.W_Pressed){
            c.setPosition(new Vector3(pos.x,pos.y-10, 0));
        }
        if(Input.S_Pressed){
            c.setPosition(new Vector3(pos.x,pos.y+10, 0));
        }
    }
}
