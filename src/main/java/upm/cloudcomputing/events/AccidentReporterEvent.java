package upm.cloudcomputing.events;

import org.apache.flink.api.java.tuple.Tuple7;

public class AccidentReporterEvent extends Tuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer> {

    public AccidentReporterEvent() {

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

    public Integer getSegment() {
        return f4;
    }

    public void setSegment(Integer segment) {
        this.f4 = segment;
    }

    public Integer getDirection() {
        return f5;
    }

    public void setDirection(Integer direction) {
        this.f5 = direction;
    }

    public Integer getPosition() {
        return f6;
    }

    public void setPosition(Integer position) {
        this.f6 = position;
    }
}
