package appli.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class ArrayListManager {

    public static <T> T popElement(ArrayList<T> arrayList) {
        if (arrayList.size() == 0) throw new ArrayIndexOutOfBoundsException();
        T toReturn = getLastElement(arrayList);
        arrayList.remove(toReturn);
        return toReturn;
    }

    public static <T> T getLastElement(ArrayList<T> arrayList) {
        if (arrayList.size() == 0) throw new ArrayIndexOutOfBoundsException();
        return arrayList.get(arrayList.size() - 1);
    }

    public static <K, V> K getKeyByValue(LinkedHashMap<K, V> lhm, V value) {
        for (Map.Entry<K, V> entry : lhm.entrySet()) {
            if (entry.getValue().equals(value)) return entry.getKey();
        }

        return null;
    }

    public static <T> String printDeck(ArrayList<T> arrayList, String wordToSetBeforeLast) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < arrayList.size(); i++) {
            T c = arrayList.get(i);
            sb.append(c);

            if (i == arrayList.size() - 2) sb.append(wordToSetBeforeLast);
            else if (i == arrayList.size() - 1) sb.append("");
            else sb.append(", ");
        }

        return sb.toString();
    }

    public static <T> String printDeck(ArrayList<T> arrayList) {
        return printDeck(arrayList, ", ");
    }

}
