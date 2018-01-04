package main;

public class HtmlSection {
	private int startIndex;
	private int endIndex;
	
	public HtmlSection(int start,int end){
		this.startIndex = start;
		this.endIndex = end;
	}
	
	public String getTransString(String rawString){
		return "'"+rawString.substring(startIndex, endIndex+1)+"'";
	}
}
