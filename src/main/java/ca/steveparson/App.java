package ca.steveparson;

public class App {
    public static void main(String[] args) throws Exception {

        if (args.length == 0) {

            System.out.printf("Wrong number of parameters:\n"
                            + "\tFirst parameter is the output file name\n"
                            + "\tFollowed by several input file names\n"
                            + "\n\ti.e. output.csv test.json test2.csv test3.xml\n"
                    , args);

            System.exit(-1);
        }

        Model model = new Model();
        Controller controller = new Controller(model);
        View view = new View(model);

        for (int i = 1; i < args.length; i++)
            controller.addRowsFromFile(args[i]);

        view.dumpToFile(args[0]);
        view.summarize();
    }
}

