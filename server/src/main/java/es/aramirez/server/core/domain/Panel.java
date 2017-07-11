package es.aramirez.server.core.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Panel {
  private String name;
  private UUID id;
  private List<Task> tasks = new ArrayList<>();

  public Panel(String name) {
    this.id = UUID.randomUUID();
    this.name = name;
  }

  public void addTask(String title) {
    tasks.add(new Task(getId(), title));
  }

  public String getId() {
    return id.toString();
  }

  public String getName() {
    return name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Panel panel = (Panel) o;

    return id != null ? id.equals(panel.id) : panel.id == null;
  }

  @Override
  public int hashCode() {
    int result = name != null ? name.hashCode() : 0;
    result = 31 * result + (id != null ? id.hashCode() : 0);
    return result;
  }

  public List<Task> getTasks() {
    return tasks;
  }
}
