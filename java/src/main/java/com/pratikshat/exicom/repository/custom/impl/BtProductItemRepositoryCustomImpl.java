package com.pratikshat.exicom.repository.custom.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;

import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.BtProductItem;
import com.pratikshat.exicom.entity.BtProducts;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.Location;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.ServiceRecource;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.interceptor.WorkflowExecuter;
import com.pratikshat.exicom.repository.custom.BtProductItemRepositoryCustom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class BtProductItemRepositoryCustomImpl implements BtProductItemRepositoryCustom {

    @PersistenceContext
    EntityManager em;

    @Autowired
    WorkflowExecuter executer;

    @Autowired
    JdbcTemplate jdbc;

    private final static String INVALID_NEGATIVE_QUANTITY_MESSAGE = "Stock does not exists";

    @Override
    public BtProductItem saveDetails(BtProductItem t, LoginUser user) throws BadRequestException, UnauthoriseException {
        executer.executeWorkflow(em, t, "INSERT", "PRE", user);
        em.persist(t);
        String manualId = "PI-BT-" + MiscellaneousUtils.fillZero("" + t.getId(), 5);
        t.setManualId(manualId);
        executer.executeWorkflow(em, t, "INSERT", "POST", user);
        return t;
    }

    @Override
    public BtProductItem updateDetails(BtProductItem t, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        executer.executeWorkflow(em, t, "UPDATE", "PRE", user);
        em.persist(t);
        executer.executeWorkflow(em, t, "UPDATE", "POST", user);
        return t;
    }

    @Override
    public BtProductItem deleteDetails(BtProductItem t, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        em.remove(t);
        return t;
    }

    @Override
    public void addQuantity(BtProductItem item, Float quantity, Boolean isCustomer, int stateId, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        if (item == null)
            throw new BadRequestException("Invalid stock item");
        System.out.println("Item id" + item.getId() + " ManualId " + item.getManualId());
        if (quantity < 0)
            throw new BadRequestException("Invalid quantity");
        if (item.getId() == null || item.getId().equals(0L))
            throw new BadRequestException("Invalid product item id");
        if (isCustomer) {
            if (stateId == 1) {
                Float tQty = item.getCustomerTotalOkQuantity();
                tQty += quantity;
                item.setCustomerTotalOkQuantity(tQty);
            } else if (stateId == 2) {
                Float tQty = item.getCustomerTotalFaultyQuantity();
                tQty += quantity;
                item.setCustomerTotalFaultyQuantity(tQty);
            } else if (stateId == 3) {
                Float tQty = item.getCustomerTotalScrapQuantity();
                tQty += quantity;
                item.setCustomerTotalScrapQuantity(tQty);
            } else if (stateId == 4) {
                Float tQty = item.getCustomerTotalRejectedQuantity();
                tQty += quantity;
                item.setCustomerTotalRejectedQuantity(tQty);
            } else if (stateId == 5) {
                Float tQty = item.getCustomerTotalBerQuantity();
                tQty += quantity;
                item.setCustomerTotalBerQuantity(tQty);
            } else
                throw new BadRequestException("Invalid state id");
            Float ttQty = item.getCustomerTotalQuantity();
            ttQty += quantity;
            item.setCustomerTotalQuantity(ttQty);
            em.persist(item);
        } else {
            if (stateId == 1) {
                Float tQty = item.getExicomTotalOkQuantity();
                tQty += quantity;
                item.setExicomTotalOkQuantity(tQty);
            } else if (stateId == 2) {
                Float tQty = item.getExicomTotalFaultyQuantity();
                tQty += quantity;
                item.setExicomTotalFaultyQuantity(tQty);
            } else if (stateId == 3) {
                Float tQty = item.getExicomTotalScrapQuantity();
                tQty += quantity;
                item.setExicomTotalScrapQuantity(tQty);
            } else if (stateId == 4) {
                Float tQty = item.getExicomTotalRejectedQuantity();
                tQty += quantity;
                item.setExicomTotalRejectedQuantity(tQty);
            } else if (stateId == 5) {
                Float tQty = item.getExicomTotalBerQuantity();
                tQty += quantity;
                item.setExicomTotalBerQuantity(tQty);
            } else
                throw new BadRequestException("Invalid state id");
            Float ttQty = item.getExicomTotalQuantity();
            ttQty += quantity;
            item.setExicomTotalQuantity(ttQty);
            executer.executeWorkflow(em, item, "UPDATE", "PRE", user);

            em.persist(item);

            executer.executeWorkflow(em, item, "UDPATE", "POST", user);
        }

    }

    @Override
    public void addQuantity(Long btProductItemId, Float quantity, Boolean isCustomer, int stateId, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        BtProductItem item = em.find(BtProductItem.class, btProductItemId);
        addQuantity(item, quantity, isCustomer, stateId, user);

    }

    @Override
    public void addQuantity(BtProducts btProducts, Location location, Float quantity, Boolean isCustomer, int stateId,
            LoginUser user) throws BadRequestException, UnauthoriseException {
        BtProductItem _item = null;
        try {
            _item = em
                    .createQuery("select item from BtProductItem item where item.product=?1 and item.warehouse=?2",
                            BtProductItem.class)
                    .setParameter(1, btProducts).setParameter(2, location).getSingleResult();
        } catch (NoResultException e) {

            _item = new BtProductItem();
            _item.setCustomerTotalBerQuantity(0F);
            _item.setCustomerTotalFaultyQuantity(0F);
            _item.setCustomerTotalOkQuantity(0F);
            _item.setCustomerTotalQuantity(0F);
            _item.setCustomerTotalRejectedQuantity(0F);
            _item.setCustomerTotalScrapQuantity(0F);
            _item.setExicomTotalBerQuantity(0F);
            _item.setExicomTotalFaultyQuantity(0F);
            _item.setExicomTotalOkQuantity(0F);
            _item.setExicomTotalQuantity(0F);
            _item.setExicomTotalRejectedQuantity(0F);
            _item.setExicomTotalScrapQuantity(0F);
            _item.setOwner(new EmployeeDetails(user.getUsername()));
            _item.setProduct(btProducts);
            _item.setWarehouse(location);
            saveDetails(_item, user);

        } catch (NonUniqueResultException ex) {
            throw new BadRequestException("Invalid product item id too many records found");
        }
        addQuantity(_item, quantity, isCustomer, stateId, user);
    }

    @Override
    public void subQuantity(BtProductItem item, Float quantity, Boolean isCustomer, int stateId, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        if (quantity < 0)
            throw new BadRequestException("Invalid item quantity");
        if (item.getId() == null || item.getId().equals(0))
            throw new BadRequestException("Invalid product item id");
        if (isCustomer) {
            if (stateId == 1) {
                Float tQty = item.getCustomerTotalOkQuantity();
                tQty -= quantity;
                if (tQty < 0)
                    throw new BadRequestException(BtProductItemRepositoryCustomImpl.INVALID_NEGATIVE_QUANTITY_MESSAGE);
                item.setCustomerTotalOkQuantity(tQty);
            } else if (stateId == 2) {
                Float tQty = item.getCustomerTotalFaultyQuantity();
                tQty -= quantity;
                if (tQty < 0)
                    throw new BadRequestException(BtProductItemRepositoryCustomImpl.INVALID_NEGATIVE_QUANTITY_MESSAGE);
                item.setCustomerTotalFaultyQuantity(tQty);

            } else if (stateId == 3) {
                Float tQty = item.getCustomerTotalScrapQuantity();
                tQty -= quantity;
                if (tQty < 0)
                    throw new BadRequestException(BtProductItemRepositoryCustomImpl.INVALID_NEGATIVE_QUANTITY_MESSAGE);
                item.setCustomerTotalScrapQuantity(tQty);

            } else if (stateId == 4) {
                Float tQty = item.getCustomerTotalRejectedQuantity();
                tQty -= quantity;
                if (tQty < 0)
                    throw new BadRequestException(BtProductItemRepositoryCustomImpl.INVALID_NEGATIVE_QUANTITY_MESSAGE);
                item.setCustomerTotalRejectedQuantity(tQty);
            } else if (stateId == 5) {
                Float tQty = item.getCustomerTotalBerQuantity();
                tQty -= quantity;
                if (tQty < 0)
                    throw new BadRequestException(BtProductItemRepositoryCustomImpl.INVALID_NEGATIVE_QUANTITY_MESSAGE);
                item.setCustomerTotalBerQuantity(tQty);
            } else
                throw new BadRequestException("Invalid state id");
            Float ttQty = item.getCustomerTotalQuantity();
            ttQty -= quantity;
            item.setCustomerTotalQuantity(ttQty);
            em.persist(item);
        } else {
            if (stateId == 1) {
                Float tQty = item.getExicomTotalOkQuantity();
                tQty -= quantity;
                if (tQty < 0)
                    throw new BadRequestException(BtProductItemRepositoryCustomImpl.INVALID_NEGATIVE_QUANTITY_MESSAGE);
                item.setExicomTotalOkQuantity(tQty);
            } else if (stateId == 2) {
                Float tQty = item.getExicomTotalFaultyQuantity();
                tQty -= quantity;
                if (tQty < 0)
                    throw new BadRequestException(BtProductItemRepositoryCustomImpl.INVALID_NEGATIVE_QUANTITY_MESSAGE);
                item.setExicomTotalFaultyQuantity(tQty);
            } else if (stateId == 3) {
                Float tQty = item.getExicomTotalScrapQuantity();
                tQty -= quantity;
                if (tQty < 0)
                    throw new BadRequestException(BtProductItemRepositoryCustomImpl.INVALID_NEGATIVE_QUANTITY_MESSAGE);
                item.setExicomTotalScrapQuantity(tQty);
            } else if (stateId == 4) {
                Float tQty = item.getExicomTotalRejectedQuantity();
                tQty -= quantity;
                if (tQty < 0)
                    throw new BadRequestException(BtProductItemRepositoryCustomImpl.INVALID_NEGATIVE_QUANTITY_MESSAGE);
                item.setExicomTotalRejectedQuantity(tQty);
            } else if (stateId == 5) {
                Float tQty = item.getExicomTotalBerQuantity();
                tQty -= quantity;
                if (tQty < 0)
                    throw new BadRequestException(BtProductItemRepositoryCustomImpl.INVALID_NEGATIVE_QUANTITY_MESSAGE);
                item.setExicomTotalBerQuantity(tQty);
            } else
                throw new BadRequestException("Invalid state id");
            Float ttQty = item.getExicomTotalQuantity();
            ttQty -= quantity;
            item.setExicomTotalQuantity(ttQty);
            em.persist(item);
        }

    }

    @Override
    public void subQuantity(Long btProductItemId, Float quantity, Boolean isCustomer, int stateId, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        BtProductItem item = null;
        try {
            em.find(BtProductItem.class, btProductItemId);
        } catch (NoResultException e) {
            throw new BadRequestException("Stock does not exist");
        }

        subQuantity(item, quantity, isCustomer, stateId, user);
    }

    @Override
    public void subQuantity(BtProducts btProducts, Location location, Float quantity, Boolean isCustomer, int stateId,
            LoginUser user) throws BadRequestException, UnauthoriseException {
        BtProductItem item = null;
        try {
            item = em
                    .createQuery("select item from BtProductItem item where item.product=?1 and item.warehouse=?2",
                            BtProductItem.class)
                    .setParameter(1, btProducts).setParameter(2, location).getSingleResult();
        } catch (NoResultException e) {
            throw new BadRequestException("Stock does not exists");
        } catch (NonUniqueResultException ex) {
            throw new BadRequestException("Invalid product item id, Multiple record found ");
        }

        subQuantity(item, quantity, isCustomer, stateId, user);
    }

    @Override
    public List<Map<String, Object>> advanceSearch(List<Long> locationIds, Map<String, Object> search, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT item.`customer_total_ber_quantity` AS customerTotalBerQuantity, "
                + " item.`customer_total_faulty_quantity` AS customerTotalFaultyQuantity,"
                + " item.`customer_total_ok_quantity` AS customerTotalOkQuantity,"
                + " item.`customer_total_quantity` AS customerTotalQuantity,"
                + " item.`customer_total_rejected_quantity` AS customerTotalRejectedQuantity,"
                + " item.`customer_total_scrap_quantity` AS customerTotalScrapQuantity,"
                + " item.`exicom_total_ber_quantity` AS exicomTotalBerQuantity,"
                + " item.`exicom_total_faulty_quantity` AS exicomTotalFaultyQuantity,"
                + " item.`exicom_total_ok_quantity` AS exicomTotalOkQuantity,"
                + " item.`exicom_total_quantity` AS exicomTotalQuantity,"
                + " item.`exicom_total_rejected_quantity` AS exicomTotalRejectedQuantity,"
                + " item.`exicom_total_scrap_quantity` AS exicomTotalScrapQuantity,"
                + " item.`manual_id` AS productItemManualId," + " loc.`location_name` AS locationName,"
                + " loc.`plant` AS locationPlant," + " btp.id AS productId," + " btp.`manual_id` AS productPartcode,"
                + " btp.`product_type` AS productType," + " btp.`product_description` AS productDescription"
                + " FROM `bt_product_item` item "
                + " JOIN `location` loc ON item.`warehouse_location_id`=loc.location_id"
                + " JOIN `bt_products` btp ON btp.id=item.`product_id` where 1=1");

        List<Object> param = new ArrayList();

        if (locationIds != null && !locationIds.isEmpty()) {
            String loc = "";
            int lent = locationIds.size();
            for (int i = 0; i < lent; i++) {
                if (i > 0)
                    loc += ",";
                loc += locationIds.get(i);
            }
            sb.append(" and loc.location_id in (" + loc + ")");
        }
        if (search.get("locationName") != null && !search.get("locationName").toString().equalsIgnoreCase("")) {
            sb.append("and loc.location_name like ?");
            param.add(prepareForLinke(search.get("locationName")));
        }
        if (search.get("locationPlant") != null && !search.get("locationPlant").toString().equalsIgnoreCase("")) {
            sb.append(" and loc.plant like ?");
            param.add(prepareForLinke(search.get("locationPlant")));
        }
        if (search.get("productPartcode") != null && !search.get("productPartcode").toString().equalsIgnoreCase("")) {
            sb.append(" and btp.`manual_id` like ? ");
            param.add(prepareForLinke(search.get("productPartcode")));
        }
        if (search.get("productDescription") != null
                && !search.get("productDescription").toString().equalsIgnoreCase("")) {
            sb.append(" and btp.`product_description` like ?");
            param.add(prepareForLinke(search.get("productDescription")));
        }

        return jdbc.queryForList(sb.toString(), param.toArray());
    }

    String prepareForLinke(Object obj) {

        return "%" + obj + "%";
    }

    @Override
    public List<Map<String, Object>> advanceSearch(Long locationId, List<Long> locationIds, Map<String, Object> search,
            LoginUser user) throws BadRequestException, UnauthoriseException {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT item.`customer_total_ber_quantity` AS customerTotalBerQuantity, "
                + " item.`customer_total_faulty_quantity` AS customerTotalFaultyQuantity,"
                + " item.`customer_total_ok_quantity` AS customerTotalOkQuantity,"
                + " item.`customer_total_quantity` AS customerTotalQuantity,"
                + " item.`customer_total_rejected_quantity` AS customerTotalRejectedQuantity,"
                + " item.`customer_total_scrap_quantity` AS customerTotalScrapQuantity,"
                + " item.`exicom_total_ber_quantity` AS exicomTotalBerQuantity,"
                + " item.`exicom_total_faulty_quantity` AS exicomTotalFaultyQuantity,"
                + " item.`exicom_total_ok_quantity` AS exicomTotalOkQuantity,"
                + " item.`exicom_total_quantity` AS exicomTotalQuantity,"
                + " item.`exicom_total_rejected_quantity` AS exicomTotalRejectedQuantity,"
                + " item.`exicom_total_scrap_quantity` AS exicomTotalScrapQuantity,"
                + " item.`manual_id` AS productItemManualId," + " loc.`location_name` AS locationName,"
                + " loc.`plant` AS locationPlant," + " btp.id AS productId," + " btp.`manual_id` AS productPartcode,"
                + " btp.`product_type` AS productType," + " btp.`product_description` AS productDescription"
                + " FROM `bt_product_item` item "
                + " JOIN `location` loc ON item.`warehouse_location_id`=loc.location_id"
                + " JOIN `bt_products` btp ON btp.id=item.`product_id` where loc.location_id =? ");

        List<Object> param = new ArrayList();

        param.add(locationId);

        if (locationIds != null && !locationIds.isEmpty()) {
            String loc = "";
            int lent = locationIds.size();
            for (int i = 0; i < lent; i++) {
                if (i > 0)
                    loc += ",";
                loc += locationIds.get(i);
            }
            sb.append(" and loc.location_id in (" + loc + ")");
        }
        if (search.get("locationName") != null && !search.get("locationName").toString().equalsIgnoreCase("")) {
            sb.append("and loc.location_name like ?");
            param.add(prepareForLinke(search.get("locationName")));
        }
        if (search.get("locationPlant") != null && !search.get("locationPlant").toString().equalsIgnoreCase("")) {
            sb.append(" and loc.plant like ?");
            param.add(prepareForLinke(search.get("locationPlant")));
        }
        if (search.get("productPartcode") != null && !search.get("productPartcode").toString().equalsIgnoreCase("")) {
            sb.append(" and btp.`manual_id` like ? ");
            param.add(prepareForLinke(search.get("productPartcode")));
        }
        if (search.get("productDescription") != null
                && !search.get("productDescription").toString().equalsIgnoreCase("")) {
            sb.append(" and btp.`product_description` like ?");
            param.add(prepareForLinke(search.get("productDescription")));
        }

        if (search.get("productType") != null && search.get("productType").toString().equalsIgnoreCase("")) {
            sb.append(" and btp.product_type like ? ");
            param.add(prepareForLinke(search.get("productType")));
        }

        return jdbc.queryForList(sb.toString(), param.toArray());
    }

    @Override
    public void addQuantity(BtProducts btProducts, Float quantity, Boolean isCustomer, int stateId, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        try {
            ServiceRecource sr = em.createQuery(
                    "select sr from ServiceRecource sr join sr.user usr join sr.location loc where usr.emailId=?1",
                    ServiceRecource.class).setParameter(1, user.getUsername()).getSingleResult();
            addQuantity(btProducts, sr.getLocation(), quantity, isCustomer, stateId, user);
        } catch (NoResultException e) {
            throw new BadRequestException("Service resource not found");
        } catch (NonUniqueResultException ex) {
            throw new BadRequestException("Multiple resource found");
        }
    }

    @Override
    public void subQuantity(BtProducts btProducts, Float quantity, Boolean isCustomer, int stateId, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        try {
            ServiceRecource sr = em.createQuery(
                    "select sr from ServiceRecource sr join sr.user usr join sr.location loc where usr.emailId=?1",
                    ServiceRecource.class).setParameter(1, user.getUsername()).getSingleResult();
            subQuantity(btProducts, sr.getLocation(), quantity, isCustomer, stateId, user);
        } catch (NoResultException e) {
            throw new BadRequestException("Service resource not found");
        } catch (NonUniqueResultException ex) {
            throw new BadRequestException("Multiple resource found");
        }

    }

}
