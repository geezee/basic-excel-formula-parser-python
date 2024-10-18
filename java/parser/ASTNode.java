package parser;

public interface ASTNode {
  public <T> T accept(ASTNodeVisitor<T> visitor);
}
