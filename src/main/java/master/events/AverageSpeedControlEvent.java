package master.events;

import org.apache.flink.api.java.tuple.Tuple6;

public class AverageSpeedControlEvent extends Tuple6<Integer, Integer, Integer, Integer, Integer, Double> {

    public AverageSpeedControlEvent(){

    }

    public Integer getTime1() {
        return f0;
    }

    public void setTime1(Integer time1) {
        this.f0 = time1;
    }

    public Integer getTime2() {
        return f1;
    }

    public void setTime2(Integer time2) {
        this.f1 = time2;
    }

    public Integer getVID() {
        return f2;
    }

    public void setVID(Integer VID) {
        this.f2 = VID;
    }

    public Integer getHighway() {
        return f3;
    }

    public void setHighway(Integer highway) {
        this.f3 = highway;
    }

    public Integer getDirection() {
        return f4;
    }

    public void setDirection(Integer direction) {
        this.f4 = direction;
    }

    public Double getAvgSpeed() {
        return f5;
    }

    public void setAvgSpeed(Double avgSpeed) {
        this.f5 = avgSpeed;
    }
}
