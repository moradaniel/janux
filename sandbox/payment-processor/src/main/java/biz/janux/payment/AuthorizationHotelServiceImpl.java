package biz.janux.payment;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.janux.bus.persistence.Persistent;
import org.janux.bus.search.SearchCriteria;

import com.trg.search.Search;

import api.org.janux.bus.service.GenericServiceImpl;

public class AuthorizationHotelServiceImpl <T extends AuthorizationHotelImpl,ID extends Serializable,DW extends AuthorizationHotelDao<T, ID, SearchCriteria,AuthorizationFacet>, DR extends AuthorizationHotelDao<T, ID, SearchCriteria,AuthorizationFacet>>
extends GenericServiceImpl<T,ID,SearchCriteria,DW,DR> 
implements AuthorizationHotelService<T>  {
    
    private
    CreditCardTypeService<SearchCriteria> creditCardTypeService;
    
    public T create(T authorization, BigDecimal authAmount, AuthorizationResponse authorizationResponse, AuthorizationType authorizationType){
        log.debug("Creating authorization {} of type {}",authorization,authorizationType);
        
        if (!isAllowAuthorization(authorizationType, authorization.getCreditCard().getType()))
            throw new IllegalStateException("This transactions type "+ authorizationType +" is not currently supported for "+authorization.getCreditCard().getType());
        
        if (authorizationType.equals(AuthorizationType.AUTHORIZATION))
        {
            if (authorization.getId()!=null && authorization.getId()!=Persistent.UNSAVED_ID)
                throw new IllegalArgumentException ("the authorization is not new: "+authorization);

            authorization.setTransactionResponse(authorizationResponse);
        }
        else
            if (authorizationType.equals(AuthorizationType.INCREMENTAL) || authorizationType.equals(AuthorizationType.REVERSAL))
            {
                if (authorization.getId()==null || authorization.getId()==Persistent.UNSAVED_ID)
                    throw new IllegalArgumentException ("the authorization should exist: "+authorization);
            }
        
        if (authorizationResponse==null)
            throw new IllegalArgumentException ("the authorization has not a response: "+authorization);

        log.debug("Authorization response {}",authorizationResponse);
        
        if (authorizationResponse.isApproved())
        {
            addModification(authorization,authorization.getDate(),authAmount);
            authorization.setEnabled(true);
            authorization = super.saveOrUpdate(authorization);
            
            log.info("Created the authorization {} of type {}",authorization,authorizationType);
        }
        else
            log.info("No created the authorization {} of type {}",authorization,authorizationType);
        
        return authorization;
    }

    /**
     * create a new {@link AuthorizationModification} and recalculates the auth amount of the {@link Authorization}
     * @param authorization
     * @param date
     * @param authAmount
     * @return
     */
    private T addModification(T authorization,Date date,BigDecimal authAmount) {
        log.debug("Adding modification for {} {} {}",new Object[]{authorization,date,authAmount});
        
        AuthorizationModification authorizationModification = new AuthorizationModificationImpl();
        authorizationModification.setAmount(authAmount);
        authorizationModification.setDate(date);

        if (authorization.getModifications()==null)
            authorization.setModifications(new ArrayList<AuthorizationModification>());
        
        authorization.getModifications().add(authorizationModification);
        calculatesAuthAmount(authorization);
        
        log.debug("Added modification {}",authorizationModification);
        
        return authorization;
    }

    /**
     * calculates the auth amount adding the amounts of the authorization modifications
     * @param authorization
     * @return
     */
    private T calculatesAuthAmount(T authorization) {
        List<AuthorizationModification> modifications = authorization.getModifications();
        BigDecimal totalAmount = new BigDecimal(0);
        if (modifications!=null)
        {
            for (Iterator iterator = modifications.iterator(); iterator.hasNext();) {
                AuthorizationModification authorizationModification = (AuthorizationModification) iterator.next();
                totalAmount = totalAmount.add(authorizationModification.getAmount());
            }
        }
        
        authorization.setAmount(totalAmount);
        return authorization;
    }

    public boolean isAllowAuthorization(AuthorizationType authorizationType, CreditCardType creditCardType) {
        
        log.debug("Checking if the authorization is allowed for {} {}",authorizationType,creditCardType);
        
        /**
         * Authorization Reversal transactions are currently supported only for Visa cards.
         */
        if (authorizationType.equals(authorizationType.REVERSAL))
        {
            CreditCardType creditCardTypeOnDatabase = getCreditCardTypeService().findByCode(CreditCardTypeEnum.VISA.getCode());
            
            if (creditCardType==null)
                throw new IllegalStateException ("The credit card type for VISA does not exist on database for code "+ CreditCardTypeEnum.VISA.getCode());

            if (!creditCardType.equals(creditCardTypeOnDatabase))
            {
                log.info("The authorization is not allowed for {} {}",authorizationType,creditCardType);
                return false;
            }
        }
        
        log.info("The authorization is allowed for {} {}",authorizationType,creditCardType);
        return true;
    }

    public void voidAuthorization(T originalAuthorization) {
        log.debug("Voiding authorization {}",originalAuthorization);
        
        if (originalAuthorization.getId()==Persistent.UNSAVED_ID)
            throw new IllegalArgumentException ("the authorization has not to new: "+originalAuthorization);
        
        originalAuthorization.setEnabled(false);
        originalAuthorization = saveOrUpdate(originalAuthorization);
        
        log.info("Voided authorization {}",originalAuthorization);
    }

    public void setBatched(T authorization) {
        log.debug("Setting the authorization as batched {}",authorization);
        
        if (authorization.isBatched())
            throw new IllegalStateException ("the "+ authorization+ " had been captured");
        
        authorization.setBatched(true);
        authorization = saveOrUpdate(authorization);
        
        log.info("Set the authorization as batched {}",authorization);
    }
    
    public void setCreditCardTypeService(CreditCardTypeService<SearchCriteria> creditCardTypeService) {
        this.creditCardTypeService = creditCardTypeService;
    }

    public CreditCardTypeService<SearchCriteria> getCreditCardTypeService() {
        return creditCardTypeService;
    }

    public T load(String uuid) {
        return super.load(uuid);
    }

    public List<T> findAuthorizationsActive(String businessUnitUuid, String creditCardUuid) {
        
        if (creditCardUuid==null || creditCardUuid.equalsIgnoreCase(""))
            throw new RuntimeException("credit card with uuid null or empty");
        
        if (businessUnitUuid==null || businessUnitUuid.equalsIgnoreCase(""))
            throw new RuntimeException("businessUnit uuid with uuid null or empty");
                
        Search searchCriteria = new Search();
        searchCriteria.addFilterEqual("batched", false);
        searchCriteria.addFilterEqual("enabled", true);
        searchCriteria.addFilterEqual("businessUnit.uuid", businessUnitUuid);
        searchCriteria.addFilterEqual("creditCard.uuid", creditCardUuid);
        
        return findAll(searchCriteria);
    }

    public boolean isAuthorizationExist(String businessUnitUuid, String creditCardUuid) {
        
        if (creditCardUuid==null || creditCardUuid.equalsIgnoreCase(""))
            throw new RuntimeException("credit card with uuid null or empty");
        
        if (businessUnitUuid==null || businessUnitUuid.equalsIgnoreCase(""))
            throw new RuntimeException("businessUnit uuid with uuid null or empty");
        
        Search searchCriteria = new Search();
        searchCriteria.addFilterEqual("businessUnit.uuid", businessUnitUuid);
        searchCriteria.addFilterEqual("creditCard.uuid", creditCardUuid);
        searchCriteria.setMaxResults(1);
        
        List<T> list = findAll(searchCriteria);
        return list.isEmpty()?false:true;
    }

    public void unBatched(T authorization) {
        log.debug("Setting the authorization as no batched {}",authorization);
        
        if (!authorization.isBatched())
            throw new IllegalStateException ("the "+ authorization+ " had not been captured");
        
        authorization.setBatched(false);
        authorization = saveOrUpdate(authorization);
        
        log.info("Set the authorization as no batched {}",authorization);
    }
}