public class ParserTest {
    public static void main(String[] args) throws Exception {

        Reader rdr = new Reader("TestImmediate_copy.asm");
        rdr.readFile();
        SymbolTable symT= new SymbolTable();
        ErrorReporter er = new ErrorReporter("TestImmediate_copy.asm");
        Scanner scn= new Scanner(symT,er);
        Parser p = new Parser(rdr.getLineNum() + 1, symT, er, scn, rdr);
        p.parseToken();

        //Testing getInterRep()
        testParser("getInterRep() number of lines", "76", Integer.toString(p.getInterRep().getLength()));

        //should we test the parseOperandBound in its entirety, and how
        testParser("getInterRep() inner contents ", "' '\"enter.u5: 112\" 0' ; OK, number <u5> [0..31].'", p.getInterRep().getLine(1).toString());

    }
    public static void testParser(String caseName, String expectedOutput, String methodOutput) throws Exception {
        System.out.println("Test -Parser Class- " + caseName);
        // expected value
        System.out.println(expectedOutput);
        // actual output
        System.out.println(methodOutput);
    }
}

