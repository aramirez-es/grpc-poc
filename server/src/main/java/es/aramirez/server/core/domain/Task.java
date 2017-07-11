package es.aramirez.server.core.domain;

public class Task {
  private String panelId;
  private String title;

  public Task(String panelId, String title) {
    this.panelId = panelId;
    this.title = title;
  }

  public String getPanelId() {
    return panelId;
  }

  public String getTaskId() {
    return getTitle();
  }

  public String getTitle() {
    return title;
  }
}
