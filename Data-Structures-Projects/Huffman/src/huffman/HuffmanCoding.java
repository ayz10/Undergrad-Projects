package huffman;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class contains methods which, when used together, perform the
 * entire Huffman Coding encoding and decoding process
 * 
 * @author Ishaan Ivaturi
 * @author Prince Rawal
 */
public class HuffmanCoding {
    /**
     * Writes a given string of 1's and 0's to the given file byte by byte
     * and NOT as characters of 1 and 0 which take up 8 bits each
     * 
     * @param filename The file to write to (doesn't need to exist yet)
     * @param bitString The string of 1's and 0's to write to the file in bits
     */
    public static void writeBitString(String filename, String bitString) {
        byte[] bytes = new byte[bitString.length() / 8 + 1];
        int bytesIndex = 0, byteIndex = 0, currentByte = 0;

        // Pad the string with initial zeroes and then a one in order to bring
        // its length to a multiple of 8. When reading, the 1 signifies the
        // end of padding.
        int padding = 8 - (bitString.length() % 8);
        String pad = "";
        for (int i = 0; i < padding-1; i++) pad = pad + "0";
        pad = pad + "1";
        bitString = pad + bitString;

        // For every bit, add it to the right spot in the corresponding byte,
        // and store bytes in the array when finished
        for (char c : bitString.toCharArray()) {
            if (c != '1' && c != '0') {
                System.out.println("Invalid characters in bitstring");
                System.exit(1);
            }

            if (c == '1') currentByte += 1 << (7-byteIndex);
            byteIndex++;
            
            if (byteIndex == 8) {
                bytes[bytesIndex] = (byte) currentByte;
                bytesIndex++;
                currentByte = 0;
                byteIndex = 0;
            }
        }
        
        // Write the array of bytes to the provided file
        try {
            FileOutputStream out = new FileOutputStream(filename);
            out.write(bytes);
            out.close();
        }
        catch(Exception e) {
            System.err.println("Error when writing to file!");
        }
    }
    
    /**
     * Reads a given file byte by byte, and returns a string of 1's and 0's
     * representing the bits in the file
     * 
     * @param filename The encoded file to read from
     * @return String of 1's and 0's representing the bits in the file
     */
    public static String readBitString(String filename) {
        String bitString = "";
        
        try {
            FileInputStream in = new FileInputStream(filename);
            File file = new File(filename);

            byte bytes[] = new byte[(int) file.length()];
            in.read(bytes);
            in.close();
            
            // For each byte read, convert it to a binary string of length 8 and add it
            // to the bit string
            for (byte b : bytes) {
                bitString = bitString + 
                String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
            }

            // Detect the first 1 signifying the end of padding, then remove the first few
            // characters, including the 1
            for (int i = 0; i < 8; i++) {
                if (bitString.charAt(i) == '1') return bitString.substring(i+1);
            }
            
            return bitString.substring(8);
        }
        catch(Exception e) {
            System.out.println("Error while reading file!");
            return "";
        }
    }

    //helped method to find minimum node between 2 queues
    private static TreeNode findMin(Queue<TreeNode>source, Queue<TreeNode>target ){
        TreeNode minNode;

        //if first queue is empty, delete and return node from second queue
        if (source.isEmpty()){
            minNode = target.peek();
            target.dequeue();
            return minNode;
        }
        //if second queue is empty, delete and return node from first queue
        else if(target.isEmpty()){
            minNode = source.peek();
            source.dequeue();
            return minNode;
        } 
        //find smaller from two queues
        /*source.peek().getData().getProbOccurrence() <= target.peek().getData().getProbOccurrence()*/ 
        else if(source.peek().getData().getProbOccurrence() <= target.peek().getData().getProbOccurrence()){
            minNode = source.peek();
            source.dequeue();
            return minNode;
        }else{
            minNode = target.peek();
            target.dequeue();
            return minNode;
        }
    }
    //helper method to traverse to encode
    private static String[] encodeTraverse(TreeNode node, String[] array, String c){
        if(node==null){
            return array;
        }
        if(node.getData().getCharacter()==null){
            encodeTraverse(node.getLeft(), array, c + "0");
            encodeTraverse(node.getRight(), array,  c + "1");
            return array;
        }
        array[(int)node.getData().getCharacter()] = c;
        return array;
    }

    //helper method to travers to decode
    private static String decodeTraverse(TreeNode node, String encodedFile){
        String decodedString = "";
        TreeNode ptr = node;
        for(int i=0; i < encodedFile.length(); i++){
            if(encodedFile.charAt(i)=='0'){
                ptr = ptr.getLeft();
            }
            else if(encodedFile.charAt(i)=='1'){
                ptr = ptr.getRight();
            }
            if(ptr.getLeft()==null && ptr.getRight()==null){
                decodedString = decodedString + ptr.getData().getCharacter();
                ptr = node;
            }
        }
        return decodedString;
    }
    /**
     * Reads a given text file character by character, and returns an arraylist
     * of CharFreq objects with frequency > 0, sorted by frequency
     * 
     * @param filename The text file to read from
     * @return Arraylist of CharFreq objects, sorted by frequency
     */
    public static ArrayList<CharFreq> makeSortedList(String filename) {
        StdIn.setFile(filename);
        /* Your code goes here */
        ArrayList<CharFreq> sortedList = new ArrayList<>();
        int[] arr = new int[128];
        int count = 0;

        //add each char to array
        do {
            char c = StdIn.readChar();
            arr[c] = arr[c]+1;
        } while(StdIn.hasNextChar());

        //count number of characters
        for (int j = 0; j<arr.length; j++){
            if (arr[j]>0)
                count = count + arr[j];
        }

        //add array elements to arraylist
        for (int i = 0; i<arr.length; i++){
            if(arr[i]>0)
                sortedList.add(new CharFreq((char)i, (double)arr[i]/count));
        }
        
        //1 unique character
        Character n = 1;
        if(sortedList.size()==1){
            sortedList.add(new CharFreq(n, 0.00));
        }

        Collections.sort(sortedList);
        return sortedList; // Delete this line
    }

    /**
     * Uses a given sorted arraylist of CharFreq objects to build a huffman coding tree
     * 
     * @param sortedList The arraylist of CharFreq objects to build the tree from
     * @return A TreeNode representing the root of the huffman coding tree
     */
    public static TreeNode makeTree(ArrayList<CharFreq> sortedList) {
        /* Your code goes here */
        Queue<TreeNode> source = new Queue<>();
        Queue<TreeNode> target = new Queue<>();
        int i;
        for (i=0; i < sortedList.size(); i++){
                TreeNode newNode = new TreeNode(sortedList.get(i),null,null);
                source.enqueue(newNode);
        }
        
        while(!source.isEmpty()||target.size()>1){
            TreeNode firstMin = findMin(source, target);
            TreeNode secMin = findMin(source, target);
            double sumOcc = firstMin.getData().getProbOccurrence()+secMin.getData().getProbOccurrence();
            TreeNode newNode = new TreeNode(new CharFreq(null, sumOcc), firstMin, secMin);
            target.enqueue(newNode);
        }

        TreeNode treeRoot = target.peek();
        target.dequeue();
        return treeRoot; // Delete this line
    }

    /**
     * Uses a given huffman coding tree to create a string array of size 128, where each
     * index in the array contains that ASCII character's bitstring encoding. Characters not
     * present in the huffman coding tree should have their spots in the array left null
     * 
     * @param root The root of the given huffman coding tree
     * @return Array of strings containing only 1's and 0's representing character encodings
     */
    public static String[] makeEncodings(TreeNode root) {
        /* Your code goes here */
        String[] arr = new String[128];
        String c = "";
        encodeTraverse(root, arr, c);
        return arr;
    }

    /**
     * Using a given string array of encodings, a given text file, and a file name to encode into,
     * this method makes use of the writeBitString method to write the final encoding of 1's and
     * 0's to the encoded file.
     * 
     * @param encodings The array containing binary string encodings for each ASCII character
     * @param textFile The text file which is to be encoded
     * @param encodedFile The file name into which the text file is to be encoded
     */
    public static void encodeFromArray(String[] encodings, String textFile, String encodedFile) {
        StdIn.setFile(textFile);
        /* Your code goes here */
        String bitString = "";
        do{
            char c = StdIn.readChar();
            bitString = bitString + encodings[c];
        }while(StdIn.hasNextChar());
        writeBitString(encodedFile, bitString);
    }
    
    /**
     * Using a given encoded file name and a huffman coding tree, this method makes use of the 
     * readBitString method to convert the file into a bit string, then decodes the bit string
     * using the tree, and writes it to a file.
     * 
     * @param encodedFile The file which contains the encoded text we want to decode
     * @param root The root of your Huffman Coding tree
     * @param decodedFile The file which you want to decode into
     */
    public static void decode(String encodedFile, TreeNode root, String decodedFile) {
        StdOut.setFile(decodedFile);
        /* Your code goes here */
        encodedFile = readBitString(encodedFile);
        String decodedString = decodeTraverse(root, encodedFile);
        StdOut.print(decodedString);
    }
}
