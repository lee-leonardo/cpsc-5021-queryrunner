package queryrunner;

import java.util.Scanner;

public class QueryConsole {
    private QueryRunner runner;

    public static void main(String[] args, QueryRunner runner) {
        Scanner userIn = new Scanner(System.in);

        connectPrompt(userIn, runner);

        /* CLI tool to add all the gui elements,
         * then show menu
         * add new parameters
         * prompt for data, these data will be then used to the 'routine'
         * these routines are partially filled sql queries or stored procedures.
         *
         * Data structure and code will grab the parameters needed for the sql query
         * underneath.
         *
         */

        System.out.println("Nothing has been implemented yet. Please implement the necessary code");
        // TODO
        // You should code the following functionality:

        //    You need to determine if it is a parameter query. If it is, then
        //    you will need to ask the user to put in the values for the Parameters in your query
        //    you will then call ExecuteQuery or ExecuteUpdate (depending on whether it is an action query or regular query)
        //    if it is a regular query, you should then get the data by calling GetQueryData. You should then display this
        //    output.
        //    If it is an action query, you will tell how many row's were affected by it.
        //
        //    This is Psuedo Code for the task:
        //    Connect()
        //    n = GetTotalQueries()
        //    for (i=0;i < n; i++)
        //    {
        //       Is it a query that Has Parameters
        //       Then
        //           amt = find out how many parameters it has
        //           Create a paramter array of strings for that amount
        //           for (j=0; j< amt; j++)
        //              Get The Paramater Label for Query and print it to console. Ask the user to enter a value
        //              Take the value you got and put it into your parameter array
        //           If it is an Action Query then
        //              call ExecuteUpdate to run the Query
        //              call GetUpdateAmount to find out how many rows were affected, and print that value
        //           else
        //               call ExecuteQuery
        //               call GetQueryData to get the results back
        //               print out all the results
        //           end if
        //      }

        // NOTE - IF THERE ARE ANY ERRORS, please print the Error output
        // NOTE - The QueryRunner functions call the various JDBC Functions that are in QueryJDBC. If you would rather code JDBC
        // functions directly, you can choose to do that. It will be harder, but that is your option.
        // NOTE - You can look at the QueryRunner API calls that are in QueryFrame.java for assistance. You should not have to
        //    alter any code in QueryJDBC, QueryData, or QueryFrame to make this work.
//                System.out.println("Please write the non-gui functionality");

    }

    public static void connectPrompt(Scanner userIn, QueryRunner runner) {
        // TODO work on the cli tool that prompts and gets the user params.
        String host, user, pass, database;

        host = userIn.nextLine().trim();
        user = userIn.nextLine().trim();
        pass = userIn.nextLine().trim();
        database = userIn.nextLine().trim();

        runner.Connect(host, user, pass, database);
    }


}
