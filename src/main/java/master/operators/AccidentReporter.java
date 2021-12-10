package master.operators;

import master.VehicleReport;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple5;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.windows.GlobalWindow;
import org.apache.flink.util.Collector;
import master.events.AccidentReporterEvent;

import java.util.Iterator;

public class AccidentReporter {
    public static SingleOutputStreamOperator<AccidentReporterEvent> detectAccidents(SingleOutputStreamOperator<VehicleReport> filterOut) {
        return filterOut
                // only take events with speed = 0
                .filter(vr -> vr.f2 == 0).setParallelism(1)
                // identify same vehicles
                //.keyBy((KeySelector<VehicleReport, Tuple5<Integer, Integer, Integer, Integer, Integer>>) vehicleReport -> Tuple5.of(vehicleReport.getVID(), vehicleReport.getHighway(), vehicleReport.getDirection(), vehicleReport.getSegment(), vehicleReport.getPosition()))

                // flink seems to have trouble with the lambda expression when using Tuples, so lets try an anonymous class
                .keyBy(new KeySelector<VehicleReport, Tuple5<Integer, Integer, Integer, Integer, Integer>>() {
                    @Override
                    public Tuple5<Integer, Integer, Integer, Integer, Integer> getKey(VehicleReport vehicleReport){
                        return Tuple5.of(vehicleReport.getVID(), vehicleReport.getHighway(), vehicleReport.getDirection(), vehicleReport.getSegment(), vehicleReport.getPosition());
                    }
                })
                .countWindow(4, 1)
                // desired output
                .apply(new AccidentReportWindowFunction());

    }

    private static class AccidentReportWindowFunction implements WindowFunction<VehicleReport, AccidentReporterEvent,
            Tuple5<Integer, Integer, Integer, Integer, Integer>, GlobalWindow> {

        @Override
        public void apply(Tuple5<Integer, Integer, Integer, Integer, Integer> inKey, GlobalWindow globalWindow, Iterable<VehicleReport> iterable, Collector<AccidentReporterEvent> collector) {
            int counter = 1;

            // Iterator is needed to traverse the events
            Iterator<VehicleReport> vehicleReportIterator = iterable.iterator();
            int time1 = vehicleReportIterator.next().getTime();

            while (vehicleReportIterator.hasNext()) {
                counter++;
                VehicleReport vehicleReport = vehicleReportIterator.next();
                // We will report an accident if vehicle reports 4 consecutive events from the same position
                if (counter == 4) {
                    AccidentReporterEvent accidentReporterEvent = new AccidentReporterEvent();
                    accidentReporterEvent.setTime1(time1);
                    accidentReporterEvent.setTime2(vehicleReport.getTime());
                    accidentReporterEvent.setVID(inKey.f0);
                    accidentReporterEvent.setHighway(inKey.f1);
                    accidentReporterEvent.setSegment(inKey.f3);
                    accidentReporterEvent.setDirection(inKey.f2);
                    accidentReporterEvent.setPosition(inKey.f4);
                    collector.collect(accidentReporterEvent);
                }
            }
        }
    }
}
