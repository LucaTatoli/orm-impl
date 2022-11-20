import controller.SpeseController;

public class Main
{
    public static void main(String... args)
    {
        SpeseController controller = new SpeseController();

        controller.loadSpeseFromTSV("/home/luca/Downloads/lista.tsv");
        System.out.println(controller.getTotalByCategory("TrasPORTI"));
        System.out.println(controller.getTotalIn("novembre"));
    }
}
