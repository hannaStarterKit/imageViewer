/**
 * 
 */
package com.starterkit.viewer.imageView;

import java.io.File;
import java.net.MalformedURLException;

import com.starterkit.viewer.imageView.container.CircuralList;

import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author HSIENKIE
 *
 */
public class SimpleImageView {

	private CircuralList<File> images = new CircuralList<>();
	
	private double scale = 1;
	
	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}

	public int getSizeOfImages(){
		return images.size();
	}

	public void addFile(File file) {
		images.add(file);
	}

	public void reset() {
		images.reset();
	}

	public Image getNext() {
		String url = null;
		try {
			url = images.getNext().toURI().toURL().toString();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Image(url);
	}

	public Image getPrevious() {
		String url = null;
		try {
			url = images.getPrevious().toURI().toURL().toString();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Image(url);
	}

}
