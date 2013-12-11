package biz.janux.commerce;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.janux.bus.persistence.DataAccessHibImplAbstract;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * @author <a href="mailto:alberto.buffagni@janux.org">Alberto Buffagni</a>
 * @since 0.4
 */
public class CreditCardFopDaoHibImpl  extends DataAccessHibImplAbstract  implements CreditCardFopDao {

	public List<CreditCardFop> findAll(CreditCardFopCriteria creditCardFopCriteria) throws Exception 
	{
		final DetachedCriteria criteria = DetachedCriteria.forClass(CreditCardFop.class);
		
		if (creditCardFopCriteria!=null)
		{
			if (creditCardFopCriteria.isEncrypted()!=null)
				criteria.add(Restrictions.eq("encrypted",creditCardFopCriteria.isEncrypted()));	
			if (creditCardFopCriteria.isMasked()!=null)
				criteria.add(Restrictions.eq("masked",creditCardFopCriteria.isMasked()));
		}
		
		final HibernateTemplate template = getHibernateTemplate();
		final List<CreditCardFop> list;
		
		if (creditCardFopCriteria!=null && creditCardFopCriteria.firstResult!=null && creditCardFopCriteria.maxResults!=null)
			list = (List<CreditCardFop>) template.findByCriteria(criteria,creditCardFopCriteria.firstResult,creditCardFopCriteria.maxResults);
		else
			list = (List<CreditCardFop>) template.findByCriteria(criteria);
		
		return list;
	}
}
