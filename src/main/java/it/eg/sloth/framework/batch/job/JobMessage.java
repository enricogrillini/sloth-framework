package it.eg.sloth.framework.batch.job;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class JobMessage {

  public static final String EXECUTION_ID = "executionID";

  Integer executionId;
  String groupName;
  String jobName;
  String message;
  String detail;
  JobStatus status;
  Integer progress;
  Timestamp started;
  Timestamp updated;

}
