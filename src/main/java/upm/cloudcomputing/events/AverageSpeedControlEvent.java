package upm.cloudcomputing.events;

import org.apache.flink.api.java.tuple.Tuple6;

public class AverageSpeedControlEvent extends Tuple6<Integer, Integer, Integer, Integer, Integer, Integer> {
    private Integer time1;
    private Integer time2;
    private Integer VID;
    private Integer highway;
    private Integer direction;
    private double avgSpeed;

    public AverageSpeedControlEvent(Integer time1, Integer time2,Integer VID, Integer highway, Integer direction, Double avgSpeed) {
        this.time1 = time1;
        this.time2 = time2;
        this.VID = VID;
        this.highway = highway;
        this.direction = direction;
        this.avgSpeed = avgSpeed;
    }

    public Integer getTime1() {
        return time1;
    }

    public void setTime1(Integer time1) {
        this.time1 = time1;
    }

    public Integer getTime2() {
        return time2;
    }

    public void setTime2(Integer time2) {
        this.time2 = time2;
    }

    public Integer getVID() {
        return VID;
    }

    public void setVID(Integer VID) {
        this.VID = VID;
    }

    public Integer getHighway() {
        return highway;
    }

    public void setHighway(Integer highway) {
        this.highway = highway;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public Double getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(Double avgSpeed) {
        this.avgSpeed = avgSpeed;
    }
}
