package org.eclipse.epsilon.eol.coverage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.Marshaller.Listener;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;

import org.eclipse.epsilon.eunit.EUnitModule;


public class StatementCoverage {



	public StatementCoverage(ModuleElement ast, File inputFile) {

	}
	private static String ParseFile = "src/org/eclipse/epsilon/eol/coverage/my_spider.eol";
	public static void main(String[] args) {
		//parse EOL module
		EolModule module = new EolModule();
		File EolFile = new File(ParseFile);
		try {
			module.parse(EolFile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//parse EUnit module
		EUnitModule eunitModule = new EUnitModule();
		
		File eunitFile = new File("src/org/eclipse/epsilon/eol/coverage/test_spider.eunit");
		try {
			eunitModule.parse(eunitFile);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//add EUnit test case listener
		EunitTestCaseListener testCaseListener = new EunitTestCaseListener();
		eunitModule.addTestListener(testCaseListener);
		
		//run the EUnit module
		try {
			eunitModule.execute();
			
		} catch (EolRuntimeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

			
	}

}
	
