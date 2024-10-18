package parser.ast;

import parser.ASTNodeVisitor;

public class Negate implements ASTNode {
  public final ASTNode node;

  public Negate(ASTNode node) {
    this.node = node;
  }

  public <T> T accept(ASTNodeVisitor<T> visitor) {
    return visitor.visitNegate(this);
  }

  public String toString() {
    return "(-" + this.node.toString() + ")";
  }
}
