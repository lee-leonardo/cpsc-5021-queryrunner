package queryrunner;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * CLI tool we've been building in all of our other classes.
 */
public class QueryConsole {
    public enum MENU_OPTION {
        query,
        help,
        quit;

        public static int ordinalOf(String value) {
            if (!isValid(value)) {
                return -1;
            }

            return MENU_OPTION.valueOf(value).ordinal();
        }

        public static MENU_OPTION fromOrdinal(int value) {
            return MENU_OPTION.values()[value];
        }

        public static boolean isValid(String value) {
            try {
                MENU_OPTION.valueOf(value);
                return true;
            } catch (Exception e) {
                return false;
            }
        }

        public static boolean isValid(int value) {
            return value >= 0 && value < MENU_OPTION.values().length;
        }
    }

    public static void main(String[] args, QueryRunner runner) {
        int menuOption = -1;
        int connectionAttempts = 0;
        boolean notConnected = false;

        Scanner userIn = new Scanner(System.in);

        greeting();

        // Connect to the mySQL database
        while (!notConnected) {
            if (connectionAttempts > 0) {
                System.out.println(
                    "We need to establish a connection to the database, to do "
                );
                System.out.println(
                    "so we will need a few pieces of information from you."
                );
            } else {
                System.out.println(
                    "The previous connection attempt failed, please try again"
                );
            }

            notConnected = connectPrompt(userIn, runner);
            connectionAttempts++;
        }

        // Menu
        while (isContinue(menuOption)) {
            menuOption = menu(userIn, runner);

            if (menuOption == MENU_OPTION.query.ordinal()) {
                queryMenu(userIn, runner);
            } else if (menuOption == MENU_OPTION.help.ordinal()) {
                help();
            }
        }

        farewell();
    }

    public static boolean connectPrompt(Scanner userIn, QueryRunner runner) {
        String host, user, pass, database;

        System.out.print("Enter the hostname (localhost?): ");
        host = userIn.nextLine().trim();

        System.out.print("Enter the username (root, etc.): ");
        user = userIn.nextLine().trim();

        System.out.print("Enter the password: ");
        pass = userIn.nextLine().trim();

        System.out.print(
                "Enter the database you wish to use (mm_cpsc502101team05): "
        );
        database = userIn.nextLine().trim();

        runner.Connect(host, user, pass, database);

        if (!runner.GetError().isBlank()) {
            System.out.println("Error: " + runner.GetError());

            runner.ClearError();
            return false;
        }

        return true;
    }

    public static int menu(Scanner userIn, QueryRunner runner) {
        boolean isContinue = true;
        String chosenOption;
        int selectedOption = -1;

        while (isContinue) {
            printMenu();

            System.out.print("Please select an option (1,2,...): ");
            chosenOption = userIn.nextLine().trim();
            System.out.println();

            try {
                selectedOption = Integer.parseInt(chosenOption);

                if (selectedOption == MENU_OPTION.query.ordinal()) {
                    queryMenu(userIn, runner);
                } else if (selectedOption == MENU_OPTION.help.ordinal()) {
                    help();
                } else if (selectedOption == MENU_OPTION.quit.ordinal()) {
                    isContinue = false;
                }
            } catch (Exception e) {
                continue;
            }
        }

        return selectedOption;
    }

    public static boolean isContinue(int option) {
        return MENU_OPTION.quit.ordinal() != option;
    }

    public static void queryMenu(Scanner userIn, QueryRunner runner) {
        int selectedQuery = -1;
        printQueryList(runner);

        while (
            !(selectedQuery >= 0 && selectedQuery < runner.GetTotalQueries())
        ) {
            System.out.print("Select a query (0, 1...): ");
            String input = userIn.nextLine().trim();

            try {
                selectedQuery = Integer.parseInt(input);
                handleQuery(userIn, runner, selectedQuery);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                selectedQuery = -1;
            }
        }
    }

    public static void handleQuery(
            Scanner userIn,
            QueryRunner runner,
            int selectedQuery) {
        int paramCount = runner.GetParameterAmtForQuery(selectedQuery);
        String[] params = new String[paramCount];

        if (runner.isParameterQuery(selectedQuery)) {
            System.out.println("This query requires parameters to run.");

            for (int i = 0; i < paramCount; i++) {
                String paramText = runner.GetParamText(selectedQuery, i);

                System.out.print(paramText + ": ");
                params[i] = userIn.nextLine().trim();
            }
        }

        if (runner.isActionQuery(selectedQuery)) {
            executeUpdate(runner, selectedQuery, params);
        } else {
            executeQuery(runner, selectedQuery, params);
        }
    }

    public static void executeQuery(
            QueryRunner runner, int queryChoice, String[] params) {

        if (runner.ExecuteQuery(queryChoice, params)) {
            printTable(runner);
        }

    }

    public static void executeUpdate(
            QueryRunner runner, int queryChoice, String[] params) {

        if (runner.ExecuteUpdate(queryChoice, params)) {
            printTable(runner);
        }
    }

    public static void printMenu() {
        System.out.println("-".repeat(15));
        System.out.println("Menu");
        System.out.println("-".repeat(15));

        for (int i = 0; i < MENU_OPTION.values().length; i++) {
            System.out.println(i + ": " + MENU_OPTION.fromOrdinal(i));
        }

        System.out.println("-".repeat(15));
        System.out.println();
    }

    public static void printQueryList(QueryRunner runner) {
        System.out.println("-".repeat(15));
        System.out.println("Queries");
        System.out.println("-".repeat(15));

        for (int i = 0; i < runner.GetTotalQueries(); i++) {
            String queryDesc = runner.getQueryDescription(i);

            System.out.println(i + ": " + queryDesc);
        }

        System.out.println("-".repeat(15));
        System.out.println();
    }

    public static void printTable(QueryRunner runner) {
        ArrayList<Integer> querySize = new ArrayList<>();
        String[] headers = runner.GetQueryHeaders();
        String[][] data = runner.GetQueryData();

        // Get the max values of each field.
        for (String el : runner.GetQueryHeaders()) {
            querySize.add(el.length());
        }

        for (int row = 0; row < data.length; row++) {
            for (int col = 0; col < data[row].length; col++) {
                int currentSize = data[row][col].length();
                int storedSize = querySize.get(col);

                if (storedSize < currentSize) {
                    querySize.set(col, currentSize);
                }
            }
        }

        // Print header
        for (int i = 0; i < headers.length; i++) {
            System.out.printf("%"+querySize.get(i)+"s\t", headers[i]);
        }
        System.out.println();

        // Print body
        for (int row = 0; row < data.length; row++) {
            for (int col = 0; col < data[row].length; col++) {
                System.out.printf("%"+querySize.get(col)+"s\t", data[row][col]);
            }

            System.out.println();
        }
        System.out.println();
    }

    public static void greeting() {
        System.out.println("Welcome to the Query Console tool");
        System.out.println("This is a tool to connect to a database and use ");
        System.out.println("a series of commands to ");
    }

    public static void farewell() {
        System.out.println("Thank you for using the query console program!");
    }

    public static void help() {
        System.out.println("-".repeat(15));
        System.out.println("Help");
        System.out.println("-".repeat(15));
        System.out.println("\nThe query console program is designed to handle");
        System.out.println("preselected queries that have been added to the");
        System.out.println("ui.");
        System.out.println("");
        System.out.println("-".repeat(15));
        System.out.println();
    }
}
