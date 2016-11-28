package org.lang.cyk.kernel;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.lang.cyk.inisialisasi.Variables;
import org.lang.cyk.strukturdata.DataCFG;
import org.lang.cyk.strukturdata.Pohon;
import org.lang.cyk.strukturdata.Tabel;

public class cykAlgorithm {
	
	static ArrayList<Character> getNonTerminal(String X) {
	    ArrayList<Character> hasil = new ArrayList<Character>();
	    for(int i=0; i<Variables.CFG.size(); i++) {
	        for(int j=0; j<Variables.CFG.get(i).countBeta(); j++) {
	            if(Variables.CFG.get(i).getBeta(j).equals(X)) {
	                hasil.add(Variables.CFG.get(i).getAlfa());
	            }
	        }
	    }
	    return hasil;
	}

	static ArrayList<Character> Konkat(ArrayList<Character> A, ArrayList<Character> B) {
	    ArrayList<Character> C = new ArrayList<Character>();
	    C=A;
	    int i=0;
	    while(i<B.size()) {
	        if(C.contains(B.get(i))==false) {
	            C.add(B.get(i));
	        }
	        i++;
	    }
	    return C;
	}

	static boolean isNULL(int idx) {
	    boolean res=false;
	    for(int i=0; i<Variables.CFG.get(idx).countBeta(); i++) {
	        if(Variables.CFG.get(idx).getBeta(i).charAt(0)=='#') {
	            res=true;
	            break;
	        }
	    }
	    return res;
	}

	static String delChar(String S, Character C) {
	    String res = "";
	    boolean cek = false;
	    for(int i=0; i<S.length(); i++) {
	        if(S.charAt(i)==C) {
	            if(cek==false) {
	                cek = true;
	            }
	            else {
	                res = res + Character.toString(S.charAt(i));
	            }
	        }
	        else {
	            res = res + Character.toString(S.charAt(i));
	        }
	    }
	    return res;
	}

	static void delBeta(Character R, String B) {
	    for(int i=0; i<Variables.CFG.size(); i++) {
	        if(Variables.CFG.get(i).getAlfa()==R) {
	            for(int j=0; j<Variables.CFG.get(i).countBeta(); j++) {
	                if(Variables.CFG.get(i).getBeta(j).equals(B)) {
	                	Variables.CFG.get(i).delBeta(j);
	                    break;
	                }
	            }
	            break;
	        }
	    }
	}

	static boolean checkNULL() {
	    boolean res=false;
	    for(int i=0; i<Variables.CFG.size(); i++) {
	        for(int j=0; j<Variables.CFG.get(i).countBeta(); j++) {
	            if(Variables.CFG.get(i).getBeta(j).charAt(0)=='#') {
	                res=true;
	                break;
	            }
	        }
	    }
	    return res;
	}

	public static void delNULL() {
	    while(checkNULL()) {
	        for(int i=Variables.CFG.size()-1; i>=0; i--) {
	            for(int j=0; j<Variables.CFG.get(i).countBeta(); j++) {
	                if(Variables.CFG.get(i).getBeta(j).equals("#")) {
	                    //PROSES GANTI BETA 
	                    for(int k=0; k<Variables.CFG.size(); k++) {
	                        for(int l=0; l<Variables.CFG.get(k).countBeta(); l++) {
	                            if(Variables.CFG.get(k).getBeta(l).length()==1) {
	                                if(Variables.CFG.get(k).getBeta(l).charAt(0)==Variables.CFG.get(i).getAlfa() && isNULL(k)==false) {
	                                    if(Variables.CFG.get(i).countBeta()==1) {
	                                    	Variables.CFG.get(k).delBeta(l);
	                                    }
	                                    else {
	                                    	Variables.CFG.get(k).addBeta("#");
	                                    }
	                                }
	                            }
	                            else {
	                                for(int m=0; m<Variables.CFG.get(k).getBeta(l).length(); m++) {
	                                    if(Variables.CFG.get(k).getBeta(l).charAt(m)==Variables.CFG.get(i).getAlfa()) {
	                                        String baru = delChar(Variables.CFG.get(k).getBeta(l), Variables.CFG.get(i).getAlfa());
	                                        Variables.CFG.get(k).addBeta(baru);
	                                        if(Variables.CFG.get(i).countBeta()==1) {
	                                        	Variables.CFG.get(k).delBeta(l);
	                                        }
	                                        break;
	                                    }
	                                }
	                            }
	                        }
	                    }
	                    //END OF PROSES GANTI BETA

	                    if(Variables.CFG.get(i).countBeta()==1) {
	                    	Variables.CFG.remove(i);
	                    	Variables.countCFG--;
	                        i--;
	                    }
	                    else {
	                        delBeta(Variables.CFG.get(i).getAlfa(), Variables.CFG.get(i).getBeta(j));
	                    }
	                }
	            }
	        }
	    }
	}

	static boolean checkUNIT() {
	    boolean res=false;
	    for(int i=0; i<Variables.CFG.size(); i++) {
	        for(int j=0; j<Variables.CFG.get(i).countBeta(); j++) {
	            if(Character.isUpperCase(Variables.CFG.get(i).getBeta(j).charAt(0)) && Variables.CFG.get(i).getBeta(j).length()==1) {
	                res=true;
	                break;
	            }
	        }
	    }
	    return res;
	}

	public static void delUNIT() {
	    while(checkUNIT()) {
	        for(int i=Variables.CFG.size()-1; i>=0; i--) {
	            for(int j=0; j<Variables.CFG.get(i).countBeta(); j++) {
	                if(Variables.CFG.get(i).getBeta(j).length()==1 && Character.isUpperCase(Variables.CFG.get(i).getBeta(j).charAt(0))) {
	                     if(Variables.CFG.get(i).getBeta(j).charAt(0)==Variables.CFG.get(i).getAlfa()) {
	                         delBeta(Variables.CFG.get(i).getAlfa(), Variables.CFG.get(i).getBeta(j));
	                         if(Variables.CFG.get(i).countBeta()==0) {
	                        	 Variables.CFG.remove(i);
	                        	 Variables.countCFG--;
	                             i--;
	                         }
	                         j--;
	                     }
	                     else {
	                         for(int k=0; k<Variables.CFG.size(); k++) {
	                             if(Variables.CFG.get(k).getAlfa()==Variables.CFG.get(i).getBeta(j).charAt(0)) {
	                                 delBeta(Variables.CFG.get(i).getAlfa(), Variables.CFG.get(i).getBeta(j));
	                                 for(int n=0; n<Variables.CFG.get(k).countBeta(); n++) {
	                                	 Variables.CFG.get(i).addBeta(Variables.CFG.get(k).getBeta(n));
	                                 }
	                             }
	                         }
	                         j--;
	                     }
	                }
	            }
	        }
	    }
	}

	static boolean isAlpha(Character A) {
	    boolean res=false;
	    for(int i=0; i<Variables.CFG.size(); i++) {
	        if(Variables.CFG.get(i).getAlfa()==A) {
	            res=true;
	            break;
	        }
	    }
	    return res;
	}

	static boolean gotoAlpha(Character A) {
	    boolean res=false;
	    for(int i=0; i<Variables.CFG.size(); i++) {
	        for(int j=0; j<Variables.CFG.get(i).countBeta(); j++) {
	            for(int k=0; k<Variables.CFG.get(i).getBeta(j).length(); k++) {
	                if(Character.isUpperCase(Variables.CFG.get(i).getBeta(j).charAt(k)) && Variables.CFG.get(i).getBeta(j).charAt(k)==A) {
	                    res=true;
	                    break;
	                }
	            }
	        }
	    }
	    return res;
	}

	static boolean isUSELESS() {
	    boolean res=false;
	    for(int i=0; i<Variables.CFG.size(); i++) {
	        if(gotoAlpha(Variables.CFG.get(i).getAlfa())==false && Variables.CFG.get(i).getAlfa()!='S') {
	            res=true;
	            break;
	        }
	        else {
	            for(int j=0; j<Variables.CFG.get(i).countBeta(); j++) {
	                for(int k=0; k<Variables.CFG.get(i).getBeta(j).length(); k++) {
	                    if(Character.isUpperCase(Variables.CFG.get(i).getBeta(j).charAt(k)) && isAlpha(Variables.CFG.get(i).getBeta(j).charAt(k))==false) {
	                        res=true;
	                        break;
	                    }
	                    else if(Character.isUpperCase(Variables.CFG.get(i).getBeta(j).charAt(k)) && isAlpha(Variables.CFG.get(i).getBeta(j).charAt(k)) && Variables.CFG.get(i).getBeta(j).charAt(k)==Variables.CFG.get(i).getAlfa() && Variables.CFG.get(i).getAlfa()!='S' && Variables.CFG.get(i).countBeta()==1) {
	                        res=true;
	                        break;
	                    }
	                }
	            }
	        }
	    }
	    return res;
	}

	public static void delUSELESS() {
	    while(isUSELESS()) {
	        for(int i=0; i<Variables.CFG.size(); i++) {
	            if(gotoAlpha(Variables.CFG.get(i).getAlfa())==false && Variables.CFG.get(i).getAlfa()!='S') {
	            	Variables.CFG.remove(i);
	            	Variables.countCFG--;
	                i--;
	            }
	            else {   
	                for(int j=0; j<Variables.CFG.get(i).countBeta(); j++) {
	                    for(int k=0; k<Variables.CFG.get(i).getBeta(j).length(); k++) {
	                        if(Character.isUpperCase(Variables.CFG.get(i).getBeta(j).charAt(k)) && isAlpha(Variables.CFG.get(i).getBeta(j).charAt(k))==false) {
	                            if(Variables.CFG.get(i).countBeta()==1) {
	                            	Variables.CFG.remove(i);
	                            	Variables.countCFG--;
	                                i--;
	                            }
	                            else {
	                                delBeta(Variables.CFG.get(i).getAlfa(), Variables.CFG.get(i).getBeta(j));
	                                j--;
	                            }
	                            break;
	                        }
	                        else if(Character.isUpperCase(Variables.CFG.get(i).getBeta(j).charAt(k)) && isAlpha(Variables.CFG.get(i).getBeta(j).charAt(k)) && Variables.CFG.get(i).getBeta(j).charAt(k)==Variables.CFG.get(i).getAlfa() && Variables.CFG.get(i).getAlfa()!='S' && Variables.CFG.get(i).countBeta()==1) {
	                        	Variables.CFG.remove(i);
	                        	Variables.countCFG--;
	                            i--;
	                            break;
	                        }
	                    }
	                }
	            }
	        }
	    }
	}

	static Character firstAvailabelAlpha() {
	    String A="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	    Character res=null;    
	    for(int i=0; i<A.length(); i++) {
	        int j;
	        for(j=0; j<Variables.CFG.size(); j++) {
	            if(A.charAt(i)==Variables.CFG.get(j).getAlfa()) {
	                break;
	            }
	        }
	        if(j==Variables.CFG.size()) {
	            res=A.charAt(i);
	            break;
	        }
	    }
	    return res;
	}

	static boolean isCFG(String b) {
	    boolean res=false;
	    for(int i=Variables.countCFG; i<Variables.CFG.size(); i++) {
	        if(Variables.CFG.get(i).countBeta()==1 && Variables.CFG.get(i).getBeta(0).equals(b)) {
	            res=true;
	            break;
	        }
	    }
	    return res;
	}

	static Character getAplhaCFG(String b) {
	    Character res = null;
	    for(int i=Variables.countCFG; i<Variables.CFG.size(); i++) {
	        if(Variables.CFG.get(i).countBeta()==1 && Variables.CFG.get(i).getBeta(0).equals(b)) {
	            res=Variables.CFG.get(i).getAlfa();
	            break;
	        }
	    }
	    return res;
	}

	public static void lastStep() {
	    for(int i=0; i<Variables.CFG.size(); i++) {
	        for(int j=0; j<Variables.CFG.get(i).countBeta(); j++) {
	            if(Variables.CFG.get(i).getBeta(j).length()>1) {
	                for(int k=0; k<Variables.CFG.get(i).getBeta(j).length(); k++) {
	                    if(Character.isUpperCase(Variables.CFG.get(i).getBeta(j).charAt(k))==false) {
	                        if(isCFG(Character.toString(Variables.CFG.get(i).getBeta(j).charAt(k)))) {
	                            Character W = getAplhaCFG(Character.toString(Variables.CFG.get(i).getBeta(j).charAt(k)));

	                            //GANTI BETA
	                            String baru;
	                            if(k==0) {
	                                baru = Character.toString(W) + Variables.CFG.get(i).getBeta(j).substring(k+1);
	                            }
	                            else if(k==Variables.CFG.get(i).getBeta(j).length()-1) {
	                                baru = Variables.CFG.get(i).getBeta(j).substring(0,k) + Character.toString(W);
	                            }
	                            else {
	                                baru = Variables.CFG.get(i).getBeta(j).substring(0,k) + Character.toString(W) + Variables.CFG.get(i).getBeta(j).substring(k+1);
	                            }

	                            Variables.CFG.get(i).changeBeta(j, baru);
	                        }
	                        else {
	                            DataCFG Ctemp = new DataCFG();
	                            Character Y = firstAvailabelAlpha();
	                            Ctemp.setAlfa(Y);
	                            Ctemp.addBeta(Character.toString(Variables.CFG.get(i).getBeta(j).charAt(k)));
	                            Variables.CFG.add(Ctemp);

	                            //GANTI BETA
	                            String baru;
	                            if(k==0) {
	                                baru = Character.toString(Y) + Variables.CFG.get(i).getBeta(j).substring(k+1);
	                            }
	                            else if(k==Variables.CFG.get(i).getBeta(j).length()-1) {
	                                baru = Variables.CFG.get(i).getBeta(j).substring(0,k) + Character.toString(Y);
	                            }
	                            else {
	                                baru = Variables.CFG.get(i).getBeta(j).substring(0,k) + Character.toString(Y) + Variables.CFG.get(i).getBeta(j).substring(k+1);
	                            }

	                            Variables.CFG.get(i).changeBeta(j, baru);
	                        }
	                    }
	                }

	                while(Variables.CFG.get(i).getBeta(j).length()>2) {
	                    for(int m=0; m<Variables.CFG.get(i).getBeta(j).length()-1; m+=2) {
	                        if(isCFG(Variables.CFG.get(i).getBeta(j).substring(m, m+2))) {
	                            Character X = getAplhaCFG(Variables.CFG.get(i).getBeta(j).substring(m, m+2));

	                            //GANTI BETA
	                            String baru;
	                            if(m==0) {
	                                baru = Character.toString(X) + Variables.CFG.get(i).getBeta(j).substring(m+2);
	                            }
	                            else if(m==Variables.CFG.get(i).getBeta(j).length()-2) {
	                                baru = Variables.CFG.get(i).getBeta(j).substring(0,m) + Character.toString(X);
	                            }
	                            else {
	                                baru = Variables.CFG.get(i).getBeta(j).substring(0,m) + Character.toString(X) + Variables.CFG.get(i).getBeta(j).substring(m+2);
	                            }

	                            Variables.CFG.get(i).changeBeta(j, baru);

	                            if(m==Variables.CFG.get(i).getBeta(j).length()-1) {
	                                break;
	                            }
	                        }
	                        else {
	                            DataCFG Ctmp = new DataCFG();
	                            Character Z = firstAvailabelAlpha();
	                            Ctmp.setAlfa(Z);
	                            Ctmp.addBeta(Variables.CFG.get(i).getBeta(j).substring(m, m+2));
	                            Variables.CFG.add(Ctmp);

	                            //GANTI BETA
	                            String baru;
	                            if(m==0) {
	                                baru = Character.toString(Z) + Variables.CFG.get(i).getBeta(j).substring(m+2);
	                            }
	                            else if(m==Variables.CFG.get(i).getBeta(j).length()-2) {
	                                baru = Variables.CFG.get(i).getBeta(j).substring(0,m) + Character.toString(Z);
	                            }
	                            else {
	                                baru = Variables.CFG.get(i).getBeta(j).substring(0,m) + Character.toString(Z) + Variables.CFG.get(i).getBeta(j).substring(m+2);
	                            }

	                            Variables.CFG.get(i).changeBeta(j, baru);

	                            if(m==Variables.CFG.get(i).getBeta(j).length()-1) {
	                                break;
	                            }
	                        }
	                    }
	                }
	            }
	        }
	    }
	}

	public static void parseCYK() {
	    int panjang = Variables.inputan.length();
	    Variables.CYKtable = new Tabel(panjang, panjang);
	    for(int j=1; j<=panjang; j++) {
	        String A = Character.toString(Variables.inputan.charAt(j-1)); 
	        Variables.CYKtable.setContent(getNonTerminal(A), j-1, j);
	        for(int i=j-2; i>=0; i--) {
	            for(int k=i+1; k<=j-1; k++) {
	                ArrayList<String> BC = new ArrayList<String>();
	                if(Variables.CYKtable.getContent(i,k).isEmpty()==false && Variables.CYKtable.getContent(k,j).isEmpty()) {
	                    int x=0;
	                    while(x<Variables.CYKtable.getContent(i,k).size()) {
	                        Character b = Variables.CYKtable.getIdxTable(i,k,x);
	                        String B = Character.toString(b);
	                        BC.add(B);
	                        x++;
	                    }
	                }
	                else if(Variables.CYKtable.getContent(i,k).isEmpty() && Variables.CYKtable.getContent(k,j).isEmpty()==false) {
	                    int y=0;
	                    while(y<Variables.CYKtable.getContent(k,j).size()) {
	                        Character c = Variables.CYKtable.getIdxTable(k,j,y);
	                        String C = Character.toString(c);
	                        BC.add(C);
	                        y++;
	                    }
	                }
	                else if(Variables.CYKtable.getContent(i,k).isEmpty()==false && Variables.CYKtable.getContent(k,j).isEmpty()==false) {   
	                    int x=0;
	                    while(x<Variables.CYKtable.getContent(i,k).size()) {
	                        Character b = Variables.CYKtable.getIdxTable(i,k,x);
	                        String B = Character.toString(b);
	                        int y=0;
	                        while(y<Variables.CYKtable.getContent(k,j).size()) {
	                            Character c = Variables.CYKtable.getIdxTable(k,j,y);
	                            String C = Character.toString(c);
	                            String bc = B + C;
	                            BC.add(bc);
	                            y++;
	                        }
	                        x++;
	                    }
	                }
	                
	                ArrayList<Character> A2 = new ArrayList<Character>();
	                int z=0;
	                while(z<BC.size()) {
	                    ArrayList<Character> tempA2 = new ArrayList<Character>();
	                    tempA2 = getNonTerminal(BC.get(z));
	                    A2 = Konkat(A2, tempA2);
	                    z++;
	                }
	                A2=Konkat(A2, Variables.CYKtable.getContent(i,j));
	                Variables.CYKtable.setContent(A2,i,j);
	            }
	        }
	    }
	       
	    if(Variables.CYKtable.getContent(0,panjang).contains('S')) {
	    	Variables.result = true;
	    }
	    else {
	    	Variables.result = false;
	    }
	    
	}

	//METHOD TREE
	static String listToString(ArrayList<Character> A) {
	    String res = "";
	    for(int i=0; i<A.size(); i++) {
	        res = res + A.get(i);
	    }
	    return res;
	}

	static boolean checkLHSRules(Character alfa, Character data) {
	    boolean res=false;
	    for(int i=0; i<Variables.CFG.size(); i++) {
	        if(Variables.CFG.get(i).getAlfa().equals(alfa)) {
	            for(int j=0; j<Variables.CFG.get(i).countBeta(); j++) {
	                if(Variables.CFG.get(i).getBeta(j).length()>1 && Variables.CFG.get(i).getBeta(j).charAt(0)==data) {
	                    res=true;
	                    break;
	                }
	            }
	            break;
	        }
	    }
	    return res;
	}

	static boolean checkRHSRules(Character alfa, Character data) {
	    boolean res=false;
	    for(int i=0; i<Variables.CFG.size(); i++) {
	        if(Variables.CFG.get(i).getAlfa().equals(alfa)) {
	            for(int j=0; j<Variables.CFG.get(i).countBeta(); j++) {
	                if(Variables.CFG.get(i).getBeta(j).length()>1 && Variables.CFG.get(i).getBeta(j).charAt(1)==data) {
	                    res=true;
	                    break;
	                }
	            }
	            break;
	        }
	    }
	    return res;
	}


	static boolean checkFlag(int brs, int kol) {
	    boolean res=false;
	    for(int a=0; a<Variables.row_temp.size(); a++) {
	        if(brs==Variables.row_temp.get(a) && kol==Variables.col_temp.get(a)) {
	            res=true;
	            break;
	        }
	    }
	    return res;
	}

	public static boolean checkRowFlag(Tabel Tabel, int baris, int kolom) {
	    boolean res=false;
	    for(int i=baris; i<=kolom-1; i++) {
	        if(checkFlag(i,kolom)==true) {
	            res=true;
	            break;
	        }
	    }
	    return res;
	}

	public static boolean checkColumnFlag(Tabel Tabel, int baris, int kolom) {
	    boolean res=false;
	    for(int i=kolom; i>=baris+1; i--) {
	        if(checkFlag(baris,i)==true) {
	            res=true;
	            break;
	        }
	    }
	    return res;
	}

	public static void replaceLeftTable(Tabel Tabel, int baris, int kolom) {
	    ArrayList<Character> data = Tabel.getContent(baris, kolom);
	    boolean cek1=false;
	    for(int i=kolom-1; i>0; i--) {
	        if(checkRowFlag(Tabel, baris, i) || checkColumnFlag(Tabel, baris, i)) {
	            ArrayList<Character> N = new ArrayList<Character>();
	            Tabel.setContent(N, baris, i);
	        }
	        else {
	            ArrayList<Integer> k2 = new ArrayList<Integer>();
	            for(int j=0; j<Tabel.getContent(baris,i).size(); j++) {
	                for(int x=0; x<data.size(); x++) {
	                    if(checkLHSRules(data.get(x), Tabel.getIdxTable(baris,i,j))==false) {
	                        if(k2.contains(j)==false) {
	                            k2.add(j);
	                        }
	                    }
	                    else {
	                        cek1=true;
	                    }
	                }
	            }

	            for(int y=0; y<data.size(); y++) {
	                for(int z=0; z<k2.size(); z++) {
	                    if(checkLHSRules(data.get(y), Tabel.getIdxTable(baris,i,k2.get(z)))) {
	                        k2.remove(z);
	                        z--;
	                    }
	                }
	            }

	            Pohon.sortArrayDesc(k2);
	            for(int w=0; w<k2.size(); w++) {
	                int del = k2.get(w);
	                Tabel.getContent(baris,i).remove(del);
	            }

	            if(cek1==true) {
	                break;
	            }
	        }
	    }
	}

	public static void replaceBottomTable(Tabel Tabel, int baris, int kolom) {
	    ArrayList<Character> data = Tabel.getContent(baris, kolom);
	    boolean cek1=false;
	    for(int i=baris+1; i<Tabel.getRows(); i++) {
	        if(checkRowFlag(Tabel, i, kolom) || checkColumnFlag(Tabel, i, kolom)) {
	            ArrayList<Character> N = new ArrayList<Character>();
	            Tabel.setContent(N, i, kolom);
	        }
	        else {            
	            ArrayList<Integer> k2 = new ArrayList<Integer>();
	            for(int j=0; j<Tabel.getContent(i,kolom).size(); j++) {
	                for(int x=0; x<data.size(); x++) {
	                    if(checkRHSRules(data.get(x), Tabel.getIdxTable(i,kolom,j))==false) {
	                        if(k2.contains(j)==false) {
	                            k2.add(j);
	                        }
	                    }
	                    else {
	                        cek1=true;
	                    }
	                }
	            }

	            for(int y=0; y<data.size(); y++) {
	                for(int z=0; z<k2.size(); z++) {
	                    if(checkRHSRules(data.get(y), Tabel.getIdxTable(i,kolom,k2.get(z)))) {
	                        k2.remove(z);
	                        z--;
	                    }
	                }
	            }

	            Pohon.sortArrayDesc(k2);
	            for(int w=0; w<k2.size(); w++) {
	                int del = k2.get(w);
	                Tabel.getContent(i,kolom).remove(del);
	            }

	            if(cek1==true) {
	                break;
	            }
	        }
	    }
	}
//	static ArrayList<String> leftmost = new ArrayList<String>();
//	static int id;
	public static void LeftMostDerivation(Pohon T) {
	    if(T == null) {
	        return;
	    }
	    else if(T.getLeft() != null && T.getRight() == null) {
	        String datainfo = listToString(T.getInfo());
	        String dataleft = listToString(T.getLeft().getInfo());
	        String lama = Variables.leftmost.get(Variables.id);
	        String baru = replaceString(lama, datainfo, dataleft);
	        Variables.leftmost.add(baru);
	        Variables.id++;
	    }
	    else {
	        String datainfo = listToString(T.getInfo());
	        String dataleft = listToString(T.getLeft().getInfo());
	        String dataright = listToString(T.getRight().getInfo());
	        String data = dataleft + dataright;
	        String lama = Variables.leftmost.get(Variables.id);
	        String baru = replaceString(lama, datainfo, data);
	        Variables.leftmost.add(baru);
	        Variables.id++;
	        LeftMostDerivation(T.getLeft());
	        LeftMostDerivation(T.getRight());
	    }
	}

	public static void readInputRules(String inFile) {
	    try{
	    	Variables.file=new File(inFile);
	    	FileInputStream fstream = new FileInputStream(Variables.file);
	        DataInputStream in = new DataInputStream(fstream);
	        BufferedReader br = new BufferedReader(new InputStreamReader(in));

	        String strLine;
	        int i = 0;
	        while ((strLine = br.readLine()) != null){
				//StringTokenizer st = new StringTokenizer(strLine, ".");
	           // System.out.println(strLine);
	        	Variables.temp[i] = strLine;
	            i++;
	        }
	        Variables.countCFG = i;
	        in.close();
	        
	    }catch (Exception e){
	        System.err.println("Error : " + e.getMessage());
	    }
	}

	public static void categorizedRules() {
		String sCFG=new String();
	    for(int i=0; i<Variables.countCFG; i++) {
	    	sCFG=sCFG+ Variables.temp[i].substring(0, Variables.temp[i].length()-1) + "\n";
	        DataCFG CFGtemp = new DataCFG();
	        CFGtemp.setAlfa(Variables.temp[i].charAt(0));
	        int start=5,end=start;
	        for(int j=start; j<Variables.temp[i].length(); j++) {
	            if(Variables.temp[i].charAt(j)==' ') {
	                if(start==end) {
	                    end++;
	                }
	                CFGtemp.addBeta(Variables.temp[i].substring(start, end));
	                j+=2;
	                start=j+1;
	                end=start;
	            }
	            else if(Variables.temp[i].charAt(j)=='.') {
	                if(start==end) {
	                    CFGtemp.addBeta(Variables.temp[i].substring(start, end+1));
	                }
	                else {
	                    CFGtemp.addBeta(Variables.temp[i].substring(start, end));
	                }
	                break;
	            }
	            else {
	                end++;
	            }
	        }
	        Variables.CFG.add(CFGtemp);
	    }
	}
	
	static String replaceString(String data, String lama, String baru) {
	    String res="";
	    if(data.length()==1 && data.equals(lama)) {
	        res = baru;
	    }
	    else {
	        for(int i=0; i<data.length(); i++) {
	            if(Character.toString(data.charAt(i)).equals(lama)) {
	                if(i==0) {
	                    res = baru + data.substring(i+1);
	                }
	                else if(i==data.length()-1) {
	                    res = data.substring(0,i) + baru;
	                }
	                else {
	                    res = data.substring(0,i) + baru + data.substring(i+1);
	                }
	                break;
	            }
	        }
	    }
	    return res;
	}

}
