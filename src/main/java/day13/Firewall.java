package day13;

public class Firewall {
    Layer[] firewall;
    int rider;

    public Firewall(int size) {
        this.firewall = new Layer[size];
        this.rider = -1;
    }

    public void moveScanner() {
        for (Layer layer : firewall) {
            if (layer != null) {
                layer.move();
            }
        }
    }

    public void moveRider() {
        rider++;
    }

    public void addLayer(Layer layer, int index) {
        firewall[index] = layer;
    }

    public boolean isRiderCaught() {
        return firewall[rider] == null ? false : firewall[rider].scannerLocation == 0;
    }


    public int getCurrentSeverity() {
        return firewall[rider] == null ? 0 : rider * firewall[rider].range;
    }

    public int getNumberLayers() {
        return firewall.length;
    }
}
