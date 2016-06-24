/**
 * 
 */
package com.starterkit.viewer.DraggableImageView;

import org.apache.log4j.Logger;

import javafx.scene.image.ImageView;


/**
 * @author HSIENKIE
 *
 */
// REV: ta klasa nie jest potrzebna
public class DraggableImageView extends ImageView {

	private double mouseX;
	private double mouseY;
	
	private static final Logger LOG = Logger.getLogger(DraggableImageView.class);

//	public DraggableImageView() {
//		
//
//        setOnMousePressed(event -> {
//        	LOG.debug("setOnMousePressed");
//            mouseX = event.getSceneX() ;
//            mouseY = event.getSceneY() ;
//        });
//
//        setOnMouseDragged(event -> {
//           LOG.debug("setOnMouseDragged");
//           double deltaX = event.getSceneX() - mouseX ;
//           double deltaY = event.getSceneY() - mouseY ;
//           relocate(getLayoutX() + deltaX, getLayoutY() + deltaY);
//           mouseX = event.getSceneX() ;
//           mouseY = event.getSceneY() ;
//        });
//    }
}
