/**
 * 
 */
package com.starterkit.viewer.imageView.container;

import java.util.ArrayList;

/**
 * @author HSIENKIE
 *
 */
public class CircuralList<T> extends ArrayList<T> {

	private int currentIndex = size() - 1;
	
	public void reset() {
		currentIndex = size() - 1;
	}

	public T getNext() {
		if (currentIndex == size()) {
			currentIndex = 0;
		}
		if (currentIndex == -1) {
			currentIndex = size() == 1 ? 0 : 1;
		}
		return super.get(currentIndex++);
	}

	public T getPrevious() {
		if (currentIndex == -1) {
			currentIndex = size() - 1;
		}
		if (currentIndex == size()) {
			currentIndex = size() == 1 ? 0 : size() - 2;
		}
		return super.get(currentIndex--);
	}
}
