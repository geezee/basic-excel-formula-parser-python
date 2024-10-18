package parser;

public class Variable implements ASTNode {
  public final String name;

  public Variable(String name) {
    this.name = name;
  }

  public <T> T accept(ASTNodeVisitor<T> visitor) {
    return visitor.visitVariable(this);
  }

  public String toString() {
    return this.name;
  }
}
