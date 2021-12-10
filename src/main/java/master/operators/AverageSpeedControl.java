package master.operators;

import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.functions.timestamps.AscendingTimestampExtractor;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.EventTimeSessionWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;
import master.VehicleReport;
import master.events.AverageSpeedControlEvent;

import java.util.HashSet;
import java.util.Set;

public class AverageSpeedControl {
    public static SingleOutputStreamOperator<AverageSpeedControlEvent> measureAvg(SingleOutputStreamOperator<VehicleReport> filterOut) {
        return filterOut
                .filter((VehicleReport vr) -> vr.getSegment() >= 52 && vr.getSegment() <= 56)
                .assignTimestampsAndWatermarks(new AscendingTimestampExtractor<VehicleReport>() {
                    @Override
                    public long extractAscendingTimestamp(VehicleReport vehicleReport) {
                        return vehicleReport.getTime() * 1000; // transform to milliseconds
                    }
                })
                //.keyBy((KeySelector<VehicleReport, Tuple3<Integer, Integer, Integer>>) vr -> Tuple3.of(vr.getVID(), vr.getHighway(), vr.getDirection()))
                // flink seems to have trouble with the lambda expression when using Tuples, so lets try an anonymous class
                .keyBy(new KeySelector<VehicleReport, Tuple3<Integer, Integer, Integer>>() {
                    @Override
                    public Tuple3<Integer, Integer, Integer> getKey(VehicleReport vehicleReport) {
                        return Tuple3.of(vehicleReport.getVID(), vehicleReport.getHighway(), vehicleReport.getDirection());
                    }
                })
                .window(EventTimeSessionWindows.withGap(Time.milliseconds(31)))
                .apply(new AverageWindow());

    }

    private static class AverageWindow implements WindowFunction<VehicleReport, AverageSpeedControlEvent,
            Tuple3<Integer, Integer, Integer>, TimeWindow> {

        @Override
        public void apply(Tuple3<Integer, Integer, Integer> key, TimeWindow timeWindow, Iterable<VehicleReport> iterable, Collector<AverageSpeedControlEvent> collector) {
            // Timewindow!

            int time1 = Integer.MAX_VALUE;
            int pos1 = Integer.MAX_VALUE;

            int time2 = Integer.MIN_VALUE;
            int pos2 = Integer.MIN_VALUE;

            Set<Integer> seenSegments = new HashSet<>();
            for (VehicleReport vr : iterable) {
                seenSegments.add(vr.getSegment());
                time1 = Math.min(time1, vr.getTime());
                pos1 = Math.min(pos1, vr.getPosition());
                time2 = Math.max(time2, vr.getTime());
                pos2 = Math.max(pos2, vr.getPosition());
            }

            // check if car covered all segments 52->56
            if (seenSegments.size() < 5) {
                return;
            }


            //Have to check that the car completes all segments 52->56
            double avgSpeed = ((pos2 - pos1) * 1.0 / (time2 - time1)) * 2.23694;// transform to mph
            if (avgSpeed > 60) {
                AverageSpeedControlEvent avgEvent = new AverageSpeedControlEvent();
                avgEvent.setTime1(time1);
                avgEvent.setTime2(time2);
                avgEvent.setVID(key.f0);
                avgEvent.setHighway(key.f1);
                avgEvent.setDirection(key.f2);
                collector.collect(avgEvent);
            }

        }
    }
}