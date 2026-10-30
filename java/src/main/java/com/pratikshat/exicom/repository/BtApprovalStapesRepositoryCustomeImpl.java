package com.pratikshat.exicom.repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.pratikshat.exicom.config.CustomeClassLoader;
import com.pratikshat.exicom.entity.BtApprovalStapes;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;

public class BtApprovalStapesRepositoryCustomeImpl implements BtApprovalStapesRepositoryCustome {
	
	@Value("${app.bt.approvalflow.path}")
	String btApprovalFlowPath;
	@Value("${app.workflow.classpath}")
	String classpath;

	@PersistenceContext
	EntityManager em;


	@Autowired
	CustomeClassLoader loader;
	
	@Override
	public BtApprovalStapes saveDetails(BtApprovalStapes t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		t.setClassLoaderUrl(btApprovalFlowPath);
		em.persist(t);
		try {
			String filename=nativeCompilation(prepareActionBody(t.getOnApprove(),t.getOnReject()),""+t.getId());
			t.setActionClassName(filename);
		} catch (BadRequestException e) {
			throw e;
		} catch (IOException e) {
			throw new BadRequestException(e);
		}
		em.merge(t);
		loader.reload();
		return t;
	}

	@Override
	public BtApprovalStapes updateDetails(BtApprovalStapes t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		em.merge(t);
		try {
			String filename=nativeCompilation(prepareActionBody(t.getOnApprove(),t.getOnReject()),""+t.getId());
			t.setActionClassName(filename);
		} catch (BadRequestException e) {
			throw e;
		} catch (IOException e) {
			throw new BadRequestException(e);
		}
		em.merge(t);
		loader.reload();
		return t;
	}

	@Override
	public BtApprovalStapes deleteDetails(BtApprovalStapes t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	private String nativeCompilation(String source,String fileName) throws BadRequestException, IOException {
		String tempfile= System.getProperty("java.io.tmpdir");
		tempfile=tempfile+File.separator+"pulse_exi_crm";
		//CHECK FOLDER EXISTS OR NOT
		File checkFolder=new File(tempfile);
		if(!checkFolder.exists()) {
			checkFolder.mkdir();
		}
		//end check folder
		StringBuilder sb=new StringBuilder();

		sb.append("import com.pratikshat.exicom.entity.*;");
		sb.append("import com.pratikshat.exicom.interceptor.BeanUtil;");
		
		sb.append("public class BtApprovalStapes"+fileName+" implements com.pratikshat.exicom.config.ApprovalWorkFlowAction{");
		sb.append(source);
		sb.append("}");
		
		File tmpSourcefile=new File(tempfile+File.separator+"BtApprovalStapes"+fileName+".java");
		
		PrintWriter out=new PrintWriter(tmpSourcefile);
		out.write(sb.toString());
		out.close();
		
		ProcessBuilder bp=new ProcessBuilder(new String[] {"javac","-sourcepath",checkFolder.getAbsolutePath(),"-classpath",classpath,"-d",btApprovalFlowPath,tmpSourcefile.getAbsolutePath()});
		bp.directory(checkFolder);
		Process ps=bp.start();
		StringBuilder errord=new StringBuilder();
		BufferedReader br=new BufferedReader(new InputStreamReader(ps.getInputStream()));
		String line;
		while((line=br.readLine())!=null) {
			errord.append(line);
		}
		br=new BufferedReader(new InputStreamReader(ps.getErrorStream()));
		while((line=br.readLine())!=null) {
			errord.append(line);
		}
		if(errord.toString().indexOf("error")>-1)
			throw new BadRequestException(errord.toString());
		return "BtApprovalStapes"+fileName;
	}
	
	private String prepareActionBody(String onApproved,String onReject) {
		StringBuilder sb=new StringBuilder();
		sb.append("public void onApproval(BtApprovalStapes approval,Object object,String approverAction,LoginUser user,javax.persistence.EntityManager _em)throws com.pratikshat.exicom.exception.BadRequestException,com.pratikshat.exicom.exception.UnauthoriseException{");
		sb.append(onApproved);
		
		sb.append("}");
		
		sb.append("public void onRejected(BtApprovalStapes approval,Object object,String approverAction,LoginUser user,javax.persistence.EntityManager _em)throws com.pratikshat.exicom.exception.BadRequestException,com.pratikshat.exicom.exception.UnauthoriseException{");
		sb.append(onReject);
		sb.append("}");
		
		return sb.toString();
	}
	
}
