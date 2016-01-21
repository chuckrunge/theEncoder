//theEncoder - Swing-style desktop app for custom file encoding
//Copyright (C) 2016  Chuck Runge
//Lombard, IL.
//CGRunge001@GMail.com

//This program is free software; you can redistribute it and/or
//modify it under the terms of the GNU General Public License
//as published by the Free Software Foundation; either version 2
//of the License, or (at your option) any later version.

//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.

//You should have received a copy of the GNU General Public License
//along with this program; if not, write to the Free Software
//Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.

package com.cgrunge;
import java.io.*;
import java.security.MessageDigest;
import java.util.Calendar;

public class EncoderUtil {

		static int iSqrt;
		static long Sqrt;
		static long nbr_bytes=0;
		static String func_parm = "";
		static char[][] test_array;
		static char[][] byte_array;
		static char[][] crypto_array;
		static char[][] scramble_array;
		public String action;
		public String fileName;
		public String password;
		
	public EncoderUtil() {}

	public EncoderUtil(String inAction, String inFileName, String inPassword) {
		action = inAction;
		fileName = inFileName;
		password = inPassword;
		
	}

	public int execute() throws java.io.IOException, java.io.FileNotFoundException {
			int iBytes=0;
			System.out.println("encoder! " + action + " file="+fileName + " password=" + password);
			func_parm = action;
			sb.append("write_array");
			timer(sb,1);
		    iBytes = load_array(fileName);
		    timer(sb,0);
			System.out.println("number of bytes =" + iBytes);
			password = makePasswordWorse(password);
			load_crypto_array(password);
		
		copy_array(byte_array, test_array); //for validation
		copy_array(test_array, byte_array); //to init byte array
		//compare_arrays(byte_array, test_array);	
		
		if(func_parm.equals("encode")) {
			System.out.println("encoding...");
			horizontal("encode");
			copy_array(scramble_array, byte_array);
			vertical("encode");
			copy_array(scramble_array, byte_array);
			substitute("->");
			copy_array(scramble_array, byte_array);
			//encod.compare_arrays(dump_array);	
		}
		
		if(func_parm.equals("decode")) {
			System.out.println("dencoding...");
			copy_array(byte_array, scramble_array);
			substitute("<-");
			//encod.dump_array(byte_array);
			copy_array(byte_array, scramble_array);
			vertical("resolve");
			//encod.dump_array(byte_array); //<==problem?
			copy_array(byte_array, scramble_array);
			horizontal("resolve");
			//encod.compare_arrays(byte_array, test_array);
		}

		sb.append("write_array");
		timer(sb,1);
		write_array(byte_array);
	    timer(sb,0);
		
		return 0;
			
	} //end execute

	public void compare_arrays(char[][] array1, char[][] array2) {
		for(int i=0;i<iSqrt;i++) {
			for(int j=0;j<iSqrt;j++) {
				if(array1[i][j] != array2[i][j])
					System.out.println(Integer.toString(i) + Integer.toString(j) + "not equal " + array1[i][j] + " " + array2[i][j]);
			}
		}	
	}

	public void copy_array(char[][] source_array, char[][] target_array) {
		for(int i=0;i<iSqrt;i++) {
			for(int j=0;j<iSqrt;j++) {
					target_array[i][j] = '.'; //0x46;
					target_array[i][j] = source_array[i][j];
			}
		}
	}

	public void dump_array(char[][] arrayX) {

			for(int i=0;i<iSqrt;i++) {
				for(int j=0;j<iSqrt;j++) {
					System.out.print(arrayX[i][j]);
				}
			}
		System.out.print("<===>");
		System.out.println();
	}

	public int load_array(String fis) {
		DataInputStream is = null;
		int i=0, j=0, m=0, n=0;
		double dSqrt=0, dValue=0;
		char input = 0xff;

		try {
			File input_file = new File(fis); //.replaceAll("\\", "\\\\") );
			if(input_file.exists()) {
				nbr_bytes = input_file.length();
				System.out.println("file length to encode: "+(int) input_file.length());
			} else
				System.out.println("File "+input_file.getAbsolutePath()+"does not exist!");
			//is = new FileReader(input_file);
			// Wrap the FileInputStream with a DataInputStream
			FileInputStream file_input = new FileInputStream (input_file);
			is    = new DataInputStream (new BufferedInputStream(file_input));
		} catch(Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
		}
		try {
		//retrieve "extra" info
		if(func_parm.equals("decode")) {
			//System.out.println("decode function" );
			char [] chArray = new char[8];
			for(m=0;m<8;m++) {
				char ch = (char) is.read();
				if(ch >= '0' && ch <= '9') {
					chArray[m] = ch;
					n = m;
				}
			}
			char [] chArray2 = new char [n+1];
			for(m=0;m<n+1;m++) {
				chArray2[m] = chArray[m];
			}
			String Sbytes = new String(chArray2);
			Integer Ii = new Integer(Sbytes);
			nbr_bytes = (9999999 - Ii.intValue());
			System.out.println("decoding bytes= " + nbr_bytes);
		} //end decode 

		dValue = (double) nbr_bytes;
		dSqrt = Math.sqrt(dValue);
		Sqrt = Math.round(dSqrt+0.5);
		System.out.println("sqrt =" + Sqrt);
		iSqrt = (int) Sqrt;
		test_array = new char[iSqrt][iSqrt];
		byte_array = new char[iSqrt][iSqrt];
		crypto_array = new char[iSqrt][iSqrt];
		scramble_array = new char[iSqrt][iSqrt];
		
		input = (char) is.read();
	    while (input != (char) -1) {
			if(i==iSqrt) {
				j++;
				i=0;
			}
			if(j==iSqrt) {
				System.out.println("Dude! File too big!");
			}
			
			byte_array[j] [i] = input;
	        i++;
			input = (char) is.read();
		}
		is.close();
		
		} catch (Exception e) {
			//datastream EOF here
			System.out.println(e.getMessage());
		}
		return((int) nbr_bytes);
	}

	public void load_crypto_array(String sz) {
		int k = 0, p = 0, r = 0;

		for(int i = 0; i < iSqrt; i++) {
			for(int j = 0; j < iSqrt; j++) {
				k = (i+j) % sz.length();
				p = (int) sz.charAt(k);
				r = p ^ 3124; //mask with magic
				crypto_array[i][j] = (char) r;
			}
		}
	}

	public void horizontal(String sz) {
	  if(sz.equals("resolve")) {
		for(int i=0;i<iSqrt;i++) {
			for(int j=0;j<iSqrt;j++) {
				//System.out.println(i + " " + j);
				if(j == 0) 
					byte_array[i][(iSqrt-1)] = scramble_array[i][j];
				else
					byte_array[i][(j-1)] = scramble_array[i][j];
			}
		}	
	  } else {
		for(int i=0;i<iSqrt;i++) {
			for(int j=0;j<iSqrt;j++) {
				//System.out.println(i + " " + j);
				if(j == iSqrt-1) 
					scramble_array[i][0] = byte_array[i][j];
				else
					scramble_array[i][j+1] = byte_array[i][j];
			}
		}
		}
	}
	public void vertical(String sz) {
	  if(sz.equals("resolve")) {
		for(int i=0;i<iSqrt;i++) {
			for(int j=0;j<iSqrt;j++) {
				//System.out.println(i + " " + j);
				if(i == 0) {
					byte_array[(iSqrt-1)][j] = scramble_array[i][j];
					//System.out.print(byte_array[0][j]);
				} else 
					byte_array[i-1][j] = scramble_array[i][j];
			}
		}	
	  } else {
		for(int i=0;i<iSqrt;i++) {
			for(int j=0;j<iSqrt;j++) {
				//System.out.println(i + " " + j);
				if(i == (iSqrt-1)) 
					scramble_array[0][j] = byte_array[i][j];
				else 
					scramble_array[i+1][j] = byte_array[i][j];
			}
		}
		}
	}

	public void substitute(String sz) {
		int k = 0, m = 0, n = 0;
		if(sz.equals("->")) {
		  for(int i = 0; i < iSqrt; i++) {
			for(int j = 0; j < iSqrt; j++) {
				k = (int) byte_array[i][j];
				m = (int) crypto_array[i][j];
				n = k ^ m;
				//if(n == m) 
				//	n = 0;
				scramble_array[i][j] = (char) n;
				//if(j<80) System.out.println(k + "==>" + n);
			}
		  }
		} else {
		  for(int i = 0; i < iSqrt; i++) {
			for(int j = 0; j < iSqrt; j++) {
				k = (int) scramble_array[i][j];
				m = (int) crypto_array[i][j];
				//if(k != 0) 
				n = k ^ m;
				byte_array[i][j] = (char) n;
			}
		  }
		}
	}

	void write_array(char[][] arrayX) {

		try{
		int k=0;
		//FileWriter os = new FileWriter("encoder.out");
		File fos = new File("encoder.out");
		// Create an output stream to the file.
	      FileOutputStream file_output = new FileOutputStream (fos);
	      // Wrap the FileOutputStream with a DataOutputStream
	      DataOutputStream os = new DataOutputStream (new BufferedOutputStream(file_output));
		
		if(func_parm.equals("encode")) {
			String Sbytes = Integer.toString((int) (9999999 - nbr_bytes));
			char chArray[] = Sbytes.toCharArray();
			for(int m=0;m<8;m++) {
				if(m<chArray.length)
					os.write((int) chArray[m]);
				else
					os.write(8 - m);
			}

		} 
		System.out.println("writing " + nbr_bytes + " bytes...");
			for(int i=0;i<iSqrt;i++) {
				for(int j=0;j<iSqrt;j++) {
					if(func_parm.equals("encode")) {
							os.write((int) arrayX[i][j]); 
					} else {
						if(k < nbr_bytes) 
							os.write((int) arrayX[i][j]); 
					}
					k++;
				}
			}
		os.close();
		} catch (IOException e) {
			System.out.println(e);
		} finally {
			
		}
	}

	long start=0;
	long end=0;
	StringBuffer sb = new StringBuffer();

public String makePasswordWorse(String password) {
	
	int i=0;	
	byte[] bytes = password.getBytes();
	byte[] newBytes = new byte[bytes.length];
	
	for(i=bytes.length;i>0;i--) {
		newBytes[(bytes.length - 1) - (i - 1)] = bytes[(i - 1)];
	}
	String sz = new String(newBytes);
	String salt = "V@1U3$R@Nd0m&PSu3d0#S@lt@V@lU3-Ck@R@cT3rs@MlthZp3C1@l^N0w*";
	
	return md5(sz+salt); 
	
}

public String md5(String sz) {
	
	String password = sz;
	MessageDigest md = null;
	
	try{
		md = MessageDigest.getInstance("SHA-256");
	} catch(Exception ex) {
		System.out.println(ex.getMessage());
		ex.printStackTrace();
	}
	
    md.update(password.getBytes());
    
    byte byteData[] = md.digest();

    //convert byte to hex
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < byteData.length; i++) {
    	sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
    }
 
    //hex in string
	return sb.toString();

}
	
public void timer(StringBuffer sb, int typ) {
	
	if(typ==0) { //stop
		end = Calendar.getInstance().getTimeInMillis();
		System.out.printf("%s elapsed: %d start: %d end: %d \n", sb, start, end, (end - start));
		start=0;end=0;
		sb.delete(0, 99);
	}
	if(typ==1) { //start
		start = Calendar.getInstance().getTimeInMillis();			
	}
}

	} //end class