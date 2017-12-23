package day13;

import java.util.Arrays;

public class Firewall {
    Layer[] firewall;
    int rider;

    public Firewall(int size) {
        this.firewall = new Layer[size];
        this.rider = -1;
    }

    private Firewall(Layer[] firewall) {
        this.firewall = firewall;
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

    public Firewall clone(){
        Layer[] newFirewall = new Layer[firewall.length];
        for (int i = 0; i < firewall.length; i++) {
            if(firewall[i] != null){
                newFirewall[i] = firewall[i].clone();
            }
        }

        return new Firewall(newFirewall);
    }
}
