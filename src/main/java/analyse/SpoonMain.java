package analyse;

import java.beans.Statement;
import java.util.List;
import java.util.Set;

import org.eclipse.jdt.core.dom.Block;

import spoon.Launcher;
import spoon.MavenLauncher;
import spoon.compiler.Environment;
import spoon.reflect.CtModel;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtCodeSnippetExpression;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtType;
import spoon.reflect.visitor.filter.TypeFilter;

public class SpoonMain {

	public static void main(String[] args) {
		
		System.out.println("Begin Analysis");

		// Parsing arguments using JCommander
		Arguments arguments = new Arguments();
		boolean isParsed = arguments.parseArguments(args);

		// if there was a problem parsing the arguments then the program is terminated.
		if(!isParsed)
			return;
		
		// Parsed Arguments
		String experiment_source_code = arguments.getSource();
		String experiment_output_filepath = arguments.getTarget();
		
		// Load project (APP_SOURCE only, no TEST_SOURCE for now)
		Launcher launcher = null;
		if(arguments.isMavenProject() ) {
			launcher = new MavenLauncher(experiment_source_code, MavenLauncher.SOURCE_TYPE.APP_SOURCE); // requires M2_HOME environment variable
		}else {
			launcher = new Launcher();
			launcher.addInputResource(experiment_source_code + "/src");
		}
		
		// Setting the environment for Spoon
		Environment environment = launcher.getEnvironment();
		environment.setCommentEnabled(true); // represent the comments from the source code in the AST
		environment.setAutoImports(true); // add the imports dynamically based on the typeReferences inside the AST nodes.
//		environment.setComplianceLevel(0); // sets the java compliance level.
		
		System.out.println("Run Launcher and fetch model.");
		launcher.run(); // creates model of project
		CtModel model = launcher.getModel(); // returns the model of the project

		// basic type filter to retrive all methods in your model
		List<CtMethod> methodList = model.getElements(new TypeFilter<CtMethod>(CtMethod.class));
		/*for(CtMethod method : methodList) {
			System.out.println("method: " + method.getSimpleName());
		}*/
		
		List<CtClass> classList = model.getElements(new TypeFilter<CtClass>(CtClass.class));
		
		for(CtClass cls : classList) {
			Set<CtMethod> methodesClasse = cls.getMethods();
			for(CtMethod md : methodesClasse) {
				CtBlock mdBlock = md.getBody();
				System.out.println(mdBlock.toString()+"\n=============");
				
				//CtCodeSnippetExpression instruction =  launcher.getFactory().createCodeSnippetExpression("System.out.println(\"  GG   \");");
				CtStatement NewInstruction = launcher.getFactory().Code().createCodeSnippetStatement("Analyse.Analyse.logAppel(\""+cls.getSimpleName()+"\",\""+md.getSimpleName()+"\")");
				mdBlock.addStatement(0,NewInstruction);
				md.setBody(mdBlock);
				
				CtBlock mdBlock2 = md.getBody();
				System.out.println(mdBlock2.toString()+"\n=============");
			}
		}
		
		//Modification code source.
		launcher.setSourceOutputDirectory("/home/e20190015195/eclipse-workspace/sortieSpoon/");
		launcher.prettyprint();
		System.out.println("FINI");
		
		
	}
}
