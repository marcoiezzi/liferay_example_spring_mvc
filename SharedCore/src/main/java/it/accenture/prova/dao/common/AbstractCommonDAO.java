package it.accenture.prova.dao.common;

import it.accenture.prova.model.common.AbstractCommonLiferayAppPOJO;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import it.accenture.prova.exception.DatabaseException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;


// , ID extends Serializable

public abstract class AbstractCommonDAO <T extends AbstractCommonLiferayAppPOJO> {
	private static final Log log = LogFactory.getLog(AbstractCommonDAO.class);
	
	@SuppressWarnings("rawtypes")
	private Class classeOggetto;
	private String nomeOggetto;
	
	@Value("#{config['paginazione.risultatiPerPagina']}")
	protected int maxResult;
	
	public AbstractCommonDAO() {
		
		Type superType = getClass().getGenericSuperclass();
		if (superType instanceof ParameterizedType) {
			Type classe = ((ParameterizedType)superType).getActualTypeArguments()[0];
			
			nomeOggetto = classe.toString().replace("class ", "");
			try {
				classeOggetto = Class.forName(nomeOggetto);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}


	
	@Autowired
	private SessionFactory sessionFactory;
	

		
	protected Session currentSession(){
		return sessionFactory.getCurrentSession();
	}

	public Serializable save( T transientInstance ) {
		log.debug("saving "+nomeOggetto + " instance");
		try {
			Serializable id = currentSession().save(transientInstance);
			log.debug("save successful");
			return id;
		} catch (Exception re) {
			log.error("save failed", re);
			throw new DatabaseException(re);
		}
	}
	
	public Serializable saveOrUpdate( T transientDetachedInstance ) {
		log.debug("saving "+nomeOggetto + " instance");
		try {
			Serializable id = transientDetachedInstance.getId();
			if (id == null) {
				id = currentSession().save(transientDetachedInstance);
			} else {
				currentSession().update(transientDetachedInstance);
			}
			log.debug("save successful");
			return id;
		} catch (Exception re) {
			log.error("save failed", re);
			throw new DatabaseException(re);
		}
	}
	
	public  void delete(T persistentInstance) {
		log.debug("deleting "+nomeOggetto + " instance");
		try {
			currentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (Exception re) {
			log.error("delete failed", re);
			throw new DatabaseException(re);
		}
	}

	@SuppressWarnings("unchecked")
	public T findById(Integer id) {
		log.debug("getting "+nomeOggetto +" instance with id: " + id);
		try {
			T instance = (T)currentSession().get(classeOggetto, id);
			return instance;
		} catch (Exception re) {
			log.error("get failed", re);
			throw new DatabaseException(re);
		}
	}

	
	@SuppressWarnings("unchecked")
	public  List<T> findByProperty(String propertyName, Object value) {
		log.debug("finding "+ nomeOggetto +" instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from "+ nomeOggetto +" as model where model."+ propertyName + "= :param";
			Query query= currentSession().createQuery(queryString);
			query.setParameter("param", value);
			return query.list();
		} catch (Exception re) {
			log.error("find by property name failed", re);
			throw new DatabaseException(re);
		}
	}
	
	@SuppressWarnings("unchecked")
	public  List<T> findByPropertyOrdered(String propertyName, Object value,List<String> orderByParams) {
		log.debug("finding "+ nomeOggetto +" instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from "+ nomeOggetto +" as model where model."+ propertyName + "= :param";
			queryString = addOrderBy(queryString, orderByParams);
			Query query= currentSession().createQuery(queryString);
			query.setParameter("param", value);
			return query.list();
		} catch (Exception re) {
			log.error("find by property name failed", re);
			throw new DatabaseException(re);
		}
	}
	
	public  int countByProperty(String propertyName, Object value) {
		log.debug("finding "+ nomeOggetto +" instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "select count(*) from "+ nomeOggetto +" as model where model."+ propertyName + "= :param";
			Query query= currentSession().createQuery(queryString);
			query.setParameter("param", value);
			int totalRecords = ((Long)query.uniqueResult()).intValue();
        
	        return totalRecords;
	        
		} catch (Exception re) {
			log.error("countTotalPagesByProperty failed", re);
			throw new DatabaseException(re);
		}
	}
	

	@SuppressWarnings("unchecked")
	public  List<T> findByProperty(String propertyName, Object value, int page) {
		log.debug("finding "+ nomeOggetto +" instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from "+ nomeOggetto +" as model where model."+ propertyName + "= :param";
			Query query= currentSession().createQuery(queryString);
			query.setParameter("param", value);
			return query.setFirstResult(maxResult * (page-1)).setMaxResults(maxResult).list();
		} catch (Exception re) {
			log.error("find by property name failed", re);
			throw new DatabaseException(re);
		}
	}
	
	@SuppressWarnings("unchecked")
	public  List<T> findByProperty(String propertyName, Object value, int page, List<String> orderByParams) {
		log.debug("finding "+ nomeOggetto +" instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from "+ nomeOggetto +" as model where model."+ propertyName + "= :param";
			
			queryString = addOrderBy(queryString, orderByParams);			
			
			Query query= currentSession().createQuery(queryString);
			query.setParameter("param", value);
			return query.setFirstResult(maxResult * (page-1)).setMaxResults(maxResult).list();
		} catch (Exception re) {
			log.error("find by property name failed", re);
			throw new DatabaseException(re);
		}
	}
	
	@SuppressWarnings("unchecked")
	public  List<T> findByPropertyNotNull(String propertyName, int page, List<String> orderByParams) {
		log.debug("finding "+ nomeOggetto +" instance with property: " + propertyName
				+ ", not null" );
		try {
			String queryString = "from "+ nomeOggetto +" as model where model."+ propertyName + " is not null";
			queryString = addOrderBy(queryString, orderByParams);			
			Query query= currentSession().createQuery(queryString);
			return query.setFirstResult(maxResult * (page-1)).setMaxResults(maxResult).list();
		} catch (Exception re) {
			log.error("find by property not null failed", re);
			throw new DatabaseException(re);
		}
	}
	
	@SuppressWarnings("unchecked")
	public  List<T> findByPropertyNotNull(String propertyName) {
		log.debug("finding "+ nomeOggetto +" instance with property: " + propertyName
				+ ", not null" );
		try {
			String queryString = "from "+ nomeOggetto +" as model where model."+ propertyName + " is not null";
			Query query = currentSession().createQuery(queryString);
			return query.list();
		} catch (Exception re) {
			log.error("find by property not null failed", re);
			throw new DatabaseException(re);
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public  List<T> findOrderedResultByHQLQueryNoParams(String queryString, int page, List<String> orderByParams) {
		log.debug("finding "+ nomeOggetto +" ordered instance by HQL query" );
		try {
			queryString = addOrderBy(queryString, orderByParams);			
			Query query= currentSession().createQuery(queryString);
			return query.setFirstResult(maxResult * (page-1)).setMaxResults(maxResult).list();
		} catch (Exception re) {
			log.error("find ordered instance by HQL query failed", re);
			throw new DatabaseException(re);
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		log.debug("finding all "+nomeOggetto +" instances");
		try {
			String queryString = "from "+nomeOggetto;
			Query query= currentSession().createQuery(queryString);
			return query.list();
		} catch (Exception re) {
			log.error("find all failed", re);
			throw new DatabaseException(re);
		}
	}
	
	public int countAll() {
		log.debug("countTotalPages for "+nomeOggetto +" instances");
		try {
			String queryString = "select count(*) from "+nomeOggetto;
			Query query= currentSession().createQuery(queryString);
			int totalRecords = ((Long)query.uniqueResult()).intValue();
	        
	        return totalRecords;
		} catch (Exception re) {
			log.error("find all failed", re);
			throw new DatabaseException(re);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findAll(int page, List<String> orderByParams) {
		log.debug("finding all "+nomeOggetto +" instances");
		try {
			String queryString = "from "+nomeOggetto;
			
			queryString = addOrderBy(queryString, orderByParams);	
			
			Query query= currentSession().createQuery(queryString);
			return query.setFirstResult(maxResult * (page-1)).setMaxResults(maxResult).list();
		} catch (Exception re) {
			log.error("find all failed", re);
			throw new DatabaseException(re);
		}
	}
	
	@SuppressWarnings("unchecked")
	public T merge(T detachedInstance) {
		log.debug("merging "+ nomeOggetto+" instance");
		try {
			T result = (T) currentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (Exception re) {
			log.error("merge failed", re);
			throw new DatabaseException(re);
		}
	}

	public List<T> findByHqlQuery(String queryString) {
		return findByHqlQuery(queryString, null);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findByHqlQuery(String queryString, Map<String, Object> params) {
		log.debug("finding (query: " + queryString + ") " + nomeOggetto + " instances");
		try {
//			if (StringUtils.contains(queryString, "from ")) 
//				throw new DatabaseException("Non specificare la clausola from, e' aggiunta automaticamente dalla query");
//			
//			queryString =  "from " + nomeOggetto + " " + queryString;
			
			Query query = currentSession().createQuery(queryString);
			
			if (params != null) {
			
				Iterator<String> iter = params.keySet().iterator();
				while (iter.hasNext()) {
					String name = iter.next();
					query.setParameter(name, params.get(name));
				}
			}
			
			return query.list();
			
		} catch (Exception re) {
			log.error("findByHqlQuery failed", re);
			throw new DatabaseException(re);
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> findByHqlQuery(String queryString, Map<String, Object> params, int page) {
		log.debug("finding (query: " + queryString + ") " + nomeOggetto + " instances");
		try {
			Query query = currentSession().createQuery(queryString);
					
			if (params != null) {
			
				Iterator<String> iter = params.keySet().iterator();
				while (iter.hasNext()) {
					String name = iter.next();
					query.setParameter(name, params.get(name));
				}
			}
			
			return query.setFirstResult(maxResult * (page-1)).setMaxResults(maxResult).list();
			
		} catch (Exception re) {
			log.error("merge failed", re);
			throw new DatabaseException(re);
		}
	}	
	
	
	public int countByHqlQuery(String queryString, Map<String, Object> params) {
		log.debug("counting (query: " + queryString + ") " + nomeOggetto + " instances");
		try {
			
			if (StringUtils.startsWithIgnoreCase(queryString, "select")) {
				queryString = "select count(*) from (" + queryString + ")";
			} else {
				queryString = "select count(*) " + queryString;
			}
			
			Query query = currentSession().createQuery(queryString);
		
			if (params != null) {
			
				Iterator<String> iter = params.keySet().iterator();
				while (iter.hasNext()) {
					String name = iter.next();
					query.setParameter(name, params.get(name));
				}
			}
			
			int totalRecords = ((Long)query.uniqueResult()).intValue();
	        
	        return totalRecords;
			
		} catch (Exception re) {
			log.error("count failed", re);
			throw new DatabaseException(re);
		}
	}

	
	
	public void flush() {
		currentSession().flush();
	}
	
	
	protected String addOrderBy(String queryString, List<String> orderByParams) {
		if (orderByParams != null) {
			
			int count = 0;
			for (String order : orderByParams) {
				
				if (count == 0) 
					queryString += " order by ";
				
				queryString += " " + order + " ";
				
				if (count < orderByParams.size() - 1)
					queryString += ", ";
				
				count ++;
			}
			
		}
		return queryString;
	}
	
	
	
	public Criteria newCriteria() {
		return currentSession().createCriteria(classeOggetto);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findByCriteria(Criteria criteria, int page)
	{
		log.debug("finding all instances by criteria");
		try {
			
			criteria = criteria.setFirstResult(maxResult * (page-1))
				.setMaxResults(maxResult);
			
			return criteria.list();
		} catch (Exception re) {
			log.error("find all  instances by criteria failed", re);
			throw new DatabaseException(re);
		}
	}
	
	public int countByCriteria(Criteria criteria) {
		log.debug("count all instances by pattern");
		try {
		
			int totalRecords = ((Number)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
			return totalRecords;
		} catch (Exception re) {
			log.error("countTotalPagesByCriteria failed", re);
			throw new DatabaseException(re);
		}			
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findByCriteria(Criteria criteria)
	{
		log.debug("finding all instances by criteria");
		try {
			return criteria.list();
		} catch (Exception re) {
			log.error("find all  instances by criteria failed", re);
			throw new DatabaseException(re);
		}
	}
	
}
