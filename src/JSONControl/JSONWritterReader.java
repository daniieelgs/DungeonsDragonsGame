package JSONControl;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class JSONWritterReader extends JSONReader{

	public JSONWritterReader(FileReader stream, String path) throws IOException, ParseException {
		
		super(stream, path);
		
	}
	
	public JSONWritterReader(File file) throws IOException, ParseException {
		super(file);
	}
	
	public JSONWritterReader(String file) throws IOException, ParseException {	
		super(file);
	}
	
	@SuppressWarnings("unchecked")
	public void write(JSONObject jObj, String key, String value) throws IOException {
		jObj.put(key, value);
		
		FileWriter streamSave = new FileWriter(path());
		streamSave.write(json().toJSONString());
		
		streamSave.close();
	}
	
	public void write(String key, String value) throws IOException {
		write(json(), key, value);
	}
	
	
}
