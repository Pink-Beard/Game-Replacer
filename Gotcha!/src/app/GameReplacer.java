package app;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class GameReplacer extends Application {

	//only copy of image and font needed. No reason to create new instances
	static Image background = new Image("/images/pokemon.jpg");
	static Font font = new Font("Times New Roman", 18);
	
	 
	 //declared globally so that methods can access
	ImageView showBackground = new ImageView(background);//not static because image will only show on top version
	Pane mainPane = new Pane();
	Scene mainScene;
	Stage mainStag;
	 
	//actually creates new windows and runs program
	public void start(Stage mainStage) throws Exception{
		createPane();
		createStage(mainStage);
		mainStag = mainStage;
		mainStag.show();
	}
	
	//Application.launch(args) is necessary when running from eclipse.
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Application.launch(args);
	}

	void createScene(){
		//creates new Scene based on mainPane
		mainScene = new Scene(mainPane);
		
		//If keyEvent is not focussed on a node on the scene, you can attach
		//a keyEvent to a scene to be consumed by the screen itself.
		Closer closer = new Closer();
		mainScene.setOnKeyPressed(closer);
	}
	
	void createButton(Button main){
		//creates event handler for the new button
		Actions handler = new Actions();
		main.setOnAction(handler);
		main.setFont(font);
		//sets button preference dimensions and label 
		main.setPrefWidth(100);
		main.setPrefHeight(60);
		main.setText("Close");
	}
	
	void createStage(Stage stage){
		//sets stage to background size and stage size to proper dimensions
		stage.setHeight(background.getHeight()+25);//displays full image (1)
		stage.setWidth(background.getWidth());
		showBackground.setFitWidth(stage.getWidth());
		showBackground.setFitHeight(stage.getHeight()-23);//displays full image (1)
	
		stage.setAlwaysOnTop(true);
		stage.setResizable(false);
		
		//launches first window in center of screen. 
		stage.centerOnScreen();
		stage.setScene(mainScene);
		stage.setTitle("Don't Play Games in Class");
		//supposed to prevent window from being moved
		
		//removes minimize, maximize and close buttons
		//unused because replacing the 
		//stage.initStyle(StageStyle.UNDECORATED);
		
		//removes minimize and maximize button
		stage.initStyle(StageStyle.UTILITY);
		
		//replaces the closed window in the center of the screen.
		Respawn launch = new Respawn();
		stage.setOnCloseRequest(launch);
	}
	
	void createPane(){
		Button Close = new Button();
		Text title = new Text("Gotcha!");
		title.setFont(font);
		title.setFill(Color.WHITE);
		
		createButton(Close);
		mainPane.getChildren().add(showBackground);
		mainPane.getChildren().add(Close);
		mainPane.getChildren().add(title);
		title.relocate(250, 50);
		Close.relocate(background.getWidth()/2-50, 400);
		createScene();
	}
}

	

class Actions implements EventHandler<ActionEvent> {
	GameReplacer window = new GameReplacer();
	public void handle(ActionEvent a){
		Rectangle2D primaryScreen = Screen.getPrimary().getVisualBounds();
		Stage stage = new Stage();
		try {
			window.start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		stage.setScene(window.mainScene);
		stage.setX(primaryScreen.getMaxX() - (Math.random() *1000) -500);
		stage.setY(primaryScreen.getMaxY() - (Math.random() *1000) -500);
		stage.setAlwaysOnTop(true);
		stage.show();
	}
}


class Closer implements EventHandler<KeyEvent>{

	@Override
	public void handle(KeyEvent keys) {
		// TODO Auto-generated method stub
		if(keys.getCode() == KeyCode.END){
			Platform.exit();
		}
	}
	
}

class Respawn implements EventHandler<WindowEvent>{
	GameReplacer window = new GameReplacer();
	@Override
	public void handle(WindowEvent event) {
		// TODO Auto-generated method stub
		Stage stage = new Stage();
		try {
			window.start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		stage.setScene(window.mainScene);
		stage.centerOnScreen();
		stage.setAlwaysOnTop(true);
		stage.show();
	}
	
}
