package it.eg.sloth.framework.job;

public enum SchedulerStatus {
  STOPPED("Spento"), ACTIVE("Attivo"), SUSPENDED("Sospseso");

  String description;

  public String getDescription() {
    return description;
  }

  private SchedulerStatus(String description) {
    this.description = description;
  }

}
