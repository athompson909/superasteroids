package edu.byu.cs.superasteroids.ship_builder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import edu.byu.cs.superasteroids.base.IView;
import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.data_access.DbOpenHelper;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.model_classes.AsteroidsGame;
import edu.byu.cs.superasteroids.model_classes.Cannon;
import edu.byu.cs.superasteroids.model_classes.Engine;
import edu.byu.cs.superasteroids.model_classes.ExtraPart;
import edu.byu.cs.superasteroids.model_classes.MainBody;
import edu.byu.cs.superasteroids.model_classes.PowerCore;
import edu.byu.cs.superasteroids.model_classes.Ship;
import edu.byu.cs.superasteroids.model_classes.ShipPart;

/**
 * Created by athom909 on 2/27/16.
 * Processes Ship builder inputs
 * installed in GameActivity.onCreate(..)
 */
public class ShipBuildingController implements IShipBuildingController {

    private ShipBuildingActivity view;
    private IShipBuildingView.PartSelectionView previousView;

    final float SCALE = 0.2f;

    public ShipBuildingController(ShipBuildingActivity shipBuildingActivity) {
        view = shipBuildingActivity;
        previousView = IShipBuildingView.PartSelectionView.MAIN_BODY;
        AsteroidsGame.setShip(new Ship());
    }

    /**
     * relevant questions related to this function:
     * so partView is passed in and then I have to do the setArrow function on "View" but what is
     *  that and how do I understand what this whole thing wants me to do??
     *  [in short: Is there some way I should be accessing "View"?
     *             Am I suppose to be accessing the context via the constructor that has a
     *              ShipBuildingActivity object as the parameter?
     *             Are you suppose to set all 4 arrows for each case in the function? (I think so)
     * I think I'm going to wait to call setStartGameButton until the onPartSelected(...)
     * @param partView
     */
    @Override
    public void onViewLoaded(IShipBuildingView.PartSelectionView partView) {
        switch(partView) {
            // for some reason an Exception is occurring here and that's what needs to be figured out... the code is seems to be right!
            case MAIN_BODY:
                //void setArrow(PartSelectionView partView, ViewDirection arrow, boolean visible, String text);
                view.setArrow(IShipBuildingView.PartSelectionView.MAIN_BODY,
                        IShipBuildingView.ViewDirection.UP, false, "");
                view.setArrow(IShipBuildingView.PartSelectionView.MAIN_BODY,
                        IShipBuildingView.ViewDirection.RIGHT, true, "Cannons");
                view.setArrow(IShipBuildingView.PartSelectionView.MAIN_BODY,
                        IShipBuildingView.ViewDirection.LEFT, false, "");
                view.setArrow(IShipBuildingView.PartSelectionView.MAIN_BODY,
                        IShipBuildingView.ViewDirection.DOWN, false, "");
                //view.setStartGameButton(true);
                return;
            case CANNON:
                view.setArrow(IShipBuildingView.PartSelectionView.CANNON,
                        IShipBuildingView.ViewDirection.UP, false, "");
                view.setArrow(IShipBuildingView.PartSelectionView.CANNON,
                        IShipBuildingView.ViewDirection.RIGHT, true, "Extra Parts");
                view.setArrow(IShipBuildingView.PartSelectionView.CANNON,
                        IShipBuildingView.ViewDirection.LEFT, true, "Main Bodies");
                view.setArrow(IShipBuildingView.PartSelectionView.CANNON,
                        IShipBuildingView.ViewDirection.DOWN, false, "");
                //view.setStartGameButton(true);
                return;
            case EXTRA_PART:
                view.setArrow(IShipBuildingView.PartSelectionView.EXTRA_PART,
                        IShipBuildingView.ViewDirection.UP, false, "");
                view.setArrow(IShipBuildingView.PartSelectionView.EXTRA_PART,
                        IShipBuildingView.ViewDirection.RIGHT, true, "Engine");
                view.setArrow(IShipBuildingView.PartSelectionView.EXTRA_PART,
                        IShipBuildingView.ViewDirection.LEFT, true, "Cannons");
                view.setArrow(IShipBuildingView.PartSelectionView.EXTRA_PART,
                        IShipBuildingView.ViewDirection.DOWN, false, "");
                //view.setStartGameButton(true);
                return;
            case ENGINE:
                view.setArrow(IShipBuildingView.PartSelectionView.ENGINE,
                        IShipBuildingView.ViewDirection.UP, false, "");
                view.setArrow(IShipBuildingView.PartSelectionView.ENGINE,
                        IShipBuildingView.ViewDirection.RIGHT, true, "Power Core");
                view.setArrow(IShipBuildingView.PartSelectionView.ENGINE,
                        IShipBuildingView.ViewDirection.LEFT, true, "Extra Parts");
                view.setArrow(IShipBuildingView.PartSelectionView.ENGINE,
                        IShipBuildingView.ViewDirection.DOWN, false, "");
                //view.setStartGameButton(true);
                return;
            case POWER_CORE:
                view.setArrow(IShipBuildingView.PartSelectionView.POWER_CORE,
                        IShipBuildingView.ViewDirection.UP, false, "");
                view.setArrow(IShipBuildingView.PartSelectionView.POWER_CORE,
                        IShipBuildingView.ViewDirection.RIGHT, false, "");
                view.setArrow(IShipBuildingView.PartSelectionView.POWER_CORE,
                        IShipBuildingView.ViewDirection.LEFT, true, "Engines");
                view.setArrow(IShipBuildingView.PartSelectionView.POWER_CORE,
                        IShipBuildingView.ViewDirection.DOWN, false, "");
                //view.setStartGameButton(true);
                // once you get to power core and select one then the game should start
                return;
            default:
                assert false;
        }
    }


    @Override
    public void update(double elapsedTime) {

    }


    /**
     * This function extracts ship part data from AsteroidsGame
     * this is only for ship data, other data will be loaded in GameController.loadContent(...)
     * then the data is used to load ship part images,
     * once the images are loaded, the image IDs are passed to the ShipBuildingActivity using
     *  the setPartViewImageList() function
     * @param content An instance of the content manager. This should be used to load images and sound.
     */
    @Override
    public void loadContent(ContentManager content) {
        List<List<Integer>> idList = AsteroidsGame.getInstance().loadShipParts(content);

        // let's see if calling this here instead makes this work better:
        //  (to fix the nullPointerException)
        view.setPartViewImageList(IShipBuildingView.PartSelectionView.MAIN_BODY, idList.get(0));
        view.setPartViewImageList(IShipBuildingView.PartSelectionView.CANNON, idList.get(1));
        view.setPartViewImageList(IShipBuildingView.PartSelectionView.EXTRA_PART, idList.get
                (2));
        view.setPartViewImageList(IShipBuildingView.PartSelectionView.ENGINE, idList.get(3));
        view.setPartViewImageList(IShipBuildingView.PartSelectionView.POWER_CORE, idList.get
                (4));
    }



    @Override
    public void unloadContent(ContentManager content) {

    }

    /**
     * ALL drawing should be done from the draw() method (i.e., if you try to draw using
     * DrawingHelper outside the draw() method, it won’t work properly).
     * Use DrawingHelper.drawImage(imageId, x, y, rotationDegrees, scaleX, scaleY, alpha) to draw
     *  the ship parts
     * Define SCALE as needed to make the ship look good on your device.
     * Origin of view is in top-left (y axis inverted). Image center drawn at (x, y). Zero degrees
     *  is up. 0 alpha is completely transparent, 255 alpha is completely opaque.
     * Drawing main body
     * Point gameViewCenter = new Point(DrawingHelper.getGameViewWidth() / 2,
     *  DrawingHelper.getGameViewHeight() / 2);
     *
     *  WE MIGHT NEED TO REMEMBER THE OFFSETS FOR DOING A ROTATE FUNCTION IN THE FUTURE
     *   ( rotatedPartOffset = GraphicsUtils.rotate(partOffset, angleRadians) )
     *
     *
     * SO BASICALLY THIS IS SOOOOO COMPLICATED FOR SOME REASON CUZ THE DRAW FUNCTION ISN'T
     * WORKING...
     */
    @Override
    public void draw() {
        AsteroidsGame.getShip().drawShip(new PointF(DrawingHelper.getGameViewWidth() / 2,
                DrawingHelper.getGameViewHeight() / 2), -Math.PI / 2, SCALE);
    }

    /**
     *
     * @param direction The direction of the swipe/fling.
     */
    @Override
    public void onSlideView(IShipBuildingView.ViewDirection direction) {
        switch (previousView) {
            case MAIN_BODY:
                onSlideMainBody(direction);
                return;
            case CANNON:
                onSlideCannon(direction);
                return;
            case EXTRA_PART:
                onSlideExtraPart(direction);
                return;
            case ENGINE:
                onSlideEngine(direction);
                return;
            case POWER_CORE:
                onSlidePowerCore(direction);
                return;
            default:
                assert false;
        }
    }

    private void onSlideMainBody(IShipBuildingView.ViewDirection direction) {
        if(AsteroidsGame.getShip().getMainBody() != null) {
            if (direction == IShipBuildingView.ViewDirection.LEFT) {
                view.animateToView(IShipBuildingView.PartSelectionView.CANNON,
                        IShipBuildingView.ViewDirection.RIGHT);
                previousView = IShipBuildingView.PartSelectionView.CANNON;
            }
        }
        // else maybe have a toast pop up saying to select a main body
    }

    private void onSlideCannon(IShipBuildingView.ViewDirection direction) {
        if(direction == IShipBuildingView.ViewDirection.LEFT) {
            if(AsteroidsGame.getShip().getCannon() != null) {
                view.animateToView(IShipBuildingView.PartSelectionView.EXTRA_PART,
                        IShipBuildingView.ViewDirection.RIGHT);
                previousView = IShipBuildingView.PartSelectionView.EXTRA_PART;
            }
        }
        else if(direction == IShipBuildingView.ViewDirection.RIGHT) {
            view.animateToView(IShipBuildingView.PartSelectionView.MAIN_BODY,
                    IShipBuildingView.ViewDirection.LEFT);
            previousView = IShipBuildingView.PartSelectionView.MAIN_BODY;
        }
    }

    private void onSlideExtraPart(IShipBuildingView.ViewDirection direction) {
        if(direction == IShipBuildingView.ViewDirection.LEFT) {
            if(AsteroidsGame.getShip().getExtraPart() != null) {
                view.animateToView(IShipBuildingView.PartSelectionView.ENGINE,
                        IShipBuildingView.ViewDirection.RIGHT);
                previousView = IShipBuildingView.PartSelectionView.ENGINE;
            }
        }
        else if(direction == IShipBuildingView.ViewDirection.RIGHT) {
            view.animateToView(IShipBuildingView.PartSelectionView.CANNON,
                    IShipBuildingView.ViewDirection.LEFT);
            previousView = IShipBuildingView.PartSelectionView.CANNON;
        }
    }

    private void onSlideEngine(IShipBuildingView.ViewDirection direction) {
        if(direction == IShipBuildingView.ViewDirection.LEFT) {
            if(AsteroidsGame.getShip().getEngine() != null) {
                view.animateToView(IShipBuildingView.PartSelectionView.POWER_CORE,
                        IShipBuildingView.ViewDirection.RIGHT);
                previousView = IShipBuildingView.PartSelectionView.POWER_CORE;
            }
        }
        else if(direction == IShipBuildingView.ViewDirection.RIGHT) {
            view.animateToView(IShipBuildingView.PartSelectionView.EXTRA_PART,
                    IShipBuildingView.ViewDirection.LEFT);
            previousView = IShipBuildingView.PartSelectionView.EXTRA_PART;
        }
    }

    private void onSlidePowerCore(IShipBuildingView.ViewDirection direction) {
        if(direction == IShipBuildingView.ViewDirection.RIGHT) {
            view.animateToView(IShipBuildingView.PartSelectionView.ENGINE,
                    IShipBuildingView.ViewDirection.LEFT);
            previousView = IShipBuildingView.PartSelectionView.ENGINE;
        }
    }


    /**
     * installs the ship part in the model
     * call view.setStartGameButton(...) to enable/disable "Start Game" button
     * @param index The list index of the selected part.
     */
    @Override
    public void onPartSelected(int index) {
        switch (previousView) {
            case MAIN_BODY:
                AsteroidsGame.getShip().setMainBody(AsteroidsGame.getInstance().getMainBodies()
                        .get(index));
                break;
            case CANNON:
                AsteroidsGame.getShip().setCannon(AsteroidsGame.getInstance().getCannons()
                        .get(index));
                break;
            case EXTRA_PART:
                AsteroidsGame.getShip().setExtraPart(AsteroidsGame.getInstance().getExtraParts()
                        .get(index));
                break;
            case ENGINE:
                AsteroidsGame.getShip().setEngine(AsteroidsGame.getInstance().getEngines()
                        .get(index));
                break;
            case POWER_CORE:
                AsteroidsGame.getShip().setPowerCore(AsteroidsGame.getInstance().getPowerCores()
                        .get(index));
                break;
            default:
                assert false;
        }
        if(AsteroidsGame.getShip().allShipPartsChosen())
            view.setStartGameButton(true);
    }

    @Override
    public void onStartGamePressed() {
        view.startGame();
    }

    @Override
    public void onResume() {

    }

    @Override
    public IView getView() {
        return null;
    }

    @Override
    public void setView(IView view) {

    }

}
