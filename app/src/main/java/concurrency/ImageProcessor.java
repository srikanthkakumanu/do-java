package concurrency;

public class ImageProcessor {
    public static void main(String[] args) {
        
    }

    private static int getBlue(int rgb) {
        return rgb & 0x000000FF;
    }

    private static int getGreen(int rgb) {
        return (rgb & 0x0000FF00) >> 8;
    }

    private static int getRed(int rgb) {
        return (rgb & 0x00FF0000) >> 16;
    }
}
