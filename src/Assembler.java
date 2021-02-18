//package src;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;

public class Assembler {

    private static String srcName;
    private static File srcFile;
    public static AssemblyUnit assemblyUnit;
    public static FileGenerator generator;

    // TODO: try to break components in main() into classes/interfaces as much as possible to make testing easier
    public static void main(String args[]) throws Exception {

        //Check if .asm file not included in CLI arguments
        if (args.length < 1) {
            System.out.println("Error: Missing .asm file");
            return;
        }

        //Check if there's too many CLI arguments
        if (args.length > 3) {
            System.out.println("Error: Too Many Arguments in CL");
            return;
        }

        //Check <src>
        if (args[0] != null) {
            srcName = args[0];
            srcFile = new File(srcName);
            if (!srcFile.canRead()) {
                System.out.println("Cannot open source file '" + srcName + "'");
                return;
            }
        }

        //Parse the Assembly Code
        String[] ls = Parser(srcFile);

        //Initialize the AssemblyUnit
        assemblyUnit = new AssemblyUnit(ls.length);

        //Splits LineStatements to Perform Lexical Analysis
        Tokenizer(ls, assemblyUnit);

        //Get listing file contents
        Listing listing = new Listing(assemblyUnit);
        String[] listingContent = listing.getListing();

        //Generate listing file
        generator = new FileGenerator(listingContent);
        generator.generateListing();
    }

    // TODO: Rename to Options? Parser (according to domain dictionary)? Just check for options in here?
    public static int Scanner(String[] options) throws Exception {
        //Return Type
        int status = 0;

        //No File Provided Error
        if(options.length < 1) return -3;

        //Too Many Options Enabled Error
        if(options.length > 3) return -2;

        boolean found = false;

        //Iterate Through Options
        for(String o:options)
            if(o.equals("-h") || o.equals("--help"))
                status = (status <= 0) ? 1 : -1;
            else if(o.equals("-l") || o.equals("--listing"))
                status = (status <= 0) ? 2 : (status == 3) ? 4 : -1;
            else if(o.equals("-v") || o.equals("--verbose"))
                status = (status <= 0) ? 3 : (status == 2) ? 4 : -1;
            else if(o.endsWith(".asm") && !found)
                found = true;
            else
                return -1;

        return found ? status : -3;
    }

    // TODO: Need to rename assemblyUnit to something else here
    //Parses - Reads File
    public static String[] Parser(File f) throws Exception {
        //Read File Using File Input Stream
        FileInputStream file = new FileInputStream(f);

        //Generate an Assembly Unit
        String assemblyUnit = "";
        //Assembly assemblyUnit = new AssemblyUnit("");

        int currentChar = file.read();
        while(currentChar > 0) {
            assemblyUnit += (char)currentChar;
            currentChar = file.read();
        }

        file.close();

        //Create an Array of LineStatements Using EOL
        return assemblyUnit.split("[\r\n]+");
    }

    //Tokenizer - Combines Tokens
    public static void Tokenizer(String[] lines, AssemblyUnit assemblyUnit) {
        //Split LineStatements into Sub Components Using Whitespace
        String[] subComponents;
        String assComments;
        for(int i = 0; i < lines.length; i++) {
            //Ignore Comments, Remove Extra WhiteSpace Then Split into SubComponents
            subComponents = lines[i].split(";")[0].trim().split("\\s+");
            assComments = lines[i].contains(";") ? lines[i].split(";")[1].trim() : "";
            LexicalAnalyzer(assemblyUnit, i, subComponents, assComments);
        }
    }

    //Lexical Analyzer
    public static void LexicalAnalyzer(AssemblyUnit assemblyUnit, int i, String[] subComponents, String comments) {
        //Perform Lexical Analysis & Detect Errors
        int len = subComponents.length;
        switch(len) {
            //Stack + Inherent Addressing Mode
            case(1):
                //System.out.println("Mnemonic || Label");
                //Check in HashSet for Mnemonic
                //If not, Add Element to Label Table
                InstructionSet instructSet = new InstructionSet();

                int code = instructSet.getCode(subComponents[0]);

                if(code == -1)
                    System.out.println("Error: Mnemonic Not Found");
                else if(code > 0x1F)
                    System.out.println("Error: Missing an Operand");
                else if(code >= 0x00 && code <= 0x1F ) {
                    //Add LineStatement to AssemblyUnit
                    assemblyUnit.setLine(i, new Instruction(subComponents[0], ""), comments);
                    assemblyUnit.setCode(i, code);
                }
                //Check if label exists in label table Else Add to Table

                //toHexString
                //System.out.print(Integer.toBinaryString(code) + " ");
                break;
            //Immediate Addressing Mode
            case(2):
                //System.out.println("Mnemonic + Operand || Label + Mnemonic");
                //Check First Element in HashSet for Mnemonic
                //If not, Add first Element to Label Table & Check Second Element


                //System.out.println("Error: Mnemonic Missing/Not Found");
                //System.out.println("Error: Missing an Operand");
                //System.out.println("Error: Operand is Too Large");
                //System.out.println("Error: Operand Not Allowed");
                break;
            //Relative Addressing Mode
            case(3):
                //System.out.println("Label + Mnemonic + Operand");
                //Add First Element to Label List
                //Check Second Element in HashSet for Mnemonic
                //Check Third Element

                //System.out.println("Error: Mnemonic Not Found");
                //System.out.println("Error: Operand is Too Large");
                //System.out.println("Error: Operand Not Allowed");
                break;
            default:
                System.out.println("Error: Exceeds Possible Number of Elements Per line");
        }


        //Prints Out Sub Components
//        System.out.print("{ ");
//        for(String s: subComponents)
//            System.out.print("[" + s + "] ");
//        System.out.print("[" + comments + "] ");
//        System.out.println("}");
    }

}