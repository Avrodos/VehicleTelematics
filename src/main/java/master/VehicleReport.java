package master;

import org.apache.flink.api.java.tuple.Tuple8;

public class VehicleReport extends Tuple8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> {

    public VehicleReport() {

    }

    public Integer getTime() {
        return f0;
    }

    public void setTime(Integer time) {
        this.f0 = time;
    }

    public Integer getVID() {
        return f1;
    }

    public void setVID(Integer VID) {
        this.f1 = VID;
    }

    public Integer getSpeed() {
        return f2;
    }

    public void setSpeed(Integer speed) {
        this.f2 = speed;
    }

    public Integer getHighway() {
        return f3;
    }

    public void setHighway(Integer highway) {
        this.f3 = highway;
    }

    public Integer getLane() {
        return f4;
    }

    public void setLane(Integer lane) {
        this.f4 = lane;
    }

    public Integer getDirection() {
        return f5;
    }

    public void setDirection(Integer direction) {
        this.f5 = direction;
    }

    public Integer getSegment() {
        return f6;
    }

    public void setSegment(Integer segment) {
        this.f6 = segment;
    }

    public Integer getPosition() {
        return f7;
    }

    public void setPosition(Integer position) {
        this.f7 = position;
    }
}
