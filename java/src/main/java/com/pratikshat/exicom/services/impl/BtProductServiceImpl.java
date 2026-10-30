package com.pratikshat.exicom.services.impl;

import com.pratikshat.exicom.entity.BtProducts;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.HsnDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.ProductFamilySubProduct;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.repository.BtProductsRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.services.BtProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class BtProductServiceImpl implements BtProductService {
	
	@PersistenceContext
	EntityManager em;
    @Autowired
    BtProductsRepository dbProducts;
    @Autowired
    UserProfileRepository dbProfile;
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Map<String, Object> getSingle(Object id, LoginUser user) throws BadRequestException, UnauthoriseException {
        if(id==null)
            throw new BadRequestException("Invalid product id");
        boolean view=dbProfile.hasObjectAccess(user.getUsername(), BtProducts.class,UserProfileRepository.VIEW);
        boolean viewAll=dbProfile.hasObjectAccess(user.getUsername(),BtProducts.class,UserProfileRepository.VIEW_ALL);
        if(view||viewAll){
            Long _id=Long.parseLong(id.toString());
            return dbProducts.getSingleProducts(_id);
        }else
            throw new UnauthoriseException("You are not authorised");

    }

    @Override
    public Page<Map<String, Object>> getPage(Pageable page, LoginUser user) throws BadRequestException, UnauthoriseException {
        boolean view=dbProfile.hasObjectAccess(user.getUsername(), BtProducts.class,UserProfileRepository.VIEW);
        boolean viewAll=dbProfile.hasObjectAccess(user.getUsername(),BtProducts.class,UserProfileRepository.VIEW_ALL);
        if(view||viewAll){
            return dbProducts.getPage(page);
        }
        else
            throw new UnauthoriseException("You are not authorised");
    }
    
    @Override
    public Page<Map<String, Object>> getPageFilter(String filterParam,Pageable page, LoginUser user) throws BadRequestException, UnauthoriseException {
        boolean view=dbProfile.hasObjectAccess(user.getUsername(), BtProducts.class,UserProfileRepository.VIEW);
        boolean viewAll=dbProfile.hasObjectAccess(user.getUsername(),BtProducts.class,UserProfileRepository.VIEW_ALL);
        if(view||viewAll){
            return dbProducts.getFilterPage(filterParam, page);
        }
        else
            throw new UnauthoriseException("You are not authorised");
    }

    @Override
	@Transactional(rollbackFor= {BadRequestException.class,UnauthoriseException.class,Exception.class},propagation = Propagation.REQUIRES_NEW,isolation = Isolation.READ_UNCOMMITTED)
	public Map<String, Object> saveObject(Map<String, Object> request, LoginUser user) throws BadRequestException, UnauthoriseException {
        if(!dbProfile.hasObjectAccess(user.getUsername(),BtProducts.class,UserProfileRepository.CREATE))
            throw new UnauthoriseException("You are not authorised");
    	 
      		if(request.get("productType")==null)
      			throw new BadRequestException("Product type cannot be null");
      	    		
      		BtProducts btProduct=new BtProducts();
      		if(request.get("productDescription")!=null && !request.get("productDescription").toString().equalsIgnoreCase(""))
      		{
      		btProduct.setProductDescription(request.get("productDescription").toString());
      		}
      		if(request.get("productPrice")!=null && !request.get("productPrice").toString().equalsIgnoreCase(""))
      		{
      		btProduct.setProductPrice(Float.parseFloat(request.get("productPrice").toString()));
      		}
      		if(request.get("productType")!=null && !request.get("productType").toString().equalsIgnoreCase(""))
      		{
      		btProduct.setProductType(request.get("productType").toString());
      		}
      		if(request.get("subProductFamily")!=null && !request.get("subProductFamily").toString().equalsIgnoreCase(""))
      		{
      		btProduct.setSubProductFamily(new ProductFamilySubProduct(Long.parseLong(request.get("subProductFamily").toString())));
      		}
      		btProduct.setOwner(new EmployeeDetails(user.getUsername()));
      		btProduct.setActive(true);
      		btProduct.setSynced(false);
      		if(request.get("hsnDetails")!=null && !request.get("hsnDetails").toString().equalsIgnoreCase(""))
      		{
      			btProduct.setHsnDetails(new HsnDetails(request.get("hsnDetails").toString()));	
      		}
      		if(request.get("partcode")!=null && !request.get("partcode").toString().equalsIgnoreCase(""))
      		{
       		btProduct.setManualId(request.get("partcode").toString());
      		}
      		dbProducts.saveDetails(btProduct, user) ;
			return null; 	
    }

    @Override
	@Transactional(rollbackFor= {BadRequestException.class,UnauthoriseException.class,Exception.class},propagation = Propagation.REQUIRES_NEW,isolation = Isolation.READ_UNCOMMITTED)
	public Map<String, Object> updateObject(Map<String, Object> request, LoginUser user) throws BadRequestException, UnauthoriseException {
        
    	if(request.get("id")!=null)
    	{
    		BtProducts btProduct =  dbProducts.getById(Long.parseLong(request.get("id").toString()));
    		if(btProduct==null) 
    			throw new BadRequestException("Wrong product!");

    		if(request.get("productDescription")!=null && !request.get("productDescription").toString().equalsIgnoreCase(""))
      		{
      		btProduct.setProductDescription(request.get("productDescription").toString());
      		}
      		if(request.get("productPrice")!=null && !request.get("productPrice").toString().equalsIgnoreCase(""))
      		{
      		btProduct.setProductPrice(Float.parseFloat(request.get("productPrice").toString()));
      		}
      		if(request.get("productType")!=null && !request.get("productType").toString().equalsIgnoreCase(""))
      		{
      		btProduct.setProductType(request.get("productType").toString());
      		}
      		if(request.get("subProductFamily")!=null && !request.get("subProductFamily").toString().equalsIgnoreCase(""))
      		{
      		btProduct.setSubProductFamily(new ProductFamilySubProduct(Long.parseLong(request.get("subProductFamily").toString())));
      		}
      		if(request.get("hsnDetails")!=null && !request.get("hsnDetails").toString().equalsIgnoreCase(""))
      		{
      			btProduct.setHsnDetails(new HsnDetails(request.get("hsnDetails").toString()));	
      		}
      		
  		
  		dbProducts.updateDetails(btProduct, user);
  		return null;
    	
    	}else {
  			throw new BadRequestException("Product Id is null");
    	}
    }

    
    @Override	
    @Transactional(rollbackFor= {BadRequestException.class,UnauthoriseException.class,Exception.class},propagation = Propagation.REQUIRES_NEW,isolation = Isolation.READ_UNCOMMITTED)
	public Map<String, Object> updateProductStatus(long productId, boolean status, LoginUser user) throws BadRequestException, UnauthoriseException {
  		dbProducts.updateStatus(productId, status, user);
  		return null;
    }
        
    @Override
    public List<Map<String, Object>> searchForList(String searchkey,LoginUser user,Pageable pageable) throws BadRequestException, UnauthoriseException {
	 Page<Map<String, Object>> list= dbProducts.getBtProductsForListSmall(searchkey, pageable);
	 return list.getContent();
		
	
    }
    
 	@Override
    public List<Map<String, Object>> advanceSearch(Map<String, Object> searchRequest, LoginUser user) throws BadRequestException, UnauthoriseException {
     
    	List<Object> value = new ArrayList<>();
		String queryString ="SELECT btp.id,btp.manual_id AS partCode,btp.active,btp.product_description AS productDescription,btp.product_price AS productPrice,\r\n"
				+ "btp.product_type AS productType,spf.product_family_product_family_id AS productFamilyId,pf.product_family_name AS productFamilyName,btp.sub_product_family_product_family_sub_product_id AS subProductFamily,spf.product_faily_sub_product_name AS subProductFamilyName,btp.synced,btp.created_by_email_id AS createdByEmailId,\r\n"
				+ "btp.last_modified_by_email_id AS lastModifiedByEmailId,btp.created_date AS createdDate,btp.last_modified_date AS lastModifiedDate,\r\n"
				+ "btp.hsn_details_hsn_code AS hsnCode,btp.owner_email_id AS ownerEmailId FROM bt_products btp JOIN product_family_sub_product spf ON btp.sub_product_family_product_family_sub_product_id= spf.product_family_sub_product_id JOIN product_family pf ON spf.product_family_product_family_id =pf.product_family_id  WHERE 1=1 ";
		
		StringBuilder builder = new StringBuilder();
		builder.append(queryString);
		
		if(searchRequest.get("partCode")!=null&&!searchRequest.get("partCode").equals("")) {
			builder.append(" and btp.`manual_id` like ?");
			value.add("%"+searchRequest.get("partCode")+"%");
		}
		if(searchRequest.get("productFamily")!=null&&!searchRequest.get("productFamily").equals("")) {
	     	builder.append(" and pf.product_family_name like ?");
			value.add("%"+searchRequest.get("productFamily")+"%");
		}
		if(searchRequest.get("productSubFamily")!=null&&!searchRequest.get("productSubFamily").equals("")) {
	     	builder.append(" and spf.product_faily_sub_product_name like ?");
			value.add("%"+searchRequest.get("productSubFamily")+"%");
		}
		if(searchRequest.get("productType")!=null&&!searchRequest.get("productType").equals("")) {
			builder.append(" and btp.product_type like ?");
			value.add("%"+searchRequest.get("productType")+"%");
		}
		if(searchRequest.get("productDescription")!=null&&!searchRequest.get("productDescription").equals("")) {
			builder.append(" and btp.product_description like ?");
			value.add("%"+searchRequest.get("productDescription")+"%");
		}
		if(searchRequest.get("hsnCode")!=null&&!searchRequest.get("hsnCode").equals("")) {
			builder.append(" and btp.hsn_details_hsn_code like ?");
			value.add("%"+searchRequest.get("hsnCode")+"%");
		}

		return jdbc.queryForList(builder.toString(),value.toArray());
	}

	@Override
	public List<Map<String, Object>> searchPartCode(String searchkey,LoginUser user,Pageable pageable) {
		 Page<Map<String, Object>> list= dbProducts.getBtProductsForListData(searchkey, pageable);
		 return list.getContent();
	}

	@Override
	public List<Map<String, Object>> searchForList(String search, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public List<Map<String, Object>> searchForList1(String search, LoginUser user, Pageable pageable)
//			throws BadRequestException, UnauthoriseException {
//		// TODO Auto-generated method stub
//		return null;
//	}
   
  
}