package edu.wpi.teamname;

import com.google.inject.Guice;
import com.google.inject.Injector;
import edu.wpi.teamname.services.database.DatabaseService;
import edu.wpi.teamname.services.database.DatabaseServiceProvider;
import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App extends Application {

  public App() {}

  @Override
  public void init() {
    log.info("Starting Up");
    Injector injector = Guice.createInjector(new DatabaseServiceProvider());
    DatabaseService db = injector.getInstance(DatabaseService.class);
    db.populateDB();
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    log.info("started");
  }

  @Override
  public void stop() {
    log.info("Shutting Down");
  }
}
