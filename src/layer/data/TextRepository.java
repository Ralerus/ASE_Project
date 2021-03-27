package layer.data;

public class TextRepository {
    public static boolean createText(String text, Difficulty difficulty){
        //DB create text
        return true;
    }

    public static Text getRandomTextBasedOn(Rules rules){
        //select text from DB where values = rules
        String text = "";
        Difficulty difficulty = Difficulty.Easy;

        return new Text(text,difficulty,text.length());
    }
}
