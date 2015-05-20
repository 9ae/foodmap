package services;

import java.util.Arrays;

public class Words {

	public static final String[] PREPS = {"a","about", "above", "across", "after", "against", 
		"along", "amid", "among", "an" , "around", "at", "atop", "before", "behind", "below", 
		"beneath", "beside", "between", "beyond", "but", "by", 
		"concerning", "down", "during", "except", "for", "from", "in", "inside",
		"into", "like", "near", "of", "off", "on", "onto", "out", "outside",
		"over", "past", "regarding", "since", "the", "through", "throughout", "to", 
		"toward", "under", "underneath", "until", "up", "upon", "with", "within", "without"};
	
	
	public static boolean binarySearch(String word, String[] words){
		int len = words.length;
		if(len==1){
			return words[0].equals(word);
		}
		int midpoint = len/2;
		int compare = word.compareTo(words[midpoint]);
		if(compare>0){
			if(midpoint==(len-1)){
				return false;
			} else {
				return binarySearch(word, Arrays.copyOfRange(words, midpoint+1, len));
			}
		} else if (compare<0){
			return binarySearch(word, Arrays.copyOfRange(words, 0, midpoint));
		} else {
			return true;
		}
	} 
	
	public static boolean isPreposition(String word){
		return binarySearch(word, PREPS);
	}

}