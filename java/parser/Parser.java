package parser;

import parser.ast.*;
import parser.ast.Boolean;
import parser.ast.Number;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Parser {

    private String[] tokens;
    private int cursor = 0;

    public Parser() {
    }

    private static String[] tokenize(String input) {
        input.replaceAll("e +", "e+").replaceAll("e -", "e-").replaceAll("e+ ", "e+").replaceAll("e- ", "e-")
             .replaceAll("> =", ">=").replaceAll("< =", "<=").replaceAll("< >", "<>");
        for (String s : new String[] { ":", ",", "\\(", "\\)", "\\*", "\\+", "/", "-", "%", "\\^", "=", "<", "<=", ">=", "<>" }) {
            input = input.replaceAll(s, " " + s + " ");
        }
        return input.trim().split("\\s+");
    }

    private void assertNotEOF() {
        if (cursor >= tokens.length) throw new RuntimeException("Unexpected: Reached EOF");
    }

    private Optional<ASTNode> parseBoolean() {
        assertNotEOF();
        String token = tokens[cursor];
        if (token.equalsIgnoreCase("true")) {
            cursor++;
            return Optional.of(new Boolean(true));
        } else if (token.equalsIgnoreCase("false")) {
            cursor++;
            return Optional.of(new Boolean(false));
        } else {
            return Optional.empty();
        }
    }

    private Optional<ASTNode> parseNumber() {
        assertNotEOF();
        String token = tokens[cursor];
        try {
            long value = Long.parseLong(token);
            cursor++;
            return Optional.of(new Number<>(value));
        } catch (NumberFormatException e1) {
            try {
                double value = Double.parseDouble(token);
                cursor++;
                return Optional.of(new Number<>(value));
            } catch (NumberFormatException e2) {
                return Optional.empty();
            }
        }
    }

    private Optional<Cell> parseCell() {
        assertNotEOF();

        String token = tokens[cursor];

        String col = "";
        String row = "";

        for (char c: token.toCharArray()) {
            if (Character.isAlphabetic(c)) {
                if (row.length() == 0) { col += c; }
                else { return Optional.empty(); }
            } else if (Character.isDigit(c)) {
                row += c;
            }
        }

        if (col.length() > 0 && row.length() > 0) {
            cursor++;
            return Optional.of(new Cell(col, Integer.parseInt(row)));
        } else {
            return Optional.empty();
        }
    }

    private Optional<ASTNode> parseCellOrCellRange() {
        return parseCell().flatMap(cell1 -> {
            if (cursor < tokens.length && tokens[cursor].equals(":")) {
                cursor++;
                Cell cell2 = parseCell().orElseThrow(() -> new ParseException("Expected a cell after :"));
                return Optional.of(new CellRange(cell1, cell2));
            } else {
                return Optional.of(cell1);
            }
        });
    }

    private Optional<ASTNode> parseNameOrFunc() {
        if (cursor >= tokens.length) return Optional.empty();
        String name = tokens[cursor];
        if (!name.matches("^[a-zA-Z_][a-zA-Z0-9_.]+$")) return Optional.empty();
        cursor++;
        for (BasicFunction fun : BasicFunction.values()) {
            if (fun.value.equalsIgnoreCase(name)) {
                assertNotEOF();
                if (!tokens[cursor].equals("(")) throw new ParseException("Expected ( after function name " + fun.value);
                cursor++;
                ArrayList<ASTNode> arguments = new ArrayList<>();
                while (true) {
                    arguments.add(parseExpr());
                    assertNotEOF();
                    if (tokens[cursor].equals(")")) { cursor++; break; }
                    else if (tokens[cursor].equals(",")) { cursor++; continue; }
                    else { throw new ParseException("Expected closed parenthesis or comma after end of argument, found " + tokens[cursor]); }
                }
                return Optional.of(new FunctionCall(fun, arguments));
            }
        }
        return Optional.of(new ExcelString(name));
    }

    private Optional<ASTNode> parseBasicExpr() {
        return parseBoolean()
                 .or(this::parseNumber)
                 .or(this::parseCellOrCellRange)
                 .or(this::parseNameOrFunc)
                 .or(() -> {
                     assertNotEOF();
                     if (tokens[cursor].equals("(")) {
                         cursor++;
                         ASTNode result = parseExpr();
                         if (cursor >= tokens.length || !tokens[cursor].equals(")")) {
                            throw new ParseException("Expected closed parenthesis, unexpected EOF");
                         }
                         return Optional.of(result);
                     } else if (tokens[cursor].equals("+")) {
                         cursor++;
                         return parseBasicExpr();
                     } else if (tokens[cursor].equals("-")) {
                         cursor++;
                         return parseBasicExpr().map(Negate::new);
                     }
                     return Optional.empty();
                 });
    }

    private BinaryOp parseBinaryOp() {
        assertNotEOF();
        for (BinaryOp op : BinaryOp.values()) {
            if (tokens[cursor].equals(op.value)) {
                cursor++;
                return op;
            }
        }
        throw new ParseException("Expected a binary operator, found " + tokens[cursor]);
    }

    private ASTNode parseExpr() {
        assertNotEOF();
        List<ASTNode> arithmeticParts = new ArrayList<>();
        List<BinaryOp> ops = new ArrayList<>(); // invariant: ops.length + 1 == arithmeticParts.length;

        while (true) {
            parseBasicExpr().ifPresentOrElse(
                arithmeticParts::add,
                () -> { throw new ParseException("Expected number, boolean, cell, range, or function call"); }
            );

            if (cursor >= tokens.length || tokens[cursor].equals(",") || tokens[cursor].equals(")"))
                break;

            ops.add(parseBinaryOp());
        }

        while (arithmeticParts.size() > 1) {
            int min_i = 0;
            for (int j=0; j<ops.size(); j++) if (ops.get(j).precedence < ops.get(min_i).precedence) min_i = j;
            arithmeticParts.set(min_i,
                new Binary(arithmeticParts.get(min_i),
                           ops.remove(min_i),
                           arithmeticParts.remove(min_i+1)));
        }

        return arithmeticParts.get(0);
    }

    public ASTNode parse(String input) {
        this.tokens = tokenize(input);
        this.cursor = 0;
        ASTNode result = parseExpr();
        if (cursor < tokens.length) {
            throw new ParseException("More than one formula was given");
        }
        return result;
    }

    public class ParseException extends RuntimeException {
        ParseException(String reason) {
            super(reason);
        }
    }
}
