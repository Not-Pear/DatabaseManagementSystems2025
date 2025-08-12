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
    ////// Skip lines until the desired record
    for (int i = 0; i < record; i++) {
        if ((line = Din.readLine()) == null || record < 0) {
          return "not in scope"; // Record out of range
        }
    }
    // Read the desired record
    nextData = Din.readLine();
    String[] data1 = nextData.split(",");
    System.out.println(Arrays.toString(data1)); 

    return nextData;
}



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
  public void overwriteRecord(int record_num, String id, String state, String city, String name) {
    if ((record_num >= 0) && (record_num < NUM_RECORDS)) {
      try {
        Dinout.seek(0); // return to the top of the file
        Dinout.skipBytes(record_num * RECORD_SIZE);
	//overwrite the specified record
        writeRecord (Dinout, id, state, city, name);
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
    File checkCSV = new File(csvfile);
    if(!checkCSV.exists()){
      System.out.println("That file does not exists!");
      return;
    }
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
System.out.println("Database object created.");
}

  public void updateRecord(String id, String feildnum, String newval){
   int num = binarySearch(id);
   if(num != -1){
    String[] fields;
    Record record = new Record();
    String input;
    
    
   try{   
      Dinout.seek(0); // return to the top of the file
      Dinout.skipBytes(num * RECORD_SIZE);
      // parse record and update fields
      fields = Dinout.readLine().split("\\s+", 0);
      record.updateFields(fields);
      if(Integer.parseInt(feildnum) == 1){
        record.setState(newval);
      }
      else if (Integer.parseInt(feildnum) == 2){
        record.setCity(newval);

      }
      else if(Integer.parseInt(feildnum) == 3){
        record.setName(newval);

      }
      
    } catch (IOException e) {
      System.out.println("There was an error while attempting to update a record from the database file.\n");
      e.printStackTrace();
    } 
   }
  }
  public void close() {
    try {
      Dinout.close();
    } catch (IOException e) {
      System.out.println("There was an error while attempting to close the database file.\n");
      e.printStackTrace();
    }
  }

    // // Skip lines until the desired record
    // for (int i = 0; i < record; i++) {
    //     if ((line = Din.readLine()) == null || record < 0) {
    //       return "not in scope"; // Record out of range
    //     }
    // }
    // // Read the desired record
    // nextData = Din.readLine();
    // String[] data1 = nextData.split(",");
    // System.out.println(Arrays.toString(data1)); 

    public void displayRecord(String recordNum)throws IOException
    {
      int recordSearch = binarySearch(recordNum);
      Record record = new Record();
      String[] fields;
      String tester;
      int count = 0;
      if (recordSearch == -1)
      {
        System.out.println("Record does not exist with that ID");
        return;
      }
      else
      {
  
        Dinout.seek(0); // return to the top of the file
  
        for(int i = 0; i < recordSearch; i++){
          //skipping unwated records
          Dinout.readLine();
        }
        // parse record and update fields
        fields = Dinout.readLine().split("\\s+", 0);
        // for(int i = 0; i < fields.length; i++){
        //   if (fields[i].)
        // }
        if(fields[1].equals("N/A") ||fields[2].equals("N/A")||fields[3].equals("N/A")){
          fields[1] = "";
          fields[2] = "";
          fields[3] = "";
        }
        //tester = Dinout.readLine();
        //System.out.println("Tester is + " + tester);
        record.updateFields(fields);
      }
      System.out.println(record.toString());
    }

  public boolean deleteRecord(String id){
   
    if(find(id) == true){
      overwriteRecord(binarySearch(id), id, "N/A", "N/A", "N/A");
      NUM_RECORDS--;
      return true;
    }
    else{ 
      return false;
    }
  }
  public boolean find(String id){
    int numba = binarySearch(id);
    if(numba != -1){
       Record test= new Record();
       test = readRecord(numba);
       if(test.getName().equals("") || test.getCity().equals("") || test.getState().equals("")){
          return false;
       }
       return true;
    }
    return false;
  }
  public void createReport() throws IOException{
    int count = 0;
    String[] fields;
    Record record = new Record(); //I cant lie i feel like this could be a memory leak if we don't delete it
    Dinout.seek(0); //return to the top of the file
    while (count != 10 && count != NUM_RECORDS)
    {
      fields = Dinout.readLine().split("\\s+", 0);
      if(fields[1].equals("N/A") || fields[2].equals("N/A") || fields[3].equals("N/A"))
      {
        //count remains the same if record is deleted
        count = count;
      }
      else
      {
        //fields = Dinout.readLine().split("\\s{2,}", 0);
        record.updateFields(fields);
        System.out.println(record.toString());
        count++;
      }

    }

  }
public void addRecord(){
  System.out.println("we did not in fact do the bonus");
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
        fields = Dinout.readLine().split("\\s+", 0);
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
      return Middle; // the record number of the record. added plus one since it starts at 0
    } else
      return -1;
  }
}
