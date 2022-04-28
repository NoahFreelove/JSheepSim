package com.jsheepsim.Entities;

import com.JEngine.Game.PlayersAndPawns.Pawn;
import com.JEngine.PrimitiveTypes.GameImage;
import com.JEngine.PrimitiveTypes.Position.Transform;
import com.JEngine.PrimitiveTypes.VeryPrimitiveTypes.Identity;
import com.jsheepsim.Simulator.Coord;
import com.jsheepsim.Simulator.WorldSettings;
import com.jsheepsim.Simulator.WorldSimulator;

import java.io.File;

public class Entity extends Pawn {
    private String name;
    private Coord pos;
    protected File image;
    private boolean hasUpdated;
    protected final WorldSimulator worldSimulator;
    protected final WorldSettings worldSettings;
    public Entity(Transform transform, Identity identity, Coord arrPos, WorldSimulator wmRef, File imagePath) {
        super(transform, new GameImage(imagePath.getAbsolutePath(),wmRef.getWorldData().getTileSize(),wmRef.getWorldData().getTileSize()), identity);
        this.name = identity.getName();
        this.pos = arrPos;
        this.worldSettings = wmRef.getWorldSettings();
        this.worldSimulator = wmRef;
        this.image = imagePath;
    }

    public void simUpdate(){

    }
    @Override
    public void Update(){
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

    public boolean hasUpdated() {
        return hasUpdated;
    }

    public void setHasUpdated(boolean hasUpdated) {
        this.hasUpdated = hasUpdated;
    }

    public WorldSimulator getWorldSimulator() {
        return worldSimulator;
    }


}
