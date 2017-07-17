package es.aramirez;

import javafx.scene.layout.BorderPane;

public abstract class DynamicPanel extends BorderPane {
  public abstract DynamicPanel addPane(BorderPane panel);
}
