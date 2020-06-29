package org.eclipse.epsilon.eol.coverage;



import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.control.IExecutionListener;

public class StatementCoverageListener implements IExecutionListener {
	List<Integer> coveredLines = new ArrayList<>();
	
	@Override
	public void aboutToExecute(ModuleElement ast, IEolContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void finishedExecuting(ModuleElement ast, Object result, IEolContext context) {
		
		//ast.setVisited();
		
		//System.out.println(ast.getFile().getName().toString().contains("eol"));
		
		if (ast.getFile().getName().toString().contains("eol")) {
			
			if(coveredLines.contains(ast.getRegion().getEnd().getLine())==false){
				coveredLines.add(ast.getRegion().getEnd().getLine());
				if (coveredLines.contains(ast.getRegion().getStart().getLine())==false) {
					coveredLines.add(ast.getRegion().getStart().getLine());
				}
			}
			coveredLines.sort(null);
			
		}
		
		// TODO Auto-generated method stub
		
	}
	public List<Integer> getCoverage() {
		
		return coveredLines;
	}

	@Override
	public void finishedExecutingWithException(ModuleElement ast, EolRuntimeException exception, IEolContext context) {
		// TODO Auto-generated method stub
		
	}
	public void resetCoveredLinse() {
		coveredLines.clear();
	}
	
}