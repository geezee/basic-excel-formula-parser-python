package parser.ast;

public enum BasicFunction {
    NOT("not"), IF("if"), AND("and"), OR("or"), MIN("min"), MAX("max"), SUM("sum"), ABS("abs"), PRODUCT("product"), MEAN("mean");

    public final String value;

    BasicFunction(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
