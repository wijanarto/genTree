package org.lang.cyk.consolesession;

import java.io.File;

import org.lang.cyk.inisialisasi.Variables;

public class ConsoleSession {

	public static boolean IsJava7Install(){
    	return (Integer.parseInt(System.getProperty("java.version").split("\\.")[1]) >= 7);
    }
	public static void printJava(){
    	if (IsJava7Install()) { 
    		System.out.println("JDK Installed : "+System.getProperty("java.home"));
    		System.out.println("JDK Version   : "+System.getProperty("java.version"));
    	}else{
    		System.out.println("Need JDK 1.7 above !");
    		System.out.println("Current Installed : ");
    		System.out.println("JDK Installed : "+System.getProperty("java.home"));
    		System.out.println("JDK Version   : "+System.getProperty("java.version"));
    	}
    }
	public static void printHelp(String[] h){
    	for (int i=0;i<h.length;i++){
    		System.out.println(h[i]);
    	}
    }
	public static void printUsage()
	{
    	printHelp(Variables.help);
	}
	public static void deleteDirOnExit(File dir)  
    {  
        // Rekursif == stack  
        dir.deleteOnExit(); //akan di kerjakan terakhir 
        File[] files = dir.listFiles();  
        if (files != null)  
        {  
            for (File f: files)  
            {  
                if (f.isDirectory())  
                {  
                    deleteDirOnExit(f);  
                }  
                else  
                {  
                    f.deleteOnExit();  
                }  
            }  
        }  
    } 
	public static boolean IsCNFfile(String filename){
		String ext=filename.substring(filename.length()-4, filename.length()).toLowerCase();
        if(ext.compareTo(".cnf")==0)
        	return true;
        else return false;
	}
	public static boolean IsCFGfile(String filename){
		String ext=filename.substring(filename.length()-4, filename.length()).toLowerCase();
        if(ext.compareTo(".cfg")==0)
        	return true;
        else return false;
	}
	public static boolean IsSTRfile(String filename){
		String ext=filename.substring(filename.length()-4, filename.length()).toLowerCase();
        if(ext.compareTo(".str")==0)
        	return true;
        else return false;
	}
	public static boolean IsTXTfile(String filename){
		String ext=filename.substring(filename.length()-4, filename.length()).toLowerCase();
        if(ext.compareTo(".txt")==0)
        	return true;
        else return false;
	}
	public static boolean IsPNGfile(String filename){
		String ext=filename.substring(filename.length()-4, filename.length()).toLowerCase();
        if(ext.compareTo(".png")==0)
        	return true;
        else return false;
	}
}
