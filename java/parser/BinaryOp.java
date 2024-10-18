package parser;

public enum BinaryOp {
  PLUS, MINUS, MULT, DIV, MODULO, POW, LESS_THAN, BIGGER_THAN, LESS_THAN_OR_EQ, BIGGER_THAN_OR_EQ,
  EQUAL, NOT_EQUAL;

  public String toString() {
    switch (this) {
    case PLUS: return "+";
    case MINUS: return "-";
    case MULT: return "*";
    case DIV: return "/";
    case MODULO: return "%";
    case POW: return "^";
    case LESS_THAN: return "<";
    case BIGGER_THAN: return ">";
    case LESS_THAN_OR_EQ: return "<=";
    case BIGGER_THAN_OR_EQ: return ">=";
    case EQUAL: return "=";
    case NOT_EQUAL: return "<>";
    default: return null;
    }
  }
}
