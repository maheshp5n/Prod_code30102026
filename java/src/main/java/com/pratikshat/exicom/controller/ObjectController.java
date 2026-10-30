package com.pratikshat.exicom.controller;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.LoginUser;


@RestController
@RequestMapping("api/object")
public class ObjectController {
	
	@Value("${app.dbtablename}")
	String dbTableName;
	
	@Autowired
	com.pratikshat.exicom.repository.ObjectDeffinitionRepo obRepo;
	@Autowired
	com.pratikshat.exicom.repository.ObjectFieldDefinitionRepo obFieldsRepo;
	
	@Autowired
	JdbcTemplate db;
	
	Map<String,String> objectDbLabels;
	
	@GetMapping("forlist")
	public List<Map<String,String>> getObjectForList(){
		return obRepo.getObjectForList();
	}
	
	@GetMapping
	public java.util.List<com.pratikshat.exicom.entity.ObjectDefinition> getObjectDefination(){
		return obRepo.findAll();
	}
	@GetMapping("{objectname}")
	public com.pratikshat.exicom.entity.ObjectDefinition getObject(@PathVariable("objecctname")String objectname){
		return obRepo.findByObjectName(objectname);
	}
	@GetMapping("{objectname}/methods")
	public java.util.List<com.pratikshat.exicom.entity.ObjectFieldDefinition> getObjectFiledDef(@PathVariable("objectname")String objectname){
		return obFieldsRepo.findByObjectDef_ObjectName(objectname);
	}
	
	@PutMapping("refreshobject")
	public ResponseEntity<?> refreshObject() throws Exception {
		objectDbLabels=getObjDbLabeldef();
		
		System.out.println(objectDbLabels);
		
		ClassPathScanningCandidateComponentProvider scannerProvider=new ClassPathScanningCandidateComponentProvider(false);
		scannerProvider.addIncludeFilter(new AnnotationTypeFilter(javax.persistence.Entity.class));
		
		for(BeanDefinition bd:scannerProvider.findCandidateComponents("com.pratikshat.exicom.entity")) {
			
			String classpathfullName=bd.getBeanClassName();
			int lastDot=classpathfullName.lastIndexOf(".");
			String onlyClassName=classpathfullName.substring(lastDot+1);
			
			System.out.println(onlyClassName);
			
			Class cls=Class.forName(classpathfullName);
			com.pratikshat.exicom.entity.ObjectDefinition _objectDefination=obRepo.findByObjectName(onlyClassName);
			if(_objectDefination==null)
				_objectDefination=new com.pratikshat.exicom.entity.ObjectDefinition();
			
			_objectDefination.setObjectName(onlyClassName);
			_objectDefination.setPackageName("com.pratikshat.exicom.entity");
			
			String bl=objectDbLabels.get(onlyClassName.toLowerCase());
			
			_objectDefination.setBojectLabelDbName(bl);
			
			System.out.println("Object Name in Lower case"+bl);
			if(bl==null)
				continue;
			_objectDefination.setObjectLableName(bl.replaceAll("_", " ").toUpperCase());
			
			obRepo.save(_objectDefination);
			
			Map<String,String> fildDef=getFiledLabDef(bl);
			
			//System.out.println(fildDef);
			
			for(Field fld:cls.getDeclaredFields()) {
				
				boolean conti=false;
				String fldDbName="";
				//
				if(fld.isAnnotationPresent(ManyToOne.class)||fld.isAnnotationPresent(OneToOne.class)) {
					
					if(fld.isAnnotationPresent(JoinColumn.class)) {
						JoinColumn jnColumn=fld.getAnnotation(JoinColumn.class);
						fldDbName = jnColumn.name();
						//System.out.println("Join Column Name found "+fldDbName);
					}
					else {
						//System.out.println("Join clumn Name not found for "+fld.getName() +" Object "+cls.getName());
						Class returnType=fld.getType();
						for(Field _idfld:returnType.getDeclaredFields()) {
							
							if(_idfld.isAnnotationPresent(javax.persistence.Id.class)) {
								//System.out.println("Id Found "+_idfld.getName()+" Returning Object "+returnType.getName());
								fldDbName= fildDef.get((fld.getName().toLowerCase()+_idfld.getName().toLowerCase()));
							}
						}
					}
				}
				else {
					if(fld.isAnnotationPresent(Column.class)) {
						Column clm=fld.getAnnotation(Column.class);
						fldDbName=clm.name();
						if(fldDbName==null||fldDbName.equalsIgnoreCase("")) {
							fldDbName=fildDef.get(fld.getName().toLowerCase());
						}
					}
					else
						fldDbName=fildDef.get(fld.getName().toLowerCase());
				}
				
				String keyName="";
				
				for(Annotation ant:fld.getAnnotations()) {
				   String _annotation=ant.toString();
				   if(_annotation.contains("Id")) {
					   keyName="PK";
				   }
				   if(_annotation.contains("ManyToOne")){
					   keyName="FK";
					   
				   }
				   if(_annotation.contains("Transient")) {
					   conti=true;
				   }
				   if(_annotation.contains("OneToMany")) {
					   conti=true;
				   }
				}
				if(conti)
					continue;
				try {
					com.pratikshat.exicom.entity.ObjectFieldDefinition _fields= obFieldsRepo.findByObjectDefAndObjectFieldName(_objectDefination, fld.getName());
					if(_fields==null)
						_fields=new com.pratikshat.exicom.entity.ObjectFieldDefinition();
					_fields.setObjectDef(new com.pratikshat.exicom.entity.ObjectDefinition(_objectDefination.getObjectName()));
					try {
//						_fields.setObjectFieldDbName(fildDef.get(fld.getName().toLowerCase()));	
//						_fields.setObjectFieldLabelName(fildDef.get(fld.getName().toLowerCase()).replaceAll("_", " ").toUpperCase());
						_fields.setObjectFieldDbName(fldDbName);
						_fields.setObjectFieldLabelName(fldDbName.replaceAll("_", " ").toUpperCase());
					}
					catch(Exception ex) {
						System.out.println("Object Name Conversion Exception for "+_objectDefination.getObjectName()+", Field Nmae =>"+fld.getName() +" Parsed Filed Name "+fldDbName);
					}
					_fields.setObjectFieldName(fld.getName());
					_fields.setObjectFieldType(fld.getType().getName());
					
					
					
					_fields.setKeyName(keyName);
					_fields.setKeyReferences(fld.getType().getName());
					
					obFieldsRepo.save(_fields);
					System.out.println("Saved Object Name => "+_objectDefination.getObjectName()+ " Db Field Name Saved "+fldDbName+" properties name "+fld.getName()+" Return type "+fld.getType().getName());

				}catch(Exception ex) {
					System.out.println("Unable to save ==> "+ex.getMessage()+"Object Name => "+_objectDefination.getObjectName()+ " Db Field Name Saved "+fldDbName+" properties name "+fld.getName()+" Return type "+fld.getType().getName());
				}
				
			}
		}
		
		return ResponseEntity.ok("Done");
	}
	@PostMapping("l")
	public List<Map<String,Object>> getData(@RequestBody Map<String,String> ql,@CurrentUser LoginUser user){
			
		String qstring= ql.get("q");//AES.decryptAES("raushan123452146", "1234567890123456", ql.get("q"));
			
			return obRepo.executeQuery(qstring, user);
	
	
	}
	@GetMapping("objecthistory")
	public List<Map<String,Object>> getObjectEditHistory(@RequestParam("objectname")String objectname,@RequestParam("objectid")String objectid){
		return obRepo.getObjectHisotory(objectname, objectid);
	}
	@GetMapping("objecthistorypage")
	public Page<Map<String,Object>> getObjectEditHistory(@RequestParam("objectname")String objectname,@RequestParam("objectid")String objectid,Pageable page){
		return obRepo.getObjectHisotory(objectname, objectid,page);
	}
	
	private String getDbLabelname(String name) {
		StringBuilder builder=new StringBuilder();
		
		if(name.length()>0) {
			for(int i=0;i<name.length();i++) {
				if(Character.isUpperCase(name.charAt(i))) {
					if(i!=0) {
						builder.append("_");
					}
					builder.append(Character.toLowerCase(name.charAt(i)));
				}
				else {
					builder.append(name.charAt(i));
				}
			}
		}
		return builder.toString();
	}
	private Map<String,String> getObjDbLabeldef(){
		List<Map<String,Object>> ls=db.queryForList("show tables");
		Map<String,String> s=new HashMap<String,String>();
		for(Map<String,Object> o :ls) {
			System.out.println(" db name "+dbTableName);
			s.put(o.get("Tables_in_"+dbTableName).toString().replaceAll("_","").toLowerCase(),o.get("Tables_in_"+dbTableName).toString());
		}
		return s;
	}
	private Map<String,String> getFiledLabDef(String dblb){
		List<Map<String,Object>> ls=db.queryForList("DESCRIBE "+dblb);
		Map<String,String> od=new HashMap<String,String>();
		for(Map<String,Object> o:ls) {
			od.put(o.get("Field").toString().replaceAll("_", "").toLowerCase(), o.get("Field").toString());
		}
		return od;
	}
}
