package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/*
Alan Huang
CS 2450 Homework 2
Dice Game
 */

public class Main extends Application {

    private Dice dice1, dice2, dice3, dice4, dice5;
    private static int rollCount;
    private int rollDown;
    private int scoreFirst = 0;
    private int scoreSecond = 0;
    private Label scoreResult;
    private Label roundResult;
    private Label rollsResult;
    private Label showHand;
    private Button rollButton;
    private String[]myHand;
    private int x = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {

        rollDown = 3;
        rollCount = 0;
        diceSetUp ();

        //Create all label and button
        Label ScoreBoard = new Label("Your Total Score is: " );
        scoreResult = new Label("0" );
        rollButton = new Button("Roll Dice" );
        Label roundScore = new Label("Round Score is: " );
        roundResult = new Label(""+scoreFirst);
        Label rollsLeft = new Label("Rolls Remaining: " );
        rollsResult = new Label("" +rollDown  );
        showHand = new Label("");
        showHand.setId("showHand");
        myHand = new String[] {"No Hand", "Two of kind", " Two Pair", "Three of a kind", "Full House", " Four of a Kind",
                "Straight", "Five of a Kind"};

        //Setup layout
        HBox rS = new HBox(10, roundScore, roundResult );
        HBox rR = new HBox(10, rollsLeft, rollsResult );
        HBox topBox = new HBox(120, rS, rR);
        HBox secBox = new HBox(10, ScoreBoard,scoreResult);
        HBox imageVBox = new HBox(10, dice1.getFirst(), dice2.getFirst(),
                dice3.getFirst(),dice4.getFirst(),dice5.getFirst());
        HBox button1 = new HBox(10, rollButton);
        HBox showBox = new HBox(10, showHand);



        //Alignment setting
        secBox.setAlignment(Pos.CENTER);
        imageVBox.setAlignment(Pos.CENTER);
        button1.setAlignment(Pos.CENTER);
        topBox.setAlignment(Pos.TOP_LEFT);
        showBox.setAlignment(Pos.CENTER);


        //Setup padding
        topBox.setPadding(new Insets(0, 10, 15, 10));
        secBox.setPadding(new Insets(0, 10 ,10 ,10));
        imageVBox.setPadding(new Insets(10, 10, 10, 10));
        button1.setPadding(new Insets(10, 10, 10, 10));
        showBox.setPadding(new Insets(0, 10, 10,  10));

        // Roll button function
        rollButton.setOnAction( new ButtonHandler() );

        // Setup GridPane
        GridPane gridPane = new GridPane();
        gridPane.add(topBox, 0, 0);
        gridPane.add(secBox, 0, 1);
        gridPane.add(imageVBox, 0, 2);
        gridPane.add(button1, 0, 3);
        gridPane.add(showBox,0,4);
        gridPane.setHgap(0);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(50));

        //Setup CSS style and Scene
        Scene myScene = new Scene (gridPane, 650, 380);
        myScene.getStylesheets().add("mystyles.css");

        primaryStage.setScene(myScene);
        primaryStage.setTitle("Dice Game");
        primaryStage.show();
    }

    class ButtonHandler implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event) {

            rollButton.setText("Roll Dice");
            rollCount ++;
            rollDown --;

            if (dice1.getHeldOrNot())
            {
                dice1.getSide();
            }
            if (dice2.getHeldOrNot())
            {
                dice2.getSide();
            }
            if (dice3.getHeldOrNot())
            {
                dice3.getSide();
            }
            if (dice4.getHeldOrNot())
            {
                dice4.getSide();
            }
            if (dice5.getHeldOrNot())
            {
                dice5.getSide();
            }

            rollsResult.setText("" + rollDown );

            if (rollCount == 3)
            {
                scoreCounter();
                roundResult.setText(String.valueOf(scoreFirst));
                showHand.setText(myHand[x]);

                rollCount = -1;
                rollDown = 4;

                dice1.resetHeld();
                dice2.resetHeld();
                dice3.resetHeld();
                dice4.resetHeld();
                dice5.resetHeld();

                rollButton.setText("Play Again");
                scoreResult.setText(String.valueOf(scoreSecond));
            }

            if(rollCount == 0)
            {
                roundResult.setText("0");
                showHand.setText("");
            }

        }
    }

    private void scoreCounter()
    {
        int [] diceCounter = {dice1.dieNumber(), dice2.dieNumber(),
                dice3.dieNumber(), dice4.dieNumber(), dice5.dieNumber()};
        int pairCounter = 0;
        int threeCounter = 0;
        int fourCounter = 0;
        int fiveCounter = 0;


        int [] dicedice = {0, 0, 0, 0, 0, 0};

        for(int i = 0; i < diceCounter.length; i++)
        {
            switch (diceCounter[i]) {
                case 0:
                    dicedice[0]++;
                    break;
                case 1:
                    dicedice[1]++;
                    break;
                case 2:
                    dicedice[2]++;
                    break;
                case 3:
                    dicedice[3]++;
                    break;
                case 4:
                    dicedice[4]++;
                    break;
                case 5:
                    dicedice[5]++;
                    break;
            }
        }

        for(int i = 0; i < dicedice.length; i++)
        {
            if(dicedice[i] == 5)
            {
                fiveCounter ++;
            }

            if(dicedice[i] == 4)
            {
                fourCounter ++;
            }

            if(dicedice [i] == 3)
            {
                threeCounter ++;
            }

            if (dicedice[i] == 2)
            {
                pairCounter ++;
            }
        }

        //Pair Score
        if( pairCounter == 2)
        {
            scoreFirst = 4;
            x = 2;
        }

        if(pairCounter == 1)
        {
            scoreFirst = 1;
            x = 1;
        }

        if (pairCounter == 0)
        {
            scoreFirst = 0;
            x = 0;
        }

        //Three of kind or Full House counter
        if( threeCounter == 1 && pairCounter ==1)
        {
            scoreFirst = 6;
            x = 4;
        }

        if( threeCounter == 1 && pairCounter == 0)
        {
            scoreFirst = 5;
            x = 3;
        }

        //Four of a kind counter
        if(fourCounter == 1)
        {
            scoreFirst = 7;
            x = 5;
        }

        //five of a kind counter
        if(fiveCounter == 1)
        {
            scoreFirst = 10;
            x = 7;
        }

        //Straight counter if player got 2 3 4 5 than he got straight
        if(dicedice[1] == 1 && dicedice[2] == 1 && dicedice[3] == 1 && dicedice [4] == 1 )
        {
            scoreFirst = 8;
            x = 6;
        }

        scoreSecond += scoreFirst;
    }

    private void diceSetUp ()
    {
        rollCount = 0;
        setUpImages images = new setUpImages();
        setUpHeldImages imageHeld = new setUpHeldImages();

        dice1 = new Dice(images.getImagesTotal(), imageHeld.getImagesTotal());
        dice2 = new Dice(images.getImagesTotal(), imageHeld.getImagesTotal());
        dice3 = new Dice(images.getImagesTotal(), imageHeld.getImagesTotal());
        dice4 = new Dice(images.getImagesTotal(), imageHeld.getImagesTotal());
        dice5 = new Dice(images.getImagesTotal(), imageHeld.getImagesTotal());
    }

    private static class setUpImages
    {
        Image die1 = new Image("file:./res/dice1.png");
        Image die2 = new Image("file:./res/dice2.png");
        Image die3 = new Image("file:./res/dice3.png");
        Image die4 = new Image("file:./res/dice4.png");
        Image die5 = new Image("file:./res/dice5.png");
        Image die6 = new Image("file:./res/dice6.png");

        Image [] imagesTotal = new Image[6];

        setUpImages()
        {
            imagesTotal[0] = die1;
            imagesTotal[1] = die2;
            imagesTotal[2] = die3;
            imagesTotal[3] = die4;
            imagesTotal[4] = die5;
            imagesTotal[5] = die6;
        }
        Image[] getImagesTotal()
        {
            return imagesTotal;
        }
    }

    public static class setUpHeldImages
    {
        Image die1 = new Image("file:./res/dice1Held.png");
        Image die2 = new Image("file:./res/dice2Held.png");
        Image die3 = new Image("file:./res/dice3Held.png");
        Image die4 = new Image("file:./res/dice4Held.png");
        Image die5 = new Image("file:./res/dice5Held.png");
        Image die6 = new Image("file:./res/dice6Held.png");

        Image [] imagesTotal = new Image[6];

        setUpHeldImages()
        {
            imagesTotal[0] = die1;
            imagesTotal[1] = die2;
            imagesTotal[2] = die3;
            imagesTotal[3] = die4;
            imagesTotal[4] = die5;
            imagesTotal[5] = die6;
        }
        Image[] getImagesTotal()
        {
            return imagesTotal;
        }
    }

    static int getRollCount() {
        return rollCount;
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}