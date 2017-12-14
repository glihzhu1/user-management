package com.all.management.user.model;
import java.util.Iterator;
import java.util.List;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/META-INF/spring/applicationContext*.xml")
@Transactional
@Configurable
public class AlluserIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    AlluserDataOnDemand dod;

	@Autowired
    AlluserRepository alluserRepository;

	@Test
    public void testCount() {
        Assert.assertNotNull("Data on demand for 'Alluser' failed to initialize correctly", dod.getRandomAlluser());
        long count = alluserRepository.count();
        Assert.assertTrue("Counter for 'Alluser' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFind() {
        Alluser obj = dod.getRandomAlluser();
        Assert.assertNotNull("Data on demand for 'Alluser' failed to initialize correctly", obj);
        Integer id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Alluser' failed to provide an identifier", id);
        obj = alluserRepository.findOne(id);
        Assert.assertNotNull("Find method for 'Alluser' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Alluser' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAll() {
        Assert.assertNotNull("Data on demand for 'Alluser' failed to initialize correctly", dod.getRandomAlluser());
        long count = alluserRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'Alluser', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Alluser> result = alluserRepository.findAll();
        Assert.assertNotNull("Find all method for 'Alluser' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Alluser' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindEntries() {
        Assert.assertNotNull("Data on demand for 'Alluser' failed to initialize correctly", dod.getRandomAlluser());
        long count = alluserRepository.count();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Alluser> result = alluserRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
        Assert.assertNotNull("Find entries method for 'Alluser' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Alluser' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testSave() {
        Assert.assertNotNull("Data on demand for 'Alluser' failed to initialize correctly", dod.getRandomAlluser());
        Alluser obj = dod.getNewTransientAlluser(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Alluser' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Alluser' identifier to be null", obj.getId());
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
        Assert.assertNotNull("Expected 'Alluser' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDelete() {
        Alluser obj = dod.getRandomAlluser();
        Assert.assertNotNull("Data on demand for 'Alluser' failed to initialize correctly", obj);
        Integer id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Alluser' failed to provide an identifier", id);
        obj = alluserRepository.findOne(id);
        alluserRepository.delete(obj);
        alluserRepository.flush();
        Assert.assertNull("Failed to remove 'Alluser' with identifier '" + id + "'", alluserRepository.findOne(id));
    }
}
