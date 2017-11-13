

public class WordTotal implements Comparable<WordTotal>{//Used to hold the word with number of timed it appeared in the file
		
		private String word;
		
		private int wordCount;
		
		public WordTotal(String s, int c){
			word = s;
			wordCount = c;
		}
		
		public String getWord(){
			return word;
		}
		
		public int getCount(){
			return wordCount;
		}

		@Override
		public int compareTo(WordTotal o) {
			// TODO Auto-generated method stub
			int comp = 0;
			if(wordCount > o.getCount()){
				comp = -1;
			}else if(wordCount < o.getCount()){
				comp = 1;
			}else{
				comp = 0;
			}
			return comp;
		}
	}