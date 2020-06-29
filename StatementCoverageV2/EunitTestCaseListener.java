package org.eclipse.epsilon.eol.coverage;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.epsilon.eunit.EUnitModule;
import org.eclipse.epsilon.eunit.EUnitTest;
import org.eclipse.epsilon.eunit.EUnitTestListener;

public class EunitTestCaseListener implements EUnitTestListener{
	List<Integer> coveredLinesForEachCase = new ArrayList<>();
	StatementCoverageListener listener = new StatementCoverageListener();
	@Override
	public void beforeCase(EUnitModule module, EUnitTest test) {
		// TODO Auto-generated method stub
		
		
		module.getContext().getExecutorFactory().addExecutionListener(listener);
		
	}

	@Override
	public void afterCase(EUnitModule module, EUnitTest test) {
		// TODO Auto-generated method stub
		
		//Analyze statement coverage
		coveredLinesForEachCase = listener.getCoverage();
		int numberOfCoveredLines = coveredLinesForEachCase.size();
		int totalLinse = module.getImports().get(0).getImportedModule().getRegion().getEnd().getLine();
		float statementCoverage = (100/totalLinse)*numberOfCoveredLines;
		
		//print out result, executed lines and statement coverage for each test case
		System.out.println(test.getCaseName()+"\t"+test.getResult());
		System.out.println(test.getCaseName()+"\tcoveredLines"+listener.getCoverage());
		System.out.println(test.getCaseName()+"\tStatementCoverage:"+statementCoverage);
		
		listener.resetCoveredLinse();
	}
	public List<Integer> getCoverage() {
		
		return coveredLinesForEachCase;
	}
}
