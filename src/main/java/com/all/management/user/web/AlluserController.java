package com.all.management.user.web;
import com.all.management.user.model.Alluser;
import com.all.management.user.model.AlluserRepository;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

@RequestMapping("/allusers")
@Controller
public class AlluserController {

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> showJson(@PathVariable("id") Integer id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        try {
            Alluser alluser = alluserRepository.findOne(id);
            if (alluser == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<String>(alluser.toJson(), headers, HttpStatus.OK);
        } catch (Exception e) {
        	e.printStackTrace();
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	@RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        try {
            List<Alluser> result = alluserRepository.findAll();
            return new ResponseEntity<String>(Alluser.toJsonArray(result), headers, HttpStatus.OK);
        } catch (Exception e) {
        	e.printStackTrace();
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json, UriComponentsBuilder uriBuilder) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
            Alluser alluser = Alluser.fromJsonToAlluser(json);
            
            alluserRepository.save(alluser);
            RequestMapping a = (RequestMapping) getClass().getAnnotation(RequestMapping.class);
            headers.add("Location",uriBuilder.path(a.value()[0]+"/"+alluser.getId().toString()).build().toUriString());
            return new ResponseEntity<String>(headers, HttpStatus.CREATED);
        } catch (Exception e) {
        	e.printStackTrace();
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
            for (Alluser alluser: Alluser.fromJsonArrayToAllusers(json)) {
                alluserRepository.save(alluser);
            }
            return new ResponseEntity<String>(headers, HttpStatus.CREATED);
        } catch (Exception e) {
        	e.printStackTrace();
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	@RequestMapping(value = "/pwd/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updatePwdFromJson(@RequestBody String json, @PathVariable("id") Integer id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
            Alluser alluser = Alluser.fromJsonToAlluser(json);
            
            Alluser alluserupdate= alluserRepository.findOne(alluser.getId());
            if(alluserupdate != null) {
	            alluserupdate.setPasswordHash(alluser.getPasswordHash());
	            alluserupdate.setLastUpdateDate(GregorianCalendar.getInstance());
	            if (alluserRepository.save(alluserupdate) == null) {
	                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
	            }
            }
            else
            	return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            return new ResponseEntity<String>(headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json, @PathVariable("id") Integer id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
            Alluser alluser = Alluser.fromJsonToAlluser(json);
            
            Alluser alluserupdate= alluserRepository.findOne(alluser.getId());
            if(alluserupdate != null) {
	            alluserupdate.setLoginId(alluser.getLoginId());
	            alluserupdate.setEmail(alluser.getEmail());
	            alluserupdate.setLastUpdateDate(GregorianCalendar.getInstance());
	            if (alluserRepository.save(alluserupdate) == null) {
	                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
	            }
            }
            else
            	return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            return new ResponseEntity<String>(headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") Integer id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
            Alluser alluser = alluserRepository.findOne(id);
            if (alluser == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
            alluserRepository.delete(alluser);
            return new ResponseEntity<String>(headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	@Autowired
    AlluserRepository alluserRepository;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Alluser alluser, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, alluser);
            return "allusers/create";
        }
        uiModel.asMap().clear();
        alluserRepository.save(alluser);
        return "redirect:/allusers/" + encodeUrlPathSegment(alluser.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Alluser());
        return "allusers/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Integer id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("alluser", alluserRepository.findOne(id));
        uiModel.addAttribute("itemId", id);
        return "allusers/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("allusers", alluserRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / sizeNo, sizeNo)).getContent());
            float nrOfPages = (float) alluserRepository.count() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("allusers", alluserRepository.findAll());
        }
        addDateTimeFormatPatterns(uiModel);
        return "allusers/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Alluser alluser, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, alluser);
            return "allusers/update";
        }
        uiModel.asMap().clear();
        alluser.setLastUpdateDate(GregorianCalendar.getInstance());
        alluserRepository.save(alluser);
        return "redirect:/allusers/" + encodeUrlPathSegment(alluser.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Integer id, Model uiModel) {
        populateEditForm(uiModel, alluserRepository.findOne(id));
        return "allusers/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Alluser alluser = alluserRepository.findOne(id);
        alluserRepository.delete(alluser);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/allusers";
    }

	@RequestMapping(value = "/loginId/{loginId}", produces = MediaType.APPLICATION_XML_VALUE, method = RequestMethod.GET)
    public String retrieveUserByloginId(@PathVariable("loginId") String loginId, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        List<Alluser> result = alluserRepository.findByLoginIdIgnoreCase(loginId);
        if(result != null && !result.isEmpty() && result.size() == 1) {
        	uiModel.addAttribute("alluser", result.get(0));
        	uiModel.addAttribute("itemId", result.get(0).getId());
        }
        return "allusers/show";
    }
	
	@RequestMapping(value = "/loginId/{loginId}", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> retrieveUserByloginIdJson(@PathVariable("loginId") String loginId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        try {
        	List<Alluser> result = alluserRepository.findByLoginIdIgnoreCase(loginId);
            if (result == null || result.isEmpty()) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
            else {
            	Alluser alluser = result.get(0);
            	return new ResponseEntity<String>(alluser.toJson(), headers, HttpStatus.OK);
            }
        } catch (Exception e) {
        	e.printStackTrace();
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	@RequestMapping(value = "/json/create", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createAndFetchUserJson(@RequestBody String json, UriComponentsBuilder uriBuilder) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        try {
        	Alluser alluser = Alluser.fromJsonToAlluser(json);
            
        	List<Alluser> result = 
        			alluserRepository.findByLoginIdOrEmailIgnoreCase(alluser.getLoginId(), alluser.getEmail());
            if (result == null || result.isEmpty()) {
            	alluser.setLastUpdateDate(GregorianCalendar.getInstance());
            	alluser = alluserRepository.save(alluser);
            	return new ResponseEntity<String>(alluser.toJson(), headers, HttpStatus.CREATED);
            }
            else if (result.size() == 1){
            	// The loginId or email has been used
            	return new ResponseEntity<String>(result.get(0).toJson(), headers, HttpStatus.FOUND);
            }
            else {
            	// too many duplicated users
            	return new ResponseEntity<String>(headers, HttpStatus.MULTI_STATUS);
            }
        } catch (Exception e) {
        	e.printStackTrace();
        	
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("alluser_lastupdatedate_date_format", DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
    }

	void populateEditForm(Model uiModel, Alluser alluser) {
        uiModel.addAttribute("alluser", alluser);
        addDateTimeFormatPatterns(uiModel);
    }

	String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
	
	@InitBinder
	public void dataBinding(WebDataBinder binder) {
	    binder.setDisallowedFields(new String[] {"lastUpdateDate"});
	}
	
}
