/**
 * 
 */
package com.starterkit.viewer.imageView;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.starterkit.viewer.imageView.collection.CircuralList;

import javafx.scene.image.Image;

/**
 * @author HSIENKIE
 *
 */
public class SimpleImageView {

	private CircuralList<String> images = new CircuralList<>();

	private double scale = 1;

	// public List<String> getNamesOfImages() {
	// List<String> names = new ArrayList<>();
	// for (File file : images) {
	// names.add(file.getName());
	// }
	// return names;
	// }

	public List<String> getImages() {
		List<String> namesOfImages = new ArrayList<>();
		for (String absolutePath : images) {
			namesOfImages.add(absolutePath.substring(absolutePath.lastIndexOf("/") + 1));
		}	
		return namesOfImages;
	}

	public void setImages(CircuralList<String> images) {
		this.images = images;
	}

	public int getCurrentIndex() {
		return images.getCurrentIndex();
	}

	public void setCurrentIndex(int currentIndex) {
		images.setCurrentIndex(currentIndex);
	}

	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}

	public int getSizeOfImages() {
		return images.size();
	}

	public void addFile(File file) {
		try {
			images.add(file.toURI().toURL().toString());
		} catch (MalformedURLException e) {
			throw new RuntimeException(
					"Protocol handler for the URL could not be found, or some other error occurred while constructing the URL");
		}
	}

	public void reset() {
		images.reset();
	}

	public Image getNext() {
		return new Image(images.getNext());
	}

	public Image getPrevious() {
		return new Image(images.getPrevious());
	}

}
