import java.util.*;
public class Main {
    public static void sortArrayList(List <String> poemList) {
        ArrayList<String> poemArrayList = new ArrayList<>(poemList);
        FileOutput.writeToFile("poemArrayListOrderByID.txt", "", false,false);
        FileOutput.writeToFile("poemArrayList.txt", "", false,false);
        for(String verse : poemArrayList){
            FileOutput.writeToFile("poemArrayList.txt", verse, true,true);
        }
        poemArrayList.sort(new idComparator());
        for(String verse : poemArrayList){
            FileOutput.writeToFile("poemArrayListOrderByID.txt", verse, true,true);
        }
    }
    public static void sortHashSet(List <String> poemList){
        HashSet<String> poemHashSet = new HashSet<>(poemList);
        FileOutput.writeToFile("poemHashSet.txt", "", false,false);
        for (String verse : poemHashSet) {
            FileOutput.writeToFile("poemHashSet.txt", verse, true,true);
        }
    }
    public static void sortHashMap(String[] poemArray){
        HashMap<Integer, String> poemHashMap = new HashMap<Integer, String>();
        for(int i = 0; i < poemArray.length ;i++){
           String[] verseSplit = poemArray[i].split("\t");
           poemHashMap.put(Integer.parseInt(verseSplit[0]), verseSplit[1]);
        }
        FileOutput.writeToFile("poemHashMap.txt", "", false,false);
        for (Integer id : poemHashMap.keySet()) {
            FileOutput.writeToFile("poemHashMap.txt", id + "\t" + poemHashMap.get(id), true,true);
        }
    }
    public static void sortTreeSet(List <String> poemList){
        TreeSet<String> TreeSet = new TreeSet<>(poemList);
        TreeSet<String> sortedTreeSet = new TreeSet<>(new idComparator());
        sortedTreeSet.addAll(poemList);
        FileOutput.writeToFile("poemTreeSetOrderByID.txt", "", false,false);
        FileOutput.writeToFile("poemTreeSet.txt", "", false,false);
        for (String verse : TreeSet) {
            FileOutput.writeToFile("poemTreeSet.txt", verse, true,true);
        }
        for (String verse : sortedTreeSet) {
            FileOutput.writeToFile("poemTreeSetOrderByID.txt", verse, true,true);
        }
    }

    public static void main(String[] args) throws Exception {

        String inputFile = args[0];
        String[] poem = FileInput.readFile(inputFile, true,true);
        List<String> poemList = Arrays.asList(poem);

        //arraylist
        sortArrayList(poemList);

        //hashset
        sortHashSet(poemList);

        //hashmap
        sortHashMap(poem);

        //treeset
        sortTreeSet(poemList);
    }
}
//Sukriye Ozturk 2210356110