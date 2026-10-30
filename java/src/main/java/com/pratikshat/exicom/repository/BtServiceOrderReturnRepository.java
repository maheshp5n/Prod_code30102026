package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.BtServiceOrderReturn;
import com.pratikshat.exicom.repository.custom.BtServiceOrderReturnRepositoryCustom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BtServiceOrderReturnRepository
        extends JpaRepository<BtServiceOrderReturn, Long>, BtServiceOrderReturnRepositoryCustom {
    @Query(value = "SELECT "
            + " sro.`id` AS sorId, "
            + " sro.`created_date` AS sorCreatedDate, "
            + " sro.`last_modified_date` AS sorLastModifiedDate, "
            + " sro.`manual_id` AS sorManualId, "
            + " sro.`return_date` AS sorReturnDate, "
            + " sro.`shipping_to` AS sorShippingTo, "
            + " sro.`shippint_mode` AS sorShippingMode, "
            + " sro.`total_number_of_quantity` AS sorTotalNumberOfQuantityReturned, "
            + " sro.`total_number_of_quantity_received` AS sorTotalNumberOfQuantityReceived, "
            + " sro.`tracking_number` AS sorTrackingNumber, "
            + " sro.`created_by_email_id` AS sorCreatedByEmailId, "
            + " sro.`last_modified_by_email_id` AS sorLastModifiedByEmailId, "
            + " sro.`destination_location_location_id` AS sorDestinationLocationId, "
            + " sro.`owner_email_id` AS sorOwnerEmailId, "
            + " sro.`source_location_location_id` AS sorSourceLocationId, "
            + " dloc.location_name AS destinationLocationName, "
            + " sloc.location_name AS sourceLoccationName, "

            + " cemp.`first_name` AS createdByfirstName, "
            + " cemp.`last_name` AS createdBylastName, "
            + " cemp.`middle_name` AS createdBymiddleName, "

            + " oemp.`first_name` AS ownerfirstName, "
            + " oemp.`last_name` AS ownerlastName, "
            + " oemp.`middle_name` AS ownermiddleName, "

            + " lemp.`first_name` AS lastModifiedByfirstName, "
            + " lemp.`last_name` AS lastModifiedBylastName, "
            + " lemp.`middle_name` AS lastModifiedBymiddleName "

            + " FROM   "

            + " `bt_service_order_return` sro  "
            + " JOIN "
            + " location sloc ON sro.`source_location_location_id`=sloc.location_id "
            + " JOIN "
            + " location dloc ON sro.`destination_location_location_id`=dloc.location_id "
            + " JOIN "
            + " employee_details cemp ON cemp.email_id=sro.`created_by_email_id` "
            + " JOIN "
            + " employee_details lemp ON lemp.email_id=sro.`last_modified_by_email_id` "
            + " JOIN "
            + " employee_details oemp ON oemp.email_id = sro.`owner_email_id`", nativeQuery = true, countQuery = "SELECT count(*) as ct "

                    + " FROM   "

                    + " `bt_service_order_return` sro  "
                    + " JOIN "
                    + " location sloc ON sro.`source_location_location_id`=sloc.location_id "
                    + " JOIN "
                    + " location dloc ON sro.`destination_location_location_id`=dloc.location_id "
                    + " JOIN "
                    + " employee_details cemp ON cemp.email_id=sro.`created_by_email_id` "
                    + " JOIN "
                    + " employee_details lemp ON lemp.email_id=sro.`last_modified_by_email_id` "
                    + " JOIN "
                    + " employee_details oemp ON oemp.email_id = sro.`owner_email_id`")
    Page<Map<String, Object>> getServiceOrderReturn(Pageable page);

    @Query(value = "SELECT "
            + " sro.`id` AS sorId, "
            + " sro.`created_date` AS sorCreatedDate, "
            + " sro.`last_modified_date` AS sorLastModifiedDate, "
            + " sro.`manual_id` AS sorManualId, "
            + " sro.`return_date` AS sorReturnDate, "
            + " sro.`shipping_to` AS sorShippingTo, "
            + " sro.`shippint_mode` AS sorShippingMode, "
            + " sro.`total_number_of_quantity` AS sorTotalNumberOfQuantityReturned, "
            + " sro.`total_number_of_quantity_received` AS sorTotalNumberOfQuantityReceived, "
            + " sro.`tracking_number` AS sorTrackingNumber, "
            + " sro.`created_by_email_id` AS sorCreatedByEmailId, "
            + " sro.`last_modified_by_email_id` AS sorLastModifiedByEmailId, "
            + " sro.`destination_location_location_id` AS sorDestinationLocationId, "
            + " sro.`owner_email_id` AS sorOwnerEmailId, "
            + " sro.`source_location_location_id` AS sorSourceLocationId, "
            + " dloc.location_name AS destinationLocationName, "
            + " sloc.location_name AS sourceLoccationName, "

            + " cemp.`first_name` AS createdByfirstName, "
            + " cemp.`last_name` AS createdBylastName, "
            + " cemp.`middle_name` AS createdBymiddleName, "

            + " oemp.`first_name` AS ownerfirstName, "
            + " oemp.`last_name` AS ownerlastName, "
            + " oemp.`middle_name` AS ownermiddleName, "

            + " lemp.`first_name` AS lastModifiedByfirstName, "
            + " lemp.`last_name` AS lastModifiedBylastName, "
            + " lemp.`middle_name` AS lastModifiedBymiddleName "

            + " FROM   "

            + " `bt_service_order_return` sro  "
            + " JOIN "
            + " location sloc ON sro.`source_location_location_id`=sloc.location_id "
            + " JOIN "
            + " location dloc ON sro.`destination_location_location_id`=dloc.location_id "
            + " JOIN "
            + " employee_details cemp ON cemp.email_id=sro.`created_by_email_id` "
            + " JOIN "
            + " employee_details lemp ON lemp.email_id=sro.`last_modified_by_email_id` "
            + " JOIN "
            + " employee_details oemp ON oemp.email_id = sro.`owner_email_id` where dloc.location_id in :locationIds or sloc.location_id in :locationIds", nativeQuery = true, countQuery = "SELECT count(*) as ct "

                    + " FROM   "

                    + " `bt_service_order_return` sro  "
                    + " JOIN "
                    + " location sloc ON sro.`source_location_location_id`=sloc.location_id "
                    + " JOIN "
                    + " location dloc ON sro.`destination_location_location_id`=dloc.location_id "
                    + " JOIN "
                    + " employee_details cemp ON cemp.email_id=sro.`created_by_email_id` "
                    + " JOIN "
                    + " employee_details lemp ON lemp.email_id=sro.`last_modified_by_email_id` "
                    + " JOIN "
                    + " employee_details oemp ON oemp.email_id = sro.`owner_email_id` where dloc.location_id in :locationIds or sloc.location_id in :locationIds")
    Page<Map<String, Object>> getServiceOrderReturn(@Param("locationIds") List<Long>locationIds,Pageable page);



    @Query(value = "SELECT "
            + " sro.`id` AS sorId, "
            + " sro.`created_date` AS sorCreatedDate, "
            + " sro.`last_modified_date` AS sorLastModifiedDate, "
            + " sro.`manual_id` AS sorManualId, "
            + " sro.`return_date` AS sorReturnDate, "
            + " sro.`shipping_to` AS sorShippingTo, "
            + " sro.`shippint_mode` AS sorShippingMode, "
            + " sro.`total_number_of_quantity` AS sorTotalNumberOfQuantityReturned, "
            + " sro.`total_number_of_quantity_received` AS sorTotalNumberOfQuantityReceived, "
            + " sro.`tracking_number` AS sorTrackingNumber, "
            + " sro.`created_by_email_id` AS sorCreatedByEmailId, "
            + " sro.`last_modified_by_email_id` AS sorLastModifiedByEmailId, "
            + " sro.`destination_location_location_id` AS sorDestinationLocationId, "
            + " sro.`owner_email_id` AS sorOwnerEmailId, "
            + " sro.`source_location_location_id` AS sorSourceLocationId, "
            + " dloc.location_name AS destinationLocationName, "
            + " sloc.location_name AS sourceLoccationName, "

            + " cemp.`first_name` AS createdByfirstName, "
            + " cemp.`last_name` AS createdBylastName, "
            + " cemp.`middle_name` AS createdBymiddleName, "

            + " oemp.`first_name` AS ownerfirstName, "
            + " oemp.`last_name` AS ownerlastName, "
            + " oemp.`middle_name` AS ownermiddleName, "

            + " lemp.`first_name` AS lastModifiedByfirstName, "
            + " lemp.`last_name` AS lastModifiedBylastName, "
            + " lemp.`middle_name` AS lastModifiedBymiddleName "

            + " FROM   "

            + " `bt_service_order_return` sro  "
            + " JOIN "
            + " location sloc ON sro.`source_location_location_id`=sloc.location_id "
            + " JOIN "
            + " location dloc ON sro.`destination_location_location_id`=dloc.location_id "
            + " JOIN "
            + " employee_details cemp ON cemp.email_id=sro.`created_by_email_id` "
            + " JOIN "
            + " employee_details lemp ON lemp.email_id=sro.`last_modified_by_email_id` "
            + " JOIN "
            + " employee_details oemp ON oemp.email_id = sro.`owner_email_id` where sro.id=:id", nativeQuery = true)
    Map<String, Object> getServiceOrderReturnById(@Param("id")Long id);

    @Query(value = "SELECT "
            + " sro.`id` AS sorId, "
            + " sro.`created_date` AS sorCreatedDate, "
            + " sro.`last_modified_date` AS sorLastModifiedDate, "
            + " sro.`manual_id` AS sorManualId, "
            + " sro.`return_date` AS sorReturnDate, "
            + " sro.`shipping_to` AS sorShippingTo, "
            + " sro.`shippint_mode` AS sorShippingMode, "
            + " sro.`total_number_of_quantity` AS sorTotalNumberOfQuantityReturned, "
            + " sro.`total_number_of_quantity_received` AS sorTotalNumberOfQuantityReceived, "
            + " sro.`tracking_number` AS sorTrackingNumber, "
            + " sro.`created_by_email_id` AS sorCreatedByEmailId, "
            + " sro.`last_modified_by_email_id` AS sorLastModifiedByEmailId, "
            + " sro.`destination_location_location_id` AS sorDestinationLocationId, "
            + " sro.`owner_email_id` AS sorOwnerEmailId, "
            + " sro.`source_location_location_id` AS sorSourceLocationId, "
            + " dloc.location_name AS destinationLocationName, "
            + " sloc.location_name AS sourceLoccationName, "

            + " cemp.`first_name` AS createdByfirstName, "
            + " cemp.`last_name` AS createdBylastName, "
            + " cemp.`middle_name` AS createdBymiddleName, "

            + " oemp.`first_name` AS ownerfirstName, "
            + " oemp.`last_name` AS ownerlastName, "
            + " oemp.`middle_name` AS ownermiddleName, "

            + " lemp.`first_name` AS lastModifiedByfirstName, "
            + " lemp.`last_name` AS lastModifiedBylastName, "
            + " lemp.`middle_name` AS lastModifiedBymiddleName "

            + " FROM   "

            + " `bt_service_order_return` sro  "
            + " JOIN "
            + " location sloc ON sro.`source_location_location_id`=sloc.location_id "
            + " JOIN "
            + " location dloc ON sro.`destination_location_location_id`=dloc.location_id "
            + " JOIN "
            + " employee_details cemp ON cemp.email_id=sro.`created_by_email_id` "
            + " JOIN "
            + " employee_details lemp ON lemp.email_id=sro.`last_modified_by_email_id` "
            + " JOIN "
            + " employee_details oemp ON oemp.email_id = sro.`owner_email_id` where sro.`id`=:id and (dloc.location_id in :locationIds or sloc.location_id in :locationIds)",nativeQuery = true)
    Map<String, Object> getServiceOrderReturnById(@Param("locationIds") List<Long>locationIds,@Param("id")Long id);

    @Query(value = "SELECT "
            + " sro.`id` AS sorId, "
            + " sro.`created_date` AS sorCreatedDate, "
            + " sro.`last_modified_date` AS sorLastModifiedDate, "
            + " sro.`manual_id` AS sorManualId, "
            + " sro.`return_date` AS sorReturnDate, "
            + " sro.`shipping_to` AS sorShippingTo, "
            + " sro.`shippint_mode` AS sorShippingMode, "
            + " sro.`total_number_of_quantity` AS sorTotalNumberOfQuantityReturned, "
            + " sro.`total_number_of_quantity_received` AS sorTotalNumberOfQuantityReceived, "
            + " sro.`tracking_number` AS sorTrackingNumber, "
            + " sro.`created_by_email_id` AS sorCreatedByEmailId, "
            + " sro.`last_modified_by_email_id` AS sorLastModifiedByEmailId, "
            + " sro.`destination_location_location_id` AS sorDestinationLocationId, "
            + " sro.`owner_email_id` AS sorOwnerEmailId, "
            + " sro.`source_location_location_id` AS sorSourceLocationId, "
            + " dloc.location_name AS destinationLocationName, "
            + " sloc.location_name AS sourceLoccationName, "

            + " cemp.`first_name` AS createdByfirstName, "
            + " cemp.`last_name` AS createdBylastName, "
            + " cemp.`middle_name` AS createdBymiddleName, "

            + " oemp.`first_name` AS ownerfirstName, "
            + " oemp.`last_name` AS ownerlastName, "
            + " oemp.`middle_name` AS ownermiddleName, "

            + " lemp.`first_name` AS lastModifiedByfirstName, "
            + " lemp.`last_name` AS lastModifiedBylastName, "
            + " lemp.`middle_name` AS lastModifiedBymiddleName "

            + " FROM   "

            + " `bt_service_order_return` sro  "
            + " JOIN "
            + " location sloc ON sro.`source_location_location_id`=sloc.location_id "
            + " JOIN "
            + " location dloc ON sro.`destination_location_location_id`=dloc.location_id "
            + " JOIN "
            + " employee_details cemp ON cemp.email_id=sro.`created_by_email_id` "
            + " JOIN "
            + " employee_details lemp ON lemp.email_id=sro.`last_modified_by_email_id` "
            + " JOIN "
            + " employee_details oemp ON oemp.email_id = sro.`owner_email_id` where dloc.location_id in :locationIds or sloc.location_id in :locationIds"
            + " and sro.total_number_of_quantity-sro.total_number_of_quantity_received<>0", nativeQuery = true, countQuery = "SELECT count(*) as ct "

                    + " FROM   "

                    + " `bt_service_order_return` sro  "
                    + " JOIN "
                    + " location sloc ON sro.`source_location_location_id`=sloc.location_id "
                    + " JOIN "
                    + " location dloc ON sro.`destination_location_location_id`=dloc.location_id "
                    + " JOIN "
                    + " employee_details cemp ON cemp.email_id=sro.`created_by_email_id` "
                    + " JOIN "
                    + " employee_details lemp ON lemp.email_id=sro.`last_modified_by_email_id` "
                    + " JOIN "
                    + " employee_details oemp ON oemp.email_id = sro.`owner_email_id` where dloc.location_id in :locationIds or sloc.location_id in :locationIds"
                    + " and sro.total_number_of_quantity-sro.total_number_of_quantity_received<>0")
	Page<Map<String, Object>> getServiceOrderReturnPendingToReceive(@Param("locationIds") List<Long> locationIds, Pageable page);

    @Query(value = "SELECT "
            + " sro.`id` AS sorId, "
            + " sro.`created_date` AS sorCreatedDate, "
            + " sro.`last_modified_date` AS sorLastModifiedDate, "
            + " sro.`manual_id` AS sorManualId, "
            + " sro.`return_date` AS sorReturnDate, "
            + " sro.`shipping_to` AS sorShippingTo, "
            + " sro.`shippint_mode` AS sorShippingMode, "
            + " sro.`total_number_of_quantity` AS sorTotalNumberOfQuantityReturned, "
            + " sro.`total_number_of_quantity_received` AS sorTotalNumberOfQuantityReceived, "
            + " sro.`tracking_number` AS sorTrackingNumber, "
            + " sro.`created_by_email_id` AS sorCreatedByEmailId, "
            + " sro.`last_modified_by_email_id` AS sorLastModifiedByEmailId, "
            + " sro.`destination_location_location_id` AS sorDestinationLocationId, "
            + " sro.`owner_email_id` AS sorOwnerEmailId, "
            + " sro.`source_location_location_id` AS sorSourceLocationId, "
            + " dloc.location_name AS destinationLocationName, "
            + " sloc.location_name AS sourceLoccationName, "

            + " cemp.`first_name` AS createdByfirstName, "
            + " cemp.`last_name` AS createdBylastName, "
            + " cemp.`middle_name` AS createdBymiddleName, "

            + " oemp.`first_name` AS ownerfirstName, "
            + " oemp.`last_name` AS ownerlastName, "
            + " oemp.`middle_name` AS ownermiddleName, "

            + " lemp.`first_name` AS lastModifiedByfirstName, "
            + " lemp.`last_name` AS lastModifiedBylastName, "
            + " lemp.`middle_name` AS lastModifiedBymiddleName "

            + " FROM   "

            + " `bt_service_order_return` sro  "
            + " JOIN "
            + " location sloc ON sro.`source_location_location_id`=sloc.location_id "
            + " JOIN "
            + " location dloc ON sro.`destination_location_location_id`=dloc.location_id "
            + " JOIN "
            + " employee_details cemp ON cemp.email_id=sro.`created_by_email_id` "
            + " JOIN "
            + " employee_details lemp ON lemp.email_id=sro.`last_modified_by_email_id` "
            + " JOIN "
            + " employee_details oemp ON oemp.email_id = sro.`owner_email_id` where dloc.location_id in :locationIds or sloc.location_id in :locationIds"
            + " and sro.total_number_of_quantity-sro.total_number_of_quantity_received=0", nativeQuery = true, countQuery = "SELECT count(*) as ct "

                    + " FROM   "

                    + " `bt_service_order_return` sro  "
                    + " JOIN "
                    + " location sloc ON sro.`source_location_location_id`=sloc.location_id "
                    + " JOIN "
                    + " location dloc ON sro.`destination_location_location_id`=dloc.location_id "
                    + " JOIN "
                    + " employee_details cemp ON cemp.email_id=sro.`created_by_email_id` "
                    + " JOIN "
                    + " employee_details lemp ON lemp.email_id=sro.`last_modified_by_email_id` "
                    + " JOIN "
                    + " employee_details oemp ON oemp.email_id = sro.`owner_email_id` where dloc.location_id in :locationIds or sloc.location_id in :locationIds"
                    + " and sro.total_number_of_quantity-sro.total_number_of_quantity_received=0")
	Page<Map<String, Object>> getServiceOrderReturnReceived(@Param("locationIds") List<Long> locationIds, Pageable page);

    @Query(value = "SELECT "
            + " sro.`id` AS sorId, "
            + " sro.`created_date` AS sorCreatedDate, "
            + " sro.`last_modified_date` AS sorLastModifiedDate, "
            + " sro.`manual_id` AS sorManualId, "
            + " sro.`return_date` AS sorReturnDate, "
            + " sro.`shipping_to` AS sorShippingTo, "
            + " sro.`shippint_mode` AS sorShippingMode, "
            + " sro.`total_number_of_quantity` AS sorTotalNumberOfQuantityReturned, "
            + " sro.`total_number_of_quantity_received` AS sorTotalNumberOfQuantityReceived, "
            + " sro.`tracking_number` AS sorTrackingNumber, "
            + " sro.`created_by_email_id` AS sorCreatedByEmailId, "
            + " sro.`last_modified_by_email_id` AS sorLastModifiedByEmailId, "
            + " sro.`destination_location_location_id` AS sorDestinationLocationId, "
            + " sro.`owner_email_id` AS sorOwnerEmailId, "
            + " sro.`source_location_location_id` AS sorSourceLocationId, "
            + " dloc.location_name AS destinationLocationName, "
            + " sloc.location_name AS sourceLoccationName, "

            + " cemp.`first_name` AS createdByfirstName, "
            + " cemp.`last_name` AS createdBylastName, "
            + " cemp.`middle_name` AS createdBymiddleName, "

            + " oemp.`first_name` AS ownerfirstName, "
            + " oemp.`last_name` AS ownerlastName, "
            + " oemp.`middle_name` AS ownermiddleName, "

            + " lemp.`first_name` AS lastModifiedByfirstName, "
            + " lemp.`last_name` AS lastModifiedBylastName, "
            + " lemp.`middle_name` AS lastModifiedBymiddleName "

            + " FROM   "

            + " `bt_service_order_return` sro  "
            + " JOIN "
            + " location sloc ON sro.`source_location_location_id`=sloc.location_id "
            + " JOIN "
            + " location dloc ON sro.`destination_location_location_id`=dloc.location_id "
            + " JOIN "
            + " employee_details cemp ON cemp.email_id=sro.`created_by_email_id` "
            + " JOIN "
            + " employee_details lemp ON lemp.email_id=sro.`last_modified_by_email_id` "
            + " JOIN "
            + " employee_details oemp ON oemp.email_id = sro.`owner_email_id`"
            + " and sro.total_number_of_quantity-sro.total_number_of_quantity_received<>0", nativeQuery = true, countQuery = "SELECT count(*) as ct "

                    + " FROM   "

                    + " `bt_service_order_return` sro  "
                    + " JOIN "
                    + " location sloc ON sro.`source_location_location_id`=sloc.location_id "
                    + " JOIN "
                    + " location dloc ON sro.`destination_location_location_id`=dloc.location_id "
                    + " JOIN "
                    + " employee_details cemp ON cemp.email_id=sro.`created_by_email_id` "
                    + " JOIN "
                    + " employee_details lemp ON lemp.email_id=sro.`last_modified_by_email_id` "
                    + " JOIN "
                    + " employee_details oemp ON oemp.email_id = sro.`owner_email_id`"
                    + " and sro.total_number_of_quantity-sro.total_number_of_quantity_received<>0")
	Page<Map<String, Object>> getServiceOrderReturnPendingToReceive(Pageable page);

    @Query(value = "SELECT "
            + " sro.`id` AS sorId, "
            + " sro.`created_date` AS sorCreatedDate, "
            + " sro.`last_modified_date` AS sorLastModifiedDate, "
            + " sro.`manual_id` AS sorManualId, "
            + " sro.`return_date` AS sorReturnDate, "
            + " sro.`shipping_to` AS sorShippingTo, "
            + " sro.`shippint_mode` AS sorShippingMode, "
            + " sro.`total_number_of_quantity` AS sorTotalNumberOfQuantityReturned, "
            + " sro.`total_number_of_quantity_received` AS sorTotalNumberOfQuantityReceived, "
            + " sro.`tracking_number` AS sorTrackingNumber, "
            + " sro.`created_by_email_id` AS sorCreatedByEmailId, "
            + " sro.`last_modified_by_email_id` AS sorLastModifiedByEmailId, "
            + " sro.`destination_location_location_id` AS sorDestinationLocationId, "
            + " sro.`owner_email_id` AS sorOwnerEmailId, "
            + " sro.`source_location_location_id` AS sorSourceLocationId, "
            + " dloc.location_name AS destinationLocationName, "
            + " sloc.location_name AS sourceLoccationName, "

            + " cemp.`first_name` AS createdByfirstName, "
            + " cemp.`last_name` AS createdBylastName, "
            + " cemp.`middle_name` AS createdBymiddleName, "

            + " oemp.`first_name` AS ownerfirstName, "
            + " oemp.`last_name` AS ownerlastName, "
            + " oemp.`middle_name` AS ownermiddleName, "

            + " lemp.`first_name` AS lastModifiedByfirstName, "
            + " lemp.`last_name` AS lastModifiedBylastName, "
            + " lemp.`middle_name` AS lastModifiedBymiddleName "

            + " FROM   "

            + " `bt_service_order_return` sro  "
            + " JOIN "
            + " location sloc ON sro.`source_location_location_id`=sloc.location_id "
            + " JOIN "
            + " location dloc ON sro.`destination_location_location_id`=dloc.location_id "
            + " JOIN "
            + " employee_details cemp ON cemp.email_id=sro.`created_by_email_id` "
            + " JOIN "
            + " employee_details lemp ON lemp.email_id=sro.`last_modified_by_email_id` "
            + " JOIN "
            + " employee_details oemp ON oemp.email_id = sro.`owner_email_id`"
            + " and sro.total_number_of_quantity-sro.total_number_of_quantity_received=0", nativeQuery = true, countQuery = "SELECT count(*) as ct "

                    + " FROM   "

                    + " `bt_service_order_return` sro  "
                    + " JOIN "
                    + " location sloc ON sro.`source_location_location_id`=sloc.location_id "
                    + " JOIN "
                    + " location dloc ON sro.`destination_location_location_id`=dloc.location_id "
                    + " JOIN "
                    + " employee_details cemp ON cemp.email_id=sro.`created_by_email_id` "
                    + " JOIN "
                    + " employee_details lemp ON lemp.email_id=sro.`last_modified_by_email_id` "
                    + " JOIN "
                    + " employee_details oemp ON oemp.email_id = sro.`owner_email_id`"
                    + " and sro.total_number_of_quantity-sro.total_number_of_quantity_received=0")
	Page<Map<String, Object>> getServiceOrderReturnReceived(Pageable page);
}
