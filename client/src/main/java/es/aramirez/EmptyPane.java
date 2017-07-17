package es.aramirez;

import javafx.scene.layout.BorderPane;

public class EmptyPane extends DynamicPanel {
  @Override
  public DynamicPanel addPane(BorderPane panel) {
    return new TuplePane(panel, new EmptyPane());
  }
}
