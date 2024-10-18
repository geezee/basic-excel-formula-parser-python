package parser.ast;

import parser.ASTNodeVisitor;

public interface ASTNode {
  public <T> T accept(ASTNodeVisitor<T> visitor);
}
