package gfx;

import core.Direction;
import entity.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class AnimationManager {
    private final SpriteSet spriteSet;
    private BufferedImage currentAnimationSheet;
    private int currentFrameTime;
    private final int updatesPerFrame;
    private int frameIndex;
    private int directionIndex;
    private String currentAnimationName;
    private final boolean isLooping;

    public AnimationManager(SpriteSet spriteSet) {
        this(spriteSet, true);
    }

    public AnimationManager(SpriteSet spriteSet, boolean isLooping) {
        this.spriteSet = spriteSet;
        this.updatesPerFrame = 20;
        this.frameIndex = 0;
        this.currentFrameTime = 0;
        this.directionIndex = 0;
        this.currentAnimationName = "";
        this.isLooping = isLooping;
        playAnimation("stand");
    }

    public Image getSprite() {
        try {
            return currentAnimationSheet.getSubimage(frameIndex * Game.SPRITE_SIZE,
                    directionIndex * Game.SPRITE_SIZE,
                    Game.SPRITE_SIZE,
                    Game.SPRITE_SIZE);
        } catch (NullPointerException e) {
            System.out.println(this);
            return null;
        }
    }

    public void update(Direction direction) {
        currentFrameTime++;
        directionIndex = direction.getAnimationRow();

        if (currentFrameTime >= updatesPerFrame) {
            currentFrameTime = 0;
            frameIndex++;

            int animationSize = currentAnimationSheet.getWidth() / Game.SPRITE_SIZE;
            if (frameIndex >= animationSize) {
                frameIndex = isLooping ? 0 : animationSize - 1;
            }
        }
    }

    public void playAnimation(String name) {
        if (!name.equals(currentAnimationName)) {
            this.currentAnimationSheet = (BufferedImage) spriteSet.getOrGetDefault(name);
            currentAnimationName = name;
            frameIndex = 0;
        }
    }
}
