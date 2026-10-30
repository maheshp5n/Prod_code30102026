package com.pratikshat.exicom.repository;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.config.CustomeClassLoader;
import com.pratikshat.exicom.entity.BtApproval;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;

public class BtApprovalRepositoryCustomeImpl implements BtApprovalRepositoryCustome{
	
	@Value("${app.bt.approvalflow.path}")
	String btApprovalFlowPath;
	@Value("${app.workflow.classpath}")
	String classpath;

	@PersistenceContext
	EntityManager em;

	@Autowired
	CustomeClassLoader clsLoader;
	
	Logger LOGGER=LoggerFactory.getLogger(BtApprovalRepository.class);

	@Override
	public BtApproval saveDetails(BtApproval t, LoginUser user) throws BadRequestException, UnauthoriseException {
		LOGGER.info("This is testing for Approval logger");
		System.out.println("This is testing system out");
		em.persist(t);
		try {
			String ct=nativeCompilation(t.getCtOnAction(), ""+t.getId());
			t.setCtClassName(ct);
			t.setCtClassPath(btApprovalFlowPath);
			em.persist(t);
			clsLoader.reload();
		} catch (BadRequestException e) {
			
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			
			e.printStackTrace();
			throw new BadRequestException(e);
		}
		return t;
	}

	@Override
	public BtApproval updateDetails(BtApproval t, LoginUser user) throws BadRequestException, UnauthoriseException {
		em.persist(t);
		try {
			String ct=nativeCompilation(t.getCtOnAction(), ""+t.getId());
			t.setCtClassName(ct);
			t.setCtClassPath(btApprovalFlowPath);
			em.persist(t);
			clsLoader.reload();

		} catch (BadRequestException e) {
			
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			
			e.printStackTrace();
			throw new BadRequestException(e);
		}
		return t;
	}

	@Override
	public BtApproval deleteDetails(BtApproval t, LoginUser user) throws BadRequestException, UnauthoriseException {
		
		return null;
	}

	
	private String nativeCompilation(String source,String fileName) throws BadRequestException, IOException {
		String tempfile= System.getProperty("java.io.tmpdir");
		StringBuilder sb=new StringBuilder();
		sb.append("import com.pratikshat.exicom.entity.*;");
		sb.append("import com.pratikshat.exicom.interceptor.BeanUtil;");
		
		sb.append("public class BtApprovalCart"+fileName+" implements com.pratikshat.exicom.config.ApprovalWorkflowCrat{");
		sb.append("public boolean isValid(Object obj,String dmlCommand,LoginUser user,javax.persistence.EntityManager em,BtApprovalStapes stape) throws com.pratikshat.exicom.exception.BadRequestException,com.pratikshat.exicom.exception.UnauthoriseException{");
		sb.append(source);
		sb.append("}");
		sb.append("}");
		tempfile=tempfile+File.separator+"pulse_exi_crm";
		//CHECK FOLDER EXISTS OR NOT
		File checkFolder=new File(tempfile);
		if(!checkFolder.exists()) {
			checkFolder.mkdir();
		}
		//end check folder
		File tmpSourcefile=new File(tempfile+File.separator+"BtApprovalCart"+fileName+".java");
		PrintWriter out=new PrintWriter(tmpSourcefile);
		out.write(sb.toString());
		out.close();
		
		LOGGER.info(sb.toString());
		LOGGER.info(tmpSourcefile.getAbsolutePath());
		
		LOGGER.info("javac -sourcepath {} -classpath {} -d {} {}",checkFolder.getAbsolutePath(),classpath,btApprovalFlowPath,tmpSourcefile.getAbsoluteFile());
		
		ProcessBuilder bp=new ProcessBuilder(new String[] {"javac","-sourcepath",checkFolder.getAbsolutePath(),"-classpath",classpath,"-d",btApprovalFlowPath,tmpSourcefile.getAbsolutePath()});
		bp.directory(checkFolder);
		
		Process ps=bp.start();
		
		StringBuilder errord=new StringBuilder();
		InputStream is=ps.getInputStream();
		int c;
		while((c=is.read())!=-1) {
			System.out.print((char)c);
			errord.append((char)c);
		}
		InputStream err=ps.getErrorStream();
		while((c=err.read())!=-1) {
			System.out.print((char)c);
			errord.append((char)c);
		}
//		BufferedReader br=new BufferedReader(new InputStreamReader(ps.getInputStream()));
//		try {
//			String line;
//			LOGGER.info("Started compilation thread");
//			while((line=br.readLine())!=null) {
//				LOGGER.info(line);
//				errord.append(line);
//			}
//			br=new BufferedReader(new InputStreamReader(ps.getErrorStream()));
//			while((line=br.readLine())!=null) {
//				LOGGER.error(line);
//				errord.append(line);
//				throw new BadRequestException(line);
//			}
//			if(errord.toString().indexOf("error")>-1)
//				throw new BadRequestException(errord.toString());
//			
//		}catch(Exception ex) {
//			throw new BadRequestException(ex.getMessage());
//		}
//		finally {
//			br.close();
//		}
		
		LOGGER.info("LOOG STRING "+errord.toString());
		
		if(errord.toString().indexOf("error")>-1)
			throw new BadRequestException(errord.toString());
		LOGGER.info("Compiled ");
		return "BtApprovalCart"+fileName;
	}

	@Override
	public void enableDisable(Long id, boolean flag) {
		BtApproval approval=null;
		try {
			approval=em.createQuery("select btapr from BtApproval btapr where btapr=?1",BtApproval.class).setParameter(1, new BtApproval(id)).getSingleResult();
			approval.setActive(flag);
			em.persist(approval);
		}catch(Exception ex) {
			LOGGER.error("Error ",ex);
		}
	}
}