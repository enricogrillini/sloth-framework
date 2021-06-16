package it.eg.sloth.form;

public enum ControlState {

    DEFAULT(-1, "primary"),
    INFO(0, "info"),
    SECONDARY(1, "secondary"),
    SUCCESS(2, "success"),
    WARNING(3, "warning"),
    DANGER(4, "danger"),
    DARK(5, "dark");

    int severity;
    String bootstrapBaseClass;

    ControlState(int severity, String bootstrapClass) {
        this.severity = severity;
        this.bootstrapBaseClass = bootstrapClass;
    }

    public int getSeverity() {
        return severity;
    }

    public boolean hasHigerSeverity(ControlState controlState) {
        return this.severity > controlState.severity;
    }

    public String getBootstrapClass() {
        return bootstrapBaseClass;
    }

}
