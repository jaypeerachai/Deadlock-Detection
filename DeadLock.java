/**
 * Created by
 * Peerachai Banyongrakkul Sec.1 5988070
 * Boonyada Lojanarungsiri Sec.1 5988153
 * DeadLock.java
 */
import java.io.*;
import java.util.*;
public class DeadLock 
{
    public static void main(String[] args)
    {
        int count = 0;
        int numRe = 0;
        char[][] matrix = new char[1000][1000];
        try 
        {
            File file = new File("Deadlock/TestCase02.txt");
            StringBuffer stringBuffer;
            try (FileReader fileReader = new FileReader(file)) 
            {
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                stringBuffer = new StringBuffer();
                String line;
                while ((line = bufferedReader.readLine()) != null)
                {
                    String[] mat;
                    mat = line.split(",");
                    stringBuffer.append(line);
                    stringBuffer.append("\n");
                    for(int i = 0 ; i<mat.length ; i++)
                    {
                        matrix[count][i] = mat[i].charAt(0);
                    }
                    count++;
                    numRe = mat.length;
                }
            }
            System.out.println("Contents of file:");
            System.out.println(stringBuffer.toString());
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        for(int i = 0; i<count ; i++)
        {
            for(int j = 0 ; j<numRe ; j++)
            {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
