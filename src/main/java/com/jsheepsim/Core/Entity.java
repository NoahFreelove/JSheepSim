package com.jsheepsim.Core;

import com.JEngine.Game.PlayersAndPawns.JPawn;
import com.JEngine.PrimitiveTypes.JImage;
import com.JEngine.PrimitiveTypes.Position.Transform;
import com.JEngine.PrimitiveTypes.VeryPrimitiveTypes.JIdentity;

import java.io.File;

public class Entity extends JPawn {
    private String name;
    private Coord pos;
    protected File image = null;

    protected final WorldSimulator worldSimulator;

    public Entity(Transform transform, JIdentity jIdentity, Coord arrPos, WorldSimulator wmRef, File imagePath) {
        super(transform, new JImage(imagePath.getAbsolutePath()), jIdentity);
        this.name = jIdentity.getName();
        this.pos = arrPos;
        this.worldSimulator = wmRef;
        this.image = imagePath;
    }

    public void simUpdate(){

    }

    public String getName() {
        return name;
    }

    public Coord getPos() {
        return pos;
    }

    public int getX(){
        return pos.x;
    }

    public int getY(){
        return pos.y;
    }

    public void setPos(Coord pos) {
        this.pos = pos;
    }
    public void setX(int x){
        this.pos.x = x;
    }
    public void setY(int y){
        this.pos.y = y;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    public WorldSimulator getWorldSimulator() {
        return worldSimulator;
    }

}
