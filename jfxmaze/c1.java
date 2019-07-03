package jfxmaze;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BoxBlur;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import static java.lang.Math.random;
import javafx.animation.*;
import javafx.scene.control.*;
import javafx.event.*;
public class c1 extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Group root=new Group();
        Scene s=new Scene(root,500,500);
        stage.setScene(s);
        Line l = new Line(10, 10, 100, 100);
        Button b=new Button("start");
        b.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e)
            {
                final Timeline timeline = new Timeline();
                timeline.setCycleCount(Timeline.INDEFINITE);
                timeline.setAutoReverse(true);
                final KeyValue kvx = new KeyValue(l.startXProperty(), 100);
                final KeyValue kvy = new KeyValue(l.startYProperty(), 100);
                final KeyFrame kf = new KeyFrame(Duration.millis(1000), kvx,kvy);
                timeline.getKeyFrames().add(kf);
                timeline.play();
            }
        });
        root.getChildren().addAll(l,b);
        stage.show();
    }
}
