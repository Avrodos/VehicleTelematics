package upm.cloudcomputing.operators;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import upm.cloudcomputing.VehicleReport;
import upm.cloudcomputing.events.SpeedRadarEvent;

public class SpeedRadar {

    private static final int MAX_SPEED = 90;

    public static SingleOutputStreamOperator detectSpeedViolation(SingleOutputStreamOperator<VehicleReport> filterOut) {
        return filterOut
                .filter((VehicleReport vr) -> vr.getSpeed() > MAX_SPEED)
                .map((MapFunction<VehicleReport, SpeedRadarEvent>) in -> {
                    Integer time = in.getTime();
                    Integer VID = in.getVID();
                    Integer speed = in.getSpeed();
                    Integer highway = in.getHighway();
                    Integer direction = in.getDirection();
                    Integer segment = in.getSegment();
                    return new SpeedRadarEvent(time, VID, highway, segment, direction, speed);
                });
    }
}
