/**
 * A program to detect whether or not the given system contains a deadlock. If the deadlock is detected, program will return the list of processes and resources that involve in the deadlock.
 * Created by
 * Peerachai Banyongrakkul Sec.1 5988070
 * Boonyada Lojanarungsiri Sec.1 5988153
 * 3/5/2018
 * DeadLock.java
 */
import java.io.*;
import java.util.*;
public class DeadLock 
{
    // main
    public static void main(String[] args)
    {
        int count = 0;
        int numRe = 0;
        char[][] matrix = new char[1000][1000];
        
        //read file and split by ","
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
            // for displaying contents of file.
//            System.out.println("Contents of file:");
//            System.out.println(stringBuffer.toString());
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        
        // find size
        int sizeE = 0;
        for(int i = 0; i<count ; i++)
        {
            for(int j = 0 ; j<numRe ; j++)
            {
                if(matrix[i][j] != '0')
                {
                    sizeE++;
                }
            }
        }
        
        // prepare everything that need to use for creating list of all nodes and list of all edges by calling CreateItem function
        HashMap NodeEdge = new HashMap();
        // call CreateItem function to create list of all nodes and list of all edges
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
        
        // call checkDeadLock function to check whether it has deadlock or not.
        checkDeadLock(node,edge);
    }
    
    
    /**
     * This is the method for creating list of all nodes and list of all edges
     * @param matrix
     * @param sizeP
     * @param sizeR
     * @param sizeE
     * @return 
     */
    public static HashMap CreateItem(char[][] matrix,int sizeP,int sizeR,int sizeE)
    {
        // create nodes and keep them in array.
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
        
        // for display list of all nodes
//        System.out.print("N = [");
//        for(int i = 0; i<node.length  ; i++)
//        {
//            if(i!=node.length-1)
//            {
//                System.out.print(node[i] + ", ");
//            }
//            else
//            {
//                System.out.print(node[i]);
//            }
//        }
//        System.out.println("]");
        
        // create edges and keep them in array
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
        
        // for displaying list of all esges
//        System.out.println();
//        System.out.print("Edge = [");
//        for(int i = 0; i < sizeE ; i++)
//        {
//            if(i != sizeE -1)
//            {
//                System.out.print(edge[i]+", ");
//            }
//            else
//            {
//                System.out.print(edge[i]);
//            }
//        }
//        System.out.println("]");

        // return list of all nodes and list of all edges in form of HashMap
        HashMap NodeEdge = new HashMap();
        NodeEdge.put(node, edge);
        return NodeEdge;
    }
    
    
    /**
     * This is a method that is used for detecting whether or not the given system contains a deadlock.
     * @param node
     * @param e 
     */
    public static void checkDeadLock(String[] node, String[] e)
    {
        String N;
        int countL = 0;
        Boolean check = false;
        Boolean deadend = true;
        HashMap edge = new HashMap();
        // create list L
        List L = new ArrayList();
        String endLoop = "";
        
        // unmark all edges
        for(int j = 0 ; j<e.length ; j++)
        {
            edge.put(e[j], "0");
        }
        int i = 0;
        
        // loop until reaching the lastest node or found deadlock
        while(i<node.length && check == false)
        {     
            // initialize node N
            N = node[i];
//            System.out.println();
//            System.out.println("---------------------------------");
//            System.out.println("STARTING FROM " + N);

            // loop until reaching dead end of N
            while(true)
            {
                String dest = "";
//                System.out.println(N);
                // check if N appears in L two times.
                if(!L.contains(N))
                {
                    deadend = true;
                    // add N to the end of L
                    L.add(N);
//                    System.out.println("L = " + L);
                    countL++;
                    Iterator it = edge.entrySet().iterator();
                    // loop for checking if there any unmarked outgoing edges from N
                    while(it.hasNext())
                    {
                        Map.Entry pair = (Map.Entry)it.next();
                        String[] SrcDest = pair.getKey().toString().split("-->");
                        // if there is any unmarked outgoing edge from N, pick an unmarked outgoing edge and mark it.
                        if(SrcDest[0].equals(N) && pair.getValue().equals("0"))
                        {
                            dest = SrcDest[1];
                            pair.setValue("1");
                            deadend = false;
                            break;
                        }
                    }
                }
                // if there is deadlock, break the loop.
                else 
                {
                    System.out.println();
                    check = true; 
                    endLoop = N;
                    L.add(endLoop);
//                    System.out.println("L = " + L);
                    break;
                }
                // follow that edge to the new current node and set that node into N
                if(deadend == false)
                {
                    N = dest;
                }
                // if we reach the dead end, we remove it and go back to previous node and make that one to become new N
                else
                {
                    if(L.size()-2 >= 0)
                    {
                        System.out.println("DEAD END of " + N);
                        System.out.println("");
                        L.remove(L.size()-1);
                        N = L.get(L.size()-1).toString();
                        L.remove(L.size()-1);
                    }
                    else
                    {
                        L.remove(L.size()-1);
                        break;
                    }
                }
            }
            i++;
            // when starting with new node, reunmarking all edges
            for(int p =0 ; p <e.length ; p++)
            {
                edge.put(e[p], "0");
            }
        }
//        System.out.println();
        
        // if deadlock is detected, print the cycle
        if(check == true)
        {
            System.out.println("A DeadLock is detected.");
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
        // if No deadlock is detected., it will display that the program find 0 cycle.
        else
        {
            System.out.println("No deadlock is detected.");
            System.out.println("The program find 0 cycle.");
        }
    }
    
}
