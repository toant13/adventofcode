package day13;

public class Layer {
    int scannerLocation;
    int range;
    private boolean goForward;

    public Layer(int range) {
        this.scannerLocation = 0;
        this.range = range;
        this.goForward = true;
    }

    public Layer(int scannerLocation, int range, boolean goForward) {
        this.scannerLocation = scannerLocation;
        this.range = range;
        this.goForward = goForward;
    }

    public void move() {
        if (goForward) {
            scannerLocation++;
            if (scannerLocation == range - 1) {
                goForward = false;
            }
        } else {
            scannerLocation--;
            if (scannerLocation == 0) {
                goForward = true;
            }
        }
    }

    public Layer clone(){
        return new Layer(this.scannerLocation, this.range, this.goForward);
    }
}
