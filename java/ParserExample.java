import parser.Parser;

public class ParserExample {
    public static void main(String[] args) {
        Parser parser = new Parser();
        System.out.println(parser.parse("MIN(A5:B89 ^ + - + - 5 * A5 / 10.0 % true, MAX(A4:B6, 10))"));
    }
}
