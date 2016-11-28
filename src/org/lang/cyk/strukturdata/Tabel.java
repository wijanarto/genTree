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
public class Tabel {
  
    @SuppressWarnings("rawtypes")
	ArrayList[][] isi;
    int rows;
    int columns;
    
    public Tabel(int baris, int kolom) {
        isi = new ArrayList[baris+1][kolom+1];
        for(int i=0; i<=baris; i++) {
            for(int j=0; j<=kolom; j++) {
                isi[i][j] = new ArrayList<Character>();
            }
        }
        this.columns = kolom;
        this.rows = baris;
    }
    
    @SuppressWarnings("unchecked")
	public
	ArrayList<Character> getContent(int baris, int kolom) {
        return this.isi[baris][kolom];
    }
    
    public void setContent(ArrayList<Character> isi, int baris, int kolom) {
        this.isi[baris][kolom] = isi;
    }
    
    public Character getIdxTable(int baris, int kolom, int idx) {
        return getContent(baris, kolom).get(idx);
    }
    
    public int getRows() {
        return this.rows;
    }
    
    public int getColumns() {
        return this.columns;
    }
}