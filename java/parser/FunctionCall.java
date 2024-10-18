package parser;

import java.util.List;
import java.util.stream.Collectors;

public class FunctionCall implements ASTNode {
  public final String functionName;
  public final List<ASTNode> args;

  public Name(String name, List<ASTNode> args) {
    this.name = name;
    this.args = args;
  }

  public <T> T accept(ASTNodeVisitor<T> visitor) {
    return visitor.visitFunctionCall(this);
  }

  public String toString() {
    return this.name + "(" + args.stream().map(a -> a.toString()).collect(Collectors.joining(", ")) + ")";
  }
}

