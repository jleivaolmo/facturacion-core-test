package com.echevarne.sap.cloud.facturacion.jms.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * An example message:
 * 
 *  // add header '_type': 'jobexecutiondata'
  { 
   "executionId": 567,
   "jobName": "a_job_name",
   "startTime": "2023-02-26T16:34:09.679+01:00"
  }
 * 
 */

@Data
@ToString
@NoArgsConstructor
public class JobExecutionData implements TypedJsonMessage{
	public static String MESSAGE_TYPE = "jobexecutiondata";	
	@Override
	@JsonIgnore
	public String getMessageType() {
		return MESSAGE_TYPE;
	}

	private long executionId;
	private String jobName;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
	private Date startTime;

//public static void main(String[] args) throws Exception {
//	JobExecutionData upbpm = new JobExecutionData();
//	upbpm.setStartTime(Date.from(Instant.now()));
//	ObjectMapper objectMapper = new ObjectMapper();
//	objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
//	objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//    objectMapper.setTimeZone(TimeZone.getDefault());
//    objectMapper.setSerializationInclusion(Include.ALWAYS);			
////	System.out.println(JmsUtils.toJson(upbpm));
//	System.out.println(objectMapper.writeValueAsString(upbpm));
//}
}