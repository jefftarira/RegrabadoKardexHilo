package pruebas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static java.util.stream.Collectors.joining;

public class Collector {

  public static void main(String[] args) {
    List<String> names = Collections.checkedList(
            new ArrayList<>(), String.class);
    Collections.addAll(names, "John", "Anton", "Heinz");
    List huh = names;
    List<Integer> numbers = huh;
    numbers.add(42);
    System.out.println(names.stream().collect(joining("+")));
  }

}
