package es.aramirez;

import javafx.scene.layout.BorderPane;

public class TuplePane extends DynamicPanel {
  public TuplePane(BorderPane left, BorderPane right) {
    setLeft(left);
    setRight(right);
  }

  @Override
  public DynamicPanel addPane(BorderPane panel) {
    return new TuplePane((BorderPane) getLeft(), new TuplePane((BorderPane) getRight(), panel));
  }
}
