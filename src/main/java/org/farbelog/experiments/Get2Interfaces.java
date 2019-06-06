package org.farbelog.experiments;
import org.farbelog.core.HasCollor;

public class Get2Interfaces {


	public static  <K extends HasCollor<org.slf4j.Logger> & org.slf4j.Logger> K getLogger(){

		return null;
	}

}
