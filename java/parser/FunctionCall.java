package parser;

import java.util.List;
import java.util.stream.Collectors;

public class FunctionCall implements ASTNode {
  public final BasicFunction functionName;
  public final List<ASTNode> args;

  public FunctionCall(BasicFunction name, List<ASTNode> args) {
    this.functionName = name;
    this.args = args;
  }

  public <T> T accept(ASTNodeVisitor<T> visitor) {
    return visitor.visitFunctionCall(this);
  }

  public String toString() {
    return this.functionName + "(" + args.stream().map(a -> a.toString()).collect(Collectors.joining(", ")) + ")";
  }
}

