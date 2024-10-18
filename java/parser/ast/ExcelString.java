package parser.ast;

import parser.ASTNodeVisitor;

public class ExcelString implements ASTNode {
  public final String value;

  public ExcelString(String value) {
    this.value = value;
  }

  public <T> T accept(ASTNodeVisitor<T> visitor) {
    return visitor.visitString(this);
  }

  public String toString() {
    return this.value;
  }
}
