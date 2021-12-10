package master.operators;

import master.VehicleReport;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.windows.GlobalWindow;
import org.apache.flink.util.Collector;
import org.apache.flink.api.java.tuple.Tuple3;
import master.events.AverageSpeedControlEvent;

public class AverageSpeedControl {
    public static SingleOutputStreamOperator<AverageSpeedControlEvent> measureAvg(SingleOutputStreamOperator<VehicleReport> filterOut) {
        return filterOut
                .filter((VehicleReport vr) -> vr.getSegment()>=52 && vr.getSegment()<=56)
                //.keyBy((KeySelector<VehicleReport, Tuple3<Integer, Integer, Integer>>) vr -> Tuple3.of(vr.getVID(), vr.getHighway(), vr.getDirection()))
                // flink seems to have trouble with the lamda expression when using Tuples, so lets try an anonymous class
                .keyBy(new KeySelector<VehicleReport, Tuple3<Integer, Integer, Integer>>() {
                    @Override
                    public Tuple3<Integer, Integer, Integer> getKey(VehicleReport vehicleReport){
                        return Tuple3.of(vehicleReport.getVID(), vehicleReport.getHighway(), vehicleReport.getDirection());
                    }
                })
                .countWindow(4,1) //do not why these numbers?
                .apply(new AverageWindow());

    }

    private static class AverageWindow implements WindowFunction<VehicleReport, AverageSpeedControlEvent,
            Tuple3<Integer, Integer, Integer>, GlobalWindow> {

        @Override
        public void apply(Tuple3<Integer, Integer, Integer> key, GlobalWindow globalWindow, Iterable<VehicleReport> iterable, Collector<AverageSpeedControlEvent> collector) {
        //globalwindow or timewindow?
            int enoughSegments = 0;

            int time1 = Integer.MAX_VALUE;
            int pos1 = Integer.MAX_VALUE;

            int time2 = 0;
            int pos2 = 0;

            for(VehicleReport vr : iterable){
                int currentTime = vr.getTime();
                int currentPos = vr.getPosition();
                time1 = Math.min(time1,currentTime) ;
                pos1 = Math.min(pos1, currentPos);
                time2 = Math.max(time2, currentTime);
                pos2 = Math.max(pos2, currentPos);
            }
            //Have to check that the car completes all segments 52->56
            double avgSpeed = ((pos2-pos1) * 1.0 / (time2-time1)) * 2.23694;//in mph
            if(avgSpeed > 60){
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
//speed above 60 mph. between segments 52-56. If multiple reports for same car, take the one with longest distance


//NOT DONE, have not controlled the right segments and also if there are multiple reports for same car.