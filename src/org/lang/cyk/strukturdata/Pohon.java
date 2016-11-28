/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lang.cyk.strukturdata;

import java.util.ArrayList;

import org.lang.cyk.inisialisasi.Variables;
import org.lang.cyk.kernel.cykAlgorithm;


/**
 *
 * @author toshiba
 */
public class Pohon {
    Pohon Left;
    Pohon Right;
    ArrayList<Character> Info;
    int Idx_node;
    
    public Pohon(ArrayList<Character> newInfo) {
        this.Left = null;
        this.Right = null;
        this.Info = newInfo;
    }
    
    public Pohon() {
        
    }
    
    public ArrayList<Character> getInfo() {
        return this.Info;
    }
    
    public void setInfo(ArrayList<Character> Info) {
        this.Info = Info;
    }
    
    public Pohon getLeft() {
        return this.Left;
    }
    
    public void setLeft(Pohon Left) {
        this.Left = Left;
    }
    
    public Pohon getRight() {
        return this.Right;
    }
    
    public void setRight(Pohon Right) {
        this.Right = Right;
    }
    
    int countLeaf(Pohon T) {
        if(T==null) {
            return 0;
        }
        else {
            if(T.getLeft()==null && T.getRight()==null) {
                return 1;
            }
            else {
                return countLeaf(T.getLeft()) + countLeaf(T.getRight());
            }
        }
    }
    
    public static String ArrayListToString(ArrayList<Character> A) {
        String res = "";
        try {
            for(int i=0; i<A.size(); i++) {
                res = res + A.get(i);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    
    public String PreOrder(Pohon T) { 
        if (T == null) {
            return " ";
        } 
        else { 
            if(T.getLeft()==null && T.getRight()==null) { //leaf
                return ArrayListToString(T.getInfo());
            }
            else if(T.getLeft()!=null && T.getRight()==null) {
                return ArrayListToString(T.getInfo()) + "(" + PreOrder(T.getLeft())+")";
            }
            else if(T.getLeft()==null && T.getRight()!=null) {
                return ArrayListToString(T.getInfo()) + "(" + PreOrder(T.getRight())+")";
            }
            else {
                return "("+ArrayListToString(T.getInfo()) + " " + PreOrder(T.getLeft()) + " " + PreOrder(T.getRight())+")";
            }
        }
    }

    public String InOrder(Pohon T) { 
        if (T == null) {
            return "";
        } 
        else { 
            if(T.getLeft()==null && T.getRight()==null) {
                return ArrayListToString(T.getInfo());
            }
            else if(T.getLeft()!=null && T.getRight()==null) {
                 return InOrder(T.getLeft()) + "(" + ArrayListToString(T.getInfo())+")";
            }
            else if(T.getLeft()==null && T.getRight()!=null) {
                 return ArrayListToString(T.getInfo()) + "(" + InOrder(T.getRight())+")";
            }
            else {
                return "("+InOrder(T.getLeft()) + " " + ArrayListToString(T.getInfo()) + " " + InOrder(T.getRight())+")";
            }
        }
    }

    public String PostOrder(Pohon T) { 
        if (T == null) {
            return "";
        } 
        else { 
            if(T.getLeft()==null && T.getRight()==null) {
                return ArrayListToString(T.getInfo());
            }
            else if(T.getLeft()!=null && T.getRight()==null) {
                 return PostOrder(T.getLeft()) + "("  + ArrayListToString(T.getInfo())+")";
            }
            else if(T.getLeft()==null && T.getRight()!=null) {
                 return PostOrder(T.getRight()) + "("  + ArrayListToString(T.getInfo())+")";
            }
            else {
                return "("+PostOrder(T.getLeft()) + " " + PostOrder(T.getRight()) + " " + ArrayListToString(T.getInfo())+")";
            }
        }
    }
    
    public void setIdxNode(int Idx_node) {
        this.Idx_node = Idx_node;
    }
    
    public void replaceInfo(Pohon T) {
        if(T != null)  {
            ArrayList<Character> info = new ArrayList<Character>();
            String tmp = Integer.toString(Idx_node);
            info.add('n');
            info.add('o');
            info.add('d');
            info.add('e');
            for(int i=0; i<tmp.length(); i++) {
                info.add(tmp.charAt(i));
            }
            T.setInfo(info);
            Idx_node++;
            replaceInfo(T.getLeft());
            replaceInfo(T.getRight());
        }
    }
  //METHOD TREE

    static boolean checkLeftRules(Character alfa, Character data) {
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

    static boolean checkRightRules(Character alfa, Character data) {
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

    public static void sortArrayDesc(ArrayList<Integer> data) {
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
    static Pohon createColoumnTree(Tabel Tabel, int baris, int kolom) {
        Pohon T;
        ArrayList<Character> data = Tabel.getContent(baris, kolom);

        if(baris==kolom-1) {
            if(data.size()>0) {
            	Variables.col_temp.add(kolom);
            	Variables.row_temp.add(baris);
                T = new Pohon(data);
                return T;
            }
            else {
                return null;
            }
        }
        else {
            if(data.size()>0) {
                if(cykAlgorithm.checkRowFlag(Tabel, baris, kolom) || cykAlgorithm.checkColumnFlag(Tabel, baris, kolom)) {
                    ArrayList<Character> N = new ArrayList<Character>();
                    Tabel.setContent(N, baris, kolom);
                    return createColoumnTree(Tabel, baris, kolom-1);
                }
                else {
                    Variables.col_temp.add(kolom);
                    Variables.row_temp.add(baris);
                    
                    cykAlgorithm.replaceLeftTable(Tabel, baris, kolom);
                    cykAlgorithm.replaceBottomTable(Tabel, baris, kolom);

                    T = new Pohon(data);
                    T.setLeft(createColoumnTree(Tabel, baris, kolom-1));
                    T.setRight(createRowTree(Tabel, baris+1, kolom));
                    return T;
                }
            }
            else {
                return createColoumnTree(Tabel, baris, kolom-1);
            }
        }
    }

    static Pohon createRowTree(Tabel Tabel, int baris, int kolom) {
        Pohon T;
        ArrayList<Character> data = Tabel.getContent(baris, kolom);

        if(baris==kolom-1) {
            if(data.size()>0) {
            	Variables.col_temp.add(kolom);
            	Variables.row_temp.add(baris);
                T = new Pohon(data);
                return T;
            }
            else {
                return null;
            }
        }
        else {
            if(data.size()>0) {
                if(cykAlgorithm.checkRowFlag(Tabel, baris, kolom) || cykAlgorithm.checkColumnFlag(Tabel, baris, kolom)) {
                    ArrayList<Character> N = new ArrayList<Character>();
                    Tabel.setContent(N, baris, kolom);
                    return createRowTree(Tabel, baris+1, kolom);
                }
                else {
                	Variables.col_temp.add(kolom);
                	Variables.row_temp.add(baris);
                    
                	cykAlgorithm.replaceLeftTable(Tabel, baris, kolom);
                	cykAlgorithm.replaceBottomTable(Tabel, baris, kolom);

                    T = new Pohon(data);
                    T.setLeft(createColoumnTree(Tabel, baris, kolom-1));
                    T.setRight(createRowTree(Tabel, baris+1, kolom));
                    return T;
                }
            }
            else {
                return createRowTree(Tabel, baris+1, kolom);
            }
        }
    }

    public static Pohon createTree1(Tabel Tabel, int baris, int kolom) {
        Pohon T;
        ArrayList<Character> data = new ArrayList<Character>();
        data.add('S');
        Tabel.setContent(data, baris, kolom);
        
        cykAlgorithm.replaceLeftTable(Tabel, baris, kolom);
        cykAlgorithm.replaceBottomTable(Tabel, baris, kolom);
        
        T = new Pohon(data);
        T.setLeft(createColoumnTree(Tabel, baris, kolom-1));
        T.setRight(createRowTree(Tabel, baris+1, kolom));
        return T;
    }


    static void connectLeaf(Pohon T, ArrayList<Character> data) {
        if(T == null) {
            return;
        }
        else {
            if(T.getLeft()==null && T.getRight()==null && Character.isUpperCase(T.getInfo().get(0))) {
                if(Variables.check == true) {
                    return;
                }
                else {
                    Pohon baru = new Pohon(data);
                    T.setLeft(baru);
                    Variables.check = true;
                }
            }
            else {
                connectLeaf(T.getLeft(), data);
                connectLeaf(T.getRight(), data);
            }
        }
    }


    public static void createTree2(Pohon T, String X) {
        int NbLeaf = T.countLeaf(T);
        for(int i=0; i<NbLeaf; i++) {
            Variables.check=false;
            ArrayList<Character> baru = new ArrayList<Character>();
            if(i>=X.length()) {
                baru.add('n');
                baru.add('u');
                baru.add('l');
                baru.add('l');
            }
            else {
                baru.add(X.charAt(i));
            }
            connectLeaf(T, baru);
        }
    }
    
    public static void finalizedTree(Pohon T) {
        if(T == null) {
            return;
        }
        else {
            ArrayList<Character> data = T.getInfo();
            if(T.getLeft()!=null && T.getRight()!=null) {            
                for(int i=0; i<data.size(); i++) {
                    for(int j=0; j<T.getLeft().getInfo().size(); j++) {
                        if(checkLeftRules(data.get(i), T.getLeft().getInfo().get(j))==false) {
                            T.getLeft().getInfo().remove(j);
                            j--;
                        }
                    }
                }
                
                for(int i=0; i<data.size(); i++) {
                    for(int j=0; j<T.getRight().getInfo().size(); j++) {
                        if(checkRightRules(data.get(i), T.getRight().getInfo().get(j))==false) {
                            T.getRight().getInfo().remove(j);
                            j--;
                        }
                    }
                }
                
                finalizedTree(T.getLeft());
                finalizedTree(T.getRight());
           }
            else {
                return;
            }
        }
    }
    
    public static void CopyTree(Pohon T, Pohon TC) {
        if(T == null) {
            return;
        }
        else {
            if(T.getLeft()==null && T.getRight()==null) {
                return;
            }
            else if(T.getLeft()==null && T.getRight()!=null) {
                Pohon R = new Pohon(T.getRight().getInfo());
                TC.setLeft(R);
            }
            else if(T.getLeft()!=null && T.getRight()==null) {
                Pohon L = new Pohon(T.getLeft().getInfo());
                TC.setLeft(L);
            }
            else {
                ArrayList<Character> I = T.getInfo();
                Pohon L = new Pohon(T.getLeft().getInfo());
                Pohon R = new Pohon(T.getRight().getInfo());
                TC.setInfo(I);
                TC.setLeft(L);
                TC.setRight(R);
                CopyTree(T.getLeft(), TC.getLeft());
                CopyTree(T.getRight(), TC.getRight());
            }
        }
    }
    
    static void createGVNode(Pohon T1, Pohon T2) {
        if(T1 != null && T2 != null)  {
            String L = Pohon.ArrayListToString(T1.getInfo());
            String N = Pohon.ArrayListToString(T2.getInfo());
            Variables.GVNode.add(N);
            Variables.GVLabel.add(L);
            createGVNode(T1.getLeft(), T2.getLeft());
            createGVNode(T1.getRight(), T2.getRight());
        }
    }

    static void createGVRules(Pohon T) {
        if(T == null) {
            return;
        }
        else {
            if(T.getLeft()==null && T.getRight()==null) {
                return;
            }
            else if(T.getLeft()==null && T.getRight()!=null) {
                String I = Pohon.ArrayListToString(T.getInfo());
                String R = Pohon.ArrayListToString(T.getRight().getInfo());
                String RuleR = I + " -- " + R;
                Variables.GVRules.add(RuleR);
            }
            else if(T.getLeft()!=null && T.getRight()==null) {
                String I = Pohon.ArrayListToString(T.getInfo());
                String L = Pohon.ArrayListToString(T.getLeft().getInfo());
                String RuleL = I + " -- " + L;
                Variables.GVRules.add(RuleL);
            }
            else {
                String I = Pohon.ArrayListToString(T.getInfo());
                String L = Pohon.ArrayListToString(T.getLeft().getInfo());
                String R = Pohon.ArrayListToString(T.getRight().getInfo());
                String RuleL = I + " -- " + L;
                String RuleR = I + " -- " + R;
                Variables.GVRules.add(RuleL);
                Variables.GVRules.add(RuleR);
                createGVRules(T.getLeft());
                createGVRules(T.getRight());
            }
        }
    }
}
