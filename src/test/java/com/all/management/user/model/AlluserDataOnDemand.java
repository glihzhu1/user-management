package com.all.management.user.model;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

@Configurable
@Component
public class AlluserDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<Alluser> data;

	@Autowired
    AlluserRepository alluserRepository;

	public Alluser getNewTransientAlluser(int index) {
        Alluser obj = new Alluser();
        setAppRole(obj, index);
        setEmail(obj, index);
        setLastUpdateDate(obj, index);
        setLoginId(obj, index);
        setPasswordHash(obj, index);
        setUserActive(obj, index);
        return obj;
    }

	public void setAppRole(Alluser obj, int index) {
        String appRole = "appRole_" + index;
        if (appRole.length() > 50) {
            appRole = appRole.substring(0, 50);
        }
        obj.setAppRole(appRole);
    }

	public void setEmail(Alluser obj, int index) {
        String email = "foo" + index + "@bar.com";
        if (email.length() > 200) {
            email = email.substring(0, 200);
        }
        obj.setEmail(email);
    }

	public void setLastUpdateDate(Alluser obj, int index) {
        Calendar lastUpdateDate = Calendar.getInstance();
        obj.setLastUpdateDate(lastUpdateDate);
    }

	public void setLoginId(Alluser obj, int index) {
        String loginId = "loginId_" + index;
        if (loginId.length() > 200) {
            loginId = loginId.substring(0, 200);
        }
        obj.setLoginId(loginId);
    }

	public void setPasswordHash(Alluser obj, int index) {
        String passwordHash = "passwordHash_" + index;
        if (passwordHash.length() > 200) {
            passwordHash = passwordHash.substring(0, 200);
        }
        obj.setPasswordHash(passwordHash);
    }

	public void setUserActive(Alluser obj, int index) {
        Boolean userActive = Boolean.TRUE;
        obj.setUserActive(userActive);
    }

	public Alluser getSpecificAlluser(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Alluser obj = data.get(index);
        Integer id = obj.getId();
        return alluserRepository.findOne(id);
    }

	public Alluser getRandomAlluser() {
        init();
        Alluser obj = data.get(rnd.nextInt(data.size()));
        Integer id = obj.getId();
        return alluserRepository.findOne(id);
    }

	public boolean modifyAlluser(Alluser obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = alluserRepository.findAll(new org.springframework.data.domain.PageRequest(from / to, to)).getContent();
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Alluser' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Alluser>();
        for (int i = 0; i < 10; i++) {
            Alluser obj = getNewTransientAlluser(i);
            try {
                alluserRepository.save(obj);
            } catch (final ConstraintViolationException e) {
                final StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    final ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
                }
                throw new IllegalStateException(msg.toString(), e);
            }
            alluserRepository.flush();
            data.add(obj);
        }
    }
}
