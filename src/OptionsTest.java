public class OptionsTest {
    public static void main(String[] args) throws Exception{
        //Testing the function isListing() from Options Class

        //Incorrect Option
        //Outputs error message, does not follow aunit convention
        String[] options = {"-z", "sample.asm"};
        Options incorrectOption = new Options();
        System.out.println("Test -Options Class- Incorrect Options");
        System.out.println("Error: Invalid Option");
        incorrectOption.setOptions(options);
        System.out.println();

        String[] op2 = {"-h", "-l", "-v", "sample.asm"};
        Options incorrect2 = new Options();
        System.out.println("Test -Options Class- Too Many Options");
        System.out.println("Error: Too Many Arguments in CL");
        incorrect2.setOptions(op2);
        System.out.println();

        //Correct options
        String[] opArray2= {"-v","-l","sample.asm"};
        Options CorrectOption = new Options();
        CorrectOption.setOptions(opArray2);
        testOptions("Test -Options Class- isVerbose()", "true", "" + CorrectOption.isVerbose());
        testOptions("Test -Options Class- isListing()", "true", "" + CorrectOption.isListing());
    }

    public static void testOptions(String testCaseName, String expectedOutput, String methodOutput) throws Exception {
        //Test Case Name
        System.out.println(testCaseName);
        //Expected and Actual Output
        System.out.println(expectedOutput);
        System.out.println(methodOutput);
    }


}

