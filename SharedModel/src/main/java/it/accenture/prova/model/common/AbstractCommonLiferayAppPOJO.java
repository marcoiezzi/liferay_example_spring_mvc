package it.accenture.prova.model.common;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractCommonLiferayAppPOJO implements Serializable {

	private static final long serialVersionUID = 1403618529425061956L;

	public abstract Integer getId();

}
