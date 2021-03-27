import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String string = "abcdefghijklmnop";
        List<Character> characters = new ArrayList<>();
        characters = string.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
        System.out.println(characters.get(0));
        characters.remove(0);
        System.out.println(characters.get(0));


    }
}
