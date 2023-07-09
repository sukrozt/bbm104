public class Main {
    /**
     * The main class of all code. Input and output files are given from here.
     *
     * @param args
     * @throws Exception if the first command is not "SetInitialTime" or the initial date has given wrong format
     */
    public static void main(String[] args) throws Exception {
        String inputPath = args[0];
        String outputPath = args[1];
        try {
            FileOutput.writeToFile(outputPath, "", false, false);
            String[] inputs = FileInput.readFile(inputPath, true, true);
            String[] theFirstRow = inputs[0].split("[\t ]+");
            String theFirstCommand = (theFirstRow)[0];
            String commandReport = "";
            for (String elt : theFirstRow) {
                commandReport += elt + "\t";
            }
            if (!theFirstCommand.equals("SetInitialTime") || (theFirstRow.length != 2))
                throw new MyException(String.format("COMMAND: %s\nERROR: First command must be set initial time! Program is going to terminate!", commandReport));
            CommandReader commandReader = new CommandReader(outputPath);
            for (String inputsAsLine : inputs) {
                String[] inputLinesAsArray = inputsAsLine.split("[\\s\\t]+");
                String reportOutput = "COMMAND: ";
                for (String elt : inputLinesAsArray) {
                    reportOutput += elt + "\t";
                }
                FileOutput.writeToFile(outputPath, reportOutput, true, true);
                commandReader.ReadCommand(inputLinesAsArray);
                if (inputsAsLine.equals(inputs[inputs.length - 1])) {
                    if (!inputLinesAsArray[0].equals("ZReport")) {
                        FileOutput.writeToFile(outputPath, "ZReport:", true, true);
                        commandReader.ReadCommand(new String[]{"ZReport"});
                    }
                }
            }
        }
        catch (MyException e) {
            FileOutput.writeToFile(outputPath, e.getMessage(), true, true);
            System.exit(0);
        }
    }
}
//Sukriye Ozturk 2210356110