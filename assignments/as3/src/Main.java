public class Main {
    public static void main(String[] args) throws Exception {
        String inputFile = args[0];
        String outputFile = args[1];

            FileOutput.writeToFile(outputFile, "", false, false);
            String[] readInput = FileInput.readFile(inputFile, true, true);
            Commands cmd = new Commands();
            for (String line : readInput) {
                try{
                    String[] eltOfLine = line.split("\t");
                    cmd.readCommand(eltOfLine, outputFile);
                }catch (Exception e){
                    FileOutput.writeToFile(outputFile, e.getMessage(), false, false);
                }
        }
    }
}