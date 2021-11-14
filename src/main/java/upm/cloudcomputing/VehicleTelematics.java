/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package upm.cloudcomputing;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import upm.cloudcomputing.operators.AccidentReporter;
import upm.cloudcomputing.operators.AverageSpeedControl;
import upm.cloudcomputing.operators.SpeedRadar;

/**
 * Skeleton for a Flink Streaming Job.
 *
 * <p>For a tutorial how to write a Flink streaming application, check the
 * tutorials and examples on the <a href="https://flink.apache.org/docs/stable/">Flink Website</a>.
 *
 * <p>To package your application into a JAR file for execution, run
 * 'mvn clean package' on the command line.
 *
 * <p>If you change the name of the main class (with the public static void main(String[] args))
 * method, change the respective entry in the POM.xml file (simply search for 'mainClass').
 */
public class VehicleTelematics {

	public static void main(String[] args) throws Exception {
		if (args.length < 2) {
			System.out.println("Please use the following argument format: <input file> <output folder>");
			throw new Exception();
		}

		String inFilePath = args[0];
		String outFilePath = args[1];

		// set up the streaming execution environment
		final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

		// TODO: set env to event time necessary?


		/*
		 * Here, you can start creating your execution plan for Flink.
		 *
		 * Start with getting some data from the environment, like
		 * 	env.readTextFile(textPath);
		 *
		 * then, transform the resulting DataStream<String> using operations
		 * like
		 * 	.filter()
		 * 	.flatMap()
		 * 	.join()
		 * 	.coGroup()
		 *
		 * and many more.
		 * Have a look at the programming guide for the Java API:
		 *
		 * https://flink.apache.org/docs/latest/apis/streaming/index.html
		 *
		 */
		DataStreamSource<String> source = env.readTextFile(inFilePath);
		SingleOutputStreamOperator<VehicleReport> filterOut;
		filterOut = source.map( (MapFunction<String, VehicleReport>) in -> {
			String[] fieldArray = in.split(",");
			Integer time = Integer.parseInt(fieldArray[0]);
			Integer VID = Integer.parseInt(fieldArray[1]);
			Integer speed = Integer.parseInt(fieldArray[2]);
			Integer highway = Integer.parseInt(fieldArray[3]);
			Integer lane = Integer.parseInt(fieldArray[4]);
			Integer direction = Integer.parseInt(fieldArray[5]);
			Integer segment = Integer.parseInt(fieldArray[6]);
			Integer position = Integer.parseInt(fieldArray[7]);
			return new VehicleReport(time, VID, speed, highway, lane, direction, segment, position);
		});

		// apply operators
		SingleOutputStreamOperator speedfines = SpeedRadar.detectSpeedViolation(filterOut);
		SingleOutputStreamOperator avgspeedfines = AverageSpeedControl.measureAvg(filterOut);
		SingleOutputStreamOperator accidents = AccidentReporter.detectAccidents(filterOut);

		// create output paths
		String speedFinesOutputPath = String.format("%s%s", outFilePath, "speedfines.csv");
		String avgSpeedFinesOutputPath = String.format("%s%s", outFilePath, "avgspeedfines.csv");
		String accidentOutputPath = String.format("%s%s", outFilePath, "accidents.csv");

		// replace with non-deprecated code
		speedfines.writeAsCsv(speedFinesOutputPath, FileSystem.WriteMode.OVERWRITE).setParallelism(1);
		avgspeedfines.writeAsCsv(avgSpeedFinesOutputPath, FileSystem.WriteMode.OVERWRITE).setParallelism(1);
		accidents.writeAsCsv(accidentOutputPath, FileSystem.WriteMode.OVERWRITE).setParallelism(1);

		// execute program
		env.execute("Vehicle Telematics");
	}
}
