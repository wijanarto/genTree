package org.lang.cyk.generator;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.lang.cyk.dotinterface.GraphViz;
import org.lang.cyk.inisialisasi.Variables;
import org.lang.cyk.strukturdata.Pohon;

public class Generator {
	public static void createNodes(Pohon T1, Pohon T2) {
	    if(T1 != null && T2 != null)  {
	        String L = Pohon.ArrayListToString(T1.getInfo());
	        String N = Pohon.ArrayListToString(T2.getInfo());
	        Variables.GVNode.add(N);
	        Variables.GVLabel.add(L);
	        createNodes(T1.getLeft(), T2.getLeft());
	        createNodes(T1.getRight(), T2.getRight());
	    }
	}
	
	public static void createRules(Pohon T) {
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
	            createRules(T.getLeft());
	            createRules(T.getRight());
	        }
	    }
	}
	
	
	
	public static void createCFG(String cfgFile, boolean console) {
		if(console==false){
			Variables.cfg_loc=cfgFile;
			Variables.cfg_file = new File(Variables.cfg_loc);
		    try {
		        FileOutputStream fos = new FileOutputStream(Variables.cfg_file);
		        DataOutputStream dos = new DataOutputStream(fos);
		        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(dos));
		        for(int a=0; a<Variables.CFG.size(); a++) {
		            String cfg = Variables.CFG.get(a).getAlfa() + " ->";
		            for(int b=0; b<Variables.CFG.get(a).countBeta(); b++) {
		                if(b==0) {
		                    cfg = cfg + " " + Variables.CFG.get(a).getBeta(b);
		                }
		                else {
		                    cfg = cfg + " | " + Variables.CFG.get(a).getBeta(b);
		                }
		            }
		            bw.write(cfg + ".");
		            bw.newLine();
		            System.out.println(cfg);
		        }
		        bw.close();
		    }
		    catch(IOException e) {
		        e.printStackTrace();
		    }
		} else {
		    for(int a=0; a<Variables.CFG.size(); a++) {
		         String cfg = Variables.CFG.get(a).getAlfa() + " ->";
		         for(int b=0; b<Variables.CFG.get(a).countBeta(); b++) {
		             if(b==0) {
		                 cfg = cfg + " " + Variables.CFG.get(a).getBeta(b);
		             }
		             else {
		                 cfg = cfg + " | " + Variables.CFG.get(a).getBeta(b);
		             }
		         }
		         System.out.println(cfg);
		    }
		}
	}
	
	public static void createDOT(String dotFileName, boolean console) {
		if(console==false){
			    try {
			    	Variables.GVCode = new File(dotFileName);
			        FileOutputStream fos = new FileOutputStream(Variables.GVCode);
			        DataOutputStream dos = new DataOutputStream(fos);
			        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(dos));
			        bw.write("graph G {");
			        bw.newLine();
			        for(int i=0; i<Variables.GVNode.size(); i++) {
			            String datanode;
			            if(Variables.GVLabel.get(i).equals("errs") || Variables.GVLabel.get(i).equals("")) {
			                datanode = Variables.GVNode.get(i) + "[label=errs, shape=box,style=rounded,height=0.02,width=0.01, color=red];";
			            }
			            else {
			                if(Character.isUpperCase(Variables.GVLabel.get(i).charAt(0))==false) {
			                    datanode = Variables.GVNode.get(i) + "[label=" + Variables.GVLabel.get(i) + ", shape=box,style=rounded,height=0.02,width=0.01,color=blue];";
			                }
			                else {
			                    datanode = Variables.GVNode.get(i) + "[label=" + Variables.GVLabel.get(i) + ", shape=box,style=rounded,height=0.02,width=0.01, color=black];";
			                }
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
		}else {
			try {
					Variables.GVCode = new File(dotFileName);
			        System.out.println("graph G {\n");
			        FileOutputStream fos = new FileOutputStream(Variables.GVCode);
			        DataOutputStream dos = new DataOutputStream(fos);
			        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(dos));
			        bw.write("graph G {");
			        bw.newLine();
			        for(int i=0; i<Variables.GVNode.size(); i++) {
			            String datanode;
			            if(Variables.GVLabel.get(i).equals("errs") || Variables.GVLabel.get(i).equals("")) {
			                datanode = Variables.GVNode.get(i) + "[label=errs, shape=box,style=rounded,height=0.02,width=0.01, color=red];";
			            }
			            else {
			                if(Character.isUpperCase(Variables.GVLabel.get(i).charAt(0))==false) {
			                    datanode = Variables.GVNode.get(i) + "[label=" + Variables.GVLabel.get(i) + ", shape=box,style=rounded,height=0.02,width=0.01,color=blue];";
			                }
			                else {
			                    datanode = Variables.GVNode.get(i) + "[label=" + Variables.GVLabel.get(i) + ", shape=box,style=rounded,height=0.02,width=0.01, color=black];";
			                }
			            }
			            System.out.println("\t"+datanode+"\n");
			            bw.write(datanode);
			            bw.newLine();
			        }
			        for(int i=0; i<Variables.GVRules.size(); i++) {
			            String data = Variables.GVRules.get(i) + ";";
			            System.out.println(data+"\n");
			            bw.write("\t"+data);
			            bw.newLine();
			        }
			        System.out.println("}\n");
			        bw.write("}");
			        bw.close();
			    
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void createImage(String gvFilename, boolean console) {
		if(console==false){
		    GraphViz gv = new GraphViz();
		    Variables.out = new File(gvFilename);
		    gv.readSource(Variables.GVCodeName);
		    gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), "png" ), Variables.out );
		} else {
		    GraphViz gv = new GraphViz();
		    Variables.out = new File(gvFilename);
		    gv.readSource(Variables.GVCodeName);
		    gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), "png" ), Variables.out );
			//return;
		}
	}
	
	public static void createPohonText(String textFileName, boolean console){
        //String output=option1.replace(".str",".txt");
		if(console==false){
			File str_output= new File(textFileName);
		    try {
		        FileOutputStream fos = new FileOutputStream(str_output);
		        DataOutputStream dos = new DataOutputStream(fos);
		        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(dos));
	
		        bw.write(Variables.preorder);
		        bw.newLine();
		        bw.write(Variables.inorder);
		        bw.newLine();
		        bw.write(Variables.postorder);
		        bw.close();
		    }
		    catch(IOException e) {
		        e.printStackTrace();
		    }
		} else {
		     System.out.println("Pohon dalam preorder : "+Variables.preorder);
		     System.out.println("Pohon dalam inorder  : "+Variables.inorder);
		     System.out.println("Pohon dalam postorder: "+Variables.postorder);
		}
	}
}
