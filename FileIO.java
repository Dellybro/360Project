import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileIO {
	
	private File f;
	
	public FileIO(String name){
		f = new File(name);
	}
	
	public void readFile() {
		try {
			BufferedReader bf = new BufferedReader(new FileReader(f));
			String line;
			while((line = bf.readLine()) != null){
				
			}
		} catch (FileNotFoundException e) {
			System.out.println("File was invalid/not found");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
