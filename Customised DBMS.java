/////////////////////////////////////////////////////////////////////////
//
//  Function Name : CustomisedDBMS
//  Description   : A Java-based customized mini DBMS application
//                  that supports basic database operations such as
//                  insertion, selection, deletion, update, and backup.
//  Input         : Accepts SQL-like user queries via console.
//  Output        : Displays and manipulates student data records.
//  Author        : Rukmini Gaikwad
//  Date          : 27/05/2025
//
///////////////////////////////////////////////////////////////////////////

import java.util.*;
import java.io.*;

///////////////////////////////////////////////////////////////////////////
//
// Class Name   : Student
// Description  : Represents an individual student record in the database.
//                Implements Serializable to support backup and restore.
//
///////////////////////////////////////////////////////////////////////////

class Student implements Serializable
 {
    public int StdID;
    public String StdName;
    public String StdCourse;
    public int StdMarks;
    public String StdCity;

    private static int Counter;

    static
    {
        Counter = 1;
    }

    ///////////////////////////////////////////////////////////////////////
    //
    // Function Name : Student (Constructor)
    // Description   : Initializes a new Student record with auto-incremented ID.
    // Input         : String name, String course, int marks, String city
    // Output        : Creates a new Student object
    //
    ///////////////////////////////////////////////////////////////////////
    
    public Student(String name, String course, int marks, String city) 
    {
        this.StdID = Counter++;
        this.StdName = name;
        this.StdCourse = course;
        this.StdMarks = marks;
        this.StdCity = city;
    }

    ///////////////////////////////////////////////////////////////////////
    //
    // Function Name : DisplayInformation
    // Description   : Displays the student information in formatted form.
    // Input         : None
    // Output        : Prints student details on console
    //
    ///////////////////////////////////////////////////////////////////////
    
    public void DisplayInformation() 
    {
        System.out.println("ID: " + this.StdID +
                           " | Name: " + this.StdName +
                           " | Course: " + this.StdCourse +
                           " | Marks: " + this.StdMarks +
                           " | City: " + this.StdCity);
    }

    ///////////////////////////////////////////////////////////////////////
    //
    // Function Name : toString
    // Description   : Converts the student data into a readable string.
    // Input         : None
    // Output        : Returns string representation of student record
    //
    ///////////////////////////////////////////////////////////////////////
    
    public String toString() 
    {
        return "ID: " + this.StdID +
               " | Name: " + this.StdName +
               " | Course: " + this.StdCourse +
               " | Marks: " + this.StdMarks +
               " | City: " + this.StdCity;
    }
}

///////////////////////////////////////////////////////////////////////////
//
// Class Name   : MarvellousDBMS
// Description  : Acts as a mini in-memory DBMS for managing student data.
//                Supports insertion, selection, deletion, update, and backup.
//
///////////////////////////////////////////////////////////////////////////

class MarvellousDBMS implements Serializable 
{
    private LinkedList<Student> Table;

    ///////////////////////////////////////////////////////////////////////
    //
    // Function Name : MarvellousDBMS (Constructor)
    // Description   : Initializes the custom DBMS with an empty table.
    // Input         : None
    // Output        : Creates a new MarvellousDBMS instance
    //
    ///////////////////////////////////////////////////////////////////////
    
    public MarvellousDBMS()
     {
        System.out.println("Marvellous DBMS Started Successfully...");
        Table = new LinkedList<>();
    }

    ///////////////////////////////////////////////////////////////////////
    //
    // Function Name : InsertIntoTable
    // Description   : Inserts a new student record into the table.
    // Input         : String name, String course, int marks, String city
    // Output        : Adds a new Student object into the Table list
    //
    ///////////////////////////////////////////////////////////////////////
    
    public void InsertIntoTable(String name, String course, int marks, String city)
     {
        Student sobj = new Student(name, course, marks, city);
        Table.add(sobj);
        System.out.println("New Record Inserted Successfully");
    }

    ///////////////////////////////////////////////////////////////////////
    //
    // Function Name : SelectStarFrom
    // Description   : Displays all student records from the table.
    // Input         : None
    // Output        : Prints all records on console
    //
    ///////////////////////////////////////////////////////////////////////
    
    public void SelectStarFrom()
     {
        System.out.println("----------------------------------------------------------");
        System.out.println("Data from the student table");
        System.out.println("----------------------------------------------------------");

        if (Table.isEmpty())
         {
            System.out.println("No records found.");
        } 
         else 
        {
            for (Student sref : Table)
            {
                System.out.println(sref);
            }
        }

        System.out.println("----------------------------------------------------------");
    }

    ///////////////////////////////////////////////////////////////////////
    //
    // Function Name : TakeBackup
    // Description   : Serializes the entire DBMS object for persistence.
    // Input         : None
    // Output        : Creates a binary file 'MarvellousDBMS.ser'
    //
    ///////////////////////////////////////////////////////////////////////
    
    public void TakeBackup() 
    {
        try (FileOutputStream fos = new FileOutputStream("MarvellousDBMS.ser");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) 
           {
            oos.writeObject(this);
            System.out.println("Backup created successfully.");
        } 
        catch (Exception e) 
        {
            System.out.println("Exception occurred while taking backup.");
        }
    }

    ///////////////////////////////////////////////////////////////////////
    //
    // Function Name : RestoreBackup
    // Description   : Deserializes a DBMS object from the backup file.
    // Input         : String path - file path of the backup file
    // Output        : Returns restored MarvellousDBMS object or null
    //
    ///////////////////////////////////////////////////////////////////////
    
    public static MarvellousDBMS RestoreBackup(String path) 
    {
        try (FileInputStream fis = new FileInputStream(path);
             ObjectInputStream ois = new ObjectInputStream(fis))
              {

            return (MarvellousDBMS) ois.readObject();
        }   
        catch (Exception e) 
        {
            System.out.println("Exception occurred while restoring backup.");
            return null;
        }
    }

    ///////////////////////////////////////////////////////////////////////
    //
    // Function Name : SelectSpecificID
    // Description   : Displays student details matching given ID.
    // Input         : int id
    // Output        : Prints matching record or 'not found' message
    //
    ///////////////////////////////////////////////////////////////////////
    
    public void SelectSpecificID(int id) 
    {
        boolean found = false;

        for (Student sref : Table) 
        {
            if (sref.StdID == id) 
            {
                found = true;
                System.out.println(sref);
                break;
            }
        }

        if (!found) 
        {
            System.out.println("Record not found.");
        }
    }

    ///////////////////////////////////////////////////////////////////////
    //
    // Function Name : SelectSpecificName
    // Description   : Displays student details matching given name.
    // Input         : String name
    // Output        : Prints matching record or 'not found' message
    //
    ///////////////////////////////////////////////////////////////////////
    
    public void SelectSpecificName(String name)
     {
        boolean found = false;

        for (Student sref : Table) 
        {
            if (name.equalsIgnoreCase(sref.StdName)) 
            {
                found = true;
                System.out.println(sref);
                break;
            }
        }

        if (!found) 
        {
            System.out.println("Record not found.");
        }
    }

    ///////////////////////////////////////////////////////////////////////
    //
    // Function Name : DeleteSpecificID
    // Description   : Deletes the record with given student ID.
    // Input         : int id
    // Output        : Removes record if found, else prints 'not found'
    //
    ///////////////////////////////////////////////////////////////////////
    
    public void DeleteSpecificID(int id) 
    {
        boolean found = false;
        int index = 0;

        for (Student sref : Table)
         {
            if (sref.StdID == id) 
            {
                found = true;
                break;
            }
            index++;
        }

        if (!found) 
        {
            System.out.println("Record not found.");
        } else 
        {
            Table.remove(index);
            System.out.println("Record deleted successfully.");
        }
    }

    ///////////////////////////////////////////////////////////////////////
    //
    // Function Name : CountStudent
    // Description   : Displays total number of student records.
    // Input         : None
    // Output        : Prints total student count
    //
    ///////////////////////////////////////////////////////////////////////
    
    public void CountStudent() 
    {
        System.out.println("Total Students: " + Table.size());
    }

    ///////////////////////////////////////////////////////////////////////
    //
    // Function Name : MaxMarks
    // Description   : Displays student having maximum marks.
    // Input         : None
    // Output        : Prints student with highest marks
    //
    ///////////////////////////////////////////////////////////////////////
    
    public void MaxMarks() 
    {
        if (Table.isEmpty()) 
        {
            System.out.println("No records available.");
            return;
        }

        Student topStudent = Collections.max(Table, Comparator.comparingInt(s -> s.StdMarks));
        System.out.println("Highest Marks: " + topStudent.StdMarks);
        System.out.println("Student: " + topStudent.StdName);
    }

    ///////////////////////////////////////////////////////////////////////
    //
    // Function Name : MinMarks
    // Description   : Displays student having minimum marks.
    // Input         : None
    // Output        : Prints student with lowest marks
    //
    ///////////////////////////////////////////////////////////////////////
    
    public void MinMarks()
     {
        if (Table.isEmpty()) 
        {
            System.out.println("No records available.");
            return;
        }

        Student lowStudent = Collections.min(Table, Comparator.comparingInt(s -> s.StdMarks));
        System.out.println("Lowest Marks: " + lowStudent.StdMarks);
        System.out.println("Student: " + lowStudent.StdName);
    }

    ///////////////////////////////////////////////////////////////////////
    //
    // Function Name : AverageMarks
    // Description   : Calculates and displays average marks of all students.
    // Input         : None
    // Output        : Prints average marks value
    //
    ///////////////////////////////////////////////////////////////////////
    
    public void AverageMarks() 
    {
        if (Table.isEmpty())
         {
            System.out.println("No records available.");
            return;
        }

        int total = 0;
        for (Student sref : Table) 
        {
            total += sref.StdMarks;
        }

        double avg = (double) total / Table.size();
        System.out.println("Class Average Marks: " + avg);
    }

    ///////////////////////////////////////////////////////////////////////
    //
    // Function Name : UpdateMarks
    // Description   : Updates the marks of a specific student ID.
    // Input         : int id, int newMarks
    // Output        : Updates student marks if ID found
    //
    ///////////////////////////////////////////////////////////////////////
    
    public void UpdateMarks(int id, int newMarks) 
    {
        boolean found = false;

        for (Student sref : Table) 
        {
            if (sref.StdID == id) 
            {
                sref.StdMarks = newMarks;
                found = true;
                System.out.println("Marks updated successfully for ID: " + id);
                break;
            }
        }

        if (!found)
        {
            System.out.println("Record not found.");
        }
    }
}

///////////////////////////////////////////////////////////////////////////
//
// Class Name   : CustomisedDBMS 
// Description  : Entry point of the Custom DBMS application. Provides
//                a console-based menu to perform all DBMS operations.
//
///////////////////////////////////////////////////////////////////////////

class CustomisedDBMS 
{
    public static void main(String[] args) throws Exception 
    {
        MarvellousDBMS mobj = MarvellousDBMS.RestoreBackup("MarvellousDBMS.ser");

        if (mobj == null) 
        {
            System.out.println("Unable to restore backup. Starting new DBMS...");
            mobj = new MarvellousDBMS();
        }

        Scanner sobj = new Scanner(System.in);
        int iOption = 0;

        System.out.println("-------------------------------------------------------------");
        System.out.println("-------------------- Marvellous DBMS ------------------------");
        System.out.println("-------------------------------------------------------------");

        while (iOption != 20) 
        {
            System.out.println("\nChoose an operation:");
            System.out.println("1  : Insert into student table");
            System.out.println("2  : Display all records");
            System.out.println("3  : Take backup");
            System.out.println("4  : Search by Student ID");
            System.out.println("5  : Search by Student Name");
            System.out.println("6  : Delete by Student ID");
            System.out.println("7  : Count total students");
            System.out.println("8  : Display highest marks");
            System.out.println("9  : Display lowest marks");
            System.out.println("10 : Display average marks");
            System.out.println("11 : Update marks");
            System.out.println("20 : Exit the DBMS");
            System.out.print("Enter your choice: ");

            iOption = sobj.nextInt();
            sobj.nextLine();

            switch (iOption)
             {
                case 1:
                    System.out.println("Enter Student Name: ");
                    String name = sobj.nextLine();
                    System.out.println("Enter Course: ");
                    String course = sobj.nextLine();
                    System.out.println("Enter Marks: ");
                    int marks = sobj.nextInt();
                    sobj.nextLine();
                    System.out.println("Enter City: ");
                    String city = sobj.nextLine();
                    mobj.InsertIntoTable(name, course, marks, city);
                    break;

                case 2:
                    mobj.SelectStarFrom();
                    break;

                case 3:
                    mobj.TakeBackup();
                    break;

                case 4:
                    System.out.print("Enter Student ID: ");
                    int id = sobj.nextInt();
                    mobj.SelectSpecificID(id);
                    break;

                case 5:
                    System.out.print("Enter Student Name: ");
                    String searchName = sobj.nextLine();
                    mobj.SelectSpecificName(searchName);
                    break;

                case 6:
                    System.out.print("Enter Student ID to delete: ");
                    int delID = sobj.nextInt();
                    mobj.DeleteSpecificID(delID);
                    break;

                case 7:
                    mobj.CountStudent();
                    break;

                case 8:
                    mobj.MaxMarks();
                    break;

                case 9:
                    mobj.MinMarks();
                    break;

                case 10:
                    mobj.AverageMarks();
                    break;

                case 11:
                    System.out.print("Enter Student ID to update marks: ");
                    int upID = sobj.nextInt();
                    System.out.print("Enter new marks: ");
                    int newMarks = sobj.nextInt();
                    mobj.UpdateMarks(upID, newMarks);
                    break;

                case 20:
                    System.out.println("Thank you for using Marvellous DBMS!");
                    mobj.TakeBackup();
                    mobj = null;
                    System.gc();
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
