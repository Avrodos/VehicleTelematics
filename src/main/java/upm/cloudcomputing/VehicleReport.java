package upm.cloudcomputing;

import org.apache.flink.api.java.tuple.Tuple8;

public class VehicleReport extends Tuple8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> {

    private Integer time;
    private Integer VID;
    private Integer speed;
    private Integer highway;
    private Integer lane;
    private Integer direction;
    private Integer segment;
    private Integer position;


    public VehicleReport(Integer time, Integer VID, Integer speed, Integer highway, Integer lane, Integer direction, Integer segment, Integer position) {
        this.time = time;
        this.VID = VID;
        this.speed = speed;
        this.highway = highway;
        this.lane = lane;
        this.direction = direction;
        this.segment = segment;
        this.position = position;
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

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Integer getHighway() {
        return highway;
    }

    public void setHighway(Integer highway) {
        this.highway = highway;
    }

    public Integer getLane() {
        return lane;
    }

    public void setLane(Integer lane) {
        this.lane = lane;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public Integer getSegment() {
        return segment;
    }

    public void setSegment(Integer segment) {
        this.segment = segment;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
