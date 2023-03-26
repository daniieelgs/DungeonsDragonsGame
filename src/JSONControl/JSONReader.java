package JSONControl;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONReader {

	private String path;
	private JSONObject jsonobject;
		
	public JSONReader(FileReader stream, String path) throws IOException, ParseException {
		
		this.path = path;
		
		jsonobject = (JSONObject) new JSONParser().parse(stream);
		
	}
	
	public JSONReader(File file) throws IOException, ParseException {
		this(new FileReader(file), file.getPath());
	}
	
	public JSONReader(String file) throws IOException, ParseException {	
		this(new FileReader(file), file);
	}
	
	public String path() {
		return path;
	}
	
	public JSONObject json() {
		return jsonobject;
	}
	
	public Object read(String key) {
		
		return jsonobject.get(key);
		
	}
	
	
	
}
