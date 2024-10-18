package parser;

public interface ASTNodeVisinor<T> {

  public <N extends java.lang.Number> T visitNumber(Number<N> n);
  public T visitCell(Cell n);
  public T visitCellRange(CellRange n);
  public T visitVariable(Variable n);
  public T visitFunctionCall(Variable n);
  public T visitBoolean(Boolean n);
  public T visitBinary(Binary n);
  public T visitUnary(Unary n);

}
