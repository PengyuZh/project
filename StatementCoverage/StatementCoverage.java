package org.eclipse.epsilon.eol.coverage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.control.StatementCoverageListener;


public class StatementCoverage {

	private ModuleElement ast;
	private File inputFile;
	private List<String> fileContents;
	private int statementCount = 0, coveredStatementCount = 0;
	private List<Integer> coveredLine = new ArrayList<>();
	

	public StatementCoverage(ModuleElement ast, File inputFile) {
		this.ast = ast;
		this.inputFile = inputFile;
		fileContents = new ArrayList<String>();
	}

	private static String ParseFile = "/Users/zhangpengyu/Documents/workspace/Project/SeatingPlan/my_spider.eol";
	public static void main(String[] args) {
		
		EolModule module = new EolModule();
		File executableFile = new File(ParseFile);
		try {
			module.parse(executableFile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		StatementCoverageListener listener = new StatementCoverageListener();
		module.getContext().getExecutorFactory().addExecutionListener(listener);
		
		try {
			module.execute();
		} catch (EolRuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		StatementCoverage statementcoverage = new StatementCoverage(module, executableFile);
		statementcoverage.analyseCoverage();

	}

	public void analyseCoverage() {
		this.readInLines();
		this.dfAST(ast);
		

		List<Integer> temp = new ArrayList<>();
		
		for (int i:coveredLine) {

			
			if(i>statementCount) {
				statementCount=i;
			}
			if(Collections.frequency(temp, i)<1) {
				temp.add(i);
			}
		}
		coveredLine = temp;
		coveredLine.sort(null);
		coveredStatementCount=coveredLine.size();
		float Coverage = (100 / statementCount) * coveredStatementCount;
		
		System.out.println("StatementCoverage="+Coverage+"%");
		System.out.println("coveredLines:"+coveredLine);

	}

	private void readInLines() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));

			String line;
			while ((line = reader.readLine()) != null) {
				fileContents.add(line);
			}

			reader.close();
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return;
		}
	}

	private void dfAST(ModuleElement ast) {
		
			if (ast.getVisited()) {

				coveredLine.add(ast.getRegion().getEnd().getLine());
			}
		for (ModuleElement child : ast.getChildren()) {
			dfAST(child);
		}
		
	}


}
