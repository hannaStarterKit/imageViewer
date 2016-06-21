/**
 * 
 */
package com.starterkit.viewer.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;

/**
 * @author HSIENKIE
 *
 */
public class ImageToView {
	private final ObjectProperty<Image> image = new SimpleObjectProperty<>();

	public final Image getImage() {
		return image.get();
	}

	public final void setImage(Image value) {
		image.set(value);
	}

	public ObjectProperty<Image> imageProperty() {
		return image;
	}
}
