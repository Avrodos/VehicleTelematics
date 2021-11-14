package upm.cloudcomputing.operators;

import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import upm.cloudcomputing.VehicleReport;

public class AccidentReporter {
    public static SingleOutputStreamOperator detectAccidents(SingleOutputStreamOperator<VehicleReport> filterOut) {
    }
}
