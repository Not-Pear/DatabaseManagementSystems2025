import java.io.IOException;

public class Record {

  private boolean empty;

  public String Id;
  public String State;
  public String City;
  public String Name;
  private int IDLength = 8;
  private int StateLength =12;
  private int CityLength = 20;
  private int NameLength = 25;

  public Record() {
    empty = true;
  }

  /**
   * Update the fields of a record from an array of fields
   * 
   * @param fields array with values of fields
   * @return nothing
   * @throws IOException
   */
  public void updateFields(String[] fields) throws IOException {
    if (fields.length == 4) {
      this.Id = fields[0];
      this.State = fields[1];
      this.City = fields[2];
      this.Name = fields[3];
   

      empty = false;
    } else
      throw new IOException();
  }

  /**
   * Check if record fields have been updated
   * 
   * @return true if record is empty otherwise false
   */
  public boolean isEmpty() {
    return empty;
  }

  public String toString() {
    return "Id: " + this.Id +
        ", State: " + this.State +
        ", City: " + this.City +
        ", Name: " + this.Name;
  }

}
