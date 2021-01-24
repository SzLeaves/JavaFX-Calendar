import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CalendarUI extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(basicLayout());
        stage.setTitle("万年历");
        stage.setResizable(false);
        stage.show();
    }

    public Scene basicLayout() throws IOException {
        Scene calendarScene = new Scene(loadFxml(new CalendarFunction()));
        return calendarScene;
    }

    // 重新加载布局文件
    private Parent loadFxml(Object controller) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setController(controller);
        fxmlLoader.setLocation(getClass().getResource("Layout.fxml"));
        return fxmlLoader.load();
    }

}
