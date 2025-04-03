package com.echevarne.sap.cloud.facturacion.gestioncambios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Class for the Entity {@link EntityComparatorResult}.
 * 
 * <p>. . .</p>
 * <p>This is the container of the differences in the SolicitudMuestreo EndPoint. . .</p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 * 
 */
public class EntityComparatorResult
{
    private List<EntityComparatorItem> diffs = new ArrayList<>();
    private String className;
    private Date date;
    
    

    /**
     * 
     * @param pClassName
     * @param pDate
     */
    public EntityComparatorResult( String pClassName, Date pDate )
    {
        super();
        className = pClassName;
        date = pDate;
    }

    /**
     * 
     * @param pDiff
     */
    public void add( EntityComparatorItem pDiff )
    {
        this.diffs.add(  pDiff );
        
    }

   	/**
   	 * 
   	 * @return
   	 */
    public List<EntityComparatorItem> getDiffs()
    {
        return diffs;
    }


    /**
     * 
     * @return
     */
    public String getClassName()
    {
        return className;
    }


    /**
     * 
     * @return
     */
    public Date getDate()
    {
        return date;
    }

}
