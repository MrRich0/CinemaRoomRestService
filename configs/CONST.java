package cinema.configs;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CONST {
    private static final int nRows = 9;
    private static final int nCols = 9;
    private static final String secret = "super_secret";

    public static int getnRows() {
        return nRows;
    }

    public static int getnCols() {
        return nCols;
    }
    public static String getsecret() {
        return secret;
    }

}

