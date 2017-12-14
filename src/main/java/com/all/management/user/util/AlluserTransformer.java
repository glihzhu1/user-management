package com.all.management.user.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.all.management.user.model.Alluser;

import flexjson.TypeContext;
import flexjson.transformer.AbstractTransformer;

public class AlluserTransformer extends AbstractTransformer {

    @Override
    public Boolean isInline() {
        return Boolean.TRUE;
    }
    
	@Override
	public void transform(Object object) {
		
		// Start the object
		TypeContext typeContext = getContext().writeOpenObject();
		typeContext.setFirst(false);

		Alluser person = (Alluser) object;
		
		// Write out the fields
	    getContext().writeName("id");
	    getContext().transform(person.getId());
	    getContext().writeComma();
	    getContext().writeName("loginId");
	    getContext().transform(person.getLoginId());
	    getContext().writeComma();
	    //getContext().writeName("lastUpdateDate");
	    //getContext().transform(person.getLastUpdateDate());
	    //getContext().writeComma();
	    
	    Calendar lastupdateDate = person.getLastUpdateDate();
	    if (lastupdateDate != null) {
	    	getContext().writeName("lastUpdateDate");
	    	//String strlastupdateDate = lastupdateDate.toString();
	    	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	    	dateFormat.setTimeZone(lastupdateDate.getTimeZone());
	    	String strlastupdateDate = dateFormat.format(lastupdateDate.getTime());
	    	getContext().transform(strlastupdateDate);
	    	getContext().writeComma();
	    }
	    getContext().writeName("passwordHash");
	    getContext().transform(person.getPasswordHash());
	    getContext().writeComma();
	    getContext().writeName("appRole");
	    getContext().transform(person.getAppRole());
	    getContext().writeComma();
	    getContext().writeName("userActive");
	    getContext().transform(person.getUserActive());
	    getContext().writeComma();
	    getContext().writeName("email");
	    getContext().transform(person.getEmail());
	    
		// Write out the phone numbers
	    //getContext().writeName("phoneNumbers");
	    
		// Open the Array of Phone Numbers
	    /*
	    TypeContext itemTypeContext = getContext().writeOpenArray();

	    List<Phone> phoneNumbers = person.getPhoneNumbers();
	    for(Phone phone : phoneNumbers){
			if (!itemTypeContext.isFirst()) getContext().writeComma();
            itemTypeContext.setFirst(false);
			
            getContext().writeOpenObject();
    	    getContext().writeName("id");
    	    getContext().transform(phone.getId());
    	    getContext().writeComma();
    	    getContext().writeName("name");
    	    getContext().transform(phone.getName());
    	    getContext().writeComma();
    	    getContext().writeName("phoneNumber");
    	    getContext().transform(phone.getPhoneNumber());
			// Close the phone object
    	    getContext().writeCloseObject();
        }
	    */
		// Close the Array of Phone Numbers
        //getContext().writeCloseArray();

		// Close the Person Object
	    getContext().writeCloseObject();
		
	}

}