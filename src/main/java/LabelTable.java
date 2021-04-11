package main.java;
import main.interfaces.ILabelTable;
import java.util.HashMap;

//Used to store seen labels, to be referred to on second pass for offset or addr location (depending on how we want to implement it)
public class LabelTable implements ILabelTable {
    private Offset offset;
    private HashMap<String, Offset> labelTable;

    //Default constructor
    public LabelTable() {
        offset = new Offset();
        labelTable = new HashMap<>();
    }

    //Create new entry into labelTable
    public void newEntry(String label) {
        offset = new Offset();
        labelTable.put(label, offset);
    }

    //Set Label start address
    public void setLabelStart(String label, int addr) {
        //get Offset object
        offset = labelTable.getOrDefault(label, new Offset());
        //update Offset object
        offset.setAddrStart(addr);
        //put updated Offset object back in labelTable
        labelTable.put(label, offset);
    }

    //Set Label end address
    public void setLabelEnd(String label, int addr) {
        //get Offset object
        offset = labelTable.getOrDefault(label, new Offset());
        //update Offset object
        offset.setAddrEnd(addr);
        //put updated Offset object back in labelTable
        labelTable.put(label, offset);
    }

//Get Label Address Code
    public Offset getAddr(String label) {
        return labelTable.getOrDefault(label, new Offset());
    }

    //Have encountered label 2 times
    public boolean hasAddr(String label) {
        return labelTable.get(label).getNumTimes() != 2;
    }

    //See if a label is present in the LabelTable
    public boolean hasStartLabel(String label) {
        return labelTable.containsKey(label);
    }

    //Print Label Table
    public void toConsole() {
        for(String label : labelTable.keySet()) {
            int start = labelTable.get(label).getStartAddr();
            int end = labelTable.get(label).getEndAddr();
            int offset = start - end;
            System.out.println("Label: " + label + ", Addr 1: " + start + ", Addr 2: " + end + ", Offset: " + offset);
        }
    }
}
