package visitor_example;

import parser.*;
import parser.Boolean;
import parser.Number;

public class VisitorExample implements ASTNodeVisitor<VisitorExample.ExcelType> {

    @Override
    public <N extends java.lang.Number> ExcelType visitNumber(Number<N> n) {
        return ExcelType.NUMBER;
    }

    @Override
    public ExcelType visitCell(Cell n) {
        throw new RuntimeException("Cells need more context to know their types");
    }

    @Override
    public ExcelType visitCellRange(CellRange n) {
        throw new RuntimeException("Cells need more context to know their types");
    }

    @Override
    public ExcelType visitVariable(Variable n) {
        throw new RuntimeException("Cells need more context to know their types");
    }

    @Override
    public ExcelType visitFunctionCall(FunctionCall n) {
        throw new RuntimeException("Function calls not supported yet");
    }

    @Override
    public ExcelType visitBoolean(Boolean n) {
        return ExcelType.BOOLEAN;
    }

    @Override
    public ExcelType visitBinary(Binary n) {
        switch (n.op) {
            case PLUS: case DIV: case MULT:
                ExcelType leftType = n.left.accept(this);
                ExcelType rightType = n.right.accept(this);
                if (leftType != ExcelType.NUMBER || rightType != ExcelType.NUMBER) {
                    throw new RuntimeException("Both sides of the binary operation are expected to be numbers");
                } else {
                    return ExcelType.NUMBER;
                }
            default: new RuntimeException("Unknown operation");
        }
        return null;
    }

    @Override
    public ExcelType visitNegate(Negate n) {
        return null;
    }

    public enum ExcelType { NUMBER, BOOLEAN; }


}