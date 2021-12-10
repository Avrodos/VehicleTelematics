package master.operators;

import master.VehicleReport;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import master.events.SpeedRadarEvent;

public class SpeedRadar {

    private static final int MAX_SPEED = 90;

    public static SingleOutputStreamOperator<SpeedRadarEvent> detectSpeedViolation(SingleOutputStreamOperator<VehicleReport> filterOut) {
        return filterOut
                .filter((VehicleReport vr) -> vr.getSpeed() > MAX_SPEED)
                .map((MapFunction<VehicleReport, SpeedRadarEvent>) in -> {
                    SpeedRadarEvent speedRadarEvent = new SpeedRadarEvent();
                    speedRadarEvent.setTime(in.getTime());
                    speedRadarEvent.setVID(in.getVID());
                    speedRadarEvent.setSpeed(in.getSpeed());
                    speedRadarEvent.setHighway(in.getHighway());
                    speedRadarEvent.setDirection(in.getDirection());
                    speedRadarEvent.setSegment(in.getSegment());
                    return speedRadarEvent;
                });
    }
}
