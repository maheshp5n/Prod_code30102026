package com.pratikshat.exicom.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.Arrays;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.config.CustomeClassLoader;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.WorkFlowEntity;
import com.pratikshat.exicom.repository.UserProfileRepository;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;


@RestController
@RequestMapping("api/workflow")
public class WorkflowController {
	
	@Value("${app.workflow.location}")
	String workflowLocation;
	@Value("${app.workflow.classpath}")
	String classpath;

	@Autowired
	CustomeClassLoader loader;

	@Autowired
	com.pratikshat.exicom.repository.WorkflowentityRepository dbWorkflow;
	@Autowired
	ServletContext context;


	@Autowired
	UserProfileRepository dbUserprofile;

	
	@Autowired
	SimpMessagingTemplate websocket;
	
	@GetMapping
	public java.util.List<com.pratikshat.exicom.model.response.WorkflowResponse> getWorkflow(){
		System.out.println(context.getRealPath("/"));
		return dbWorkflow.getworkflow();
	}
	
	@PostMapping
	public ResponseEntity<?> submitWorkFlow(@RequestBody com.pratikshat.exicom.model.request.WorkflowRequest request,@CurrentUser LoginUser user,HttpServletResponse response)throws Exception{
		if(!dbUserprofile.hasObjectAccess(user.getUsername(), WorkFlowEntity.class, UserProfileRepository.CREATE)){
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
			
		String classname=request.getObject()+new java.util.Date().getTime();
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		PrintWriter printOut=new PrintWriter(out);
		String classPath=compileNative(genrateSourceFile(classname,request.getOnEntityManager()),classname,printOut,false,user.getUsername());		
		if(out.toString().indexOf("error")!=-1) {
			throw new Exception(out.toString());
		}
		
		com.pratikshat.exicom.entity.WorkFlowEntity _workflow=new com.pratikshat.exicom.entity.WorkFlowEntity();
		_workflow.setActive(true);
		_workflow.setClassLoaderUrl(classPath);
		_workflow.setClassName(classname);
		_workflow.setCreatedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
		_workflow.setCreatedDate(new java.util.Date());
		_workflow.setDescription(request.getDescription());
		
		_workflow.setDmlCommand(request.getDmlCommand());
		_workflow.setModifiedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
		_workflow.setModifiedDate(new java.util.Date());
		_workflow.setObject(new com.pratikshat.exicom.entity.ObjectDefinition(request.getObject()));
		_workflow.setOnevent(request.getOn());
		_workflow.setOwner(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
		_workflow.setCodeSnip(request.getOnEntityManager());
		
		dbWorkflow.save(_workflow);

		loader.reload();
		loader.loadClass(classname);

		try {
			File s=new File(workflowLocation+File.separator+classname+".java");
			if(s.exists()&&s.isFile()) {
				s.delete();
			}
		}catch(Exception ex) {
			System.out.println("Unable to delete file ");
		}
		return ResponseEntity.ok(_workflow);
	}
	@PutMapping("{id}/disableenable")
	public ResponseEntity<?> disableworkflow(@PathVariable("id")Long id,@RequestParam("flag")boolean flag,@CurrentUser LoginUser user,HttpServletResponse response)throws IOException{
		
		if(!dbUserprofile.hasObjectAccess(user.getUsername(), WorkFlowEntity.class, UserProfileRepository.EDIT)){
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		
		com.pratikshat.exicom.entity.WorkFlowEntity _workflow=dbWorkflow.getById(id);


		if(_workflow!=null) {
			_workflow.setActive(flag);
			_workflow.setModifiedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			_workflow.setModifiedDate(new java.util.Date());
			dbWorkflow.save(_workflow);
		}
		return ResponseEntity.ok(dbWorkflow.getworkflow(id));
	}
	@PutMapping("{id}")
	public ResponseEntity<?> editworkflow(@PathVariable("id")Long id,@RequestBody com.pratikshat.exicom.model.request.WorkflowRequest body,HttpServletResponse response,@CurrentUser LoginUser user) throws Exception{
		if(body.getId()!=id) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Bad Id");
			return null;
		}
		com.pratikshat.exicom.entity.WorkFlowEntity _workflow=dbWorkflow.getById(id);
		if(_workflow==null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Entity not found");
			return null;
		}
		
		String classname=_workflow.getClassName();//body.getObject()+new java.util.Date().getTime();
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		PrintWriter printOut=new PrintWriter(out);
		//String classPath=compileCode(genrateSourceFile(classname,body.getOnEntityManager()),classname,printOut);
		String classPath=compileNative(genrateSourceFile(classname,body.getOnEntityManager()),classname,printOut,false,user.getUsername());
		
		if(out.toString().indexOf("error")!=-1) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,out.toString());
			return null;
		}
		_workflow.setClassLoaderUrl(classPath);
		_workflow.setClassName(classname);
		_workflow.setDescription(body.getDescription());
		_workflow.setDmlCommand(body.getDmlCommand());
		_workflow.setModifiedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
		_workflow.setModifiedDate(new java.util.Date());
		_workflow.setObject(new com.pratikshat.exicom.entity.ObjectDefinition(body.getObject()));
		_workflow.setOnevent(body.getOn());
		_workflow.setOwner(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
		_workflow.setCodeSnip(body.getOnEntityManager());
		loader.reload();
		loader.loadClass(classname);
		dbWorkflow.save(_workflow);
		try {
			File s=new File(workflowLocation+File.separator+classname+".java");
			if(s.exists()&&s.isFile()) {
				s.delete();
			}
		}catch(Exception ex) {
			System.out.println("Unable to delete file ");
		}
		return ResponseEntity.ok(body);
	}
	
	
	private String compileCode(String sourccode,String fileName,PrintWriter out) throws Exception{
		
		File sourceFile=new File(workflowLocation+File.separator+fileName+".java"); //File.createTempFile(fileName, ".java");
		sourceFile.deleteOnExit();
		FileWriter fileWriter=new FileWriter(sourceFile);
		fileWriter.write(sourccode);
		fileWriter.close();
		
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        
        File parentDirectory = sourceFile.getParentFile();
        
        fileManager.setLocation(StandardLocation.CLASS_OUTPUT, Arrays.asList(parentDirectory));
        
        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(Arrays.asList(sourceFile));
        
        compiler.getTask(out, fileManager, null, Arrays.asList("-classpath",classpath),null,compilationUnits).call();
      
        fileManager.close();
		
        return parentDirectory.getAbsolutePath();
		
	}
	private String genrateSourceFile(String className,String ongenral) {
		String source=String.format("import com.pratikshat.exicom.entity.*;\n"
				+"import com.pratikshat.exicom.utils.*;\n"
				+"public class %s {"
				+"public void executeTask(javax.persistence.EntityManager em,Object object,com.pratikshat.exicom.entity.LoginUser user) throws Exception{%s}"
				+ "}", className,ongenral==null?"":ongenral);
		
		return source;
	}
	private String compileNative(String sourcecode,String fileName,PrintWriter out,boolean temp,String userName) throws Exception{
		
		File sourceFile=new File(workflowLocation+File.separator+fileName+".java");
		FileWriter fileWriter=new FileWriter(sourceFile);
		fileWriter.write(sourcecode);
		fileWriter.close();
		
		//System.out.println(sourcecode);
		
		StringBuilder sb=new StringBuilder();
		ProcessBuilder pr=new ProcessBuilder(new String[] {"javac","-classpath",classpath,"-d",workflowLocation,sourceFile.getAbsolutePath()});
		Process ps= pr.start();
		BufferedReader br=new BufferedReader(new InputStreamReader(ps.getInputStream()));
		String line;
		while((line=br.readLine())!=null) {
			
			sb.append(line);
			out.print(line);
		}
		br=new BufferedReader(new InputStreamReader(ps.getErrorStream()));
		String errorLine;
		while((errorLine=br.readLine())!=null) {
			
			sb.append(errorLine);
			out.print(errorLine);
		}
		
		if(temp)
			websocket.convertAndSendToUser(userName, "/topic/compile", sb.toString());
			//websocket.convertAndSend("/topic/compile",sb.toString());
		else
			if(sb.toString().indexOf("error")!=-1) throw new Exception("Unable to compile file"+sb.toString());
		return sourceFile.getParentFile().getAbsolutePath();
	}
	@MessageMapping("/compile/{objectName}")
	public void comp(@DestinationVariable("objectName") String objectName,@Payload String data,Principal princple) {
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		PrintWriter printOut=new PrintWriter(out);
		
		try {
			compileNative(genrateSourceFile("Temp"+objectName,data),"Temp"+objectName,printOut,true,princple.getName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
