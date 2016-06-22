/**
 * 
 */
package com.starterkit.viewer.controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.starterkit.viewer.DraggableImageView.DraggableImageView;
import com.starterkit.viewer.imageView.SimpleImageView;
import com.starterkit.viewer.model.ImageToView;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
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
	private ToggleButton slideButton;

	@FXML
	private Button previousButton;

	@FXML
	private TableColumn<String, String> nameColumn;

	@FXML
	private TableView<String> resultTable;

	@FXML
	private DraggableImageView imageView = new DraggableImageView();

	@FXML
	private ScrollPane scrollPane = new ScrollPane();

	final private FileChooser fileChooser = new FileChooser();

	private SimpleImageView simpleImageView = new SimpleImageView();

	private final ImageToView model = new ImageToView();

	private Timer timer;

	public ViewerController() {
		LOG.debug("Constructor");
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

		initializeResultTable();

		imageView.imageProperty().bind(model.imageProperty());
		resultTable.itemsProperty().bind(model.resultProperty());

		slideButton.disableProperty().bind(model.imageProperty().isNull());
		nextButton.disableProperty().bind(model.imageProperty().isNull());
		previousButton.disableProperty().bind(model.imageProperty().isNull());
	}

	private void initializeResultTable() {

		nameColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()));

		resultTable.setPlaceholder(new Label(resources.getString("table.emptyText")));

		resultTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				LOG.debug(newValue + " selected");

				if (newValue != null) {
					simpleImageView.setCurrentIndex(resultTable.getSelectionModel().getSelectedIndex() - 1);
					model.setImage(simpleImageView.getNext());
				}
			}
		});

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
			model.setResult(new ArrayList<String>(simpleImageView.getImages()));
			resultTable.getSortOrder().clear();
		}
	}

	@FXML
	private void previousButtonAction(ActionEvent event) {
		LOG.debug("'Previous' button clicked");
		model.setImage(simpleImageView.getPrevious());
	}

	@FXML
	private void nextButtonAction(ActionEvent event) {
		LOG.debug("'Next' button clicked");
		model.setImage(simpleImageView.getNext());
	}

	private void configureFileChooser(final FileChooser fileChooser) {
		fileChooser.setTitle(resources.getString("fileChooser.title"));
		fileChooser.setInitialDirectory(new File(System.getProperty(resources.getString("initialDirectory"))));
		fileChooser.getExtensionFilters()
				.addAll(new FileChooser.ExtensionFilter(resources.getString("extensionFilterJPG"),
						resources.getString("extensionFilter_jpg")),
				new FileChooser.ExtensionFilter(resources.getString("extensionFilterPNG"),
						resources.getString("extensionFilter_png")));
	}

	// Timer
	@FXML
	private void slideShowButtonAction(ActionEvent event) {
		LOG.debug("'Slide' button clicked");

		if (slideButton.isSelected()) {
			slideButton.setText(resources.getString("button.slideStop"));
			LOG.debug("'Slide' button is Selected");
			Task<Void> backgroundTask = new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					LOG.debug("call() called");
					long delay = 1000;
					timer = new Timer();
					timer.schedule(new TimerTask() {

						@Override
						public void run() {
							model.setImage(simpleImageView.getNext());
						}
					}, 0, delay);
					return null;
				}

				@Override
				protected void succeeded() {
					LOG.debug("succeeded() called");
				}
			};
			new Thread(backgroundTask).start();
		} else {
			slideButton.setText(resources.getString("button.slidePlay"));
			LOG.debug("'Slide' button is not Selected, slide show is ended");
			timer.cancel();
		}

	}

}
