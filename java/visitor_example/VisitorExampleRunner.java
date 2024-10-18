package visitor_example;

import parser.*;

import javax.swing.text.html.parser.Parser;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class VisitorExampleRunner {

    public static void main(String[] args) {

        ArrayList<ASTNode> funArgs = new ArrayList<>();

        funArgs.add(new parser.Boolean(true));
        funArgs.add(new Binary(new parser.Boolean(false), BinaryOp.PLUS, new parser.Number<Double>(1.0)));
        funArgs.add(new CellRange(new Cell("A", 1), new Cell("B", 10)));

        ASTNode test = new FunctionCall(BasicFunction.IF, funArgs);

        ASTNode teoExpr = new Binary(new parser.Number<Integer>(5), BinaryOp.MULT, new parser.Cell("A", 5));

        System.out.println(test.toString());

        System.out.println(teoExpr.accept(new TypeCheckingVisitor(null)));
    }

}
