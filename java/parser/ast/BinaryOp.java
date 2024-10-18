package parser.ast;

public enum BinaryOp {
  PLUS("+", 3), MINUS("-", 3), MULT("*", 2), DIV("/", 2), MODULO("%", 0), POW("^", 1), LESS_THAN("<", 4),
  BIGGER_THAN(">", 4), LESS_THAN_OR_EQ("<=", 4), BIGGER_THAN_OR_EQ(">=", 4), EQUAL("=", 4), NOT_EQUAL("<>", 4);

  public final String value;
  public final int precedence;

  BinaryOp(String value, int precedence) {
    this.value = value;
    this.precedence = precedence;
  }

  public String toString() {
    return this.value;
  }
}
