/**
 * 
 */
package com.starterkit.viewer.model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.scene.image.Image;

/**
 * @author HSIENKIE
 *
 */
public class ImageToView {

	private final ObjectProperty<Image> image = new SimpleObjectProperty<>();
	private final ListProperty<String> result = new SimpleListProperty<>(
			FXCollections.observableList(new ArrayList<>()));

	public final Image getImage() {
		return image.get();
	}

	public final void setImage(Image value) {
		image.set(value);
	}

	public ObjectProperty<Image> imageProperty() {
		return image;
	}

	public final List<String> getResult() {
		return result.get();
	}

	public final void setResult(List<String> value) {
		result.setAll(value);
	}

	public ListProperty<String> resultProperty() {
		return result;
	}
}
