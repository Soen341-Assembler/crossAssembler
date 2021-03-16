//Intermediate Representation (IR) comprised of parsed LineStatements along with their respective codes
public class InterRep implements IInterRep {
    //Array of line statements
    private final ILineStatement[] lines;

    //Parameterized Constructor
    public InterRep(int len) {
        lines = new LineStatement[len];
    }

    //Add LineStatement with a LineStatement object
    public void addLine(int i, ILineStatement l) {
        lines[i] = l;
    }

    //Add LineStatement with a label, Instruction object and comment
    public void addLine(int i, String l, IInstruction in, String c) {
        lines[i] = new LineStatement(l, in, c);
    }

    //Get LineStatement
    public ILineStatement getLine(int i) {
        return lines[i];
    }

    //Get length of LineStatement array
    public int getLength() {
        return lines.length;
    }

    //Set a LineStatement's label
    public void setLabel(int i, String label) {
        lines[i].setLabel(label);
    }

    //Set a LineStatement's comments
    public void setComment(int i, String comment) {
        lines[i].setComment(comment);
    }


    //Check line i for instruction
    public boolean hasInstruction(int i) {
        //Check for empty line or no instruction
        if (lines[i] == null || lines[i].getInstruction().getMnemonic().getMne() == "")
            return false;

        return true;
    }

    //Set a LineStatement's instruction
    public void setInstruction(int i, IInstruction instruction) {
        lines[i].setInstruction(instruction);
    }

    //Set code of mnemonic
    public void setMachineCode(int i) {
        //Get instruction
        IInstruction instr = this.getLine(i).getInstruction();
        //Check if operand present in instruction
        if (instr.getOperand().getOp() != ""){
            //Compute updated code with "Opcode + Operand" and update the LineStatement instruction
            int opcode = instr.getMnemonic().getOpcode();
            int operand = Integer.parseInt(instr.getOperand().getOp());
            int updatedCode;
            if (instr.getMnemonic().getOpcode() == 0x90 && operand < 0) {
                // special case for ldc.i3 and negative operands
                switch(operand) {
                    case -4:
                        updatedCode = opcode + 4;
                        instr.setOpcode(updatedCode);
                        this.setInstruction(i, instr);
                    case -3:
                        updatedCode = opcode + 5;
                        instr.setOpcode(updatedCode);
                        this.setInstruction(i, instr);
                    case -2:
                        updatedCode = opcode + 6;
                        instr.setOpcode(updatedCode);
                        this.setInstruction(i, instr);
                    case -1:
                        updatedCode = opcode + 7;
                        instr.setOpcode(updatedCode);
                        this.setInstruction(i, instr);
                }
            } else {
                updatedCode = opcode + operand;
                instr.setOpcode(updatedCode);
                this.setInstruction(i, instr);
            }
        }
    }

    //Returns a String representable of an InterRep object
    public String toString() {
        String IR = "";
        for(int i = 0; i < this.getLength(); i++)
            IR = IR.concat(String.format("Line %s: %s", i, lines[i].toString() + "\n"));

        return IR;
    }
}