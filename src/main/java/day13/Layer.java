package day13;

public class Layer {
    int scannerLocation;
    int range;
    private boolean goForward;

    public Layer(int range) {
        this.scannerLocation = -1;
        this.range = range;
        this.goForward = true;
    }

    public void move() {
        if (goForward) {
            scannerLocation++;
            if(scannerLocation == range-1){
                goForward = false;
            }
        } else {
            scannerLocation--;
            if(scannerLocation == 0){
                goForward = true;
            }
        }
    }
}
