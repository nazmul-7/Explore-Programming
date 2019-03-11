package com.example.triangle3soft.exploreprogramming.helperClass;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Nazmul.7 on 1/17/2018.
 */

public class Database extends SQLiteOpenHelper {
    public final static String db_name     ="Login.db";


    public final static String profile  ="profile";
    public final static String pcol_1  ="id";
    public final static String pcol_2 = "username";
    public final static String pcol_3 = "password";
    public final static String pcol_4 = "status";

    public final static String score  ="score";
    public final static String scol_1 = "id";
    public final static String scol_2 = "course";
    public final static String scol_3 = "module";
    public final static String scol_4 = "chapter";
    public final static String scol_5 = "status";
    public final static String scol_6 = "valid";



    public final static String content ="content";
    public final static String ccol_2 = "course";
    public final static String ccol_3 = "module";
    public final static String ccol_4 = "chapter";
    public final static String ccol_5 = "header";
    public final static String ccol_6 = "content";

    public final static String question ="question";
    public final static String qcol_2 = "course";
    public final static String qcol_3 = "module";
    public final static String qcol_4 = "chapter";
    public final static String qcol_5 = "serial";
    public final static String qcol_6 = "question";
    public final static String qcol_7 = "ans1";
    public final static String qcol_8 = "ans2";
    public final static String qcol_9 = "ans3";
    public final static String qcol_10 = "rightanswer";

    Context context;


    public Database(Context context) {

        super(context, db_name, null, 1);

        this.context = context;


    }
    // SELECT count(*) FROM "+db_name+" WHERE type='table' AND name='"+content+"' status Boolean DEFAULT false

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL("CREATE TABLE IF NOT EXISTS " + profile + "(id INTEGER PRIMARY KEY AUTOINCREMENT,username TEXT ,password TEXT ,status Boolean)");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + score + "(id INTEGER ,course INTEGER ,module INTEGER ,chapter INTEGER,status boolean, valid boolean)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("Drop table if exists " + db_name);
        onCreate(db);


    }

    public boolean insert_data(String username, String password, String skill) {
        SQLiteDatabase db = this.getWritableDatabase();

        boolean flag;


        ContentValues new_content = new ContentValues();
        new_content.put(pcol_2, username);
        new_content.put(pcol_3, password);
        if (skill.equals("Beginner")) {
            new_content.put(pcol_4, false);
            flag = false;

        } else {
            new_content.put(pcol_4, true);
            flag = true;
        }
        long restult = db.insert(profile, null, new_content);
        int id = getid(username,password);

        storescorecontent(id,flag);

        if (restult == -1) {
            return false;
        } else return true;


    }



    private int getid(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select "+pcol_1+" from " + profile + " where username = '" + username + "' and password ='" + password + "'", null);

        int i = 0;
        while (res.moveToNext()) {

            i = res.getInt(0);
        }
        return i;
    }

    public void updateScore(int id ,int course_id, int module_id, int chapter_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("update "+score+" set status  = 1 ,valid  = 1 where id = "+id+" and course = "+course_id+" and module = "+module_id+" and chapter = "+chapter_id);
        Log.i("SQL","update "+score+" set status  = 1 ,valid  = 1 where id = "+id+" and course = "+course_id+" and module = "+module_id+" and chapter = "+chapter_id);
    }

    public Cursor get_username() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select username from " + profile, null);
        return res;

    }

    public Cursor validate_username(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from " + profile + " where username = '" + username + "' and password ='" + password + "'", null);

        return res;

    }

    public Cursor fetch_content(int course, int module, int chapter) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT content  FROM content where course = " + course + " and module = " + module + " and chapter =" + chapter;

        Cursor res = db.rawQuery(query, null);

        return res;

    }

    public Cursor get_info(String name) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from "+profile+" where username = '"+name+"'", null);
        return  res;


    }

    public Cursor fetch_question(int course, int module, int chapter, int serial) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  question , ans1 ,ans2,ans3,rightanswer FROM question where course = " + course + " and module = " + module + " and chapter =" + chapter + " and serial = " + serial;

        Cursor res = db.rawQuery(query, null);

        return res;

    }

    public Cursor tableExists(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor resultSet = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='" + tableName + "';", null);
        return resultSet;
    }

    public void store_question() {

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("CREATE TABLE IF NOT EXISTS " + question + "(question_id INTEGER PRIMARY KEY AUTOINCREMENT,course INTEGER ,module INTEGER ,chapter INTEGER, serial INTEGER, question TEXT, ans1 TEXT,ans2 TEXT,ans3 TEXT,rightanswer TEXT,status Boolean DEFAULT 'false')");

        ContentValues new_content = new ContentValues();

        new_content.put(qcol_2, 1);
        new_content.put(qcol_3, 1);
        new_content.put(qcol_4, 1);
        new_content.put(qcol_5, 1);
        new_content.put(qcol_6, "To distribute your application to different platforms, how many Java versions do you need to create?\n");
        new_content.put(qcol_7, "One for each platform");
        new_content.put(qcol_8, "Two versions");
        new_content.put(qcol_9, "Just one version");
        new_content.put(qcol_10, "Just one version");
        db.insert(question, null, new_content);

        new_content.put(qcol_2, 1);
        new_content.put(qcol_3, 1);
        new_content.put(qcol_4, 1);
        new_content.put(qcol_5, 2);
        new_content.put(qcol_6, "Which one of the following statements is true?");
        new_content.put(qcol_7, "Java is used only in NASA's space related applications");
        new_content.put(qcol_8, "Java has a huge developer community");
        new_content.put(qcol_9, "Java is used only in web and mobile applications");
        new_content.put(qcol_10, "Java has a huge developer community");

        db.insert(question, null, new_content);

        new_content.put(qcol_2, 1);
        new_content.put(qcol_3, 1);
        new_content.put(qcol_4, 2);
        new_content.put(qcol_5,1);
        new_content.put(qcol_6, "Which method prints text in a Java program?");
        new_content.put(qcol_7, "System.out.println()");
        new_content.put(qcol_8, "out.writeText()");
        new_content.put(qcol_9, "System.printText()");
        new_content.put(qcol_10,"System.out.println()");

        db.insert(question, null, new_content);

        new_content.put(qcol_2, 1);
        new_content.put(qcol_3, 1);
        new_content.put(qcol_4, 2);
        new_content.put(qcol_5, 2);
        new_content.put(qcol_6, "Which line declare a method called \"hello()\".");
        new_content.put(qcol_7, "void hello(String s)");
        new_content.put(qcol_8, "int hello()");
        new_content.put(qcol_9, "void hello()");
        new_content.put(qcol_10,"void hello()");

        db.insert(question, null, new_content);

        new_content.put(qcol_2, 1);
        new_content.put(qcol_3, 1);
        new_content.put(qcol_4, 3);
        new_content.put(qcol_5, 1);
        new_content.put(qcol_6, "Single-line comments are created using:");
        new_content.put(qcol_7, "** characters at the beginning of the line");
        new_content.put(qcol_8, "// characters at the beginning of the line");
        new_content.put(qcol_9, "*/ characters at the beginning of the line");
        new_content.put(qcol_10,"// characters at the beginning of the line");
        db.insert(question, null, new_content);


        new_content.put(qcol_2, 1);
        new_content.put(qcol_3, 1);
        new_content.put(qcol_4, 3);
        new_content.put(qcol_5,2);
        new_content.put(qcol_6, "You can add a Java doc style comment by using:");
        new_content.put(qcol_7, "/* and */ characters to wrap a comment.");
        new_content.put(qcol_8, "// characters in the beginning of the line.");
        new_content.put(qcol_9, "/** and */ characters to wrap a comment.");
        new_content.put(qcol_10,"/** and */ characters to wrap a comment.");
        db.insert(question, null, new_content);

        new_content.put(qcol_2, 1);
        new_content.put(qcol_3, 1);
        new_content.put(qcol_4, 4);
        new_content.put(qcol_5, 1);
        new_content.put(qcol_6, "Which variable type would you use for a city name?");
        new_content.put(qcol_7, "int");
        new_content.put(qcol_8, "String");
        new_content.put(qcol_9, "double");
        new_content.put(qcol_10,"String");

        db.insert(question, null, new_content);

        new_content.put(qcol_2, 1);
        new_content.put(qcol_3, 1);
        new_content.put(qcol_4, 4);
        new_content.put(qcol_5, 2);
        new_content.put(qcol_6, "What will be the data type of the blank? \n\n class People {\n" +
                "  public static void main(String[ ]args) {\nString  name = \"John\";\n" +
                "    int  age = 24;\n" +
                "    _____  height = 189.87;\n" +
                "  }\n" +
                "}");
        new_content.put(qcol_7, "int");
        new_content.put(qcol_8, "boolean");
        new_content.put(qcol_9, "double");
        new_content.put(qcol_10,"double");

        db.insert(question, null, new_content);
        new_content.put(qcol_2, 1);
        new_content.put(qcol_3, 1);
        new_content.put(qcol_4, 5);
        new_content.put(qcol_5,1);
        new_content.put(qcol_6, "What value is stored in the result variable?\n" +
                "int x = 8, y = 5;\n" +
                "int result = x % y;");
        new_content.put(qcol_7, "8");
        new_content.put(qcol_8, "3");
        new_content.put(qcol_9, "5");
        new_content.put(qcol_10,"3");


        db.insert(question, null, new_content);

        new_content.put(qcol_2, 1);
        new_content.put(qcol_3, 1);
        new_content.put(qcol_4, 5);
        new_content.put(qcol_5, 2);
        new_content.put(qcol_6, "What is the result of the following code?\n" +
                "int x = 15; int y = 4;\n" +
                "int result = x / y;\n" +
                "System.out.println(result);");
        new_content.put(qcol_7, "3");
        new_content.put(qcol_8, "4");
        new_content.put(qcol_9, "2");
        new_content.put(qcol_10,"3");

        db.insert(question, null, new_content);
   /**************************************************************************************************************************************/

        new_content.put(qcol_2, 1);
        new_content.put(qcol_3, 1);
        new_content.put(qcol_4, 5);
        new_content.put(qcol_5,1);
        new_content.put(qcol_6, "What value is stored in the result variable?\n" +
                "int x = 8, y = 5;\n" +
                "int result = x % y;");
        new_content.put(qcol_7, "8");
        new_content.put(qcol_8, "3");
        new_content.put(qcol_9, "5");
        new_content.put(qcol_10,"3");


        db.insert(question, null, new_content);

        new_content.put(qcol_2, 1);
        new_content.put(qcol_3, 1);
        new_content.put(qcol_4, 5);
        new_content.put(qcol_5, 2);
        new_content.put(qcol_6, "What is the result of the following code?\n" +
                "int x = 15; int y = 4;\n" +
                "int result = x / y;\n" +
                "System.out.println(result);");
        new_content.put(qcol_7, "3");
        new_content.put(qcol_8, "4");
        new_content.put(qcol_9, "2");
        new_content.put(qcol_10,"3");

        db.insert(question, null, new_content);

        new_content.put(qcol_2, 1);
        new_content.put(qcol_3, 1);
        new_content.put(qcol_4, 6);
        new_content.put(qcol_5,1);
        new_content.put(qcol_6, "What value is stored in the result variable?\n" +
                "int x = 8, y = 5;\n" +
                "int result = x % y;");
        new_content.put(qcol_7, "8");
        new_content.put(qcol_8, "3");
        new_content.put(qcol_9, "5");
        new_content.put(qcol_10,"3");


        db.insert(question, null, new_content);

        new_content.put(qcol_2, 1);
        new_content.put(qcol_3, 1);
        new_content.put(qcol_4, 6);
        new_content.put(qcol_5, 2);
        new_content.put(qcol_6, "What is the result of the following code?\n" +
                "int x = 15; int y = 4;\n" +
                "int result = x / y;\n" +
                "System.out.println(result);");
        new_content.put(qcol_7, "3");
        new_content.put(qcol_8, "4");
        new_content.put(qcol_9, "2");
        new_content.put(qcol_10,"3");

        db.insert(question, null, new_content);

        new_content.put(qcol_2, 1);
        new_content.put(qcol_3, 1);
        new_content.put(qcol_4, 7);
        new_content.put(qcol_5,1);
        new_content.put(qcol_6, "What value is stored in the result variable?\n" +
                "int x = 8, y = 5;\n" +
                "int result = x % y;");
        new_content.put(qcol_7, "8");
        new_content.put(qcol_8, "3");
        new_content.put(qcol_9, "5");
        new_content.put(qcol_10,"3");


        db.insert(question, null, new_content);

        new_content.put(qcol_2, 1);
        new_content.put(qcol_3, 1);
        new_content.put(qcol_4, 7);
        new_content.put(qcol_5, 2);
        new_content.put(qcol_6, "What is the result of the following code?\n" +
                "int x = 15; int y = 4;\n" +
                "int result = x / y;\n" +
                "System.out.println(result);");
        new_content.put(qcol_7, "3");
        new_content.put(qcol_8, "4");
        new_content.put(qcol_9, "2");
        new_content.put(qcol_10,"3");

        db.insert(question, null, new_content);

        new_content.put(qcol_2, 1);
        new_content.put(qcol_3, 1);
        new_content.put(qcol_4, 8);
        new_content.put(qcol_5,1);
        new_content.put(qcol_6, "What value is stored in the result variable?\n" +
                "int x = 8, y = 5;\n" +
                "int result = x % y;");
        new_content.put(qcol_7, "8");
        new_content.put(qcol_8, "3");
        new_content.put(qcol_9, "5");
        new_content.put(qcol_10,"3");


        db.insert(question, null, new_content);

        new_content.put(qcol_2, 1);
        new_content.put(qcol_3, 1);
        new_content.put(qcol_4, 8);
        new_content.put(qcol_5, 2);
        new_content.put(qcol_6, "What is the result of the following code?\n" +
                "int x = 15; int y = 4;\n" +
                "int result = x / y;\n" +
                "System.out.println(result);");
        new_content.put(qcol_7, "3");
        new_content.put(qcol_8, "4");
        new_content.put(qcol_9, "2");
        new_content.put(qcol_10,"3");

        db.insert(question, null, new_content);

        new_content.put(qcol_2, 1);
        new_content.put(qcol_3, 2);
        new_content.put(qcol_4, 1);
        new_content.put(qcol_5,1);
        new_content.put(qcol_6, "What value is stored in the result variable?\n" +
                "int x = 8, y = 5;\n" +
                "int result = x % y;");
        new_content.put(qcol_7, "8");
        new_content.put(qcol_8, "3");
        new_content.put(qcol_9, "5");
        new_content.put(qcol_10,"3");


        db.insert(question, null, new_content);

        new_content.put(qcol_2, 1);
        new_content.put(qcol_3, 2);
        new_content.put(qcol_4, 1);
        new_content.put(qcol_5, 2);
        new_content.put(qcol_6, "What is the result of the following code?\n" +
                "int x = 15; int y = 4;\n" +
                "int result = x / y;\n" +
                "System.out.println(result);");
        new_content.put(qcol_7, "3");
        new_content.put(qcol_8, "4");
        new_content.put(qcol_9, "2");
        new_content.put(qcol_10,"3");

        db.insert(question, null, new_content);

        new_content.put(qcol_2, 1);
        new_content.put(qcol_3, 2);
        new_content.put(qcol_4, 2);
        new_content.put(qcol_5,1);
        new_content.put(qcol_6, "What value is stored in the result variable?\n" +
                "int x = 8, y = 5;\n" +
                "int result = x % y;");
        new_content.put(qcol_7, "8");
        new_content.put(qcol_8, "3");
        new_content.put(qcol_9, "5");
        new_content.put(qcol_10,"3");


        db.insert(question, null, new_content);

        new_content.put(qcol_2, 1);
        new_content.put(qcol_3, 2);
        new_content.put(qcol_4, 2);
        new_content.put(qcol_5, 2);
        new_content.put(qcol_6, "What is the result of the following code?\n" +
                "int x = 15; int y = 4;\n" +
                "int result = x / y;\n" +
                "System.out.println(result);");
        new_content.put(qcol_7, "3");
        new_content.put(qcol_8, "4");
        new_content.put(qcol_9, "2");
        new_content.put(qcol_10,"3");

        db.insert(question, null, new_content);


    }




    public boolean storedata() {

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("CREATE TABLE IF NOT EXISTS " + content + "(content_id INTEGER PRIMARY KEY AUTOINCREMENT,course INTEGER ,module INTEGER ,chapter INTEGER,header TEXT, content TEXT)");


        ContentValues new_content = new ContentValues();
        new_content.put(ccol_2, 1);
        new_content.put(ccol_3, 1);
        new_content.put(ccol_4, 1);
        new_content.put(ccol_5,"Introduction to Java");

        new_content.put(ccol_6, "Java is a high level, modern programming language designed in the early 1990s by Sun Microsystems, and currently owned by Oracle. \n" +
                "\n" +
                "Java is Platform Independent, which means that you only need to write the program once to be able to run it on a number of different platforms!\n" +
                "Java is portable, robust, and dynamic, with the ability to fit the needs of virtually any type of application.\n" +
                "Java guarantees that you'll be able to Write Once, Run Anywhere.\n" + "More than 3 billion devices run Java. \n" +
                "Java is used to develop apps for Google's Android OS, various Desktop Applications, such as media players, antivirus programs, Web Applications, Enterprise Applications (i.e. banking), and many more!\n" +
                "Learn, practice, and then join the huge community of Java developers around the world!");
        db.insert(content, null, new_content);
        new_content.put(ccol_2, 1);
        new_content.put(ccol_3, 1);
        new_content.put(ccol_4, 2);
        new_content.put(ccol_5, "A Hello World Program");
        new_content.put(ccol_6, "In Java, every line of code that can actually run needs to be inside a class.\n" +
                "In our example, we named the class MyClass. You will learn more about classes in the upcoming modules.\n" +
                "\n" +
                "In Java, each application has an entry point, or a starting point, which is a method called main. Along with main, the keywords public and static will also be explained later. \n" +
                "As a summary:\n" +
                "- Every program in Java must have a class.\n" +
                "- Every Java program starts from the main method.\n" + "To run our program, the main method must be identical to this signature:\n" +
                "public static void main(String[ ] args)\n" +
                "\n" +
                "- public: anyone can access it\n" +
                "- static: method can be run without creating an instance of the class containing the main method\n" +
                "- void: method doesn't return any value\n" +
                "- main: the name of the method\n" +
                "\n" +
                "For example, the following code declares a method called test, which does not return anything and has no parameters:\n" +
                "void test()\n" +
                "\n" +
                "The method's parameters are declared inside the parentheses that follow the name of the method. \n" +
                "For main, it's an array of strings called args. We will use it in our next lesson, so don't worry if you don't understand it all now.\n" + "System.out.println()\n" +
                "\n" +
                "Next is the body of the main method, enclosed in curly braces:\n" +
                "{\n" +
                "   System.out.println(\"Hello World!\");\n" +
                "}\n" +
                "\n" +
                "The println method prints a line of text to the screen.\n" +
                "The System class and its out stream are used to access the println method.\n" +
                "In classes, methods, and other flow-control structures code is always enclosed in curly braces { }.\n" + "Semicolons in Java\n" +
                "\n" +
                "You can pass a different text as the parameter to the println method to print it.\n" +
                "class MyClass {\n" +
                "  public static void main(String[ ] args) {\n" +
                "    System.out.println(\"I am learning Java\");\n" +
                "  }\n" +
                "}\n" +
                "Try It Yourself\n" +
                "\n" +
                "In Java, each code statement must end with a semicolon. \n" +
                "Remember: do not use semicolons after method and class declarations that follow with the body defined using the curly braces.");
        db.insert(content, null, new_content);
        new_content.put(ccol_2, 1);
        new_content.put(ccol_3, 1);
        new_content.put(ccol_4, 3);
        new_content.put(ccol_5, "Java Comments");
        new_content.put(ccol_6, "Comments\n" +
                "\n" +
                "The purpose of including comments in your code is to explain what the code is doing.\n" +
                "Java supports both single and multi-line comments. All characters that appear within a comment are ignored by the Java compiler.\n" +
                "\n" +
                "A single-line comment starts with two forward slashes and continues until it reaches the end of the line. \n" +
                "For example:\n" +
                "// this is a single-line comment\n" +
                "x = 5; // a single-line comment after code\n" +
                "\n" +
                "Adding comments as you write code is a good practice, because they provide clarification and understanding when you need to refer back to it, as well as for others who might need to read it.\n" + "Multi-Line Comments\n" +
                "\n" +
                "Java also supports comments that span multiple lines.\n" +
                "You start this type of comment with a forward slash followed by an asterisk, and end it with an asterisk followed by a forward slash. \n" +
                "For example:\n" +
                "/*  This is also a\n" +
                "    comment spanning\n" +
                "    multiple lines */\n" +
                "\n" +
                "Note that Java does not support nested multi-line comments. \n" +
                "However, you can nest single-line comments within multi-line comments.\n" +
                "/* This is a single-line comment:\n" +
                "\n" +
                "    // a single-line comment\n" +
                "\n" +
                " */ \n" + "Documentation Comments\n" +
                "\n" +
                "Documentation comments are special comments that have the appearance of multi-line comments, with the difference being that they generate external documentation of your source code. These begin with a forward slash followed by two asterisks, and end with an asterisk followed by a forward slash. \n" +
                "For example:\n" +
                "/** This is a documentation comment */\n" +
                "\n" +
                "/** This is also a\n" +
                "    documentation comment */\n" +
                "\n" +
                "Javadoc is a tool which comes with JDK and it is used for generating Java code documentation in HTML format from Java source code which has required documentation in a predefined format.\n" +
                "\n" +
                "When a documentation comment begins with more than two asterisks, Javadoc assumes that you want to create a \"box\" around the comment in the source code. It simply ignores the extra asterisks.\n" +
                "For example:\n" +
                "/**********************\n" +
                "\n" +
                "This is the start of a method\n" +
                "\n" +
                "***********************/\n" +
                "\n" +
                "This will retain just the text \"This is the start of a method\" for the documentation.");


        long restult = db.insert(content, null, new_content);


        new_content.put(ccol_2, 1);
        new_content.put(ccol_3, 1);
        new_content.put(ccol_4, 4);
        new_content.put(ccol_5, "Variables");
        new_content.put(ccol_6, "Variables store data for processing.\n" +
                "A variable is given a name (or identifier), such as area, age, height, and the like. The name uniquely identifies each variable, assigning a value to the variable and retrieving the value stored.\n" +
                "\n" +
                "Variables have types. Some examples:\n" +
                "- int: for integers (whole numbers) such as 123 and -456\n" +
                "- double: for floating-point or real numbers with optional decimal points and fractional parts in fixed or scientific notations, such as 3.1416, -55.66.\n" +
                "- String: for texts such as \"Hello\" or \"Good Morning!\". Text strings are enclosed within double quotes.You can declare a variable of a type and assign it a value. Example:\n" +
                "String name = \"David\";\nchar stands for character and holds a single character.\n" +
                "\n" +
                "Another type is the Boolean type, which has only two possible values: true and false.\n" +
                "This data type is used for simple flags that track true/false conditions.\n" +
                "For example:");
        db.insert(content, null, new_content);

        new_content.put(ccol_2, 1);
        new_content.put(ccol_3, 1);
        new_content.put(ccol_4, 5);
        new_content.put(ccol_5, "Primitive Operators");
        new_content.put(ccol_6, "Java provides a rich set of operators to use in manipulating variables. A value used on either side of an operator is called an operand. \n" +
                "For example, in the expression below, the numbers 6 and 3 are operands of the plus operator:\n" +
                "int x = 6 + 3;.\nJava arithmetic operators: \n" +
                "+ addition\n" +
                "- subtraction\n" +
                "* multiplication\n" +
                "/ division\n" +
                "% modulo.\nAddition\n" +
                "\n" +
                "The + operator adds together two values, such as two constants, a constant and a variable, or a variable and a variable.\nSubtraction\n" +
                "The - operator subtracts one value from another.\nMultiplication\n" +
                "\n" +
                "The * operator multiplies two values.\nDivision\n" +
                "The / operator divides one value by another.\nModulo\n" +
                "\n" +
                "The modulo (or remainder) math operation performs an integer division of one value by another, and returns the remainder of that division. \n" +
                "The operator for the modulo operation is the percentage (%) character.\n" +
                "Example:\n" +
                "int value = 23;\n" +
                "int res = value % 6; // res is 5");
        db.insert(content, null, new_content);

        new_content.put(ccol_2, 1);
        new_content.put(ccol_3, 1);
        new_content.put(ccol_4, 6);
        new_content.put(ccol_5, "Increment & Decrement");
        new_content.put(ccol_6, "Increment Operators\n" +
                "\n" +
                "An increment or decrement operator provides a more convenient and compact way to increase or decrease the value of a variable by one. \n" +
                "For example, the statement x=x+1; can be simplified to ++x; \n" +
                "Example:\n" +
                "int test = 5;\n" +
                "++test; // test is now 6.\nThe decrement operator (--) is used to decrease the value of a variable by one.\n" +
                "int test = 5;\n" +
                "--test; // test is now 4.\nPrefix & Postfix\n" +
                "\n" +
                "Two forms, prefix and postfix, may be used with both the increment and decrement operators.\n" +
                "With prefix form, the operator appears before the operand, while in postfix form, the operator appears after the operand. Below is an explanation of how the two forms work:\n" +
                "Prefix: Increments the variable's value and uses the new value in the expression. \n" +
                "Example:\n" +
                "int x = 34;\n" +
                "int y = ++x; // y is 35.\nThe value of x is first incremented to 35, and is then assigned to y, so the values of both x and y are now 35.\n" +
                "Postfix: The variable's value is first used in the expression and is then increased. \n" +
                "Example:\n" +
                "int x = 34;\n" +
                "int y = x++; // y is 34.\nAssignment Operators\n" +
                "\n" +
                "You are already familiar with the assignment operator (=), which assigns a value to a variable.\n" +
                "int value = 5;\n" +
                "\n" +
                "This assigned the value 5 to a variable called value of type int.\n" +
                "\n" +
                "Java provides a number of assignment operators to make it easier to write code.\n" +
                "Addition and assignment (+=):\n" +
                "int num1 = 4;\n" +
                "int num2 = 8;\n" +
                "num2 += num1; // num2 = num2 + num1;\n" +
                "\n" +
                "// num2 is 12 and num1 is 4.\nSubtraction and assignment (-=):\n" +
                "int num1 = 4;\n" +
                "int num2 = 8;\n" +
                "num2 -= num1; // num2 = num2 - num1;\n" +
                "\n" +
                "// num2 is 4 and num1 is 4.");
        db.insert(content, null, new_content);

        new_content.put(ccol_2, 1);
        new_content.put(ccol_3, 1);
        new_content.put(ccol_4, 7);
        new_content.put(ccol_5, "Strings");
        new_content.put(ccol_6, "Strings\n" +
                "\n" +
                "A String is an object that represents a sequence of characters.\n" +
                "For example, \"Hello\" is a string of 5 characters.\n" +
                "\n" +
                "For example:\n" +
                "String s = \"Explore Programming\";.\nString Concatenation\n" +
                "\n" +
                "The + (plus) operator between strings adds them together to make a new string. This process is called concatenation.\n" +
                "The resulted string is the first string put together with the second string.\n" +
                "For example:\n" +
                "String firstName, lastName;\n" +
                "firstName = \"David\";\n" +
                "lastName = \"Williams\";\n" +
                "\n" +
                "System.out.println(\"My name is \" + firstName +\" \"+lastName);\n" +
                "        \n" +
                "// Prints: My name is David William ");
        db.insert(content, null, new_content);

        new_content.put(ccol_2, 1);
        new_content.put(ccol_3, 1);
        new_content.put(ccol_4, 8);
        new_content.put(ccol_5, "Getting User Input");
        new_content.put(ccol_6, "Getting User Input\n" +
                "\n" +
                "While Java provides many different methods for getting user input, the Scanner object is the most common, and perhaps the easiest to implement. Import the Scanner class to use the Scanner object, as seen here:\n" +
                "import java.util.Scanner; \n" +
                "\n" +
                "In order to use the Scanner class, create an instance of the class by using the following syntax:\n" +
                "Scanner myVar = new Scanner(System.in);\n" +
                "\n" +
                "You can now read in different kinds of input data that the user enters. \n" +
                "Here are some methods that are available through the Scanner class:\n" +
                "Read a byte - nextByte()\n" +
                "Read a short - nextShort()\n" +
                "Read an int - nextInt()\n" +
                "Read a long - nextLong()\n" +
                "Read a float - nextFloat()\n" +
                "Read a double - nextDouble()\n" +
                "Read a boolean - nextBoolean()\n" +
                "Read a complete line - nextLine()\n" +
                "Read a word - next()\n" +
                "\n" +
                "Example of a program used to get user input:\n" +
                "import java.util.Scanner;\n" +
                "\n" +
                "class MyClass {\n" +
                "  public static void main(String[ ] args) {\n" +
                "    Scanner myVar = new Scanner(System.in);\n" +
                "    System.out.println(myVar.nextLine());        \n" +
                "  }\n" +
                "}");
        db.insert(content, null, new_content);


        if (restult == -1) {
            return false;
        } else return true;


    }


    private void storescorecontent(int id, boolean skill) {

        boolean b = false;



        adsconrecontent(id,1,1,1,true,b);adsconrecontent(id,1,1,2,skill,b);
        adsconrecontent(id,1,1,3,skill,b);adsconrecontent(id,1,1,4,skill,b);
        adsconrecontent(id,1,1,5,skill,b);adsconrecontent(id,1,1,6,skill,b);
        adsconrecontent(id,1,1,7,skill,b);adsconrecontent(id,1,1,8,skill,b);

        adsconrecontent(id,1,2,1,skill,b);adsconrecontent(id,1,2,2,skill,b);
        adsconrecontent(id,1,2,3,skill,b);adsconrecontent(id,1,2,4,skill,b);
        adsconrecontent(id,1,2,5,skill,b);adsconrecontent(id,1,2,6,skill,b);
        adsconrecontent(id,1,2,7,skill,b);adsconrecontent(id,1,2,8,skill,b);

        adsconrecontent(id,1,3,1,skill,b);adsconrecontent(id,1,3,2,skill,b);
        adsconrecontent(id,1,3,3,skill,b);adsconrecontent(id,1,3,4,skill,b);
        adsconrecontent(id,1,3,5,skill,b);adsconrecontent(id,1,3,6,skill,b);
        adsconrecontent(id,1,3,7,skill,b);adsconrecontent(id,1,3,8,skill,b);

        adsconrecontent(id,1,4,1,skill,b);adsconrecontent(id,1,4,2,skill,b);
        adsconrecontent(id,1,4,3,skill,b);adsconrecontent(id,1,4,4,skill,b);
        adsconrecontent(id,1,4,5,skill,b);adsconrecontent(id,1,4,6,skill,b);
        adsconrecontent(id,1,4,7,skill,b);adsconrecontent(id,1,4,8,skill,b);

        adsconrecontent(id,1,5,1,skill,b);adsconrecontent(id,1,5,2,skill,b);
        adsconrecontent(id,1,5,3,skill,b);adsconrecontent(id,1,5,4,skill,b);
        adsconrecontent(id,1,5,5,skill,b);adsconrecontent(id,1,5,6,skill,b);
        adsconrecontent(id,1,5,7,skill,b);adsconrecontent(id,1,5,8,skill,b);

        adsconrecontent(id,2,1,1,true,b);adsconrecontent(id,2,1,2,skill,b);
        adsconrecontent(id,2,1,3,skill,b);adsconrecontent(id,2,1,4,skill,b);
        adsconrecontent(id,2,1,5,skill,b);adsconrecontent(id,2,1,6,skill,b);
        adsconrecontent(id,2,1,7,skill,b);adsconrecontent(id,2,1,8,skill,b);

        adsconrecontent(id,2,2,1,skill,b);adsconrecontent(id,2,2,2,skill,b);
        adsconrecontent(id,2,2,3,skill,b);adsconrecontent(id,2,2,4,skill,b);
        adsconrecontent(id,2,2,5,skill,b);adsconrecontent(id,2,2,6,skill,b);
        adsconrecontent(id,2,2,7,skill,b);adsconrecontent(id,2,2,8,skill,b);

        adsconrecontent(id,2,3,1,skill,b);adsconrecontent(id,2,3,2,skill,b);
        adsconrecontent(id,2,3,3,skill,b);adsconrecontent(id,2,3,4,skill,b);
        adsconrecontent(id,2,3,5,skill,b);adsconrecontent(id,2,3,6,skill,b);
        adsconrecontent(id,2,3,7,skill,b);adsconrecontent(id,2,3,8,skill,b);

        adsconrecontent(id,2,4,1,skill,b);adsconrecontent(id,2,4,2,skill,b);
        adsconrecontent(id,2,4,3,skill,b);adsconrecontent(id,2,4,4,skill,b);
        adsconrecontent(id,2,4,5,skill,b);adsconrecontent(id,2,4,6,skill,b);
        adsconrecontent(id,2,4,7,skill,b);adsconrecontent(id,2,4,8,skill,b);

        adsconrecontent(id,2,5,1,skill,b);adsconrecontent(id,2,5,2,skill,b);
        adsconrecontent(id,2,5,3,skill,b);adsconrecontent(id,2,5,4,skill,b);
        adsconrecontent(id,2,5,5,skill,b);adsconrecontent(id,2,5,6,skill,b);
        adsconrecontent(id,2,5,7,skill,b);adsconrecontent(id,2,5,8,skill,b);

        adsconrecontent(id,3,1,1,true,b);adsconrecontent(id,3,1,2,skill,b);
        adsconrecontent(id,3,1,3,skill,b);adsconrecontent(id,3,1,4,skill,b);
        adsconrecontent(id,3,1,5,skill,b);adsconrecontent(id,3,1,6,skill,b);
        adsconrecontent(id,3,1,7,skill,b);adsconrecontent(id,3,1,8,skill,b);

        adsconrecontent(id,3,2,1,skill,b);adsconrecontent(id,3,2,2,skill,b);
        adsconrecontent(id,3,2,3,skill,b);adsconrecontent(id,3,2,4,skill,b);
        adsconrecontent(id,3,2,5,skill,b);adsconrecontent(id,3,2,6,skill,b);
        adsconrecontent(id,3,2,7,skill,b);adsconrecontent(id,3,2,8,skill,b);

        adsconrecontent(id,3,3,1,skill,b);adsconrecontent(id,3,3,2,skill,b);
        adsconrecontent(id,3,3,3,skill,b);adsconrecontent(id,3,3,4,skill,b);
        adsconrecontent(id,3,3,5,skill,b);adsconrecontent(id,3,3,6,skill,b);
        adsconrecontent(id,3,3,7,skill,b);adsconrecontent(id,3,3,8,skill,b);

        adsconrecontent(id,3,4,1,skill,b);adsconrecontent(id,3,4,2,skill,b);
        adsconrecontent(id,3,4,3,skill,b);adsconrecontent(id,3,4,4,skill,b);
        adsconrecontent(id,3,4,5,skill,b);adsconrecontent(id,3,4,6,skill,b);
        adsconrecontent(id,3,4,7,skill,b);adsconrecontent(id,3,4,8,skill,b);

        adsconrecontent(id,3,5,1,skill,b);adsconrecontent(id,3,5,2,skill,b);
        adsconrecontent(id,3,5,3,skill,b);adsconrecontent(id,3,5,4,skill,b);
        adsconrecontent(id,3,5,5,skill,b);adsconrecontent(id,3,5,6,skill,b);
        adsconrecontent(id,3,5,7,skill,b);adsconrecontent(id,3,5,8,skill,b);







        }

    private void adsconrecontent(int id, int i, int i1, int i2, boolean skill, boolean b) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues new_content = new ContentValues();

        new_content.put(scol_1,id);new_content.put(scol_2,i);new_content.put(scol_3,i1);
        new_content.put(scol_4,i2);new_content.put(scol_5,skill);new_content.put(scol_6,b);
        db.insert(score, null, new_content);
    }


    public Integer queryscore(int id, int course_id, int i1) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT COUNT(valid) FROM "+score+" WHERE valid= 1 and id = "+id+" and course = "+course_id+" and module = "+i1, null);



        int i = 0;
        while (res.moveToNext()) {

            i = res.getInt(0);
        }
        return i;

    }

    public Integer getstatus(int id, int course_id, int i1) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select status from "+score+"  where id = "+id+" and course = "+course_id+" and module = "+i1+" and chapter = 1", null);


        int i = 0;
        while (res.moveToNext()) {

            i = res.getInt(0);
        }
        return i;

    }

    public void updatestatus(int id, int course_id, int module_id) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("update "+score+" set status  = 1 where id = "+id+" and course = "+course_id+" and module = "+module_id+" and chapter = 1");



    }
}

