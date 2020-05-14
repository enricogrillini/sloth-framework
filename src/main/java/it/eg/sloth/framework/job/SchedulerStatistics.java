package it.eg.sloth.framework.job;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SchedulerStatistics {
  
  SchedulerStatus status;
  int scheduledJobCount;
  int scheduledTriggerCount;
  
  int runningJob;
  int totalExecutions;
}
