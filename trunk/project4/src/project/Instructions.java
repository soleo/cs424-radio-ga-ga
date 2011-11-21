package project;

public class Instructions {
	public Instructions(){
		
	}
	
	void drawContent(){
		Utils.globalProcessing.fill(232,123,52);
		Utils.globalProcessing.rect(10,10, Utils.globalProcessing.getWidth() - 230, Utils.globalProcessing.getHeight() - 20);
	}
}
