package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Crossword {

	List<String> words;
	Map<String,String> descriptions; 
	int tableSise;

	public Crossword() {
		words = new ArrayList<String>();
		descriptions = new TreeMap<String,String>();
	}

	public void addWord(String newWord) {
		words.add(newWord);
	}
	public void addDescription(String word,String newDescription) {
		descriptions.put(word,newDescription);
	}
	public boolean checkSize(String word) {
		if (word.length() >= 3 && word.length() <= 10)
			return true;
		return false;
	}

	public boolean checkCharacters(String word) {
		for (char letter : word.toCharArray()) {
			if (!(65 <= (int) letter && 90 >= (int) letter || (int) letter == 209)) {
				return false;
			}
		}
		return true;
	}

	public boolean checkDuplicates(String newWord) {
		for (String word : words) {
			if (word.equals(newWord)) 
				return true;
		}
		return false;
	}

	public void deleteWord(String removeWord) {
		deleteDescription(words.indexOf(removeWord));
		words.remove(removeWord);
		
	}
	
	private void deleteDescription(int position) {
		descriptions.remove(position);
	}
	
	public void setDescription(int position,String newDescription) {
		descriptions.put(words.get(position),newDescription);
	}

	public List<String> getWords() {
		return words;
	}
	public String getDescription(String word) {
		return descriptions.get(word);
	} 
}
