import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Huffman {


    public static void main(String[] args) throws IOException {    
        Heap heap = new Heap();
        try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
            
            String line = br.readLine();
            while(line!=null){
                String[] words = line.split(" ");
                int value = Integer.parseInt(words[1]);
                String kar = words[0]; 
                //System.out.println(value+" "+kar);
                heap.add(new Node(value,kar));

                line = br.readLine();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }


        
        if(heap.getSize()==1){
            System.out.println(heap.remove().kar + " " + 0);
        }else{
            Node root = null;
            while(heap.getSize()>0){
                Node node1=heap.remove(),node2=null;
    
                if(heap.getSize()>0)
                    node2=heap.remove();
                
                if(node2 == null){
                    root=node1;
                }else{
                    Node newNode = new Node(node1.value+node2.value,node1.kar+node2.kar);
                    newNode.leftChild=node1;
                    newNode.rightChild=node2;
                    heap.add(newNode);
                }
            }
            root.printNodes(" ");
        }


    }

}

class Node{
    
    public int value;
    public String kar;
    public Node leftChild=null,rightChild=null;

    public Node(int value,String kar){
        this.value=value;
        this.kar=kar;
    }
    @Override
    public String toString() {
        return kar+" "+value;
    }

    public void printNodes(String code){
        if(this.leftChild!=null)
            this.leftChild.printNodes(code+"0");
        
        if(this.rightChild!=null)
            this.rightChild.printNodes(code+"1");
        
        if(this.rightChild==null&&this.leftChild==null)
            System.out.println(kar+" "+code);
    }
}

class Heap {
 
    private Node[] Heap;
    private int size;
    private int maxsize;
  
    public Heap(){
 
        this.maxsize = 30;
        this.size = 0;
 
        Heap = new Node[this.maxsize + 1];
        Heap[0] = new Node(-1," ");
    }

    
    
    public void add(Node Node){
        
        if (size >= maxsize)
        return;
        
        Heap[++size] = Node;
        int current = size;
        
        while (Heap[current].value < Heap[parent(current)].value) {
            change(current, parent(current));
            current = parent(current);
        }
    }
    
    private void change(int kid, int parent){
        Node tmp = Heap[kid];
        Heap[kid] = Heap[parent];
        Heap[parent] = tmp;
    }

    public Node remove(){
        Node popped = Heap[1];
        Heap[1] = Heap[size--];
        goDown(1);
        
        return popped;
    }
    private void goDown(int pos){      
        if(!(pos > (size / 2))){
            int swapPos=pos;
            if(rightChild(pos)<=size){
                if (Heap[leftChild(pos)].value < Heap[rightChild(pos)].value){
                    swapPos = leftChild(pos);
                }else{
                    swapPos = rightChild(pos);
                }
            }else{
                swapPos = leftChild(pos);
            }

            if(Heap[pos].value > Heap[leftChild(pos)].value || Heap[pos].value > Heap[rightChild(pos)].value){
                change(pos,swapPos);
                goDown(swapPos);
            }   
        }       
    }
    
    
    private int parent(int pos) { 
        return pos / 2; 
    }
    private int leftChild(int pos){ 
        return (2 * pos); 
    }
    private int rightChild(int pos){
        return (2 * pos) + 1;
    }

    public int getSize() {
        return size;
    }
}
