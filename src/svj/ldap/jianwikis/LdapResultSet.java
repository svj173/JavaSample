package svj.ldap.jianwikis;


import java.util.Map;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 08.02.2016 15:05
 */
public class LdapResultSet
{
    Map attributeMap;

   public final Map<String, Object> getAttributeMap() {
       return attributeMap;
   }

   public final void setAttributeMap(Map<String, Object> attributeMap) {
       this.attributeMap = attributeMap;
   }

   public Object getAttribute(String attribute){

       if(attributeMap == null){

           return null;
       }else{

           return attributeMap.get(attribute);
       }
   }
}
