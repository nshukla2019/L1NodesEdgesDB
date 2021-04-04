package edu.wpi.teamname.services.database;

import com.google.inject.Inject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DatabaseService {
  /*
   Database service class. This class will be loaded as a Singleton by Guice.
  */

  ArrayList<String[]> dbRows = new ArrayList<>();

  private final Connection connection;

  @Inject
  DatabaseService(Connection connection) {
    this.connection = connection;
  }


  int rows = 0;

  public void insertData(ArrayList<String[]> arrayList) {
    for (String[] line : arrayList) {
      try {
        Statement stmt = connection.createStatement();
        String query =
            "INSERT into L1Nodes(nodeID, xCoord, yCoord, floor, building, nodeType, longName, shortName)"
                + " VALUES("
                + "'"
                + line[0]
                + "',"
                + Integer.valueOf(line[1])
                + ","
                + Integer.valueOf(line[2])
                + ","
                + "'"
                + line[3]
                + "',"
                + "'"
                + line[4]
                + "',"
                + "'"
                + line[5]
                + "',"
                + "'"
                + line[6]
                + "',"
                + "'"
                + line[7]
                + "'"
                + ")";
        rows = stmt.executeUpdate(query);
        rows++;
      } catch (SQLException e) {
        log.error(e.getMessage());
      }
    }
    log.info("Rows Impacted: " + rows);
  }

  public void populateDB() {

    try {

      // creates a file with the file name we are looking to read
      File file =
          new File(
              "/Users/nupurshukla/Desktop/WPI/Computer Science/CS 3733/Project Maps and Data/L1Nodes.csv");
      // used to read data from a file
      FileReader fr = new FileReader(file);

      // used to read the text from a character-based input stream.
      BufferedReader br = new BufferedReader(fr);

      String line = "";
      String[] tempArr;

      // reads first line (this is the header of each file and we don't need it)
      br.readLine();

      // if there is something in the file (after line 1)
      while ((line = br.readLine()) != null) {

        // adds arguments into the array separated by the commas ("," is when it knows the next
        // index)
        tempArr = line.split(",");

        // each line is a row in our database
        dbRows.add(tempArr);
      }

      // closes the BufferedReader
      br.close();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }

    // we've read in the CSV file and now we want to update each column in the DB

    // this fnc is passed the array of string[] which contain all info we need
    // should go through the function and populate each row with correct info
    insertData(dbRows);
  }
}
