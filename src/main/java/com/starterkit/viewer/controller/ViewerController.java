/**
 * 
 */
package com.starterkit.viewer.controller;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.starterkit.viewer.DraggableImageView.DraggableImageView;
import com.starterkit.viewer.imageView.SimpleImageView;
import com.starterkit.viewer.model.ImageToView;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.stage.FileChooser;

/**
 * @author HSIENKIE
 *
 */
public class ViewerController {

	private static final Logger LOG = Logger.getLogger(ViewerController.class);

	/**
	 * Resource bundle loaded with this controller. JavaFX injects a resource
	 * bundle specified in {@link FXMLLoader#load(URL, ResourceBundle)} call.
	 * <p>
	 * NOTE: The variable name must be {@code resources}.
	 * </p>
	 */
	@FXML
	private ResourceBundle resources;

	/**
	 * URL of the loaded FXML file. JavaFX injects an URL specified in
	 * {@link FXMLLoader#load(URL, ResourceBundle)} call.
	 * <p>
	 * NOTE: The variable name must be {@code location}.
	 * </p>
	 */
	@FXML
	private URL location;

	/**
	 * JavaFX injects an object defined in FXML with the same "fx:id" as the
	 * variable name.
	 */
	@FXML
	private Button openButton;

	@FXML
	private Button nextButton;

	@FXML
	private Button slideButton;

	@FXML
	private Button previousButton;

	@FXML
	private DraggableImageView imageView = new DraggableImageView();

	@FXML
	private ScrollPane scrollPane = new ScrollPane();

	final private FileChooser fileChooser = new FileChooser();

	private SimpleImageView simpleImageView = new SimpleImageView();

	private final ImageToView model = new ImageToView();

	public ViewerController() {
		LOG.debug("Constructor");

		// imageView.setPlaceholder(new
		// Label(resources.getString("table.emptyText")));
	}

	/**
	 * The JavaFX runtime calls this method after loading the FXML file.
	 * <p>
	 * The @FXML annotated fields are initialized at this point.
	 * </p>
	 * <p>
	 * NOTE: The method name must be {@code initialize}.
	 * </p>
	 */
	@FXML
	private void initialize() {
		LOG.debug("initialize()");
		imageView.imageProperty().bind(model.imageProperty());
	}

	
	@FXML
	private void scrolling(ScrollEvent scrollEvent) {
		if (scrollEvent.getDeltaY() > 0) {
			simpleImageView.setScale(simpleImageView.getScale() + 0.1);
		} else if (scrollEvent.getDeltaY() < 0) {
			simpleImageView.setScale(simpleImageView.getScale() - 0.1);
		}
		imageView.setScaleX(simpleImageView.getScale());
		imageView.setScaleY(simpleImageView.getScale());
	}

	@FXML
	private void openButtonAction(ActionEvent event) {
		LOG.debug("'Open' button clicked");
		configureFileChooser(fileChooser);
		List<File> list = fileChooser.showOpenMultipleDialog(openButton.getScene().getWindow());
		if (list != null) {
			for (File file : list) {
				simpleImageView.addFile(file);
			}
			simpleImageView.reset();
			model.setImage(simpleImageView.getNext());
		}
	}

	@FXML
	private void previousButtonAction(ActionEvent event) {
		LOG.debug("'Previous' button clicked");
		if (simpleImageView.getSizeOfImages() != 0) {
			model.setImage(simpleImageView.getPrevious());
		}
	}

	@FXML
	private void nextButtonAction(ActionEvent event) {
		LOG.debug("'Next' button clicked");
		if (simpleImageView.getSizeOfImages() != 0) {
			model.setImage(simpleImageView.getNext());
		}
	}

	private static void configureFileChooser(final FileChooser fileChooser) {
		fileChooser.setTitle("View Pictures");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Images", "*.*"),
				new FileChooser.ExtensionFilter("JPG", "*.jpg"), new FileChooser.ExtensionFilter("PNG", "*.png"));
	}
//Timer
	@FXML
	private void slideShowButtonAction(ActionEvent event) {
		LOG.debug("'Slide' button clicked");
		if (simpleImageView.getSizeOfImages() != 0) {
			Task<Void> backgroundTask = new Task<Void>() {

				/**
				 * This method will be executed in a background thread.
				 */
				@Override
				protected Void call() throws Exception {
					LOG.debug("call() called");
					long delay = 1000;
					new Timer().schedule(new TimerTask() {

					    @Override
					    public void run() {
					    	model.setImage(simpleImageView.getNext());
					    }
					}, 0, delay);
					return null;
				}

				@Override
				protected void succeeded() {

				}
			};

			Thread thread = new Thread(backgroundTask);
			thread.start();
		}

	}

}
