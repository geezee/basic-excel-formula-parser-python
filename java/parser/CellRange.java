package parser;

public class CellRange implements ASTNode {
  public final Cell start;
  public final Cell end;

  public CellRange(Cell start, Cell end) {
    this.start = start;
    this.end = end;
  }

  public <T> T accept(ASTNodeVisitor<T> visitor) {
    return visitor.visitCellRange(this);
  }

  public String toString() {
    return this.start + ":" + this.end;
  }
}

