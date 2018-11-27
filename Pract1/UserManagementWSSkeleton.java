
/**
 * UserManagementWSSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
    package es.upm.fi.sos.t3.usermanagement;

import java.util.*;
    /**
     *  UserManagementWSSkeleton java skeleton for the axisService
     */
    public class UserManagementWSSkeleton{
        
    	private static Map<String,String> lisUsPas=new HashMap <String,String>();
        private String nombreUs;
        private String passUs;
        private static boolean esAdmin;
        private boolean logged;
    	
        /**
         * Auto generated method signature
         * 
             * @return  
         */
        
                 public void logout()
            {
                //TODO : fill this with the necessary business logic
                logged=false;
        }
     
         
        /**
         * Auto generated method signature
         * 
                                     * @param user 
             * @return response 
         */
        
                 public es.upm.fi.sos.t3.usermanagement.Response login
                  (es.upm.fi.sos.t3.usermanagement.User user) {
                	 Response res = new Response();
                	 
                	 
                	 if(esAdmin==false){
                		 String aux="admin";
                		 lisUsPas.put(aux, aux);
                		 esAdmin=true;
                	 	}
                	 if(logged==true && user.getName().equals(nombreUs) && user.getPwd().equals(passUs)){
                		 res.setResponse(true);
                		 return res;
                	 }
                	 if(logged==false && lisUsPas.containsKey(user.getName()) && lisUsPas.containsValue(user.getPwd())){
                		 nombreUs=user.getName();
                		 passUs=user.getPwd();
                		 logged=true;
                		 res.setResponse(true);
                		 return res;
                	 }
                	 res.setResponse(false);
                	 return res;
                 }
     
         
        /**
         * Auto generated method signature
         * 
                                     * @param user1 
             * @return response2 
         */
        
                 public es.upm.fi.sos.t3.usermanagement.Response addUser
                  (es.upm.fi.sos.t3.usermanagement.User user1) {
                	 Response res=new Response();
                	 if(logged==true && nombreUs.equals("admin")){
                		 if(lisUsPas.containsKey(user1.getName())){
                			 //Error, está añadiendo a un usuario que ya existe
                			 res.setResponse(false);
                		 }
                		 else{
                			 //Añade un nuevo usuario
                			 lisUsPas.put(user1.getName(), user1.getPwd());
                			 res.setResponse(true);
                		 }
                			
                	 }
                	 return res;
                 }
     
         
        /**
         * Auto generated method signature
         * 
                                     * @param passwordPair 
             * @return response3 
         */
        
                 public es.upm.fi.sos.t3.usermanagement.Response changePassword( es.upm.fi.sos.t3.usermanagement.PasswordPair passwordPair){
                	 Response res=new Response();
                	 
                	 if(logged==true && passUs.equals(passwordPair.getOldpwd())){
                		 lisUsPas.put(nombreUs, passwordPair.getNewpwd());
                		 res.setResponse(true);
                		 return res;
                	 }
                	 
                	 res.setResponse(false);
                	 return res;        
                 }
     
         
        /**
         * Auto generated method signature
         * 
                                     * @param username 
             * @return response4 
         */
        
                 public es.upm.fi.sos.t3.usermanagement.Response removeUser( es.upm.fi.sos.t3.usermanagement.Username username){
                	 Response res=new Response();
                	 
                	 if(logged==true && nombreUs.equals("admin") && !username.getUsername().equals("admin") && lisUsPas.containsKey(username.getUsername())){
                		 lisUsPas.remove(username.getUsername());
                		 res.setResponse(true);
                		 return res;
                	 }
                	 res.setResponse(false);
                	 return res;
                 }
     
    }
    