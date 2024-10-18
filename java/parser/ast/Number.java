package parser.ast;

import parser.ASTNodeVisitor;

public class Number<N extends java.lang.Number> implements ASTNode {
  public final N value;

  public Number(N value) {
    this.value = value;
  }

  public <T> T accept(ASTNodeVisitor<T> visitor) {
    return visitor.visitNumber(this);
  }

  public String toString() {
    return this.value.toString();
  }
}

