package ui;

import java.awt.*;
import java.net.URL;
import java.awt.image.ImageObserver;

// Represents a splash screen of a given image that can be displayed before the application opens
final class SplashScreen extends Frame {
    private static final ImageObserver NO_OBSERVER = null;
    private static final int IMAGE_ID = 0;
    private final String imageId;
    private MediaTracker mediaTracker;
    private Image image;

    // EFFECTS: constructs a splash screen with the given image.
    SplashScreen(String imageId) {
        if (imageId == null || imageId.trim().length() == 0) {
            throw new IllegalArgumentException("Image ID has no content.");
        }
        this.imageId = imageId;
    }

    // EFFECTS: displays splash screen to user
    public void splash() {
        initImageAndTracker();
        setSize(image.getWidth(NO_OBSERVER), image.getHeight(NO_OBSERVER));
        center();
        mediaTracker.addImage(image, IMAGE_ID);
        try {
            mediaTracker.waitForID(IMAGE_ID);
        } catch (InterruptedException ex) {
            System.out.println("Cannot track image load.");
        }
        new SplashWindow(this, image);
    }

    // EFFECTS: loads splash screen image from url
    private void initImageAndTracker() {
        mediaTracker = new MediaTracker(this);
        URL imageURL = SplashScreen.class.getResource(imageId);
        image = Toolkit.getDefaultToolkit().getImage(imageURL);
    }

    // MODIFIES: this
    // EFFECTS: centers frame on screen
    private void center() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle frame = getBounds();
        setLocation((screen.width - frame.width) / 2, (screen.height - frame.height) / 2);
    }

    // Displays splash screen to user
    private final class SplashWindow extends Window {
        SplashWindow(Frame parent, Image image) {
            super(parent);
            image1 = image;
            setSize(image1.getWidth(NO_OBSERVER), image1.getHeight(NO_OBSERVER));
            Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
            Rectangle window = getBounds();
            setLocation((screen.width - window.width) / 2,(screen.height - window.height) / 2);
            setVisible(true);
        }

        // EFFECTS: draws splash screen image
        @Override public void paint(Graphics graphics) {
            if (image1 != null) {
                graphics.drawImage(image1,0,0,this);
            }
        }

        private Image image1;
    }
}
