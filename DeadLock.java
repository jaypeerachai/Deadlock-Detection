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
        int sizeE = 0;
        for(int i = 0; i<count ; i++)
        {
            for(int j = 0 ; j<numRe ; j++)
            {
                System.out.print(matrix[i][j] + " ");
                if(matrix[i][j] != '0')
                {
                    sizeE++;
                }
            }
            System.out.println();
        }
        HashMap NodeEdge = new HashMap();
        NodeEdge = CreateItem(matrix,count,numRe,sizeE); 
        Set set = NodeEdge.entrySet();
        Iterator i = set.iterator();
        String[] node = new String[count+numRe];
        String[] edge = new String[sizeE];
        while(i.hasNext())
        {
            Map.Entry me =  (Map.Entry)i.next();
            node = (String[]) me.getKey();
            edge = (String[]) me.getValue();
        }
        
        checkDeadLock(node,edge);
    }
    
    public static HashMap CreateItem(char[][] matrix,int sizeP,int sizeR,int sizeE)
    {
        String[] node = new String[sizeP+sizeR];
        int count = 0;
        for(int i = 0; i<sizeP ; i++)
        {
            String temp = "P";
            node[count] = temp+Integer.toString(i);
            count++;
        }
        for(int j = 0 ; j<sizeR ;j++)
        {
            String temp = "R";
            node[count] = temp+Integer.toString(j);
            count++;
        }
        System.out.println();
        System.out.print("N = [");
        for(int i = 0; i<node.length  ; i++)
        {
            if(i!=node.length-1)
            {
                System.out.print(node[i] + ", ");
            }
            else
            {
                System.out.print(node[i]);
            }
        }
        System.out.println("]");
        
        String[] edge = new String[sizeE];
        int countE = 0;
        int countN = 0;
        for(int i = 0 ; i<sizeP; i++)
        {
            for( int j = 0 ; j<sizeR ; j++)
            {
                String temp = "";
                if(matrix[i][j] == '1')
                {
                    temp = node[i]+"-->"+node[j+sizeP];
                    edge[countE] = temp;
                    countE++;
                }
                else if(matrix[i][j] == '2')
                {
                    temp = node[j+sizeP]+"-->"+node[i];
                    edge[countE] = temp;
                    countE++;
                }
            }
        }
        
        System.out.println();
        System.out.print("Edge = [");
        for(int i = 0; i < sizeE ; i++)
        {
            if(i != sizeE -1)
            {
                System.out.print(edge[i]+", ");
            }
            else
            {
                System.out.print(edge[i]);
            }
        }
        System.out.println("]");
        HashMap NodeEdge = new HashMap();
        NodeEdge.put(node, edge);
        return NodeEdge;
    }
    
    public static void checkDeadLock(String[] node, String[] e)
    {
        String N;
        Boolean check = false;
        HashMap edge = new HashMap();
        List L = new ArrayList();
        String endLoop = "";
        
        // unmark all edge
        for(int j = 0 ; j<e.length ; j++)
        {
            edge.put(e[j], 0);
        }
        int i = 0;
        while(i<node.length && check == false)
        {     
            N = node[i];
            //System.out.println(L + " " + N);
            while(true)
            {
                String dest = "";
                System.out.println(N);
                System.out.println("L = " + L);
                if(!L.contains(N))
                {
                    L.add(N);
                    Iterator it = edge.entrySet().iterator();
                    while(it.hasNext())
                    {
                        Map.Entry pair = (Map.Entry)it.next();
                        String[] SrcDest = pair.getKey().toString().split("-->");
                        if(SrcDest[0].equals(N) && pair.getValue().equals(0))
                        {
                            dest = SrcDest[1];
                            //System.out.println(pair.getKey().toString().substring(0, 2) + " " + pair.getKey().toString().substring(5,7));
                            pair.setValue('1');
                            break;
                        }
                    }
                }
                else 
                {
                    System.out.println();
                    System.out.println("A DeadLock is detected");
                    check = true; 
                    endLoop = N;
                    L.add(endLoop);
                    break;
                }
                N = dest;
            }
            i++;
        }
        System.out.println("L = " + L);
        System.out.print("The program find this cycle: ");
        for(int k = L.indexOf(endLoop) ; k<L.size() ; k++)
        {
            if(k != L.size()-1)
            {
                System.out.print(L.get(k) + " -> ");
            }
            else
            {
                System.out.println(L.get(k));
            }
        }
    }
    
}
