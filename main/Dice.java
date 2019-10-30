package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Random;

public class Dice
{
    private ImageView dieFace;
    private Image[] images;
    private Image[] imageHeld;
    private Random randomNumbers;
    private int computerRan;
    private int imageSwitch;
    private boolean heldOrNot;

    Dice(Image[] images, Image[] imageHeld)
    {
        this.images = images;
        this.imageHeld = imageHeld;
        dieFace = new ImageView(this.images[0]);
        heldOrNot = false;
        imageSwitch = 0;
        dieFace.setFitWidth(100);
        dieFace.setFitHeight(100);
        dieFace.setPreserveRatio(true);
        dieFace.setPickOnBounds(true);
        dieFace.setId("myImage");
    }

    ImageView getSide()
    {
        randomNumbers = new Random();
        computerRan = randomNumbers.nextInt(5)+1;
        dieFace.setImage(this.images[computerRan]);

        dieFace.setOnMouseClicked((event ->
        {
            switch (imageSwitch)
            {
                case 0:
                    if(Main.getRollCount() != -1 && Main.getRollCount() != 0)
                    {
                        dieFace.setImage(this.imageHeld[computerRan]);
                        heldOrNot = true;
                        imageSwitch = 1;
                    }
                    break;
                case 1:
                    if(Main.getRollCount() != -1 && Main.getRollCount() != 0)
                    {
                        dieFace.setImage(this.images[computerRan]);
                        heldOrNot = false;
                        imageSwitch = 0;
                    }
                    break;
            }
        }));
        return dieFace;
    }

    ImageView getFirst() { return dieFace; };

    int dieNumber() { return computerRan; }

    void resetHeld() { heldOrNot = false; }

    boolean getHeldOrNot() { return !heldOrNot; }

}
