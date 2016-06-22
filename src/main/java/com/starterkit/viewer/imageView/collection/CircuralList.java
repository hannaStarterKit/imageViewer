/**
 * 
 */
package com.starterkit.viewer.imageView.collection;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.starterkit.viewer.controller.ViewerController;

/**
 * @author HSIENKIE
 *
 */
public class CircuralList<T> extends ArrayList<T> {

	private static final Logger LOG = Logger.getLogger(CircuralList.class);

	private int currentIndex = size() - 1;

	public int getCurrentIndex() {
		return currentIndex;
	}

	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}

	public void reset() {
		currentIndex = size() - 1;
	}

	public T getNext() {
		if (currentIndex == size() - 1) {
			currentIndex = 0;
		} else {
			currentIndex++;
		}
		LOG.debug("CurrentIndex: " + currentIndex);
		return super.get(currentIndex);
	}

	public T getPrevious() {
		if (currentIndex == 0) {
			currentIndex = size() - 1;
		}
		else {
			currentIndex--;
		}
		LOG.debug("CurrentIndex: " + currentIndex);
		return super.get(currentIndex);
	}
}
