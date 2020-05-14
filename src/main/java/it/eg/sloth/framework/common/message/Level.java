package it.eg.sloth.framework.common.message;

public enum Level {
  ERROR(4), WARN(3), SUCCESS(2), INFO(1);

  int severity;

  private Level(int severity) {
    this.severity = severity;
  }

  public boolean hasHigerSeverity(Level level) {
    return this.severity > level.severity;
  }

}
