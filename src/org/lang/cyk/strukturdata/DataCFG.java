/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lang.cyk.strukturdata;

import java.util.ArrayList;

/**
 *
 * @author toshiba
 */
public class DataCFG {
    Character alfa;
    ArrayList<String> beta = new ArrayList<String>();
    
    public Character getAlfa() {
        return this.alfa;
    }
    
    public void setAlfa(Character alfa) {
        this.alfa = alfa;
    }
    
    public String getBeta(int idx) {
        return this.beta.get(idx);
    }
    
    public void addBeta(String b) {
        this.beta.add(b);
    }
    
    public void delBeta(int idx) {
        this.beta.remove(idx);
    }
    
    public int countBeta() {
        return this.beta.size();
    }
    
    public void changeBeta(int idx, String baru) {
        this.beta.set(idx, baru);
    }
}
