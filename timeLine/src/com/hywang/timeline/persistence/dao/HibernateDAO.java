package com.hywang.timeline.persistence.dao;

import java.io.Serializable;
import java.util.List;

import com.hywang.timeline.persistence.exception.DataAccessRuntimeException;

public interface HibernateDAO {
	/**
	 * @author
	 * @param className The class of the requested instance
	 * @param id The id of the requested instance
	 * @return Serializable
	 *         Returns an instance of className that represent the id
	 *         This method is used to retrieve one entity.
	 */
	public Serializable findById(String className, Serializable id)
	   throws DataAccessRuntimeException;

	/**
	 * @author
	 * @param className The class of the requested instance
	 * @param example The Object containing parameters for search
	 * @return List
	 *         Returns list matching the example object
	 *         This method is used to retrieve one entity.
	 */
	@SuppressWarnings("rawtypes")
	public List findByExample(Serializable example) throws DataAccessRuntimeException;

	/**
	 * Saving a new instance
	 * 
	 * @author
	 * @param className The class of the requested instance
	 * @param instance The instance to be saved
	 *           This method is used to save full objects.
	 * @return Serializable
	 */
	public Serializable save(Serializable instance) throws DataAccessRuntimeException;

	/**
	 * Saving an existing instance
	 * 
	 * @author
	 * @param className The class of the requested instance
	 * @param instance The instance to be saved
	 * @return Serializable
	 */
	public Serializable update(Serializable instance) throws DataAccessRuntimeException;

	/**
	 * Delete an existing instance
	 * 
	 * @author
	 * @param className The class of the requested instance
	 * @param instance The instance to be deleted
	 *           This method is used to delete objects.
	 */
	public boolean delete(Serializable instance) throws DataAccessRuntimeException;
	
}
