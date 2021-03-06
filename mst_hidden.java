import java.awt.Image;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import javax.swing.JProgressBar;

public class mst_hidden extends mst_graph {
	protected int hiddenSeed;
	protected String hiddenWords;
	protected primMaze hiddenPrim;
	
	public static void main(String[] args)
	{
		JProgressBar progressBar = new JProgressBar();
		mst_hidden graph = new mst_hidden("4321", "D:\\workplace\\mst_graph\\gunter.png","fakoLOL", progressBar,"7980","theReal");
		//graph.decryptDebug();
	
		graph.encrypt();
	}


	public mst_hidden(String seed, String location, String words, JProgressBar progressBar,String hiddenSeed,String hiddenWords) {
		super(seed, location, words, progressBar);
		// TODO Auto-generated constructor stub
		this.hiddenSeed = Integer.parseInt(hiddenSeed);
		this.hiddenWords= hiddenWords;
		hiddenPrim = null; // Hidden prim requires normal prim to generate. Putting it as null in constructor minimizes possibility
	}
	
	@Override
	public void encrypt() {
	setWords(words);
	writeInformationToNewImage(prim);
	hiddenPrim = new primMaze(7980,prim);
	setWords(hiddenWords);
	writeInformationToNewImage(hiddenPrim);
	picture.show();
	}
	
	public void decryptDebug()
	{
		LinkedList<point> header = prim.getHeaderPoint(15);
		extractInformationFromLinkedList(header);
		System.out.println(getHiddenListInString());// kinda a stub for now
		
		mst_graph getFake = new mst_graph("4321", "D:\\workplace\\mst_graph\\gunterhidden.png", "aids", progressBar);
		System.out.println(getFake.decrypt());
	//	getFake.getPrim().printPrim();
		mst_graph realPrim = new mst_graph("7980", "D:\\workplace\\mst_graph\\gunterhidden.png", "aidz", progressBar, getFake.getPrim());
		System.out.println(realPrim.decrypt());

	}
	
	/* stub will need to rewrite in the future */
	public void writeHeader(primMaze prim)
	{
		LinkedList<String> binaryOfSeed = generateBinaryBits("aids"); 
		LinkedList<point> header = prim.getHeaderPoint(15);
		System.out.println(binaryOfSeed);
		System.out.println(header);
		
		while(!header.isEmpty() && !binaryOfSeed.isEmpty())
		{
			writeInformationToSpecificBit(header.remove(0), binaryOfSeed.remove(0), binaryOfSeed.remove(0), binaryOfSeed.remove(0));
		}
	}
	
	@Override
	public LinkedList<String> generateBinaryBits(String message)
	{
		LinkedList<String> returnedList = new LinkedList<String>();
		ArrayList<String> splitMessage = new ArrayList<String>(Arrays.asList(message.split("")));
		for(int a = 0; a < splitMessage.size(); a++)
		{
			updateHoldingNextBits(splitMessage.get(a).charAt(0));
			returnedList.addAll(holdingNextBits);
		}
		returnedList.add("00"); // Exit Character
		returnedList.add("01");
		returnedList.add("10");
		returnedList.add("11");
		returnedList.add("00"); // Due to randomness it's possible for 27 to generate from random blocks so i put two you know
		returnedList.add("01"); // just in case
		returnedList.add("10");
		returnedList.add("11");
		
		for(int a = 0; a < 24; a++) //after we fill the allBits array with exit val + our data just fill the shit in with whateveer
		{
			returnedList.add(getRandomBinary());
		}
		/*Needs 6 * 4 bits for the exit character to able to be read correctly*/



		return returnedList;
		
	}
	
}
