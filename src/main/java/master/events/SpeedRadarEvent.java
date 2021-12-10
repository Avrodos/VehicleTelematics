package master.events;

import org.apache.flink.api.java.tuple.Tuple6;

public class SpeedRadarEvent extends Tuple6<Integer, Integer, Integer, Integer, Integer, Integer> {

    public SpeedRadarEvent() {

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

    public Integer getHighway() {
        return f2;
    }

    public void setHighway(Integer highway) {
        this.f2 = highway;
    }

    public Integer getSegment() {
        return f3;
    }

    public void setSegment(Integer segment) {
        this.f3 = segment;
    }

    public Integer getDirection() {
        return f4;
    }

    public void setDirection(Integer direction) {
        this.f4 = direction;
    }

    public Integer getSpeed() {
        return f5;
    }

    public void setSpeed(Integer speed) {
        this.f5 = speed;
    }
}
