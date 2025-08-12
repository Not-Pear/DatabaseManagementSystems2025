import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.BufferedReader;
import java.util.Scanner;  // Import the Scanner class

import java.io.FileReader;
import java.util.Arrays;
// instance variables:
// recordSize: the number of bytes in a record
// numRecords: the number of sorted records in the .data file
// dataFileptr: the fileptr for the opened data file
// openFlag: true if open, false otherwise
// any others you want, e.g., overflowFileptr, numOverflowRecords

public class DB {
  private int NUM_RECORDS = 15;

  //NOTE: this needs to be checked as I do not know the actual record size
  private int RECORD_SIZE = 52;

  // public int NUM_RECORDS;
  // public int RECORD_SIZE;

  //file pointer
  private RandomAccessFile Dinout;


  //NOTE: There is an extra variable called Industry. The original input.csv file had 5 fields while we only have 4. You can delete it. 
  private int num_records;
  public String Id;
  public String State;
  public String City;
  public String Name;
  private int IDLength = 8;
  private int StateLength =12;
  private int CityLength = 20;
  private int NameLength = 25;

  public DB() {
    this.Dinout = null;
    this.num_records = 0;
    this.Id = "ID";
    this.State = "STATE";
    this.City = "CITY";
    this.Name = "NAME";
  }

  /**
   * Opens the file in read/write mode
   * 
   * @param filename (e.g., input.data)
   * @return status true if operation successful
   */
  public void  open(String filename) {
    // Set the number of records
    String csvfile = filename+".csv";
    String datafile = filename+".data";
    String configfile = filename + ".config";
    try{
    RandomAccessFile config = new RandomAccessFile(configfile, "r");
    //properly set the sizes based on the config file
    String line = config.readLine();
    if(line != null){
      String[] nums = line.split(" ");
      NUM_RECORDS = Integer.parseInt(nums[0]);
      RECORD_SIZE = Integer.parseInt(nums[1]);
      }
    else{
      System.err.println("Youre line is null\n");
    }
  } catch(IOException e){
      System.err.println("Your config file failed to open");
    e.printStackTrace();
  }

    // Open file in read/write mode
    try {
      this.Dinout = new RandomAccessFile(datafile, "rw");
      System.out.println("Successfully opened " + datafile + "\n");
    } catch (FileNotFoundException e) {
      System.out.println("Could not open file\n");
      e.printStackTrace();
    }
  }

  //NOTE: Used the scanner class to read the file as we were already using it for the menu
  public String readCSV(String filename, int record) throws IOException {
    String nextData = "";
    String line;
    RandomAccessFile Din = new RandomAccessFile(filename+".csv", "r");
    // Skip lines until the desired record
    for (int i = 0; i < record; i++) {
        if ((line = Din.readLine()) == null || record < 0) {
          return "not in scope"; // Record out of range
        }
        // else{
        //   String[] data = line.split(",");
        //   System.out.println(Arrays.toString(data)); 
        // }
    }
    // Read the desired record
    nextData = Din.readLine();
    String[] data1 = nextData.split(",");
    System.out.println(Arrays.toString(data1)); 
    // while((line = Din.readLine()) != null){
    //   String[] data1 = line.split(",");
    //   System.out.println(Arrays.toString(data1)); 
    // }

    return nextData;
}

//         // NOTE: Use this for loop if you only want the record and comment out the while statement
//         // for(int i = 0; i < record - 1; i++) {
//         //     // use comma as separator
//         //   if(file.hasNextLine() == true && record > 0){
//         //     file.nextLine();
//         //   }
//         //   else{
//         //     return "Record Not in scope";
//         //   }
//         // }
//         // nextData = file.nextLine();


//         //This prints out the rest of the csv file
//         while (file.hasNext()) {
//             // use comma as separator
//             String nextData1 = file.nextLine();
//             String[] data = nextData1.split(",");
//             System.out.println(Arrays.toString(data));
//         }
        
//     } catch (IOException e) {
//         e.printStackTrace();
//     }
//     return nextData;
// }
  /** 
   * Writes the data to the location specified by file parameter
   *  
   */

  //NOTE: I converted most of this except the writeBytes so you could see the old variables. You might need to change the number values for the substring

  //Another NOTE: This works, however if you check the small-colleges data file, there isn't spaces like input.data something to iron out
  public void writeRecord(RandomAccessFile file, String Id, String State, String City, String Name) {
    	//format input values to be put in record
      this.Id = String.format("%-" + IDLength + "s", Id.length() > IDLength ? Id.substring(0, IDLength) : Id);
      this.State = String.format("%-" + StateLength + "s", State.length() > StateLength ? State.substring(0, StateLength) : State);
      this.City = String.format("%-" + CityLength + "s", City.length() > CityLength ? City.substring(0, CityLength) : City);
      this.Name = String.format("%-" + NameLength + "s", Name.length() > NameLength ? Name.substring(0, NameLength) : Name);
  

	try {
		file.writeBytes(this.Id +" "+  this.State +" "+  this.City + " "+ this.Name + "\n");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}  
}

  
  /**Takes in record number of record to be changed as well as what values to overwrite with 
   * 
   *  
   */ 
  public void overwriteRecord(int record_num, String Id, String Experience, String Married, String Wage, String Industry ) {
    if ((record_num >= 0) && (record_num < this.num_records)) {
      try {
        Dinout.seek(0); // return to the top of the file
        Dinout.skipBytes(record_num * RECORD_SIZE);
	//overwrite the specified record
        writeRecord (Dinout, Id, State, City, Name);
      } catch (IOException e) {
        System.out.println("There was an error while attempting to overwrite a record from the database file.\n");
        e.printStackTrace();
      }
    }
	System.out.println("Record Successfully Overwritten");
  }

  /** Opens CSV file and creates new data file
   * Reads CSV file attributes
   * @throws IOException 
   */

  //NOTE: converted this to support small colleges csv file
  public void createDB(String filename) throws IOException {
      String csvfile = filename+".csv";
      String datafile = filename+".data";
      String configfile = filename + ".config";
      File checkConfig = new File(configfile);
      File checkData = new File(datafile);
      if (checkConfig.exists()){
            checkConfig.delete();   
      }
      if (checkData.exists()){
          checkData.delete();
    }
      RandomAccessFile Din = new RandomAccessFile(csvfile, "r");
      RandomAccessFile Dout = new RandomAccessFile(datafile,"rw"); 
      RandomAccessFile config = new RandomAccessFile(configfile, "rw");

      String line;
      int numlines = 0;
      int counter = 0;
      
      while ((line = Din.readLine()) != null) {
          
          String[] attribute = readCSV(filename, numlines).split(",");
          numlines++;
          writeRecord (Dout, attribute[0], attribute[1], attribute[2], attribute[3]);
      }
      Dout.close();
      int newrecsize = 69; //all the feild values added up plus 4 for the 3 spaces and 1 newline (running in linux so newline is 1)
      Din.close();
      try{
      String cfile = numlines + " " + newrecsize;
      config.writeBytes(cfile);
  } catch (IOException e){
    e.printStackTrace();
  }
  config.close();
}

  /**
   * Close the database file
   */
  public void close() {
    try {
      Dinout.close();
    } catch (IOException e) {
      System.out.println("There was an error while attempting to close the database file.\n");
      e.printStackTrace();
    }
  }

  /**
   * Get record number n (Records numbered from 0 to NUM_RECORDS-1)
   * 
   * @param record_num
   * @return values of the fields with the name of the field and
   *         the values read from the record
   */
  public Record readRecord(int record_num) {
    Record record = new Record();
    String[] fields;
    if ((record_num >= 0) && (record_num < NUM_RECORDS -1)) {
      try {
        Dinout.seek(0); // return to the top of the file
        Dinout.skipBytes(record_num * RECORD_SIZE);
        // parse record and update fields
        fields = Dinout.readLine().split("\\s{2,}", 0);
        record.updateFields(fields);
      } catch (IOException e) {
        System.out.println("There was an error while attempting to read a record from the database file.\n");
        e.printStackTrace();
      }
    }
    else{
      System.out.println("You entered a number outside of the possible range of records");
    }
    return record;
  }

  /**
   * Binary Search by record id
   * 
   * @param id
   * @return Record number (which can then be used by read to
   *         get the fields) or -1 if id not found
   */
  public int binarySearch(String id) {
    int Low = 0;
    int High = NUM_RECORDS - 1;
    int Middle = 0;
    boolean Found = false;
    Record record;

    while (!Found && (High >= Low)) {
      Middle = (Low + High) / 2;
      record = readRecord(Middle);
      String MiddleId = record.Id;

      // int result = MiddleId[0].compareTo(id); // DOES STRING COMPARE
      int result = Integer.parseInt(MiddleId) - Integer.parseInt(id); // DOES INT COMPARE of MiddleId[0] and id
      if (result == 0)
        Found = true;
      else if (result < 0)
        Low = Middle + 1;
      else
        High = Middle - 1;
    }
    if (Found) {
      return Middle; // the record number of the record
    } else
      return -1;
  }
}
