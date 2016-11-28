package org.lang.cyk.consolesession;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.RootPaneContainer;

import org.lang.cyk.inisialisasi.ParameterisasiAplikasi;
import org.lang.cyk.inisialisasi.Variables;



/**
 *
 * genTree *.cfg -i *.str <-o|<-oi *.png>|<-ot *.txt> >
 */
public class genTree  {

    public static void main(String args[]) {
		Variables.optionsDIR = new File(Variables.pwd+"\\outputs\\");
		Variables.optionsDIR.mkdir();
		if(Variables.optionsDIR!=null && !Variables.optionsDIR.isDirectory()){
			System.out.println("Error create directry DIR:"+Variables.optionsDIR);
		} else {
			Variables.optdir=Variables.optionsDIR.getAbsolutePath();
		}
		if( args.length == 0 && !((args.length==5)||(args.length==4)))
		{
			if (ConsoleSession.IsJava7Install()){
				ConsoleSession.printJava();
				ConsoleSession.printUsage();
				ConsoleSession.deleteDirOnExit(Variables.optionsDIR);
				System.exit(1);
			} else {
				ConsoleSession.printJava();
				ConsoleSession.deleteDirOnExit(Variables.optionsDIR);
				System.exit(1);
			}
			
		} 

		
		switch(args.length){
		case 1 : //argumen=1, 
				Variables.optinput1=args[0];
			     if (Variables.optinput1.compareTo("-h")==0){ //-h help
			    	 ConsoleSession.printUsage();
		        	 System.exit(1);
		         } 
		         break;
		case 4 : 
			Variables.optinput1=args[0];    //*.cfg
			Variables.opt1=args[1];         //-i
			Variables.optfilename1=args[2]; //*.str
			Variables.opt2=args[3];         //-o,-ot,-oi
			//      *.cfg                   -i                      *.str                     -ot
			if(ConsoleSession.IsCFGfile(Variables.optinput1)&&(Variables.opt1.compareTo("-i")==0)&&
					ConsoleSession.IsSTRfile(Variables.optfilename1)&&(Variables.opt2.compareTo("-ot")==0)){ //*.txt
				    //im_t_both=-1
					Variables.treeTextFile=Variables.optfilename1.replace(".str",".txt");
					Variables.treeImgFile=Variables.optfilename1.replace(".str",".png");
					Variables.LMDFile=Variables.optfilename1.replace(".str",".lmd");
					Variables.GVCodeName=Variables.optdir+"\\"+Variables.optinput1.replace(".cfg",".dot");//source dot file (wajib)
					
					ParameterisasiAplikasi.genFileParameterized(-1,Variables.optinput1, Variables.opt1,Variables.optfilename1,Variables.pwd+"/outputs/ "+
							Variables.treeTextFile,Variables.pwd+"/outputs/ "+Variables.treeImgFile,Variables.pwd+"/outputs/ "+Variables.LMDFile);
					Variables.tDelta = Variables.tEnd - Variables.tStart;
					Variables.elapsedSeconds = Variables.tDelta / 1000.0;
					System.out.println("Time taken : "+Variables.elapsedSeconds+" sec");
			}else if (ConsoleSession.IsCFGfile(Variables.optinput1)&&(Variables.opt1.compareTo("-i")==0)&&
					ConsoleSession.IsSTRfile(Variables.optfilename1)&&(Variables.opt2.compareTo("-c")==0)){//to console 	
			    //im_t_both=0
				Variables.treeTextFile=Variables.optfilename1.replace(".str",".txt");
				Variables.treeImgFile=Variables.optfilename1.replace(".str",".png");
				Variables.LMDFile=Variables.optfilename1.replace(".str",".lmd");
				Variables.GVCodeName=Variables.optdir+"\\"+Variables.optinput1.replace(".cfg",".dot");//source dot file (wajib)
				
				ParameterisasiAplikasi.genConsoleParameterized(1,Variables.optinput1, Variables.opt1,Variables.optfilename1,Variables.treeTextFile,
						Variables.treeImgFile, Variables.LMDFile);
				
				Variables.tDelta = Variables.tEnd - Variables.tStart;
				Variables.elapsedSeconds = Variables.tDelta / 1000.0;
				System.out.println("Time taken : "+Variables.elapsedSeconds+" sec");
				
				JFrame frame = new JFrame("Pohon");
				Toolkit tk = Toolkit.getDefaultToolkit();
			    Dimension d = tk.getScreenSize();
				ImageIcon img = new ImageIcon(Variables.out.getAbsolutePath());
				JLabel emptyLabel=new JLabel(img);
				JScrollPane sp1= new JScrollPane(emptyLabel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
						JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				JTextArea tLMD = new JTextArea();tLMD.setBackground(new java.awt.Color(255, 255, 0));
				tLMD.setEditable(false);
				
				JScrollPane sp2= new JScrollPane(tLMD,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
						JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				JTextArea tDOT = new JTextArea();tDOT.setBackground(new java.awt.Color(255, 0, 255));
				tDOT.setEditable(false);
				JScrollPane sp3= new JScrollPane(tDOT,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
						JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				JTextArea tTREE = new JTextArea();tTREE.setBackground(new java.awt.Color(0, 255, 255));
				tTREE.setEditable(false);
				JScrollPane sp4= new JScrollPane(tTREE,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
						JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				GroupLayout layout = new GroupLayout(frame.getContentPane());
			    frame.getContentPane().setLayout(layout);
			    layout.setAutoCreateGaps(true);
			    layout.setAutoCreateContainerGaps(true);
			    layout.setHorizontalGroup(layout.createSequentialGroup()
			    		.addComponent(sp2,100, d.width/4, d.width/4)//(tLMD)
			    		.addComponent(sp1,100, d.width/4, d.width/4)//(emptyLabel)
			            .addComponent(sp4,100, d.width/4, d.width/4)//(tTREE)
			            .addComponent(sp3,100, d.width/4, d.width/4)//(tDOT)
			        );
			       
			    layout.setVerticalGroup(layout.createSequentialGroup()
			    	    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			    	        .addComponent(sp2)//(tLMD)
			    	        .addComponent(sp1)//(emptyLabel)
			    	    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			    	        .addGroup(layout.createSequentialGroup()
			    	            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			    	            		.addComponent(sp4)//(tTREE)
			    	            		.addComponent(sp3)//(tDOT)
			    	            		))))
			    	);
			    tLMD.append("Left Most Derivation :\n");
                for(int k=0; k<Variables.leftmost.size(); k++) {
                	tLMD.append(" ==> " + Variables.leftmost.get(k)+"\n");
                }
				
				tDOT.append("graph G {\n");
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
		            tDOT.append("     "+datanode+"\n");
		        }
		        for(int i=0; i<Variables.GVRules.size(); i++) {
		            String data = Variables.GVRules.get(i) + ";";
		            tDOT.append("     "+data);
		            tDOT.append("\n");
		        }
		        tDOT.append("   }\n");
				tTREE.append("Pohon dalam preorder : "+Variables.preorder+"\n"+"Pohon dalam inorder : "+Variables.inorder+
						"\n"+"Pohon dalam postorder : "+Variables.postorder);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.pack();
				frame.setVisible(true);
				ConsoleSession.deleteDirOnExit(Variables.optionsDIR);
			}else if (ConsoleSession.IsCFGfile(Variables.optinput1)&&(Variables.opt1.compareTo("-i")==0)&&ConsoleSession.IsSTRfile(Variables.optfilename1)&&
					(Variables.opt2.compareTo("-oi")==0)){//*.png 	
				    //im_t_both=0
					Variables.treeTextFile=Variables.optfilename1.replace(".str",".txt");
					Variables.treeImgFile=Variables.optfilename1.replace(".str",".png");
					Variables.LMDFile=Variables.optfilename1.replace(".str",".lmd");
					Variables.GVCodeName=Variables.optdir+"\\"+Variables.optinput1.replace(".cfg",".dot");//source dot file (wajib)
					ParameterisasiAplikasi.genFileParameterized(0,Variables.optinput1, Variables.opt1,Variables.optfilename1,Variables.optdir+"\\"+
							Variables.treeTextFile,Variables.optdir+"\\"+Variables.treeImgFile, Variables.optdir+"\\"+Variables.LMDFile);
					Variables.tDelta = Variables.tEnd - Variables.tStart;
					Variables.elapsedSeconds = Variables.tDelta / 1000.0;
					System.out.println("Time taken : "+Variables.elapsedSeconds+" sec");
			}else if (ConsoleSession.IsCFGfile(Variables.optinput1)&&(Variables.opt1.compareTo("-i")==0)&&ConsoleSession.IsSTRfile(Variables.optfilename1)&&
					(Variables.opt2.compareTo("-o")==0)) { // default *convert to txt
				//im_t_both=1
				Variables.treeTextFile=Variables.optfilename1.replace(".str",".txt");
				Variables.treeImgFile=Variables.optfilename1.replace(".str",".png");
				Variables.LMDFile=Variables.optfilename1.replace(".str",".lmd");
				Variables.GVCodeName=Variables.optdir+"\\"+Variables.optinput1.replace(".cfg",".dot");//source dot file (wajib)
				System.out.println(Variables.optfilename1+"==>"+Variables.treeTextFile +"-"+Variables.treeImgFile+"-" +
						Variables.LMDFile+"==>"+Variables.GVCodeName);
				System.out.println("Generate Tree in Text");
				ParameterisasiAplikasi.genFileParameterized(1,Variables.optinput1, Variables.opt1,Variables.optfilename1,Variables.optdir+"\\"+
						Variables.treeTextFile,Variables.optdir+"\\"+Variables.treeImgFile, Variables.optdir+"\\"+Variables.LMDFile);
				Variables.tDelta = Variables.tEnd - Variables.tStart;
				Variables.elapsedSeconds = Variables.tDelta / 1000.0;
				System.out.println("Time taken : "+Variables.elapsedSeconds+" sec");
			} else {
	      		System.out.println("Error in options");	
	      		ConsoleSession.printUsage();
	      	 }
			break;
		case 5 : //genTree *.cfg -i <*.str> -o [*.txt|*.png|null]
			Variables.optinput1=args[0];    //*.cfg
			Variables.opt1=args[1];         //-i
			Variables.optfilename1=args[2]; //*.str
			Variables.opt2=args[3];         //-o
				 //      *.cfg                   -i                      *.str                     -ot
		      	 if(ConsoleSession.IsCFGfile(Variables.optinput1)&&(Variables.opt1.compareTo("-i")==0)&&ConsoleSession.IsSTRfile(Variables.optfilename1)&&
		      			 (Variables.opt2.compareTo("-ot")==0)){ //*.txt
		      		Variables.opt3=args[4];         //[*.txt]
		      		Variables.treeTextFile=Variables.opt3;
		      		Variables.treeImgFile=Variables.optfilename1.replace(".str",".png");
		      		Variables.LMDFile=Variables.optfilename1.replace(".str",".lmd");
		      		Variables.GVCodeName=Variables.optdir+"\\"+Variables.optinput1.replace(".cfg",".dot");//source dot file (wajib)
					System.out.println("Generate Tree in Text");
					System.out.println("arguments : "+Variables.optinput1+" "+Variables.opt1+" "+Variables.optfilename1+" "+Variables.opt2+" "+Variables.opt3);
					ParameterisasiAplikasi.genFileParameterized(-1,Variables.optinput1, Variables.opt1,Variables.optfilename1,Variables.optdir+"\\"+
							Variables.treeTextFile,Variables.optdir+"\\"+Variables.treeImgFile,Variables.optdir+"\\"+Variables.LMDFile);
					Variables.tDelta = Variables.tEnd - Variables.tStart;
					Variables.elapsedSeconds = Variables.tDelta / 1000.0;
					System.out.println("Time taken : "+Variables.elapsedSeconds+" sec");
		      	 }else if (ConsoleSession.IsCFGfile(Variables.optinput1)&&(Variables.opt1.compareTo("-i")==0)&&ConsoleSession.IsSTRfile(Variables.optfilename1)&&
		      			 (Variables.opt2.compareTo("-oi")==0)){//*.png 	
		      		Variables.opt3=args[4];         //[*.txt]
		      		Variables.treeTextFile=Variables.opt3;
		      		Variables.treeImgFile=Variables.optfilename1.replace(".str",".png");
		      		Variables.LMDFile=Variables.optfilename1.replace(".str",".lmd");
		      		Variables.GVCodeName=Variables.optdir+"\\"+Variables.optinput1.replace(".cfg",".dot");//source dot file (wajib)
					System.out.println("Generate Tree in Text");
					System.out.println("arguments : "+Variables.optinput1+" "+Variables.opt1+" "+Variables.optfilename1+" "+Variables.opt2+" "+Variables.opt3);
					ParameterisasiAplikasi.genFileParameterized(0,Variables.optinput1, Variables.opt1,Variables.optfilename1,Variables.optdir+"\\"+
							Variables.treeTextFile,Variables.optdir+"\\"+Variables.treeImgFile,Variables.optdir+"\\"+Variables.LMDFile);
					Variables.tDelta = Variables.tEnd - Variables.tStart;
					Variables.elapsedSeconds = Variables.tDelta / 1000.0;
					System.out.println("Time taken : "+Variables.elapsedSeconds+" sec");
		      	 }else {
		      		System.out.println("Error in options");	
		      		ConsoleSession.printUsage();
		      	 }
		         break;
		default : ConsoleSession.printUsage();
				  break;
		}
    }

}
