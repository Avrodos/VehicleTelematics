package upm.cloudcomputing.events;

import org.apache.flink.api.java.tuple.Tuple6;

public class SpeedRadarEvent extends Tuple6<Integer, Integer, Integer, Integer, Integer, Integer> {
    private Integer time;
    private Integer VID;
    private Integer highway;
    private Integer segment;
    private Integer direction;
    private Integer speed;

    public SpeedRadarEvent(Integer time, Integer VID, Integer highway, Integer segment, Integer direction, Integer speed) {
        this.time = time;
        this.VID = VID;
        this.highway = highway;
        this.segment = segment;
        this.direction = direction;
        this.speed = speed;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
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

    public Integer getSegment() {
        return segment;
    }

    public void setSegment(Integer segment) {
        this.segment = segment;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }
}
