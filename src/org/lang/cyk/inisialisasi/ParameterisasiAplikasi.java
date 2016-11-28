package org.lang.cyk.inisialisasi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.lang.cyk.dotinterface.GraphViz;
import org.lang.cyk.exception.GrammarError;
import org.lang.cyk.generator.Generator;
import org.lang.cyk.kernel.cykAlgorithm;
import org.lang.cyk.strukturdata.Pohon;

public class ParameterisasiAplikasi {
	//System.out.println("Arguments input: "+  optinput1+" "+      opt1+" "+    optfilename1+" "+  opt2+" "+    opt3+      opt4);
	//                           -1,0,1
	public static void genFileParameterized(int im_t_both,String option1, String option2,String option3,String option4,String option5, String optlmd){
		   //                  generate1(1,                  optinput1,       opt1,          optfilename1,  treeTextFile,  treeImgFile,    LMDFile);
		 
				//=====================start
		Variables.str_input= new File(option3);//save input string file in options dir
				try{
		            FileInputStream fstream = new FileInputStream(Variables.str_input);
		            DataInputStream in = new DataInputStream(fstream);
		            BufferedReader br = new BufferedReader(new InputStreamReader(in));                
		            String strLine;
		            while ((strLine = br.readLine()) != null){
		                StringTokenizer st = new StringTokenizer(strLine, " ");
		                Variables.inputan=st.nextToken();
		            }
		            in.close();
		        }catch (Exception e){
		            System.err.println("Error : " + e.getMessage());
		        }
				//temp=new String[25];
		        if(Variables.cfg_mode==false) {
		        	cykAlgorithm.readInputRules(option1);//cnf file optinput1
		        }

		        GrammarError ER = new GrammarError();
		        ER.isRulesValid(Variables.temp,Variables.countCFG);
		        
		        if(ER.getStatusError()==true) {
		            System.out.println("Error : " + ER.getMessage());
		        } else 
		        {
		            ER.isStringValid(Variables.inputan);
		            if(ER.getStatusError()==true) {
		            	System.out.println("Error : " + ER.getMessage());
		            }
		            else {
		            	cykAlgorithm.categorizedRules();
		                if(Variables.cfg_mode==false) {
		                	cykAlgorithm.delNULL();
		                	cykAlgorithm.delUNIT();
		                	cykAlgorithm.delUSELESS();
		                	cykAlgorithm.lastStep();
		                    //=======================================================================cek lagi
		                    System.out.println("CNF Rule :");
		                    Generator.createCFG(option1,false);//output console disini
		                    System.out.println("Input String : "+Variables.inputan);
		               }
		                //take time from here
		                //===============================================
		                Variables.tStart=System.currentTimeMillis();
		                //===============================================

		                cykAlgorithm.parseCYK(); //set table segitiga pada algoritma CYK (Variables.CYKtabel)

		                if(Variables.result==false) { //tidak ada start symbol 'S'
		                    ER.isStringCFG(Variables.CFG, Variables.inputan);
		                    if(ER.getStatusError()==true) {
		                    	                    	
		                    	System.out.println("Error parsing input string (S): " + ER.getMessage());
		                    	Variables. GVCode = ER.genErrorDot(Variables.CFG, Variables.inputan, option3);//(CFG, inputan)
		                        GraphViz gv = new GraphViz();

		                        Variables.out = new File(option5); //opt3 save dot file in outputs dir
		                        gv.readSource(Variables.GVCodeName);
		                        gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), "png" ), Variables.out );
		                        
		                    }
		                    else {
		                    	System.out.println("Error parsing input string: Urutan string tidak sesuai rules (I): " + ER.getMessage());
		                    	Variables.Tree = ER.createTErrorInput(Variables.CFG, Variables.inputan, Variables.CYKtable, 0, Variables.CYKtable.getColumns());
		                        ArrayList<Character> S = new ArrayList<Character>();
		                        S.add('e');S.add('r');S.add('r');S.add('s');
		                        Variables.Tree.setInfo(S);
		                        Pohon.createTree2(Variables.Tree, Variables.inputan);
		                        Pohon.finalizedTree(Variables.Tree.getLeft());
		                        Pohon.finalizedTree(Variables.Tree.getRight());
		                        
		                        Pohon.CopyTree(Variables.Tree, Variables.Tree2);
		                        Variables.Tree2.setIdxNode(0);
		                        Variables.Tree2.replaceInfo(Variables.Tree2);
		                        Generator.createNodes(Variables.Tree, Variables.Tree2);
		                        Generator.createRules(Variables.Tree2);
		                        Generator.createDOT(Variables.GVCodeName,false);
		                        Generator.createImage(option5,false);
		                        //createDOTFile(option3.replace(".str",".dot"));
		        		     	//createGVImage(option3.replace(".str",".png"));
		                    }
		                } else { //ada 'S' mean everything running well
		                	Variables.Tree = Pohon.createTree1(Variables.CYKtable, 0, Variables.CYKtable.getColumns());                    
		                	Pohon.createTree2(Variables.Tree, Variables.inputan);
		                	Pohon.finalizedTree(Variables.Tree);

		                	Variables.preorder = Variables.Tree.PreOrder(Variables.Tree);
		                	Variables.inorder = Variables.Tree.InOrder(Variables.Tree);
		                	Variables.postorder = Variables.Tree.PostOrder(Variables.Tree);
		             

						    //=======start====Dokumentasi left most derivation
		                	Variables.leftmost.add(Pohon.ArrayListToString(Variables.Tree.getInfo()));
		                	Variables.id=0;
		                	cykAlgorithm.LeftMostDerivation(Variables.Tree);
		                    if (!optlmd.isEmpty()){
		    					File str_outlmd= new File(optlmd);//save lmd file in optins dir
		    				    try {
		    				        FileOutputStream fos = new FileOutputStream(str_outlmd);
		    				        DataOutputStream dos = new DataOutputStream(fos);
		    				        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(dos));
		    				        bw.write("Left Most Derivation : ");
			    				    bw.newLine();
		    				        for(int k=0; k<Variables.leftmost.size(); k++) {
		    				        	 bw.write(Variables.leftmost.get(k));
		    	    				     bw.newLine();
		    		                }
		    				        bw.close();
		    				    }
		    				    catch(IOException e) {
		    				        e.printStackTrace();
		    				    }
		                    }
		                    //String LMD=new String();
		                    //for(int k=0; k<leftmost.size(); k++) {
		                    //    System.out.println(LMD+" => " + leftmost.get(k));
		                    //}
		                  //=======end====Dokumentasi left most derivation
		                    Pohon.CopyTree(Variables.Tree, Variables.Tree2);
		                    Variables.Tree2.setIdxNode(0);
		                    Variables.Tree2.replaceInfo(Variables.Tree2);
		                    Generator.createNodes(Variables.Tree, Variables.Tree2);
		                    Generator.createRules(Variables.Tree2);
		                    switch(im_t_both){
		                    case -1 : 	Generator.createPohonText(option4,false);
		                    			Generator.createDOT(Variables.GVCodeName,false);
		       		     				break;
		                    case 0  :  	Generator.createDOT(Variables.GVCodeName,false);
		                    			Generator.createImage(option5,false);
		       		    				break;
		                    case 1  :	Generator.createPohonText(option4,false);
		                    			Generator.createDOT(Variables.GVCodeName,false);
		                    			Generator.createImage(option5,false);
		                		     	break;
		                    }
		                }    
		                //take end time
		                Variables.tEnd=System.currentTimeMillis();
		                //========================================
		            }
		        }
				//=====================end
			}

			public static void genConsoleParameterized(int im_t_both,String option1, String option2,String option3,String option4,String option5, String optlmd){
				   //                  generate1(1,                  optinput1,       opt1,          optfilename1,  treeTextFile,  treeImgFile,    LMDFile);
				 
						//=====================start
						Variables.str_input= new File(option3);//save input string file in options dir
						try{
				            FileInputStream fstream = new FileInputStream(Variables.str_input);
				            DataInputStream in = new DataInputStream(fstream);
				            BufferedReader br = new BufferedReader(new InputStreamReader(in));                
				            String strLine;
				            while ((strLine = br.readLine()) != null){
				                StringTokenizer st = new StringTokenizer(strLine, " ");
				                Variables.inputan=st.nextToken();
				            }
				            in.close();
				        }catch (Exception e){
				            System.err.println("Error : " + e.getMessage());
				        }
						//temp=new String[25];
				        if(Variables.cfg_mode==false) {
				        	cykAlgorithm.readInputRules(option1);//cnf file optinput1
				        }

				        GrammarError ER = new GrammarError();
				        ER.isRulesValid(Variables.temp,Variables.countCFG); // simpan status di GE.setStatusError() 
				        
				        if(ER.getStatusError()==true) { //cek status start symbol
				            System.out.println("Error Grammar: " + ER.getMessage());
				            System.exit(1);
				        } else 
				        {
				            ER.isStringValid(Variables.inputan);
				            if(ER.getStatusError()==true) {
				            	System.out.println("Error Input: " + ER.getMessage());
				            	System.exit(1);
				            }
				            else {
				            	cykAlgorithm.categorizedRules();
				                if(Variables.cfg_mode==false) {
				                	cykAlgorithm.delNULL();
				                	cykAlgorithm.delUNIT();
				                	cykAlgorithm.delUSELESS();
				                	cykAlgorithm.lastStep();
				                    //=======================================================================cek lagi
				                    System.out.println("CNF Rule :");
				                    Generator.createCFG(option1,true);//output console disini
				                    System.out.println("Input String : "+Variables.inputan);
				               }
				                //take time from here
				                //===============================================
				                Variables.tStart=System.currentTimeMillis();
				                //===============================================
				                cykAlgorithm.parseCYK();

				                if(Variables.result==false) { //tidak ada start symbol 'S', input string tidak valid
				                    ER.isStringCFG(Variables.CFG, Variables.inputan);
				                    if(ER.getStatusError()==true) {
				                    	System.out.println("Invalid string input, " + ER.getMessage());
				                    	Variables.GVCode = ER.genErrorDot(Variables.CFG, Variables.inputan, option3);//(CFG, inputan)
				                        GraphViz gv = new GraphViz();

				                        Variables.out = new File(option5); //opt3 save dot file in outputs dir
				                        gv.readSource(Variables.GVCode.getAbsolutePath());//(Variables.GVCodeName);
				                        gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), "png" ), Variables.out );
				                    }
				                    else {
				                    	System.out.println("Gammar cannot match string input, " + ER.getMessage());
				                    	Variables.Tree = ER.createTErrorInput(Variables.CFG, Variables.inputan, Variables.CYKtable, 0, Variables.CYKtable.getColumns());
				                        ArrayList<Character> S = new ArrayList<Character>();
				                        S.add('e');S.add('r');S.add('r');S.add('s');
				                        Variables.Tree.setInfo(S);
				                        Pohon.createTree2(Variables.Tree, Variables.inputan);
				                        Pohon.finalizedTree(Variables.Tree.getLeft());
				                        Pohon.finalizedTree(Variables.Tree.getRight());
				                        
				                        Pohon.CopyTree(Variables.Tree, Variables.Tree2);
				                        Variables.Tree2.setIdxNode(0);
				                        Variables.Tree2.replaceInfo(Variables.Tree2);
				                        Generator.createNodes(Variables.Tree, Variables.Tree2);
				                        Generator.createRules(Variables.Tree2);
				                        Generator.createDOT(Variables.GVCodeName,true);
				                        Generator.createImage(option5,true);
				                    }
				                }
				                else { //ada 'S'
				                	//everything running well
				                	Variables.Tree = Pohon.createTree1(Variables.CYKtable, 0, Variables.CYKtable.getColumns());                    
				                	Pohon.createTree2(Variables.Tree, Variables.inputan);
				                	Pohon.finalizedTree(Variables.Tree);

				                	Variables.preorder = Variables.Tree.PreOrder(Variables.Tree);
				                	Variables.inorder = Variables.Tree.InOrder(Variables.Tree);
				                	Variables.postorder = Variables.Tree.PostOrder(Variables.Tree);
				             

								    //=======start====Dokumentasi left most derivation
				                	Variables.leftmost.add(Pohon.ArrayListToString(Variables.Tree.getInfo()));
				                	Variables.id=0;
				                	cykAlgorithm.LeftMostDerivation(Variables.Tree);
				                    String LMD=new String();
				                    System.out.println("Left Most Derivation :");
				                    for(int k=0; k<Variables.leftmost.size(); k++) {
				                        System.out.println(LMD+" => " + Variables.leftmost.get(k));
				                    }
				                  //=======end====Dokumentasi left most derivation
				                    Pohon. CopyTree(Variables.Tree, Variables.Tree2);
				                    Variables.Tree2.setIdxNode(0);
				                    Variables. Tree2.replaceInfo(Variables.Tree2);
				                    Generator.createNodes(Variables.Tree, Variables.Tree2);
				                    Generator.createRules(Variables.Tree2);
				                    switch(im_t_both){
				                    case -1 : 	Generator.createPohonText(option4,true);
				                    			Generator.createDOT(Variables.GVCodeName,true);
				       		     				break;
				                    case 0  :  	Generator.createDOT(Variables.GVCodeName,true);
				                    			Generator.createImage(option5,true);
				       		    				break;
				                    case 1  :	Generator.createPohonText(option4,true);
				                    			Generator.createDOT(Variables.GVCodeName,true);
				                    			Generator.createImage(option5,true);
				                		     	break;
				                    }
				                } 
				                //take end time
				                Variables.tEnd=System.currentTimeMillis();
				                //========================================
				            }
				        }
						//=====================end
					}
}
