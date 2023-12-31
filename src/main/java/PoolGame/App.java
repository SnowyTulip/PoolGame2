/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package PoolGame;

import java.io.File;
import java.io.IOException;
import java.util.*;

import PoolGame.Config.GameConfig;
import PoolGame.Config.GameConfigReader;
import PoolGame.GameObject.Game;
import PoolGame.Items.Pocket;
import PoolGame.Items.PoolTable;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import org.json.simple.parser.ParseException;

import PoolGame.Config.GameConfigReader.ConfigKeyMissingException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import static PoolGame.App.GameDifficultyLevel.*;

/** The JavaFX application */
public class App extends Application {
    private Game game;
    enum GameDifficultyLevel {
        easy,hard,normal
    }
    private Group gameGroup;
    private Scene scene;
    private Stage stage;

    private Map<GameDifficultyLevel, GameConfig> gameConfigs;
    private Map<GameDifficultyLevel, String> configFileDict;
    private String configsPath;
    private final double FRAMETIME = 1.0 / 60.0;
    public App(){
        super();
        initConfigMarco();
    }


//    private ConfigReader loadConfig(List<String> args) {
////        LoadConfigs();
//        String configPath;
//        boolean isResourcesDir = false;
//		if (args.size() > 0) {
//			configPath = args.get(0);
//		} else {
//            configPath = "src/main/resources/config.json";
//			configPath = "/configs/config.json";
//            configPath = "/config_hard.json";
//            isResourcesDir = true;
//		}
//		// parse the file:
//        ConfigReader config = null;
//        try {
//            config = new ConfigReader(configPath, isResourcesDir);
//        } catch (IOException | ParseException | ConfigKeyMissingException | IllegalArgumentException e) {
//            e.printStackTrace();
//            System.err.printf("ERROR: %s\n", e.getMessage());
//            System.exit(1);
//        }
//        return config;
//    }

    private void setDifficultyLevel(GameDifficultyLevel level){
        if (game != null) {
            GameConfig config = this.gameConfigs.get(level);
            this.game.setConfig(config);
        }
    }

    @Override
    public void start(Stage stage) {
        gameGroup = new Group();
        // 创建HBox容器，用于容纳canvas和侧边栏
        scene = new Scene(gameGroup);
        RegisterKeyBoard(scene);
        this.stage = stage;
        stage.setScene(scene);
        stage.setTitle("PoolGame");
        stage.show();
        LoadConfigs();

        GameConfig gameConfig = gameConfigs.get(easy);
        game = new Game(gameConfig);

        stage.setWidth(game.getWindowDimX() + 15);
        stage.setHeight(game.getWindowDimY() +
                Pocket.RADIUS +
                PoolTable.POCKET_OFFSET +
                12 ); // Magic number to get bottom to align
        game.addDrawables(gameGroup);

        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame frame = new KeyFrame(Duration.seconds(FRAMETIME), (actionEvent) -> game.tick());

        timeline.getKeyFrames().add(frame);
        timeline.play();
    }
    private void RegisterKeyBoard(Scene scene) {
        scene.setOnKeyPressed(event -> {
            this.game.GamePause();
            if (event.getCode() == KeyCode.ESCAPE) {
                // 创建对话框
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("游戏状态");
                alert.setHeaderText(null);
                alert.setContentText("选择您操作:");

                // 添加按钮
                ButtonType setDifficultyBtn = new ButtonType("设置游戏难度");
                ButtonType gameGoBackBtn = new ButtonType("向前回滚");
                ButtonType gameGoNextBtn = new ButtonType("向后回滚");
                ButtonType cancelBtn = new ButtonType("取消");
                alert.getButtonTypes().setAll(setDifficultyBtn, gameGoBackBtn, gameGoNextBtn,cancelBtn);

                // 显示对话框并等待用户响应
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == setDifficultyBtn) {

                    showGameDifficultyLevelSelect();
                } else if (result.get() == gameGoBackBtn) {
                    this.game.GameGoBack();
                } else if (result.get() == gameGoNextBtn) {
                    this.game.GameGoNext();
                }
                else if (result.get() == cancelBtn){
                    alert.close();
                }
            }
            this.game.GameStart();
        });
    }
    private void showGameDifficultyLevelSelect() {
        Stage dialogStage = new Stage();
        dialogStage.setTitle("选择游戏难度");
        dialogStage.initModality(Modality.APPLICATION_MODAL);

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER);

        ComboBox<String> difficultyComboBox = new ComboBox<>();
        difficultyComboBox.setItems(FXCollections.observableArrayList(
                this.gameConfigs.keySet().stream()
                        .map(String::valueOf)
                        .toArray(String[]::new)
        ));
        difficultyComboBox.getSelectionModel().selectFirst();
        difficultyComboBox.setPrefWidth(Double.MAX_VALUE);
        vbox.getChildren().add(difficultyComboBox);

        // 创建“确定”按钮
        Button okButton = new Button("确定");
        okButton.setOnAction(event -> {
            String selectedDifficulty = difficultyComboBox.getValue();
            setGameDifficulty(selectedDifficulty);
            dialogStage.close();
        });
        Button cancelButton = new Button("取消");
        cancelButton.setOnAction(event -> {
            dialogStage.close();
        });
        HBox buttonBox = new HBox(okButton, cancelButton);
        buttonBox.setSpacing(100);
        buttonBox.setAlignment(Pos.CENTER);
        vbox.getChildren().add(buttonBox);

        Scene dialogScene = new Scene(vbox, 300, 100);
        dialogStage.setScene(dialogScene);
        dialogStage.showAndWait();
    }
    private void setGameDifficulty(String difficulty){
        GameDifficultyLevel level = GameDifficultyLevel.valueOf(difficulty);
        GameConfig config = this.gameConfigs.get(level);
        this.game.setConfig(config);
        this.gameGroup.getChildren().clear();
        this.game.addDrawables(gameGroup);
    }

    private void initConfigMarco(){
        gameConfigs = new EnumMap<>(GameDifficultyLevel.class);
        configFileDict = new EnumMap<>(GameDifficultyLevel.class);
        configFileDict.put(easy,"config_easy.json");
        configFileDict.put(hard,"config_hard.json");
        configFileDict.put(normal,"config_normal.json");
        configsPath = "src/main/resources/";
    }

    private void LoadConfigs() {
        File directory = new File(configsPath);
        GameConfigReader gameConfigReader;
        for(GameDifficultyLevel level : configFileDict.keySet()){
            File configFile = new File(directory,configFileDict.get(level));
            if(configFile.exists()){
                try {
                    gameConfigReader = new GameConfigReader(configFile.getPath(),false);
                    this.gameConfigs.put(level, gameConfigReader.getConfig());
                } catch (IOException | ConfigKeyMissingException | ParseException e) {
                    throw new RuntimeException(e);
                }
            }
            else{
                throw new RuntimeException("config file:" + level.toString() + " does not exist");
            }
        }

    }

    /**
     * The entry point of the program
     * @param args CLI arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
