package upm.cloudcomputing.events;

import org.apache.flink.api.java.tuple.Tuple7;

public class AccidentReporterEvent extends Tuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer> {
    private Integer time1;
    private Integer time2;
    private Integer VID;
    private Integer highway;
    private Integer segment;
    private Integer direction;
    private Integer position;

    public AccidentReporterEvent(Integer time1, Integer time2,Integer VID, Integer highway, Integer segment, Integer direction, Integer position) {
        this.time1 = time1;
        this.time2 = time2;
        this.VID = VID;
        this.highway = highway;
        this.segment = segment;
        this.direction = direction;
        this.position = position;
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

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
