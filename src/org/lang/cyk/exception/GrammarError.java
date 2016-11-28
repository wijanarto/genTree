/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lang.cyk.exception;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import org.lang.cyk.inisialisasi.Variables;
import org.lang.cyk.strukturdata.DataCFG;
import org.lang.cyk.strukturdata.Pohon;
import org.lang.cyk.strukturdata.Tabel;

/**
 *
 * @author toshiba
 */
public class GrammarError {
    private boolean status_error = false;
    private String message;
    
    public String getMessage() {
        return this.message;
    }
    
    void setMessage(String message) {
        this.message = message;
    }
    
    public boolean getStatusError() {
        return this.status_error;
    }
    
    void setStatusError(boolean status_error) {
        this.status_error = status_error;
    }
    
    public void isStringValid(String S) {
        for(int i=0; i<S.length(); i++) {
            if(Character.isLowerCase(S.charAt(i))==false) {
                if(S.charAt(i) == ' ') {
                    setStatusError(true);
                    setMessage("Invalid karakter spasi "+S.charAt(i)+" pada input string.");
                    break;
                }
                else if(Character.isUpperCase(S.charAt(i))) {
                    setStatusError(true);
                    setMessage("Invalid simbol non-terminal " + S.charAt(i) + " pada input string.");
                    break;
                }
                else {
                    setStatusError(true);
                    setMessage("Karakter simbol " + S.charAt(i) + " invalid.");
                    break;
                }
            }
        }
    }
    
    public void isRulesValid(String[] S, int size) {
        if(S[0].charAt(0) != 'S' ) {
            setStatusError(true);
            setMessage("Start symbol tidak ada");
        }
        else {
            for(int i=0; i<size; i++) {
                if(Character.isUpperCase(S[i].charAt(0))==false) {
                    setStatusError(true);
                    setMessage("left hand side karakter "+S[i].charAt(0)+"  bukan simbol non-terminal di baris " + (i+1));
                    break;
                }
                else if(S[i].charAt(1)!=' ' && S[i].charAt(1)!='-') {
                    setStatusError(true);
                    setMessage("left hand side karakter lebih dari satu simbol di baris " + (i+1));
                    break;
                }
                else if(S[i].charAt(1)!=' ' || S[i].charAt(4)!=' ') {
                    setStatusError(true);
                    setMessage("Himpunan simbol tidak ada spasi di baris " + (i+1));
                    break;
                }
                else if(S[i].charAt(S[i].length()-1)!='.') {
                    setStatusError(true);
                    setMessage("Tiap rule harus di akhir dengan simbol titik di baris " + (i+1));
                    break;
                }
                else {
                    boolean b2=false;
                    for(int j=5; j<S[i].length()-1; j++) {
                        if( Character.isLetterOrDigit(S[i].charAt(j))==false && Character.isDigit(S[i].charAt(j))==false && S[i].charAt(j)!='|' && S[i].charAt(j)!='#' && S[i].charAt(j)!=' ') {
                            b2=true;
                            setMessage("Terdapat simbol tidak dikenal dalam rule baris " + (i+1)+" : "+S[i].charAt(j));
                            break;
                        }
                        else if(S[i].charAt(j)=='|' && (S[i].charAt(j-1)!=' ' || S[i].charAt(j+1)!=' ')) {
                            b2=true;
                            setMessage("Tiap rule set simbol tidak boleh ada spasi pada baris " + (i+1)+" : "+S[i].charAt(j));
                            break;
                        }
                        else if(S[i].charAt(j)==' ' && (S[i].charAt(j-1)==' ' || S[i].charAt(j+1)==' ')) {
                            b2=true;
                            setMessage("Terlalu banyak spasi pada rule baris " + (i+1)+" : "+S[i].charAt(j));
                            break;
                        }
                        else if(S[i].charAt(j)==' ' && S[i].charAt(j+1)!='|' && S[i].charAt(j-1)!='|' && S[i].charAt(j+1)!=' ' && Character.isAlphabetic(S[i].charAt(j+1))) {
                            b2=true;
                            setMessage("Tiap rule set simbol tidak dipisah dengan tanda '|' pada baris " + (i+1)+" : "+S[i].charAt(j));
                            break;
                        }
                    }

                    if(b2==true) {
                        setStatusError(true);
                        break;
                    }
                }
            }
        }
    }
    
    //ERROR JIKA TIDAK ADA STRING DALAM RULES
    public void isStringCFG(ArrayList<DataCFG> CFG, String S) {
        for(int x=0; x<S.length(); x++) {
            boolean cek=false;
            for(int i=0; i<CFG.size(); i++) {
                for(int j=0; j<CFG.get(i).countBeta(); j++) {
                    for(int k=0; k<CFG.get(i).getBeta(j).length(); k++) {
                        if(S.charAt(x)==CFG.get(i).getBeta(j).charAt(k)) {
                            cek=true;
                            break;
                        }
                    }
                }
            }
            if(cek==false) {
                setStatusError(true);
                setMessage("rule " +"'"+ S.charAt(x)+"'"+" tidak dapat di produksi ");
                break;
            }
        }
    }
    
    ArrayList<Character> getNonTerminal(ArrayList<DataCFG> CFG, String X) {
        ArrayList<Character> hasil = new ArrayList<Character>();
        for(int i=0; i<CFG.size(); i++) {
            for(int j=0; j<CFG.get(i).countBeta(); j++) {
                if(CFG.get(i).getBeta(j).equals(X)) {
                    hasil.add(CFG.get(i).getAlfa());
                }
            }
        }
        return hasil;
    }
    
    String listToString(ArrayList<Character> A) {
        String res = "";
        for(int i=0; i<A.size(); i++) {
            res = res + A.get(i);
        }
        return res;
    }
    
    public File genErrorDot(ArrayList<DataCFG> CFG, String S, String dotFile) {
        //File GVCode = new File("C:\\tempcykparse\\GraphTree.dot");
        File GVCode = new File(dotFile.replace(".str",".dot"));//(genTree.optfilename1.replace(".str",".errdot"));
        //GVCode.deleteOnExit();
       // ArrayList<String> GVNode = new ArrayList<String>();
       // ArrayList<String> GVLabel = new ArrayList<String>();
       // ArrayList<String> GVRules = new ArrayList<String>();
        
        int a=0, b=a+1;
        for(int x=0; x<S.length(); x++) {
            Variables.GVNode.add("node" + a);
            Variables.GVLabel.add(Character.toString(S.charAt(x)));
            Variables.GVNode.add("node" + b);
            ArrayList<Character> tmp = getNonTerminal(CFG, Character.toString(S.charAt(x)));
            if(tmp.isEmpty()) {
            	Variables.GVLabel.add("errs");
            }
            else {
            	Variables.GVLabel.add(listToString(tmp));
            }
            Variables.GVRules.add(Variables.GVNode.get(b) + " -- " + Variables.GVNode.get(a));
            a+=2;
            b=a+1;
        }
         
        try {
            FileOutputStream fos = new FileOutputStream(GVCode);
            DataOutputStream dos = new DataOutputStream(fos);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(dos));
            bw.write("graph E {");
            bw.newLine();
            for(int i=0; i<Variables.GVNode.size(); i++) {
                String datanode;
                if(Variables.GVLabel.get(i).equals("errs")) {
                    datanode = Variables.GVNode.get(i) + "[label=" + Variables.GVLabel.get(i) + ", shape=box,style=rounded,height=0.02,width=0.01, color=red];";
                }
                else if(Character.isUpperCase(Variables.GVLabel.get(i).charAt(0))==false) {
                    datanode = Variables.GVNode.get(i) + "[label=" + Variables.GVLabel.get(i) + ", shape=box,style=rounded,height=0.02,width=0.01, color=blue];";
                }
                else {
                    datanode = Variables.GVNode.get(i) + "[label=" + Variables.GVLabel.get(i) + ", shape=box,style=rounded,height=0.02,width=0.01, color=black];";
                }
                bw.write(datanode);
                bw.newLine();
            }
            for(int i=0; i<Variables.GVRules.size(); i++) {
                String data = Variables.GVRules.get(i) + ";";
                bw.write(data);
                bw.newLine();
            }
            bw.write("}");
            bw.close();
            
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
        return GVCode;
    }
    
    //ERROR JIKA SUSUNAN STRING SALAH
    boolean checklhsRules(ArrayList<DataCFG> CFG, Character alfa, Character data) {
        boolean res=false;
        for(int i=0; i<CFG.size(); i++) {
            if(CFG.get(i).getAlfa().equals(alfa)) {
                for(int j=0; j<CFG.get(i).countBeta(); j++) {
                    if(CFG.get(i).getBeta(j).length()>1 && CFG.get(i).getBeta(j).charAt(0)==data) {
                        res=true;
                        break;
                    }
                }
                break;
            }
        }
        return res;
    }

    boolean checkrhsRules(ArrayList<DataCFG> CFG, Character alfa, Character data) {
        boolean res=false;
        for(int i=0; i<CFG.size(); i++) {
            if(CFG.get(i).getAlfa().equals(alfa)) {
                for(int j=0; j<CFG.get(i).countBeta(); j++) {
                    if(CFG.get(i).getBeta(j).length()>1 && CFG.get(i).getBeta(j).charAt(1)==data) {
                        res=true;
                        break;
                    }
                }
                break;
            }
        }
        return res;
    }

    void sortDesc(ArrayList<Integer> data) {
        int tmp;
        for(int i=0; i<data.size(); i++) {
            for(int j=0; j<data.size(); j++) {
                if(data.get(i)>data.get(j)) {
                    tmp=data.get(i);
                    data.set(i,data.get(j));
                    data.set(j,tmp);
                }
            }
        }
    }
    
    void replaceLTError(ArrayList<DataCFG> CFG, Tabel Tabel, int baris, int kolom) {
       ArrayList<Character> data = Tabel.getContent(baris, kolom);
        boolean cek1=false;
        for(int i=kolom-1; i>0; i--) {
            if(baris==i-1) {
                break;
            }
            else {
                ArrayList<Integer> k2 = new ArrayList<Integer>();
                for(int j=0; j<Tabel.getContent(baris,i).size(); j++) {
                    for(int x=0; x<data.size(); x++) {
                        if(checklhsRules(CFG, data.get(x), Tabel.getIdxTable(baris,i,j))==false) {
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
                        if(checklhsRules(CFG, data.get(y), Tabel.getIdxTable(baris,i,k2.get(z)))) {
                            k2.remove(z);
                            z--;
                        }
                    }
                }

                sortDesc(k2);
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

    void replaceBTError(ArrayList<DataCFG> CFG, Tabel Tabel, int baris, int kolom) {
        ArrayList<Character> data = Tabel.getContent(baris, kolom);
        boolean cek1=false;
        for(int i=baris+1; i<Tabel.getRows(); i++) {
            if(kolom==i+1) {
                break;
            }
            else {
                ArrayList<Integer> k2 = new ArrayList<Integer>();
                for(int j=0; j<Tabel.getContent(i,kolom).size(); j++) {
                    for(int x=0; x<data.size(); x++) {
                        if(checkrhsRules(CFG, data.get(x), Tabel.getIdxTable(i,kolom,j))==false) {
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
                        if(checkrhsRules(CFG, data.get(y), Tabel.getIdxTable(i,kolom,k2.get(z)))) {
                            k2.remove(z);
                            z--;
                        }
                    }
                }

                sortDesc(k2);
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

    Pohon createCTError(ArrayList<DataCFG> CFG, Tabel Tabel, int baris, int kolom) {
        Pohon T;
        ArrayList<Character> data = Tabel.getContent(baris, kolom);
        if(baris==Tabel.getRows()-1 || kolom==1) {
            if(data.size()>0) {
                T = new Pohon(data);
                return T;
            }
            else {
                return null;
            }
        }
        else {
            if(data.size()>0) {
                replaceLTError(CFG, Tabel, baris, kolom);
                replaceBTError(CFG, Tabel, baris, kolom);

                T = new Pohon(data);
                T.setLeft(createCTError(CFG, Tabel, baris, kolom-1));
                T.setRight(createRTError(CFG, Tabel, baris+1, kolom));
                return T;
            }
            else {
                return createCTError(CFG, Tabel, baris, kolom-1);
            }
        }
    }

    Pohon createRTError(ArrayList<DataCFG> CFG, Tabel Tabel, int baris, int kolom) {
        Pohon T;
        ArrayList<Character> data = Tabel.getContent(baris, kolom);
        if(baris==Tabel.getRows()-1 || kolom==1) {
            if(data.size()>0) {
                T = new Pohon(data);
                return T;
            }
            else {
                return null;
            }
        }
        else {
            if(data.size()>0) {
                replaceLTError(CFG, Tabel, baris, kolom);
                replaceBTError(CFG, Tabel, baris, kolom);

                T = new Pohon(data);
                T.setLeft(createCTError(CFG, Tabel, baris, kolom-1));
                T.setRight(createRTError(CFG, Tabel, baris+1, kolom));
                return T;
            }
            else {
                return createRTError(CFG, Tabel, baris+1, kolom);
            }
        }
    }

    Pohon createTError(ArrayList<DataCFG> CFG, Tabel Tabel, int baris, int kolom) {
        Pohon T;
        ArrayList<Character> data = new ArrayList<Character>();
        data.add('S');

        replaceLTError(CFG, Tabel, baris, kolom);
        replaceBTError(CFG, Tabel, baris, kolom);

        T = new Pohon(data);
        T.setLeft(createCTError(CFG, Tabel, baris, kolom-1));
        T.setRight(createRTError(CFG, Tabel, baris+1, kolom));
        return T;
    }
    
    public Pohon createTErrorInput(ArrayList<DataCFG> CFG, String inputan, Tabel tabel, int baris, int kolom) {
        Pohon Tree = createTError(CFG, tabel, 0, tabel.getColumns());
        return Tree;
    }
}
