package org.lang.cyk.inisialisasi;

import java.io.File;
import java.util.ArrayList;

import org.lang.cyk.strukturdata.DataCFG;
import org.lang.cyk.strukturdata.Pohon;
import org.lang.cyk.strukturdata.Tabel;

public class Variables {
	//Time management
	public static long tStart;
	public static long tEnd;
	public static long tDelta;
	public static double elapsedSeconds;// = tDelta / 1000.0; ;
	
	public static ArrayList<DataCFG> CFG = new ArrayList<DataCFG>();
	public static String inputan;
    public static String pwd = System.getProperty("user.dir");//working directory
    public static String optdir;
    public static File optionsDIR;
    public static File file; //*.cfg
    public static File str_input;
    public static File cfg_file;
    public static File GVCode;
    public static File out;
    public  static String cfg_loc = "";
    public static String GVCodeName="";
    public static String[] temp = new String[25];
    
    public static int countCFG;
    public int[] idxBeta;
    public  static boolean result;
    public static boolean cfg_mode = false;
    public static Tabel CYKtable;
    public static Pohon Tree = new Pohon();
    public static Pohon Tree2 = new Pohon();
    public static String preorder;
    public static String inorder;
    public static String postorder;
    public static int idx_file=0;
    static public String opt1,opt2,opt3,opt4,optinput1,optinput2,optfilename1,optfilename2;
    static public String treeTextFile,treeImgFile,LMDFile;
	
    public static ArrayList<String> GVNode = new ArrayList<String>();
    public static ArrayList<String> GVLabel = new ArrayList<String>();
    public static ArrayList<String> GVRules = new ArrayList<String>();
    public static ArrayList<Integer> row_temp = new ArrayList<Integer>();
    public static ArrayList<Integer> col_temp = new ArrayList<Integer>();
    public static boolean check=false;
    public static ArrayList<String> leftmost = new ArrayList<String>();
    public static int id;
	public static String[] help={
		"Usage: java -jar genTree.jar [<FILE Options>]", 
		"===================================================",
		"No file and blank options will print this help program ",
		"FILE: Reference to the file containing the notation ",
		"      of Chomsky Normal Form (*.cfg file),to be generated",
		"Options consists of:",
		"-h   Special case, print this help, java -jar genTree.jar -h\n", 
		"-i   <*.str>, string filename",                                                   
		"     This options follow *.cfg FILE, options -i followd by *.str file",
		"     as input file to test grammar *.cfg file",
		"     Eg. java -jar genTree.jar bla.cfg -i input.str\n",
		"-c   Will output both input *.cfg and *.str then tree form, followed",
		"     left most derivation and dot file to standard output terminal",
		"     or console, ",
		"     Eg.  java -jar genTree.jar bla.cfg -i bli.str -c\n",
		"-o   [*.txt | *.png | null]",      
		"     This options use with -i, where -o means output from test file *.str\n",
		"     -o  followed by *.txt if you want to write tree output file in text file",
		"         Eg. java -jar genTree.jar bla.cfg -i xxx.str -o output.txt\n",
		"     -o  followed by *.png if you want to write tree output file as image",
		"         Eg. java -jar genTree.jar bla.cfg -i xxx.str -o output.png\n",
		"     IF NO FILE SPECIFIED AFTER -o, THE SYSTEM WILL BE GENERATE BOTH *.txt,",
		"     AND *.png AS FOLLOW",
		"     Eg. java -jar genTree.jar bla.cfg -i xxx.str -o\n",
		"-ot  [*.txt | null]",      
		"     This option use with -i, where -ot means output from test file *.str\n",
		"     -ot follow by *.txt if you want to write tree output file in text",
		"         ex. java -jar genTree.jar bla.cfg -i xxx.str -ot output.txt\n",
		"     IF NO FILE SPECIFIED (null) AFTER -ot, THE SYSTEM WILL BE GENERATE",
		"     *.txt AS FOLLOW,",
		"     Eg. java -jar genTree.jar bla.cfg -i xxx.str -ot\n",
		"-oi  [*.png | null]",      
		"     This option use with -i, where -oi means output from test file *.str\n",
		"     -oi follow by *.png if you want to write tree output file in image",
		"         ex. java -jar genTree.jar bla.cfg -i xxx.str -oi output.png\n",
		"     IF NO FILE SPECIFIED (null) AFTER -oi, THE SYSTEM WILL BE GENERATE",
		"     *.png AS FOLLOW,",
		"     Eg. java -jar genTree.jar bla.cfg -i xxx.str -oi "
		};

}
