package test;

import java.util.ArrayList;
import java.util.List;
// Somehow make sure this order: <name, abbreviation>
public class Category {
	private static List<String[]> categories;
	public void addCategory(String[] names) {
		categories.add(names);
	}
	public List<String> getCategoryNames(){
		List<String> names = new ArrayList<String>();
		for (String[] child : categories) {
			names.add(child[0]);
		}
		return names;
	}
	public List<String> getCategoryAbbreviations(){
		List<String> abbr = new ArrayList<String>();
		for (String[] child : categories){
			abbr.add(child[0]);
		}
		return abbr;
	}
	public static String getCategoryWithAbbreviation(String filename) {
		for (String[] child : categories) {
			if (filename.contains(child[1])){
				return child[0];
			}
		}
		return null;
	}
	public String getCategoryWithName(String filename){
		for (String[] child : categories) {
			if (filename.contains(child[0])){
				return child[0];
			}
		}
		return null;
	}

}
